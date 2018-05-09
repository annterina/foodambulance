<%--
  Created by IntelliJ IDEA.
  User: annterina
  Date: 16.03.18
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Food ambulance</title>
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
  </head>

  <body>
  <nav class="navbar navbar-expand-lg navbar-light bg-info">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="index.jsp">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="resources/recipes.html">Recipes</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="resources/planner.html">Planner</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="resources/admin.html">Admin Console</a>
        </li>
      </ul>
      <form class="form-inline my-2 my-lg-0">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Sign In</button>
      </form>
    </div>
  </nav>

  <div class="container">
      <div id="app"></div>
  </div>

  <script src="resources/js/jquery-3.3.1.min.js"></script>
  <script src="resources/js/bootstrap.min.js"></script>
  <script src="resources/scripts/vue.js"></script>
  <script src="resources/scripts/app.js"></script>
  </body>
</html>
