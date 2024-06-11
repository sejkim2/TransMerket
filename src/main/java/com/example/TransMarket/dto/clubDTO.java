package com.example.TransMarket.dto;

import lombok.Data;

@Data
public class clubDTO {
    private String clubId;
    private String clubName;
    private String leagueName;
    private int numOfPlayers;

    public clubDTO(String clubId) {
        this.clubId = clubId;
    }
}
