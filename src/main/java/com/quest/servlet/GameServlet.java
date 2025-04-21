package com.quest.servlet;

import com.quest.service.GameService;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null) {
            // Якщо немає сесії – повертаємо на старт
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }

        GameService gameService = (GameService) session.getAttribute("gameService");
        SessionState state      = (SessionState) session.getAttribute("state");

        String playerName = req.getParameter("playerName");
        if (playerName != null) {
            state.setPlayerName(playerName);
        }

        // Отримуємо вибір
        String choice = req.getParameter("choice");
        if (choice != null) {
            // Опрацьовуємо вибір і оновлюємо поточне питання
            String nextId = gameService.processChoice(state, choice);
            state.setCurrentQuestionId(nextId);
        } else {
            // Перший раз на /play – почне з "start"
            state.setCurrentQuestionId("start");
        }

        session.setAttribute("state", state);

        String currId = state.getCurrentQuestionId();
        Question current = gameService.getQuestionById(currId);
        if (gameService.isEndState(currId)) {
            // Кінець гри – передаємо дані в result.jsp
            req.setAttribute("currentQuestion", current);
            req.setAttribute("state", state);
            req.getRequestDispatcher("/result.jsp")
                    .forward(req, resp);
        } else {
            // Продовжуємо гру – передаємо дані в game.jsp
            req.setAttribute("currentQuestion", current);
            req.getRequestDispatcher("/game.jsp")
                    .forward(req, resp);
        }
    }
}
