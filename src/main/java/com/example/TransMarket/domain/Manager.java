package com.example.TransMarket.domain;

import lombok.Data;

@Data
public class Manager {

    private String managerId;
    private String managerName;
    private String clubId;

    public Manager(String managerId, String managerName, String clubId) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.clubId = clubId;
    }
}
