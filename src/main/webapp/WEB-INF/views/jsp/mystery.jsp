<%--
  Created by IntelliJ IDEA.
  User: t.kasko
  Date: 7.12.19
  Time: 13.25
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: t.kasko
  Date: 25.9.19
  Time: 21.53
  To change this template use File | Settings | File Templates.
--%>
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
<div class="jumbotron">
    <div class="container">
       <p>Copy and paste the amino acid sequence:</p>
       <form method="post">
           <label>
               <textarea name="text"  rows="12" placeholder="Enter all data from the amino acid sequence:"></textarea>
           </label>
           <input type="hidden" name="_csrf" value="{{_csrf.token}}" />
           <p><button type="submit">Get result</button> <button type="reset">Clean out</button></p>
       </form>
    </div>
</div>

<div class="container">
    <div class="row">
        <c:forEach items="${aminoAcidList}" var="amonoAcid">
            ${amonoAcid} —
        </c:forEach>
    </div>
</div>


</body>
</html>

