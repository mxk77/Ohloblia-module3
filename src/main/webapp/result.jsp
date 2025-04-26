<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Котячий квест — Результат</title>
</head>
<body>
  <c:choose>
    <c:when test="${fn:length(currentQuestion.options) == 0
                   && (currentQuestion.id == 'forestFish'
                       || currentQuestion.id == 'catchSuccess'
                       || currentQuestion.id == 'homeFeed')}">
      <h1>Перемога, ${state.playerName}!</h1>
      <p>Вітаємо, ви успішно завершили квест.</p>
    </c:when>
    <c:otherwise>
      <h1>Поразка, ${state.playerName}...</h1>
      <p>Спробуйте ще раз!</p>
    </c:otherwise>
  </c:choose>

  <p>Ігор зіграно: ${state.gamesPlayed}</p>
  <a href="${pageContext.request.contextPath}/start">Почати заново</a>
</body>
</html>
