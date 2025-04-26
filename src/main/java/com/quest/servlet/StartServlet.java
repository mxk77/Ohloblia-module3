package com.quest.servlet;

import com.quest.service.GameService;
import com.quest.service.model.SessionState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    // Єдиний екземпляр сервісу для всіх сеансів
    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        SessionState state = (SessionState) session.getAttribute("state");

        if (state == null) {
            // Перший візит — створюємо новий стан
            state = new SessionState();
        } else {
            // Рестарт гри — лишаємо gamesPlayed і playerName, тільки скидаємо питання
            state.resetForNewGame();  // має встановлювати currentQuestionId = "start"
        }

        session.setAttribute("state", state);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 1) Отримуємо існуючу сесію та стан
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        SessionState state = (SessionState) session.getAttribute("state");
        if (state == null) {
            state = new SessionState();
            state.setCurrentQuestionId("start");
        }

        // 2) Зчитуємо ім'я з форми
        String newName = req.getParameter("playerName");
        if (newName != null && !newName.isEmpty()) {
            String oldName = state.getPlayerName();

            // Якщо ім'я змінилося — скидаємо лічильник і поточний прогрес
            if (oldName == null || !oldName.equals(newName)) {
                state.setGamesPlayed(0);      // скинути кількість ігор
                state.resetForNewGame();      // скинути currentQuestionId → "start"
            }

            state.setPlayerName(newName);
        }

        // 3) Зберігаємо оновлений стан і переходимо до /play
        session.setAttribute("state", state);
        resp.sendRedirect(req.getContextPath() + "/play");
    }
}
