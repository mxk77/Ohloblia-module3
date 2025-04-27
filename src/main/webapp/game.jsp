<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>🎮 Гра — Котячий квест</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
  <h3>Гравець: 🐱 ${state.playerName}</h3>
  <h2>❓ ${currentQuestion.text}</h2>
  <div class="quest-options">
  <c:forEach var="opt" items="${currentQuestion.options}">
    <form action="${pageContext.request.contextPath}/play" method="post" style="display:inline">
      <input type="hidden" name="choice" value="${opt.id}"/>
      <button type="submit">▶️ ${opt.text}</button>
    </form>
  </c:forEach>
  </div>
</body>
</html>