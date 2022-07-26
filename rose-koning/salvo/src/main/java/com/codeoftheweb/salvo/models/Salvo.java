package com.codeoftheweb.salvo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    private long turnTracker;

    @ElementCollection
    private List<String> location;

    public Salvo(){}

    public Salvo(long turnTracker, List<String> locations){
        this.setTurnTracker(turnTracker);
        this.setLocation(locations);
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer=gamePlayer;
    }

    @JsonIgnore
    public GamePlayer getGamePlayer(){
        return gamePlayer;
    }


    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }


    public long getTurnTracker() {
        return turnTracker;
    }

    public void setTurnTracker(long turnTracker) {
        this.turnTracker = turnTracker;
    }

}
