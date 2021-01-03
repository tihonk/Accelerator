<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <spring:url value="/resources/core/css/dssp.css" var="coreCss" />
    <spring:url value="/resources/core/css/main.css" var="mainCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/resources/core/js/dssp.js" var="coreJs" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
    <link href="${mainCss}" rel="stylesheet" />
    <title>DSSP</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
</head>

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
<div class="jumbotron h-100">
    <form class="tabs-wrapper" method="post">
        <input style="display: none" type="radio" name="radio" id="b1" checked>
        <input style="display: none" type="radio" name="radio" id="b2">

        <label for="b1" class="form-buttons" id="btn-1"></label>
        <label for="b2" class="form-buttons" id="btn-2"></label>
        <div class="break"></div> <!-- break new line-->
        <div class="tabs">
            <div class="tab tab-1">
                <span>Upload file of DSSP</span>
                <div id="drop-area">
                    <form class="my-form">
                        <div class="center back-white pt-4 h-35">
                            <p class="description">Choose a PDB file or drop it here</p>
                            <input type="file" id="fileElem" name="file" multiple onchange="handleFiles(this.files)">
                            <label class="button" for="fileElem">Select file</label>
                        </div>
                    </form>
                </div>
            </div>
            <div class="tab tab-2">
                <span class="header">Text from DSSP</span>
                <div class="break"></div> <!-- break new line-->
                <textarea class="text-area" name="text"></textarea>
            </div>
            <p class="center pt-1">
                <input type="hidden" name="_csrf" value="{{_csrf.token}}" />
                <button type="submit">Get result</button>
                <button type="reset">Clean out</button>
            </p>

        </div>
    </form>
    <script src="${coreJs}"></script>
</div>
</body>
</html>
