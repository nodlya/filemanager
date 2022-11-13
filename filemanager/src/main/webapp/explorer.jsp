<%--
  Created by IntelliJ IDEA.
  User: Nodlya
  Date: 12.11.2022
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>File Manager</title>
</head>
<body>
<form method="post">
    <input type="submit" name="exit" value="Exit"/>
</form>
<hr>
<h3>${currentPath}</h3>
<hr>
<h4>${date}</h4>
<hr>
<a href="${contextPath}?path=${prevPath}">>Up</a><br>
<c:forEach var="directory" items="${directories}">
    <a href="${contextPath}?path=${directory.getAbsolutePath()}">${directory.getName()}/</a><br>
</c:forEach>
<c:forEach var="file" items="${files}">
    <a href="${contextPath}?path=${file.getAbsolutePath()}">${file.getName()}</a><br>
</c:forEach>
</body>
</html>