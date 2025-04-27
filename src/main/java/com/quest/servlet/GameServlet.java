package com.quest.servlet;

import com.quest.service.GameService;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServlet extends HttpServlet {
    private static final Logger LOG            = Logger.getLogger(GameServlet.class.getName());
    private static final String ATTR_STATE     = "state";
    private static final String ATTR_QUESTION  = "currentQuestion";
    private static final String ATTR_WIN       = "isWin";
    private static final String REDIRECT_START = "/start";
    private static final String JSP_GAME       = "/game.jsp";
    private static final String JSP_RESULT     = "/result.jsp";

    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        // переадресація всіх GET запитів в єдиний обробник
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            handleRequest(req, resp);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error in GameServlet", ex);
        }
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (!isSessionValid(session, req, resp)) return;

        SessionState state = (SessionState) session.getAttribute(ATTR_STATE);
        updatePlayerName(req, state);
        updateCurrentQuestion(req, state);

        // зберігаємо оновлений стан
        session.setAttribute(ATTR_STATE, state);

        // готуємо дані для JSP
        Question current = gameService.getQuestionById(state.getCurrentQuestionId());
        req.setAttribute(ATTR_QUESTION, current);
        req.setAttribute(ATTR_STATE, state);

        // кінцева чи проміжна сторінка?
        if (gameService.isEndState(current.getId())) {
            boolean isWin = List.of(
                    GameService.ID_FOREST_FISH,
                    GameService.ID_CATCH_SUCCESS,
                    GameService.ID_HOME_MEOW,
                    GameService.ID_MUSHROOM_SAFE,
                    GameService.ID_ATTIC_EAT,
                    GameService.ID_GARDEN_MILK,
                    GameService.ID_VICTORY_HOME
            ).contains(current.getId());

            state.incrementGamesPlayed();
            session.setAttribute(ATTR_STATE, state);
            req.setAttribute(ATTR_WIN, isWin);
            forward(req, resp, JSP_RESULT);
        } else {
            forward(req, resp, JSP_GAME);
        }
    }

    private boolean isSessionValid(HttpSession session, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (session == null || session.getAttribute(ATTR_STATE) == null) {
            resp.sendRedirect(req.getContextPath() + REDIRECT_START);
            return false;
        }
        return true;
    }

    private void updatePlayerName(HttpServletRequest req, SessionState state) {
        String playerName = req.getParameter("playerName");
        if (playerName != null && !playerName.isBlank()) {
            state.setPlayerName(playerName);
        }
    }

    private void updateCurrentQuestion(HttpServletRequest req, SessionState state) {
        String choice = req.getParameter("choice");
        if (choice != null) {
            String nextId = gameService.processChoice(state, choice);
            state.setCurrentQuestionId(nextId);
        } else if (state.getCurrentQuestionId() == null) {
            // початковий стан, якщо ще не встановлено
            state.setCurrentQuestionId(GameService.ID_START);
        }
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
}