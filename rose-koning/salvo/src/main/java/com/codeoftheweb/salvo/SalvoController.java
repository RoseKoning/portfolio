package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.exceptions.AlreadyInUseException;
import com.codeoftheweb.salvo.exceptions.GameIsFullExeption;
import com.codeoftheweb.salvo.exceptions.NotYourTurnException;
import com.codeoftheweb.salvo.exceptions.ShipsAlreadyPlacedException;
import com.codeoftheweb.salvo.logic.*;
import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import com.codeoftheweb.salvo.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.AccessControlException;
import java.security.InvalidParameterException;
import java.util.*;

import static com.codeoftheweb.salvo.utils.ApiUtils.makeMap;

@RestController
public class SalvoController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/api/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return ApiUtils.isAuthenticated(authentication) ? authentication.getName() : null;
    }

    @RequestMapping(path = "/api/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String username, @RequestParam String password) {

        try {
            new RegisterPlayer(playerRepository, passwordEncoder).run(username, password);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (AlreadyInUseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(value = "/api/games", method = RequestMethod.GET)
    public List<Map<String, Object>> getGames(Authentication authentication) {
        return new RetrieveGames(gameRepository).run();
    }

    @RequestMapping(value = "/api/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {
        try {
            long gamePlayerId = new CreateGame(playerRepository, gameRepository, gamePlayerRepository, authentication).run();
            return new ResponseEntity<>(makeMap("gamePlayerId", gamePlayerId), HttpStatus.CREATED);
        } catch (AccessControlException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/game/{gameId}/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long gameId, Authentication authentication) {
        try {
            long gamePlayerId = new JoinGame(gameRepository, playerRepository, gamePlayerRepository, authentication, gameId).run();
            return new ResponseEntity<>(makeMap("gamePlayerId", gamePlayerId), HttpStatus.CREATED);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (AccessControlException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (GameIsFullExeption e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping("/api/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable long gamePlayerId, Authentication authentication) {
        try {
            Map<String, Object> gameView = new GetGameView(gameRepository, gamePlayerRepository, scoreRepository, authentication, gamePlayerId).run();
            return new ResponseEntity<>(gameView, HttpStatus.OK);
        } catch (AccessControlException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/games/players/{gamePlayerId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setShips(@PathVariable Long gamePlayerId, @RequestBody Set<Ship> ships, Authentication authentication) {
        try {
            new SetShips(authentication, gamePlayerRepository, gamePlayerId, ships, shipRepository).run();
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (AccessControlException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (ShipsAlreadyPlacedException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (InvalidParameterException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }


    }

    @RequestMapping(value = "/api/games/players/{gamePlayerId}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> setSalvoes(@PathVariable Long gamePlayerId, @RequestBody Salvo salvo, Authentication authentication) {

        try {
            new SetSalvoes(authentication, gamePlayerRepository, gamePlayerId, salvo, salvoRepository).run();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (AccessControlException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (NotYourTurnException e) {
            return new ResponseEntity<>(makeMap("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }

    }
}