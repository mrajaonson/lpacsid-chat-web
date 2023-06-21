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
    <title>Inscription</title>
</head>
<body class="d-flex flex-column h-100">
<div class="container m-auto text-center" style="width: 500px">
    <form action="Signup" method="post">
        <h2 class="title">Inscription</h2>
        <div class="form-floating mb-2">
            <input type="text" class="form-control" id="username" name="username" required>
            <label for="username">Login</label>
        </div>
        <div class="form-floating mb-2">
            <input type="password" class="form-control" id="password" name="password" required>
            <label for="password">Mot de passe</label>
        </div>
        <div class="form-floating mb-2">
            <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm" required>
            <label for="passwordConfirm">Confirmation mot de passe</label>
        </div>
        <p class="text-danger"><%= request.getAttribute("errorSignup") != null ? request.getAttribute("errorSignup") : ""%></p>
        <p class="text-danger"><%= request.getAttribute("errorLogin") != null ? request.getAttribute("errorLogin") : ""%></p>
        <p class="text-danger"><%= request.getAttribute("errorPassword") != null ? request.getAttribute("errorPassword") : ""%></p>
        <button class="btn btn-outline-primary" type="submit" name="signup">
            Créer un compte
        </button>
    </form>
    <form action="Auth" method="GET">
        <button class="btn btn-outline-secondary">
            Retour
        </button>
    </form>
</div>

<%-- FOOTER --%>
<jsp:include page="footer.jsp" />
</body>
</html>
