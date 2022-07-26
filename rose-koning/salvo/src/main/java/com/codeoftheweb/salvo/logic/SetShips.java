package com.codeoftheweb.salvo.logic;

import com.codeoftheweb.salvo.exceptions.ShipsAlreadyPlacedException;
import com.codeoftheweb.salvo.models.GamePlayer;
import com.codeoftheweb.salvo.models.Ship;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.ShipRepository;
import com.codeoftheweb.salvo.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.security.AccessControlException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.codeoftheweb.salvo.utils.ApiUtils.makeMap;
import static com.codeoftheweb.salvo.utils.ApiUtils.isAuthenticated;

public class SetShips {
    private Authentication authentication;
    private GamePlayerRepository gamePlayerRepository;
    private long gamePlayerId;
    private Set<Ship> ships;
    private ShipRepository shipRepository;

    public SetShips(Authentication authentication, GamePlayerRepository gamePlayerRepository, long gamePlayerId, Set<Ship> ships, ShipRepository shipRepository) {
        this.authentication = authentication;
        this.gamePlayerRepository = gamePlayerRepository;
        this.gamePlayerId = gamePlayerId;
        this.ships = ships;
        this.shipRepository= shipRepository;
    }

    public void run() throws ShipsAlreadyPlacedException {
        if (!isAuthenticated(authentication)) {
            throw new AccessControlException("Player is not logged in");
        }

        Optional<GamePlayer> gamePlayer = gamePlayerRepository.findById(gamePlayerId);
        Set<Ship> presentShips = gamePlayer.get().getShips();
        if (presentShips.size() == 5) {
            throw new ShipsAlreadyPlacedException("Ships are already placed");
        }

        String username = gamePlayer.get().getPlayer().getUsername();
        if (username != authentication.getName()) {
            throw new AccessControlException("user not authorized");
        }

        if (!gamePlayer.isPresent()) {
            throw new InvalidParameterException("this gameplayer doens´t exist");
        }

        Set<Ship> shipSet = new HashSet<Ship>(ships);
        gamePlayer.get().setShips(shipSet);

        shipSet.forEach(ship -> {
            ship.setGamePlayer(gamePlayer.get());
            shipRepository.save(ship);
        });

        gamePlayerRepository.save(gamePlayer.get());
    }
}