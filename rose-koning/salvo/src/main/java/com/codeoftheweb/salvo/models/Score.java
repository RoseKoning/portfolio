package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Score {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double points;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    public Score(){};

    public Score(Game game, Player player, double points){
        this.game = game;
        this.player = player;
        this.setPoints(points);
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public Game getGame() {
        return game;
    }
}
