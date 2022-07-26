package com.codeoftheweb.salvo.logic;

import com.codeoftheweb.salvo.exceptions.NotYourTurnException;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Salvo;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.SalvoRepository;
import com.codeoftheweb.salvo.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.security.AccessControlException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.codeoftheweb.salvo.utils.ApiUtils.makeMap;

public class SetSalvoes {
    private Authentication authentication;
    private GamePlayerRepository gamePlayerRepository;
    private long gamePlayerId;
    private Salvo salvo;
    private SalvoRepository salvoRepository;

    public SetSalvoes(Authentication authentication, GamePlayerRepository gamePlayerRepository, long gamePlayerId, Salvo salvo, SalvoRepository salvoRepository){
        this.authentication=authentication;
        this.gamePlayerRepository= gamePlayerRepository;
        this.gamePlayerId=gamePlayerId;
        this.salvo=salvo;
    }

    public void run() throws NotYourTurnException {
       if (!ApiUtils.isAuthenticated(authentication)) {
        throw new AccessControlException("Player is not logged in");
    }
    Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);

        if (!gamePlayer.isPresent()) {
        throw new IllegalArgumentException("this gameplayer doens´t exist");
    }

    String username = gamePlayer.get().getPlayer().getUsername();

        if (username != authentication.getName()) {
       throw new AccessControlException("user not authorized");
    }

    Set<Salvo> previousSalvoes = gamePlayer.get().getSalvoes();

        if (previousSalvoes.size() != 0) {
        for (Salvo previousSalvo : previousSalvoes) {
            if (previousSalvo.getTurnTracker() == salvo.getTurnTracker()) {
                throw new NotYourTurnException("wait until your turn");
            }
        }
    }

        gamePlayer.get().addSalvo(salvo);

        gamePlayerRepository.save(gamePlayer.get());
    }
}
