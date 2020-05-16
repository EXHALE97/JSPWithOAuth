<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="topnav">
    <li><a class="active" href="${pageContext.request.contextPath}/">Home</a></li>
    <li><a href="${pageContext.request.contextPath}/viewTechnologies">View all</a></li>
    <li><a href="${pageContext.request.contextPath}/addTechnology">Add</a></li>
    <li class="dropdown">
        <a href="javascript:void(0)" class="dropbtn", style="display: ${login_display}">Log in</a>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/google.auth" title="Google">
                Google<img src="<c:url value='/resources/pictures/google_plus_logo.png'/>" alt="">
            </a>
            <a class="" href="${pageContext.request.contextPath}/github.auth" title="Github">
                Github<img src="<c:url value='/resources/pictures/github_logo.png'/>" alt="">
            </a>
        </div>
    </li>
    <li class="dropdown" id="user_info">
        <img src="${picture_url}" alt=""/>
        <a href="javascript:void(0)" class="dropbtn">${user_name}</a>
        <div class="dropdown-content">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </li>
</ul>
