<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Котячий квест — Ласкаво просимо</title>
</head>
<body>
  <h1>Ласкаво просимо до котячого квесту!</h1>

  <form action="${pageContext.request.contextPath}/start" method="post">
    <label for="playerName">Ваше ім'я:</label>
    <input type="text" id="playerName" name="playerName" required />

    <button type="submit">Почати гру</button>
  </form>
</body>
</html>