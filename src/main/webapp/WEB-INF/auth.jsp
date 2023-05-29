<%--
  User: rajaonson
  Date: 11/05/2023
  Time: 01:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style><jsp:include page="../css/bootstrap.min.css" /></style>
    <style><jsp:include page="../css/bootstrap-icons.css" /></style>
    <title>Auth</title>
</head>
<body>
    <form class="container text-center" action="Auth" method="post" style="width: 30%">
        <h2 class="title">Connexion</h2>
        <div class="form-floating mb-2">
            <input type="login" class="form-control" id="login" name="login" required>
            <label for="login">Login</label>
        </div>
        <div class="form-floating mb-2">
            <input type="password" class="form-control" id="password" name="password" required>
            <label for="password">Mot de passe</label>
        </div>
        <button class="btn btn-primary" type="submit" name="auth">
            Se connecter
        </button>
    </form>
</body>
</html>
