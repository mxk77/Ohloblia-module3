package com.quest.servlet;

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
class StartServletTest {
    @InjectMocks
    private StartServlet servlet;

    @Mock private HttpServletRequest req;
    @Mock private HttpServletResponse resp;
    @Mock private HttpSession session;
    @Mock private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        // Підстановка RequestDispatcher і contextPath
        when(req.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);
        when(req.getContextPath()).thenReturn("");
    }

    @Test
    void doGet_withNoExistingState_createsNewStateAndForwards() throws ServletException, IOException {
        when(req.getSession(true)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(null);

        servlet.doGet(req, resp);

        // Додається новий SessionState із currentQuestionId == "start"
        ArgumentCaptor<SessionState> captor = ArgumentCaptor.forClass(SessionState.class);
        verify(session).setAttribute(eq("state"), captor.capture());
        SessionState created = captor.getValue();
        assertNotNull(created);
        assertEquals("start", created.getCurrentQuestionId());

        // Перенаправлення на index.jsp
        verify(dispatcher).forward(req, resp);
    }

    @Test
    void doGet_withExistingState_resetsQuestionAndForwards() throws ServletException, IOException {
        when(req.getSession(true)).thenReturn(session);
        SessionState existing = new SessionState();
        existing.setCurrentQuestionId("foo");
        when(session.getAttribute("state")).thenReturn(existing);

        servlet.doGet(req, resp);

        // currentQuestionId має бути скинутий
        assertEquals("start", existing.getCurrentQuestionId());
        verify(session).setAttribute("state", existing);
        verify(dispatcher).forward(req, resp);
    }

    @Test
    void doPost_withNoSession_redirectsToStart() throws IOException{
        when(req.getSession(false)).thenReturn(null);

        servlet.doPost(req, resp);
        verify(resp).sendRedirect("/start");
    }

    @Test
    void doPost_withNullStateAndName_registersAndRedirects() throws IOException {
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("state")).thenReturn(null);
        when(req.getParameter("playerName")).thenReturn("Alice");

        servlet.doPost(req, resp);

        // Реєстрація нового гравця
        ArgumentCaptor<SessionState> captor = ArgumentCaptor.forClass(SessionState.class);
        verify(session).setAttribute(eq("state"), captor.capture());
        SessionState s = captor.getValue();
        assertEquals("Alice", s.getPlayerName());
        assertEquals("start", s.getCurrentQuestionId());

        verify(resp).sendRedirect("/play");
    }

    @Test
    void doPost_withExistingStateWithoutName_doesNotRegisterButRedirects() throws IOException {
        when(req.getSession(false)).thenReturn(session);
        SessionState existing = new SessionState();
        existing.registerPlayer("Bob");
        existing.setCurrentQuestionId("foo");
        when(session.getAttribute("state")).thenReturn(existing);
        when(req.getParameter("playerName")).thenReturn(null);

        servlet.doPost(req, resp);

        // Перевіряємо, що state не змінився
        verify(session).setAttribute("state", existing);
        assertEquals("Bob", existing.getPlayerName());
        assertEquals("foo", existing.getCurrentQuestionId());

        verify(resp).sendRedirect("/play");
    }
}