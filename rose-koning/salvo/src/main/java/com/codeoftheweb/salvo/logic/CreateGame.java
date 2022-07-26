package com.codeoftheweb.salvo.logic;


import com.codeoftheweb.salvo.models.Game;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;

import com.codeoftheweb.salvo.repositories.ScoreRepository;
import com.codeoftheweb.salvo.utils.ApiUtils;
import org.springframework.security.core.Authentication;

import java.security.AccessControlException;

public class CreateGame {

    private PlayerRepository playerRepository;
    private GameRepository gameRepository;
    private GamePlayerRepository gamePlayerRepository;
    private Authentication authentication;

    public CreateGame(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, Authentication authentication) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.gamePlayerRepository = gamePlayerRepository;
        this.authentication = authentication;
    }


    public long run() {

        if (!ApiUtils.isAuthenticated(authentication)){
            throw new AccessControlException("user not logged in");
        }

            String username = authentication.getName();
            Player player = playerRepository.findByUsername(username);

            Game game = new Game();
            gameRepository.save(game);

            GamePlayer gamePlayer = new GamePlayer(game, player);
            gamePlayerRepository.save(gamePlayer); //  gamePlayer.setId(...)

            return gamePlayer.getId();
        }

    }

