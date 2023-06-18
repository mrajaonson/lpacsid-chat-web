<%--
  User: rajaonson
  Date: 17/06/2023
  Time: 01:40
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style><jsp:include page="../css/bootstrap.min.css" /></style>
    <style><jsp:include page="../css/bootstrap-icons.css" /></style>
    <title>Connexion</title>
</head>
<body>
    <form class="container text-center" action="Login" method="post" style="width: 30%">
        <h2 class="title">Connexion</h2>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="username" name="username" required>
            <label for="username">Login</label>
        </div>
        <div class="form-floating mb-2">
            <input type="password" class="form-control" id="password" name="password" required>
            <label for="password">Mot de passe</label>
        </div>
        <button class="btn btn-outline-primary" type="submit" name="login">
            Se connecter
        </button>
    </form>
</body>
</html>
