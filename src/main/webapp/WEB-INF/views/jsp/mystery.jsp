<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/core/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/core/css/core.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/core/css/mystery.css">
    <title>Document</title>
</head>
<nav>
    <div class="nav-wrap grey darken-4">
        <a href="#" class="logo">Accelerator</a>
    </div>
</nav>
<body>

<div class="bg-img"></div>
<div class="wrapper">
    <form class="" method="post">
        <div class="container z-depth-3 container-main relative">
            <div class="mt-3">
                <div id="tab-2">
                    <textarea class="textarea z-depth-1 textarea-custom" id="textarea" name="text"
                              placeholder="Put it here"></textarea>
                </div>
                <div class="range-wrapper">
                    <div>Max distance[Å]: </div>
                    <p class="range-field range-custom">
                        <input type="range" id="test5" min="0" max="10"/>
                    </p>
                    <div class="selectedElement">Element: <span id="selectedElement">H</span></div>
                </div>
                <div class="buttons center">
                    <button type="button" class="btn purple darken-1 btn-primary"
                            onclick="return getDsspResult(this.id, 'handler-url');">Get result
                    </button>
                    <button type="reset" class="btn purple darken-1 btn-primary">Clean out</button>
                </div>
            </div>
            <div class="container z-depth-3 result-container hidden">
                <div class="result-header">Result for <span id="result-header__fileName"></span></div>
                <div class="close-result">&#9932;</div>
                <textarea class="textarea result-content"></textarea>
            </div>
        </div>
    </form>
    <script src="${pageContext.request.contextPath}/resources/core/materialize/js/materialize.min.js"></script>
</div>
</body>