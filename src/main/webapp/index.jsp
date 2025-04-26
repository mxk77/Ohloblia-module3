<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Котячий квест</title>
  <style>
    details {
      margin-bottom: 1em;
      font-family: sans-serif;
    }
    summary {
      font-weight: bold;
      cursor: pointer;
    }
  </style>
</head>
<body>
  <h1>Ласкаво просимо до котячого квесту!</h1>

  <details>
    <summary>Дізнатись передісторію...</summary>
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
    <h3>Ваші персонажі:</h3>
    <ul>
      <c:forEach var="entry" items="${state.gamesByPlayer.entrySet()}">
        <li>
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