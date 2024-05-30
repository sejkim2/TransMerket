package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Contact {

    private String playerId;
    private String clubId;
    private String startYear;
    private String endYear;
    private double weeklySalary;

    public Contact(String playerId, String clubId, String startYear, String endYear, double weeklySalary) {
        this.playerId = playerId;
        this.clubId = clubId;
        this.startYear = startYear;
        this.endYear = endYear;
        this.weeklySalary = weeklySalary;
    }
}
