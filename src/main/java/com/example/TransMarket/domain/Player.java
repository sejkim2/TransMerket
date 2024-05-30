package com.example.TransMarket.domain;

import lombok.Data;

@Data
/*
@Data has
Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode, Value
 */
public class Player {
    private String playerId;
    private String playerName;
    private int age;
    private String nationality;
    private String clubId;
//    private char importance;
    private Character importance; // Nullable


    public Player() {
    }

    public Player(String playerId, String playerName, int age, String nationality, String clubId) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.age = age;
        this.nationality = nationality;
        this.clubId = clubId;
    }

    public Player(String playerId, String playerName, int age, String nationality, String clubId, Character importance) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.age = age;
        this.nationality = nationality;
        this.clubId = clubId;
        this.importance = importance;
    }
}

