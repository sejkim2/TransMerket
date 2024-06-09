package com.example.TransMarket.dto;

import lombok.Data;

@Data
public class playerDTO {

    private String playerId;
    private String playerName;
    private int age;
    private String clubName;

    public playerDTO(String playerId) {
        this.playerId = playerId;
    }
}
