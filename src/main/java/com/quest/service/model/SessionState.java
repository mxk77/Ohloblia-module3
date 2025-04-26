package com.quest.service.model;

public class SessionState {
    private String playerName;        // ім’я гравця
    private String currentQuestionId; // поточне питання
    private int gamesPlayed;

    public SessionState(){
        this.currentQuestionId = "start";
        this.gamesPlayed = 0;
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

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /** Збільшує лічильник зіграних ігор на 1 */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void setGamesPlayed(int gamesPlayed){
        this.gamesPlayed=gamesPlayed;
    }

    /** Скидає стан для нової гри */
    public void resetForNewGame() {
        this.currentQuestionId = "start";
    }
}
