package com.quest.service.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class SessionState {
    private String playerName;                // поточний гравець
    private String currentQuestionId;         // поточне питання

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

    /** Реєструє нового гравця, якщо він ще не існує */
    public void registerPlayer(String name) {
        gamesByPlayer.putIfAbsent(name, 0);
        this.playerName = name;
    }

    /** Повертає ігри для поточного гравця */
    public int getGamesPlayed() {
        return gamesByPlayer.getOrDefault(playerName, 0);
    }

    /** Збільшує лічильник ігор поточного гравця і оновлює мапу */
    public void incrementGamesPlayed() {
        int count = getGamesPlayed() + 1;
        gamesByPlayer.put(playerName, count);
    }

    /** Скидає гру до початку (не міняючи gamesByPlayer) */
    public void resetForNewGame() {
        this.currentQuestionId = "start";
    }

    /** Повертає всі зареєстровані ніки з порядком вставки */
    public Map<String, Integer> getGamesByPlayer() {
        return gamesByPlayer;
    }
}