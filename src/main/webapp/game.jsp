<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Котячий квест — Гра</title>
</head>
<body>
  <h2>${currentQuestion.text}</h2>

  <c:forEach var="opt" items="${currentQuestion.options}">
    <form action="${pageContext.request.contextPath}/play" method="post" style="display:inline">
      <input type="hidden" name="choice" value="${opt.id}" />
      <button type="submit">${opt.text}</button>
    </form>
  </c:forEach>
</body>
</html>
