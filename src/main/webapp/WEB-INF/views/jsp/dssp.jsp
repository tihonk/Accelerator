<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/core/materialize/css/materialize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/core/css/core.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/core/css/dssp.css">
    <title>Dssp</title>
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
            <div class="row">
                <ul class="tabs row tabs-center col s12 m6 push-m3">
                    <li class="tab col s6 tab-btn"><a class="active white-text" href="#tab-1">Upload</a></li>
                    <li class="tab col s6 tab-btn"><a class="white-text" href="#tab-2">Text</a></li>
                </ul>
            </div>
            <div class="mt-3">
                <div id="tab-1">
                    <div class="textarea z-depth-1 textarea-custom dashed-border" id="dropArea">
                        <p class="description white-text text-main">Choose a PDB file or drop it here</p>
                        <div class="row">
                            <div class="file-field input-field col s12 m4 push-m4">
                                <div class="btn purple darken-1">
                                    <span>File</span>
                                    <input type="file" id="fileElem" accept=".pdb, .txt"
                                           onchange="showFile(this.files[0]); droppedFileName.style.display = 'none';">
                                    <button type="reset" hidden id="clearForm"></button>
                                </div>
                                <div class="file-path-wrapper relative">
                                    <span class="dropped-file-name">Hello</span>
                                    <input class="file-path validate" type="text">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="tab-2">
                    <textarea class="textarea z-depth-1 textarea-custom" id="textarea" name="text"
                              placeholder="Put it here"></textarea>
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
    <script src="${pageContext.request.contextPath}/resources/core/js/dssp.js"></script>
</div>
</body>