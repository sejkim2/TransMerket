package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Match {

    private String home;
    private String away;
    private String matchDate;
    private String winner;

    public Match(String home, String away, String matchDate, String winner) {
        this.home = home;
        this.away = away;
        this.matchDate = matchDate;
        this.winner = winner;
    }
}
