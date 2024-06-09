package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Club {

    private String clubId;
    private String clubName;
    private String leagueId;
    private int wins;

    public Club() {
    }

    public Club(String clubId, String clubName, String leagueId, int wins) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.leagueId = leagueId;
        this.wins = wins;
    }
}

