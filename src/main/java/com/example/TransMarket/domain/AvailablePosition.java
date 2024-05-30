package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class AvailablePosition {

    private String playerId;
    private String positionId;
    private char proficiency; //not null
    private char preference;    //not null

    public AvailablePosition(String playerId, String positionId, char proficiency, char preference) {
        this.playerId = playerId;
        this.positionId = positionId;
        this.proficiency = proficiency;
        this.preference = preference;
    }
}