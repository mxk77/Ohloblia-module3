<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>🏁 Результат — Котячий квест</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
    <c:choose>
        <c:when test="${isWin}">
          <h1>🎉 Перемога, ${state.playerName}!</h1>
          <!-- victory image -->
          <img src="${pageContext.request.contextPath}/assets/img/victory.jpg"
               alt="Перемога" class="scene-img">
        </c:when>
        <c:otherwise>
          <h1>😿 Поразка, ${state.playerName}...</h1>
          <!-- defeat image -->
          <img src="${pageContext.request.contextPath}/assets/img/defeat.jpg"
               alt="Поразка" class="scene-img">
        </c:otherwise>
      </c:choose>

  <p class="result-text"><em>${currentQuestion.text}</em></p>
  <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/start'">
    🔁 Почати заново
  </button>
</body>
</html>