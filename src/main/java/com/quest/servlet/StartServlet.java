package com.quest.servlet;

import com.quest.service.model.SessionState;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartServlet extends HttpServlet {
    private static final Logger LOG             = Logger.getLogger(StartServlet.class.getName());
    private static final String ATTR_STATE      = "state";
    private static final String REDIRECT_START  = "/start";
    private static final String URL_PLAY        = "/play";
    private static final String JSP_INDEX       = "/index.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            handleGet(req, resp);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error in StartServlet GET", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            handlePost(req, resp);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error in StartServlet POST", ex);
        }
    }

    private void handleGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        SessionState state = getOrCreateState(session);
        state.resetForNewGame();     // лишаємо історію ігор, скидаємо тільки запитання
        session.setAttribute(ATTR_STATE, state);
        forward(req, resp, JSP_INDEX);
    }

    private void handlePost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            redirect(resp, req.getContextPath() + REDIRECT_START);
            return;
        }
        SessionState state = getOrCreateState(session);

        String newName = req.getParameter("playerName");
        if (newName != null && !newName.isBlank()) {
            state.registerPlayer(newName);
            state.resetForNewGame();  // скидаємо поточне питання
        }

        session.setAttribute(ATTR_STATE, state);
        redirect(resp, req.getContextPath() + URL_PLAY);
    }

    private SessionState getOrCreateState(HttpSession session) {
        SessionState state = (SessionState) session.getAttribute(ATTR_STATE);
        if (state == null) {
            state = new SessionState();
        }
        return state;
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private void redirect(HttpServletResponse resp, String url)
            throws IOException {
        resp.sendRedirect(url);
    }
}