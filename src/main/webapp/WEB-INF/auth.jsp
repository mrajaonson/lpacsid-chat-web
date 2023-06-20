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
<body class="d-flex flex-column h-100">
<div class="container m-auto text-center" style="width: 500px">
    <h2>Express Chat</h2>
    <div class="col-lg-6 mx-auto">
        <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
            <form action="Login" method="GET">
                <button class="btn btn-outline-primary">
                    Se connecter
                </button>
            </form>
            <form action="Signup" method="GET">
                <button class="btn btn-outline-success">
                    S'inscrire
                </button>
            </form>
        </div>
    </div>
</div>

<%-- FOOTER --%>
<jsp:include page="footer.jsp" />
</body>
</html>
