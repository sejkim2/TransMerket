package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Position {
    private String positionId;
    private String positionName;

    public Position(String positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }
}
