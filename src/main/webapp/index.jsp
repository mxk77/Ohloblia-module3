<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>😺 Котячий квест</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>
  <h1>😺 Ласкаво просимо до котячого квесту!</h1>
  <!-- welcome image -->
  <img src="${pageContext.request.contextPath}/assets/img/welcome.jpg"
       alt="Затишний дворик" class="scene-img">
  <details>
    <summary>Передісторія</summary>
        <p>
          Ви — котик-авантюрист, що колись жив у затишному подвір’ї,
          де завжди було вдосталь риби та молока. Але одного дня
          господар зник, а запаси скінчилися…
        </p>
        <p>
          Тепер вам доведеться самостійно шукати їжу по навколишньому світу:
          у лісі, провулках, на дахах будинків та в чужих садах.
          Кожний ваш вибір може привести до перемоги або поразки.
        </p>
  </details>

  <c:if test="${not empty state.gamesByPlayer}">
    <h3>Ваші котики:</h3>
    <ul>
      <c:forEach var="e" items="${state.gamesByPlayer.entrySet()}">
        <li>
          <button type="button"
                  onclick="document.getElementById('playerName').value='${e.key}'">
            🐾 ${e.key} (<strong>${e.value}</strong> ігор)
          </button>
        </li>
      </c:forEach>
    </ul>
  </c:if>

  <form action="${pageContext.request.contextPath}/start" method="post">
    <input type="text" id="playerName" name="playerName" placeholder="Ваш котячий нік" required>
    <button type="submit">▶️ Почати гру</button>
  </form>
</body>
</html>