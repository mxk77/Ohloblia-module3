<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"       prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Котячий квест — Результат</title>
</head>
<body>
  <c:choose>
    <c:when test="
      ${empty currentQuestion.options
        and (
          currentQuestion.id == 'forestFish'
          or currentQuestion.id == 'catchSuccess'
          or currentQuestion.id == 'homeFeed'
          or currentQuestion.id == 'mushroomSafe'
          or currentQuestion.id == 'atticEat'
          or currentQuestion.id == 'gardenMilk'
          or currentQuestion.id == 'victoryHome'
        )
      }">
      <h1>Перемога, ${state.playerName}!</h1>
    </c:when>
    <c:otherwise>
      <h1>Поразка, ${state.playerName}...</h1>
    </c:otherwise>
  </c:choose>

  <!-- Текст саме цього фінального вузла -->
  <p><em>${currentQuestion.text}</em></p>

  <p>Ігор зіграно: ${state.gamesPlayed}</p>
  <p><a href="${pageContext.request.contextPath}/start">Почати заново</a></p>
</body>
</html>