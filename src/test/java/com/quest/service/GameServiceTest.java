package com.quest.service;

import com.quest.service.model.SessionState;
import com.quest.service.model.Question;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService service;

    @BeforeEach
    void init() {
        service = new GameService();
    }

    @Test
    void allQuestionsLoaded() {
        // Переконаємося, що для кожного ID є об’єкт Question
        for (String qid : new String[]{
                GameService.ID_START,
                GameService.ID_FOREST,
                GameService.ID_FOREST_BERRIES,
                GameService.ID_FOREST_MUSHROOMS,
                GameService.ID_MUSHROOM_SAFE,
                GameService.ID_MUSHROOM_RISKY,
                GameService.ID_FOREST_FISH,
                GameService.ID_DEEP_FOREST,
                GameService.ID_WOLF_ENCOUNTER,
                GameService.ID_ALLEY,
                GameService.ID_CATCH_SUCCESS,
                GameService.ID_CATCH_FAIL,
                GameService.ID_STEAL_FISH,
                GameService.ID_HOME_MEOW,
                GameService.ID_ALLEY_ESCAPE,
                GameService.ID_ROOF,
                GameService.ID_PIGEON_CHASE,
                GameService.ID_ATTIC,
                GameService.ID_ATTIC_EAT,
                GameService.ID_ATTIC_EXIT,
                GameService.ID_GARDEN,
                GameService.ID_GARDEN_MILK,
                GameService.ID_GARDEN_HERBS,
                GameService.ID_VICTORY_HOME
        }) {
            Question q = service.getQuestionById(qid);
            assertNotNull(q, () -> "Питання за ID `" + qid + "` має бути завантажене");
            assertEquals(qid, q.getId());
        }
    }

    @Test
    void processChoiceStartToForest() {
        SessionState st = new SessionState();
        st.setCurrentQuestionId(GameService.ID_START);
        String next = service.processChoice(st, GameService.OPT_GO_FOREST);
        assertEquals(GameService.ID_FOREST, next);
        assertEquals(GameService.ID_FOREST, st.getCurrentQuestionId());
    }

    @Test
    void processChoiceThrowsOnInvalidOption() {
        SessionState st = new SessionState();
        st.setCurrentQuestionId(GameService.ID_START);
        assertThrows(IllegalArgumentException.class,
                () -> service.processChoice(st, "no_such_opt"));
    }

    @Test
    void deepSequenceLeadsToWolf() {
        SessionState st = new SessionState();
        // start -> forest
        service.processChoice(st, GameService.OPT_GO_FOREST);
        // forest -> mushrooms
        service.processChoice(st, GameService.OPT_PICK_MUSHROOMS);
        // mushrooms -> go deeper
        service.processChoice(st, GameService.OPT_EAT_SAFE);
        // deepForest -> hide
        st.setCurrentQuestionId(GameService.ID_DEEP_FOREST);
        String next = service.processChoice(st, GameService.OPT_HIDE);
        assertEquals(GameService.ID_WOLF_ENCOUNTER, next);
    }

    @Test
    void isEndStateIdentifiesTerminalQuestions() {
        // усі питання з порожнім options мають isEndState==true
        for (String terminal : new String[]{
                GameService.ID_FOREST_BERRIES,
                GameService.ID_MUSHROOM_RISKY,
                GameService.ID_FOREST_FISH,
                GameService.ID_WOLF_ENCOUNTER,
                GameService.ID_CATCH_SUCCESS,
                GameService.ID_CATCH_FAIL,
                GameService.ID_HOME_MEOW,
                GameService.ID_ALLEY_ESCAPE,
                GameService.ID_ATTIC_EAT,
                GameService.ID_ATTIC_EXIT,
                GameService.ID_GARDEN_MILK,
                GameService.ID_GARDEN_HERBS,
                GameService.ID_VICTORY_HOME
        }) {
            assertTrue(service.isEndState(terminal),
                    () -> terminal + " має бути кінцевим станом");
        }

        // а от стартове питання — не кінцевий стан
        assertFalse(service.isEndState(GameService.ID_START));
    }

    @Test
    void getQuestionByIdReturnsNullForUnknown() {
        assertNull(service.getQuestionById("unknown_id"),
                "Для невідомого ID має повертатися null");
    }
}