package com.quest.servlet;

import com.quest.service.GameService;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GameServletTest {
    @InjectMocks
    private GameServlet servlet;

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse resp;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher gameDisp;
    @Mock private RequestDispatcher resultDisp;

    @BeforeEach
    void setUp() {
        when(req.getContextPath()).thenReturn("");
        when(req.getRequestDispatcher("/game.jsp")).thenReturn(gameDisp);
        when(req.getRequestDispatcher("/result.jsp")).thenReturn(resultDisp);
        lenient().when(req.getParameter("playerName")).thenReturn(null);
    }

    @Test
    void doPost_noSession_redirectsToStart() throws Exception {
        when(req.getSession(false)).thenReturn(null);

        servlet.doPost(req, resp);

        verify(resp).sendRedirect("/start");
        verifyNoMoreInteractions(gameDisp, resultDisp);
    }

    @Test
    void doPost_nullState_redirectsToStart() throws Exception {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(null);

        servlet.doPost(req, resp);

        verify(resp).sendRedirect("/start");
        verify(session, never()).setAttribute(eq("state"), any());
    }

    @Test
    void doPost_initialState_noChoice_forwardsToGame() throws ServletException, IOException {
        SessionState state = new SessionState();
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(state);
        when(req.getParameter("choice")).thenReturn(null);

        servlet.doPost(req, resp);

        // Перевірка форварду на game.jsp
        verify(gameDisp).forward(req, resp);
        // Поточне питання встановлено
        ArgumentCaptor<Question> qc = ArgumentCaptor.forClass(Question.class);
        verify(req).setAttribute(eq("currentQuestion"), qc.capture());
        assertEquals(GameService.ID_START, qc.getValue().getId());
    }

    @Test
    void doPost_withChoice_updatesState_andForwardsToGame() throws ServletException, IOException {
        SessionState state = new SessionState();
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(state);
        when(req.getParameter("choice")).thenReturn(GameService.OPT_GO_FOREST);
        // Початкове питання 'start'

        servlet.doPost(req, resp);

        // Після вибору questionId 'forest'
        assertEquals(GameService.ID_FOREST, state.getCurrentQuestionId());
        // Форвард на game.jsp
        verify(gameDisp).forward(req, resp);
    }

    @Test
    void doPost_endState_forwardsToResult_andSetsWinFalseOrTrue() throws ServletException, IOException {
        SessionState state = new SessionState();
        // Імітація кінцевого стану 'forestFish' (перемога)
        state.setCurrentQuestionId(GameService.ID_FOREST_FISH);
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(state);
        when(req.getParameter("choice")).thenReturn(null);

        servlet.doPost(req, resp);

        // Збільшено лічильник ігор
        assertEquals(1, state.getGamesPlayed());
        // isWin == true
        ArgumentCaptor<Boolean> wc = ArgumentCaptor.forClass(Boolean.class);
        verify(req).setAttribute(eq("isWin"), wc.capture());
        assertTrue(wc.getValue());
        // Форвард на result.jsp
        verify(resultDisp).forward(req, resp);
    }

    @Test
    void doPost_endState_winFalse_forCatchFail() throws ServletException, IOException {
        SessionState state = new SessionState();
        // Імітація кінцевого стану 'catchFail' (поразка)
        state.setCurrentQuestionId(GameService.ID_CATCH_FAIL);
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(state);
        when(req.getParameter("choice")).thenReturn(null);

        servlet.doPost(req, resp);

        assertEquals(1, state.getGamesPlayed());
        ArgumentCaptor<Boolean> wc = ArgumentCaptor.forClass(Boolean.class);
        verify(req).setAttribute(eq("isWin"), wc.capture());
        assertFalse(wc.getValue());
        verify(resultDisp).forward(req, resp);
    }
}