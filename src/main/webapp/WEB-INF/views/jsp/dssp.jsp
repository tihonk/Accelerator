<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accelerator</title>
    <spring:url value="/resources/core/css/mystery.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <ul class="menu">
                <a class="navbar-brand" href="/">Accelerator</a>
                <!-- My cabinet button hided untill implementation of Sign In and Register functionality -->
                <!--
                <li><a href=#>My cabinet</a>
                    <ul class="submenu">
                        <li><a href=#>My account</a></li>
                        <li><a href=#>My history</a></li>
                        <li>
                            <form:form action="LogoutServlet" method="post">
                                <a class="signOutSubmenu" action="LogoutServlet" href="${pageContext.request.contextPath}/LogoutServlet">Sign Out</a>
                            </form:form>
                        </li>
                    </ul>
                    -->
                </li>
            </ul>
        </div>
    </div>
</nav>

<body>
    This is a DSSP
</body>
</html>

