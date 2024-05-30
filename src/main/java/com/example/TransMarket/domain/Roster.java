package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Roster {

    private String clubId;
    private String playerId;

    public Roster(String clubId, String playerId) {
        this.clubId = clubId;
        this.playerId = playerId;
    }
}
