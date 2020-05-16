<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style>
    <%@include file="/resources/style.css" %>
</style>

<head>
    <title>Content</title>
</head>

<body>
    <jsp:include page="_menu.jsp"/>
    <jsp:include page="_header.jsp"/>

    <div class="centered">
        <h3>Java Enterprise Edition Technologies</h3>
        <p class="error">${errorMessage}</p>
    </div>

    <table border="1" width="90%">
        <tr>
            <th>Name</th>
            <th>Version for Java 4</th>
            <th>Version for Java 5</th>
            <th>Version for Java 6</th>
            <th>Version for Java 7</th>
            <th>Version for Java 8</th>
            <th>Description</th>
            <th colspan="2"></th>
        </tr>

        <c:forEach items="${list}" var="t">
            <tr>
                <td>${t.name}</td>
                <td>${t.versionForJava4}</td>
                <td>${t.versionForJava5}</td>
                <td>${t.versionForJava6}</td>
                <td>${t.versionForJava7}</td>
                <td>${t.versionForJava8}</td>
                <td>${t.description}</td>
                <td><a href="editTechnology?id=${t.id}">Edit</a></td>
                <td><a href="deleteTechnology?id=${t.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
