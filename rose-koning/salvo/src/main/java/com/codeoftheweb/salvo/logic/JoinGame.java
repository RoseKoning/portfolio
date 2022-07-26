package com.codeoftheweb.salvo.logic;

import com.codeoftheweb.salvo.exceptions.GameIsFullExeption;
import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import com.codeoftheweb.salvo.utils.ApiUtils;
import org.springframework.security.core.Authentication;

import java.security.AccessControlException;
import java.security.InvalidParameterException;
import java.util.Set;

public class JoinGame {
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GamePlayerRepository gamePlayerRepository;
    private Authentication authentication;
    private long gameId;

    public JoinGame(GameRepository gameRepository, PlayerRepository playerRepository, GamePlayerRepository gamePlayerRepository, Authentication authentication, long gameId){
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gamePlayerRepository = gamePlayerRepository;
        this.authentication = authentication;
        this.gameId = gameId;
    }

    public long run() throws GameIsFullExeption {
        if (!ApiUtils.isAuthenticated(authentication)) {
            throw new AccessControlException("user not logged in");
        }
        Game game = gameRepository.getOne(gameId);
        if (game == null) {
            throw new InvalidParameterException("game doesn´t excist");
        }

        Set<GamePlayer> gamePlayers = game.getGamePlayers();
        if (gamePlayers.size() == 2) {
            throw new GameIsFullExeption("Game is full");
        }


        String username = authentication.getName();
        Player player = playerRepository.findByUsername(username);

        GamePlayer gamePlayer = new GamePlayer(game, player);
        gamePlayerRepository.save(gamePlayer);

        return gamePlayer.getId();

    }
}
