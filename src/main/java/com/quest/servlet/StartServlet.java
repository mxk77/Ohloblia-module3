package com.quest.servlet;

import com.quest.service.GameService;
import com.quest.service.model.SessionState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StartServlet extends HttpServlet {
    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionState state = new SessionState();

        HttpSession session = req.getSession(true);

        session.setAttribute("gameService", gameService);

        req.getRequestDispatcher("/index.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("playerName");

        HttpSession session = req.getSession(false);

        if (session != null && playerName != null && !playerName.isEmpty()) {
            SessionState state = (SessionState) session.getAttribute("state");
            state.setPlayerName(playerName);
            session.setAttribute("state", state);
        }

        resp.sendRedirect(req.getContextPath() + "/play");
    }
}
