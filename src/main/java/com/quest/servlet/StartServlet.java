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
            state = new SessionState();
        } else {
            state.resetForNewGame();  // лишаємо історію ігор :contentReference[oaicite:6]{index=6}
        }
        session.setAttribute("state", state);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }
        SessionState state = (SessionState) session.getAttribute("state");
        if (state == null) {
            state = new SessionState();
        }

        String newName = req.getParameter("playerName");
        if (newName != null && !newName.isBlank()) {
            // Реєструємо нового або повертаємо існуючого гравця
            state.registerPlayer(newName);
            state.resetForNewGame();            // скидаємо поточне питання
        }

        session.setAttribute("state", state);
        resp.sendRedirect(req.getContextPath() + "/play");
    }
}
