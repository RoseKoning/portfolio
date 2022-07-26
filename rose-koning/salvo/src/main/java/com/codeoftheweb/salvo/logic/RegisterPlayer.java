package com.codeoftheweb.salvo.logic;

import com.codeoftheweb.salvo.exceptions.AlreadyInUseException;
import com.codeoftheweb.salvo.models.Player;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterPlayer {
    private PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterPlayer(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String username, String password) throws AlreadyInUseException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Missing data");
        }

        if (playerRepository.findByUsername(username) != null) {
            throw new AlreadyInUseException("Name already in use");
        }

        playerRepository.save(new Player(username, passwordEncoder.encode(password)));
    }
}
