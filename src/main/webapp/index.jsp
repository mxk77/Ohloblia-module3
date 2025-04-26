<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Котячий квест — Ласкаво просимо</title></head>
<body>
  <h1>Ласкаво просимо до котячого квесту!</h1>

  <!-- Список вже створених персонажів -->
  <c:if test="${not empty state.gamesByPlayer}">
    <h3>Ваші персонажі:</h3>
    <ul>
      <c:forEach var="entry" items="${state.gamesByPlayer.entrySet()}">
        <li>
          <!-- Кнопка вибору існуючого ніку -->
          <button type="button"
                  onclick="document.getElementById('playerName').value='${entry.key}'">
            ${entry.key} (зіграно ${entry.value})
          </button>
        </li>
      </c:forEach>
    </ul>
  </c:if>
  <form action="${pageContext.request.contextPath}/start" method="post">
    <label for="playerName">Ваше ім'я:</label>
    <input type="text" id="playerName" name="playerName" required />
    <button type="submit">Почати гру</button>
  </form>
</body>
</html>