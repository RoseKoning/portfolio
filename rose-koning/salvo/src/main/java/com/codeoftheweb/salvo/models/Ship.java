package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ship {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String type;
    @ElementCollection
    private List <String> location;


    public Ship() {
    }

    public Ship(String type,List<String> location) {
        this.type = type;
        this.setLocation(location);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;


    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer=gamePlayer;
    }

    public GamePlayer getGamePlayer(){
        return gamePlayer;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }
}

