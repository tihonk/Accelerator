<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<!-- Заголовок страницы, контейнер для других важных данных (не отображается) -->
<head>
    <spring:url value="/resources/core/css/dssp.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
    <!-- Заголовок страницы в браузере -->
    <title>Табы на CSS</title>
    <!-- Подключаем CSS -->
    <!-- Кодировка страницы -->
    <meta charset="UTF-8">
    <!-- Адаптив -->
    <meta name="viewport" content="width=device-width">
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
<!-- Отображаемое тело страницы -->
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <ul class="menu">
                <a class="navbar-brand" href="/">Accelerator</a>
            </ul>
        </div>
    </div>
</nav>
<div class="margin-top">
    <div class="container">
        <div class="wrapper">
            <!-- Контент -->
            <div class="content">
                <div class="tabs">
                    <nav class="tabs__items">
                        <a href="#tab_01" class="tabs__item"><span>Upload</span></a>
                        <a href="#tab_02" class="tabs__item"><span>Browse</span></a>
                        <a href="#tab_03" class="tabs__item"><span>URL</span></a>
                    </nav>
                    <div class="tabs__body">
                        <div id="tab_01" class="tabs__block">
                            <div id="upzone">
                                Drop Files Here
                            </div>

                            <!-- (C) UPLOAD STATUS -->
                            <div id="upstat"></div>

                            <!-- (D) FALLBACK -->
                            <form id="upform" action="dd-upload.php" method="post" enctype="multipart/form-data">
                                <input type="file" name="upfile" accept="image/*" required>
                                <input type="submit" value="Upload File">
                            </form>
                        </div>
                        <div id="tab_02" class="tabs__block">
                            <input type="file">
                        </div>
                        <div id="tab_03" class="tabs__block">
                            <input type="url">
                        </div>
                    </div>
                </div>
                <div class="text">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="script.js"></script>
</body>
</html>
