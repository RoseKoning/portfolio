package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.*;



@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @CreatedDate
    private Date creationDate = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Ship> ships = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Salvo> salvoes = new HashSet<>();

    public GamePlayer() {
    }

    public GamePlayer(Game game, Player player) {
        this.setGame(game);
        this.setPlayer(player);
    }

    public Long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public Set<Ship> setShips(Set<Ship> ships) {
        return this.ships = ships;
    }


    public void addShip(Ship ship) {
        ship.setGamePlayer(this);
        ships.add(ship);
    }

    public void addSalvo(Salvo salvo) {
        salvo.setGamePlayer(this);
        salvoes.add(salvo);
    }


    public Set<Salvo> setSalvoes(Set<Salvo> salvoes) {
        return this.salvoes = salvoes;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

}


