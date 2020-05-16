<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<style>
    <%@include file="/resources/style.css" %>
</style>
<head>
    <title>Edit Technology</title>
</head>
<body>
    <jsp:include page="_menu.jsp"/>
    <jsp:include page="_header.jsp"/>

    <div class="centered">
        <h3>Edit Technology Form</h3>
        <p class="error">${errorMessage}</p>
    </div>

    <c:if test="${not empty t}">
        <form method="POST" action="${pageContext.request.contextPath}/editTechnology">
            <input type="hidden" name="id" value="${t.id}"/>

            <label> Name:
                <input type="text" name="name" value="${ t.name != null ? t.name : "" }"/>
            </label>
            <label> Version for java 4:
                <input type="text" name="versionForJava4" value="${ t.versionForJava4 != null ? t.versionForJava4 : "" }"/>
            </label>
            <label> Version for java 5:
                <input type="text" name="versionForJava5" value="${ t.versionForJava5 != null ? t.versionForJava5 : "" }"/>
            </label>
            <label> Version for java 6:
                <input type="text" name="versionForJava6" value="${ t.versionForJava6 != null ? t.versionForJava6 : "" }"/>
            </label>
            <label> Version for java 7:
                <input type="text" name="versionForJava7" value="${ t.versionForJava7 != null ? t.versionForJava7 : "" }"/>
            </label>
            <label> Version for java 8:
                <input type="text" name="versionForJava8" value="${ t.versionForJava8 != null ? t.versionForJava8 : "" }"/>
            </label>
            <label> Description
                <textarea name="description" cols="40" rows="4">${t.description != null ? t.description : "" }</textarea>
            </label>
            <div class="centered">
                <input type="submit" value="Submit"/>
            </div>
        </form>
    </c:if>
</body>
</html>
