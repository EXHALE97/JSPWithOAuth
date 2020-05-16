<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<style>
    <%@include file="/resources/style.css" %>
</style>
<head>
    <title>Add Technology entry form</title>
</head>
<body>
    <jsp:include page="_menu.jsp"/>
    <jsp:include page="_header.jsp"/>

    <div class="centered">
        <h3>Add Technology Form</h3>
        <p class="error">${errorMessage}</p>
    </div>

    <form method="POST" action="${pageContext.request.contextPath}/addTechnology">
        <label> Name:
            <input type="text" name="name"/>
        </label>
        <label> Version for java 4:
            <input type="text" name="versionForJava4"/>
        </label>
        <label> Version for java 5:
            <input type="text" name="versionForJava5"/>
        </label>
        <label> Version for java 6:
            <input type="text" name="versionForJava6"/>
        </label>
        <label> Version for java 7:
            <input type="text" name="versionForJava7"/>
        </label>
        <label> Version for java 8:
            <input type="text" name="versionForJava8"/>
        </label>
        <label> Description
            <textarea name="description" cols="40" rows="4"></textarea>
        </label>
        <div class="centered">
            <input type="submit" value="Submit"/>
        </div>
    </form>
</body>
</html>
