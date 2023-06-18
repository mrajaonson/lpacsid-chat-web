<%--
  User: rajaonson
  Date: 11/05/2023
  Time: 01:01
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style><jsp:include page="../css/bootstrap.min.css" /></style>
    <style><jsp:include page="../css/bootstrap-icons.css" /></style>
    <title>Auth</title>
</head>
<body>
<div class="container text-center" style="width: 30%">
    <form action="Login" method="GET">
        <button class="btn btn-outline-primary">
            Se connecter
        </button>
    </form>
    <form action="Signup" method="GET">
        <button class="btn btn-outline-primary">
            S'inscrire
        </button>
    </form>
</div>
</body>
</html>
