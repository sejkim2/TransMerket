package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Trophy {

    private String trophyId;
    private String trophyName;

    public Trophy(String trophyId, String trophyName) {
        this.trophyId = trophyId;
        this.trophyName = trophyName;
    }
}
