package com.quest.service.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SessionState implements Serializable {
    private String playerName;
    private String currentQuestionId;

    private final Map<String, Integer> gamesByPlayer = new LinkedHashMap<>();

    public SessionState() {
        this.currentQuestionId = "start";
    }

    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCurrentQuestionId() {
        return currentQuestionId;
    }
    public void setCurrentQuestionId(String currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public void registerPlayer(String name) {
        gamesByPlayer.putIfAbsent(name, 0);
        this.playerName = name;
    }

    public int getGamesPlayed() {
        return gamesByPlayer.getOrDefault(playerName, 0);
    }

    public void incrementGamesPlayed() {
        int count = getGamesPlayed() + 1;
        gamesByPlayer.put(playerName, count);
    }

    public void resetForNewGame() {
        this.currentQuestionId = "start";
    }

    public Map<String, Integer> getGamesByPlayer() {
        return gamesByPlayer;
    }
}