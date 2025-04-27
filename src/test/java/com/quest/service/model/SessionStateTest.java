package com.quest.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SessionStateTest {

    private SessionState state;

    @BeforeEach
    void setUp() {
        state = new SessionState();
    }

    @Test
    void defaultCurrentQuestionIdIsStart() {
        assertEquals("start", state.getCurrentQuestionId(),
                "Початковий currentQuestionId має бути 'start'");
    }

    @Test
    void defaultPlayerNameIsNull() {
        assertNull(state.getPlayerName(),
                "До реєстрації гравця playerName має бути null");
    }

    @Test
    void defaultGamesPlayedForUnregisteredPlayerIsZero() {
        // без реєстрації getGamesPlayed поверне 0
        assertEquals(0, state.getGamesPlayed());
    }

    @Test
    void registerPlayerAddsEntryAndSetsName() {
        state.registerPlayer("Alice");
        assertEquals("Alice", state.getPlayerName());
        assertTrue(state.getGamesByPlayer().containsKey("Alice"),
                "Після registerPlayer має з’явитися ключ у gamesByPlayer");
        assertEquals(0, state.getGamesPlayed(),
                "Новий гравець починає з 0 ігор");
    }

    @Test
    void incrementGamesPlayedIncreasesOnlyCurrentPlayerCount() {
        state.registerPlayer("Bob");
        state.incrementGamesPlayed();
        state.incrementGamesPlayed();
        assertEquals(2, state.getGamesPlayed(),
                "Лічильник для Bob має бути 2 після двох викликів");
    }

    @Test
    void multiplePlayersMaintainIndependentCounts() {
        // Carol грає 1 гру
        state.registerPlayer("Carol");
        state.incrementGamesPlayed();
        assertEquals(1, state.getGamesPlayed());

        // Dave новий — має 0
        state.registerPlayer("Dave");
        assertEquals(0, state.getGamesPlayed());

        // Dave грає 1 гру
        state.incrementGamesPlayed();
        assertEquals(1, state.getGamesPlayed());

        // Повертаємось до Carol — бачимо його 1 гру
        state.registerPlayer("Carol");
        assertEquals(1, state.getGamesPlayed());
    }

    @Test
    void resetForNewGameOnlyResetsQuestionIdNotGames() {
        state.registerPlayer("Eve");
        state.incrementGamesPlayed(); // Eve:1
        state.setCurrentQuestionId("someQuestion");

        state.resetForNewGame();
        assertEquals("start", state.getCurrentQuestionId(),
                "resetForNewGame має скинути лише currentQuestionId");
        assertEquals(1, state.getGamesPlayed(),
                "resetForNewGame не має скидати лічильник ігор");
    }

    @Test
    void getGamesByPlayerReflectsUpdates() {
        Map<String,Integer> map = state.getGamesByPlayer();
        state.registerPlayer("Frank");
        state.incrementGamesPlayed(); // Frank:1

        assertEquals(1, map.get("Frank"),
                "getGamesByPlayer має відображати оновлення лічильника в реальному часі");
    }
}