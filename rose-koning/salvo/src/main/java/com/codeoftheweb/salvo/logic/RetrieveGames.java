package com.codeoftheweb.salvo.logic;

import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.repositories.GameRepository;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class RetrieveGames {

    private GameRepository gameRepository;

    public RetrieveGames(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Map<String, Object>> run() {
        return this.gameRepository.findAll().stream().map(game -> {
            Map<String, Object> gameMap = new TreeMap<>();

            gameMap.put("id", game.getId());
            gameMap.put("created", game.getCreation());
            if(game.getEndDate()!=null){
                gameMap.put("endDate", game.getEndDate());
            }
            List<Map<String, Object>> gamePlayersList = new ArrayList<>();

            Set<GamePlayer> gamePlayers = game.getGamePlayers();

            gamePlayers.forEach(gamePlayer -> {
                Map<String, Object> gamePlayerMap = new TreeMap<>();

                gamePlayerMap.put("id", gamePlayer.getId());

                Map<String, Object> playerMap = new TreeMap<>();

                playerMap.put("id", gamePlayer.getPlayer().getId());
                playerMap.put("username", gamePlayer.getPlayer().getUsername());

                gamePlayerMap.put("player", playerMap);

                gamePlayersList.add(gamePlayerMap);
            });

            gameMap.put("gamePlayers", gamePlayersList);

            return gameMap;
        }).collect(toList());
    }
}
