package com.quest.servlet;

import com.quest.service.GameService;
import com.quest.service.model.Question;
import com.quest.service.model.SessionState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class GameServlet extends HttpServlet {
    // 1) Ініціалізуємо сервіс один раз — ніколи не буде null
    private final GameService gameService = new GameService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Переадресуємо на doPost для уніфікованої обробки
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 2) Отримуємо сесію, не створюючи нову
        HttpSession session = req.getSession(false);
        // 3) Якщо сесії нема або в ній немає стану — повертаємо на старт
        if (session == null || session.getAttribute("state") == null) {
            resp.sendRedirect(req.getContextPath() + "/start");
            return;
        }

        // 4) Дістаємо стан і опрацьовуємо введене ім'я (якщо є)
        SessionState state = (SessionState) session.getAttribute("state");
        String playerName = req.getParameter("playerName");
        if (playerName != null && !playerName.isEmpty()) {
            state.setPlayerName(playerName);
        }

        // 5) Обробка вибору: якщо є choice, переходимо далі, інакше — починаємо з "start"
        String choice = req.getParameter("choice");
        if (choice != null) {
            String nextId = gameService.processChoice(state, choice);
            state.setCurrentQuestionId(nextId);
        } else if (state.getCurrentQuestionId() == null) {
            state.setCurrentQuestionId("start");
        }

        // 6) Оновлюємо стан у сесії
        session.setAttribute("state", state);

        // 7) Підтягуємо поточне питання та форвардимо на потрібну JSP
        Question current = gameService.getQuestionById(state.getCurrentQuestionId());
        req.setAttribute("currentQuestion", current);
        req.setAttribute("state", state);

        if (gameService.isEndState(current.getId())) {
            // оновлюємо лічильник для поточного гравця
            state.incrementGamesPlayed();       // Map.put(playerName, count) :contentReference[oaicite:9]{index=9}
            session.setAttribute("state", state);
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
        }
    }
}