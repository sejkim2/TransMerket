package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Career {

    private String playerId;
    private String trophyId;
    private String season;

    public Career(String playerId, String trophyId, String season) {
        this.playerId = playerId;
        this.trophyId = trophyId;
        this.season = season;
    }
}
