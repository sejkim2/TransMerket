package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class League {

    private String leagueId;
    private String leagueName;

    public League() {
    }

    public League(String leagueId, String leagueName) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
    }
}
