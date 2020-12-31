<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<!-- Заголовок страницы, контейнер для других важных данных (не отображается) -->
<head>
    <spring:url value="/resources/core/css/dssp.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/resources/core/js/dssp.js" var="coreJs" />
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
<form class="tabs-wrapper" method="post">
    <input style="display: none" type="radio" name="radio" id="b1" checked>
    <input style="display: none" type="radio" name="radio" id="b2">

    <label for="b1" class="form-buttons" id="btn-1"></label>
    <label for="b2" class="form-buttons" id="btn-2"></label>
    <div class="break"></div> <!-- break new line-->
    <div class="tabs">
        <div class="tab tab-1">
            <span class="header">Upload file of DSSP</span>
            <div id="drop-area">
                <form class="my-form">
                    <p class="description">Choose a PDB file or drop it here</p>
                    <input type="file" id="fileElem" name="file" multiple onchange="handleFiles(this.files)">
                    <label class="button" for="fileElem">Select file</label>
                </form>
            </div>
        </div>
        <div class="tab tab-2">
            <span class="header">Text from DSSP</span>
            <div class="break"></div> <!-- break new line-->
            <textarea class="text-area" name="text"></textarea>
        </div>
        <input type="submit" class="button submit" name="dssp" value="Send">

    </div>
</form>
<script src="${coreJs}"></script>
</body>
</html>
