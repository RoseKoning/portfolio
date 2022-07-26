package com.codeoftheweb.salvo.logic;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.ScoreRepository;
import org.springframework.security.core.Authentication;

import java.security.AccessControlException;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class GetGameView {
    private GameRepository gameRepository;
    private GamePlayerRepository gamePlayerRepository;
    private ScoreRepository scoreRepository;
    private Authentication authentication;
    private long gamePlayerId;

    public GetGameView(GameRepository gameRepository, GamePlayerRepository gamePlayerRepository,ScoreRepository scoreRepository, Authentication authentication, long gamePlayerId) {
        this.gameRepository = gameRepository;
        this.gamePlayerRepository = gamePlayerRepository;
        this.scoreRepository = scoreRepository;
        this.authentication = authentication;
        this.gamePlayerId = gamePlayerId;
    }

    public Map<String, Object> run() {

        Map<String, Object> gameView = new TreeMap<>();
        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);

        String username = gamePlayer.get().getPlayer().getUsername();

        if (username == authentication.getName()) {
            Game game = gamePlayer.get().getGame();

            long gameId = game.getId();

            gameView.put("id", gameId);
            gameView.put("created", game.getCreation());

            GamePlayer opponent = game.getGamePlayers().stream().filter(gamePlayer2 -> gamePlayer2.getId() != gamePlayerId).findAny().orElse(null);
            Set<GamePlayer> gamePlayers = gamePlayer.get().getGame().getGamePlayers();
            List<Map<String, Object>> gamePlayerList = gamePlayers.stream().map(gamePlayer1 -> {
                Map<String, Object> gamePlayerInfo = new TreeMap<>();
                gamePlayerInfo.put("id", gamePlayer1.getId());
                Map<String, Object> playerInfo = new TreeMap<>();
                playerInfo.put("id", gamePlayer1.getPlayer().getId());
                playerInfo.put("userName", gamePlayer1.getPlayer().getUsername());
                gamePlayerInfo.put("player", playerInfo);
                if (opponent != null && opponent.getId() < gamePlayer.get().getId()) {
                    playerInfo.put("turn", "odd");
                } else {
                    playerInfo.put("turn", "even");
                }
                return gamePlayerInfo;
            }).collect(toList());

            gameView.put("gamePlayers", gamePlayerList);

            List<String> opponentSalvoes = new ArrayList<>();
            if (opponent != null) {
                opponent.getSalvoes().forEach(salvo -> {
                    salvo.getLocation().forEach(location -> opponentSalvoes.add(location));
                });
            }


            Set<Ship> ships = gamePlayer.get().getShips();
            List<Map<String, Object>> shipList = ships.stream().map(ship -> {
                List<String> hits;
                Map<String, Object> shipInfo = new TreeMap<>();
                shipInfo.put("player", ship.getGamePlayer().getId());
                shipInfo.put("type", ship.getType());
                shipInfo.put("location", ship.getLocation());
                hits = ship.getLocation().stream().filter(opponentSalvoes::contains).collect(toList());
                shipInfo.put("hits", hits);
                if (hits.size() == ship.getLocation().size()) {
                    shipInfo.put("sunk", true);
                }
                return shipInfo;
            }).collect(toList());
            gameView.put("ships", shipList);


            Set<Salvo> salvoes = gamePlayer.get().getSalvoes();
            if (opponent != null) {
                Set<Ship> opponentShips = opponent.getShips();
                List<Map<String, Object>> opponentShipList = opponentShips.stream().map(ship -> {
                    List<String> hits = new ArrayList<>();
                    Map<String, Object> opppenentShipInfo = new TreeMap<>();
                    ship.getLocation().forEach(location -> {
                        salvoes.stream().filter(salvo -> salvo.getLocation().contains(location)).map(salvo -> location).forEach(hits::add);
                    });
                    opppenentShipInfo.put("hits", hits);

                    if (ship.getLocation().size() == hits.size()) {
                        opppenentShipInfo.put("type", ship.getType());
                    }
                    return opppenentShipInfo;
                }).collect(toList());
                gameView.put("opponentShips", opponentShipList);

                boolean gameOver = this.areAllOpponentShipsSunk(opponentShipList);

                if (gameOver) {
                    if (game.getEndDate() == null) {
                        game.setEndDate(new Date());

                        Score score = new Score(game,gamePlayer.get().getPlayer(), 2);
                        Score score1 = new Score(game, opponent.getPlayer(), 0);
                        scoreRepository.save(score);
                        scoreRepository.save(score1);
                        gameRepository.save(game);
                    }

                }
            }

            List<Salvo> salvoes2 = salvoes.stream().distinct().collect(toList());
            List<Map<String, Object>> salvoList = salvoes2.stream().map(salvo -> {
                Map<String, Object> salvoInfo = new TreeMap<>();
                salvoInfo.put("turn", salvo.getTurnTracker());
                salvoInfo.put("player", salvo.getGamePlayer().getId());
                salvoInfo.put("location", salvo.getLocation());
                return salvoInfo;
            }).collect(toList());
            gameView.put("salvoes", salvoList);
            ships.forEach(ship -> {
                ship.getLocation().forEach(location -> {
                    if (opponentSalvoes.contains(location)) {
                        int locationToRemove = opponentSalvoes.indexOf(location);
                        opponentSalvoes.remove(locationToRemove);
                    }
                });
            });

            boolean gameOver = this.areAllShipsSunk(shipList);
            if (gameOver) {
                if (game.getEndDate() == null) {
                    game.setEndDate(new Date());
                    Score score = new Score(game,gamePlayer.get().getPlayer(), 0);
                    Score score1 = new Score(game, opponent.getPlayer(), 2);
                    scoreRepository.save(score);
                    scoreRepository.save(score1);
                    gameRepository.save(game);
                }
            }

            if (game.getEndDate() != null) {
                gameView.put("endDate", game.getEndDate());

            }
            gameView.put("missedShots", opponentSalvoes);

            Set<Score> scores = gamePlayer.get().getPlayer().getScores();
            Score playerScore = scores.stream().filter(score -> score.getGame().getId() == gameId).findAny().orElse(null);

            if (playerScore != null) {
                gameView.put("score", playerScore.getPoints());
            }
            List<Long> allSalvoes = new ArrayList<>();
            game.getGamePlayers().forEach(gamePlayer1 -> gamePlayer1.getSalvoes().forEach(salvo -> allSalvoes.add(salvo.getTurnTracker())));
            if (allSalvoes.size() != 0) {
                long turnTracker = Collections.max(allSalvoes) + 1;
                gameView.put("turn", turnTracker);
            } else {
                long turnTracker = 1;
                gameView.put("turn", turnTracker);

            }

            return gameView;
        }

        throw new

                AccessControlException("Player is not authorized");
    }

    private boolean areAllOpponentShipsSunk(List<Map<String, Object>> opponentShipList) {
        List<Object> count = new ArrayList<>();
        opponentShipList.forEach(ship -> {
            if (ship.containsKey("type")) {
                count.add(ship.get("type"));
            }
        });
        if (count.size() == 5) {
            return true;
        } else
            return false;
    }

    private boolean areAllShipsSunk(List<Map<String, Object>> shipList) {
        List<Object> count = new ArrayList<>();
        shipList.forEach(ship -> {
            if (ship.containsKey("sunk")) {
                count.add(ship.get("sunk"));
            }
        });
        if (count.size() == 5) {
            return true;
        } else
            return false;
    }
}
