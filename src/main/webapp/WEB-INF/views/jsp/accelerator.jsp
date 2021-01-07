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
    <spring:url value="/resources/core/css/accelerator.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <spring:url value="/resources/core/js/accelerator.js" var="coreJs" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <ul class="menu">
                <a class="navbar-brand" href="/">Accelerator</a>
                <!--
                   MyCabinet button is hided untill implementation of Register and Sign In functionality
                  -->
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
                </li>
                -->
            </ul>
        </div>
    </div>
</nav>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <p>Copy and paste the PDB file:</p>
            <form method="post">
                <div class="col-md-4 divTextaria">
                    <label>
                        <textarea name="text"  rows="12" placeholder="Enter all dates from the PDB file:"></textarea>
                    </label>
                    <input type="hidden" name="_csrf" value="{{_csrf.token}}" />
                    <p><button type="submit">Get result</button> <button type="reset">Clean out</button></p>
                </div>
                <div class="col-md-3 selectElement">
                    <input type="checkbox" id="selectElement-input">
                    <label for="selectElement-input" id="lab" >
                        <div class="black-btn ">Select Element:</div>
                    </label>
                    <section id="periodic_table">
                        <div>
                            <div>
                                <div>H</div>
                                <div>Li</div>
                                <div>Na</div>
                                <div>K</div>
                                <div>Rb</div>
                                <div>Cs</div>
                                <div>Fr</div>
                            </div>
                            <div>
                                <div>Be</div>
                                <div>Mg</div>
                                <div>Ca</div>
                                <div>Sr</div>
                                <div>Ba</div>
                                <div>Ra</div>
                            </div>
                            <div>
                                <div>Sc</div>
                                <div>Y</div>
                                <div>X</div>
                                <div>X</div>
                            </div>
                            <div>
                                <div>Ti</div>
                                <div>Zr</div>
                                <div>Hf</div>
                                <div>Rf</div>
                            </div>
                            <div>
                                <div>V</div>
                                <div>Nb</div>
                                <div>Ta</div>
                                <div>Db</div>
                            </div>
                            <div>
                                <div>Cr</div>
                                <div>Mo</div>
                                <div>W</div>
                                <div>Sg</div>
                            </div>
                            <div>
                                <div>Mn</div>
                                <div>Tc</div>
                                <div>Re</div>
                                <div>Bh</div>
                            </div>
                            <div>
                                <div>Fe</div>
                                <div>Ru</div>
                                <div>Os</div>
                                <div>Hs</div>
                            </div>
                            <div>
                                <div>Co</div>
                                <div>Rh</div>
                                <div>Ir</div>
                                <div>Mt</div>
                            </div>
                            <div>
                                <div>Ni</div>
                                <div>Pd</div>
                                <div>Pt</div>
                                <div>Ds</div>
                            </div>
                            <div>
                                <div>Cu</div>
                                <div>Ag</div>
                                <div>Au</div>
                                <div>Rg</div>
                            </div>
                            <div>
                                <div>Zn</div>
                                <div>Cd</div>
                                <div>Hg</div>
                                <div>Cn</div>
                            </div>
                            <div>
                                <div>B</div>
                                <div>Al</div>
                                <div>Ga</div>
                                <div>In</div>
                                <div>Ti</div>
                                <div>Uut</div>
                            </div>
                            <div>
                                <div>C</div>
                                <div>Si</div>
                                <div>Ge</div>
                                <div>Sn</div>
                                <div>Rb</div>
                                <div>Fl</div>
                            </div>
                            <div>
                                <div>N</div>
                                <div>P</div>
                                <div>As</div>
                                <div>Sb</div>
                                <div>Bi</div>
                                <div>Uup</div>
                            </div>
                            <div>
                                <div>O</div>
                                <div>S</div>
                                <div>Se</div>
                                <div>Te</div>
                                <div>Po</div>
                                <div>Lv</div>
                            </div>
                            <div>
                                <div>F</div>
                                <div>Cl</div>
                                <div>Br</div>
                                <div>I</div>
                                <div>At</div>
                                <div>Uus</div>
                            </div>
                            <div>
                                <div>He</div>
                                <div>Ne</div>
                                <div>Ar</div>
                                <div>Kr</div>
                                <div>Xe</div>
                                <div>Rn</div>
                                <div>Uuo</div>
                            </div>
                        </div>
                        <div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                            <div>
                                <div>La</div>
                                <div>Ac</div>
                            </div>
                        </div>
                    </section>
                    <div class="filter"></div>
                    <input type="text" class="selectedElement" name="element">
                </div>
                <div class="col-md-4 divSelect">
                    <p class="polzun">The maximum distance to the atom:
                        <input  type="range" min="0" max="10" name="maxCount" onchange="document.getElementById('rangeValue').innerHTML = this.value;" >
                        <span id="rangeValue">5</span>Å
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="container">
</div>
    ${text}
</div>
<script src="${coreJs}"></script>
</body>
</html>
