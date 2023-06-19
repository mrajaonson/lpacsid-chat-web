<%--
  User: rajaonson
  Date: 12/05/2023
  Time: 00:26
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.lpacsid.chat.beans.Conversation" %>
<%@ page import="fr.lpacsid.chat.beans.Message" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style><jsp:include page="../css/bootstrap.min.css"/></style>
    <style><jsp:include page="../css/bootstrap-icons.css"/></style>
    <script><jsp:include page="../js/bootstrap.bundle.min.js"/></script>
    <title>Home</title>
</head>
<body>
<div class="container-fluid h-100">
    <div class="row align-items-start h-100">
        <div class="col-2 h-100" style="background-color: #4d5154">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                Créer une discussion
            </button>

            <!-- Modal -->
            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                 aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <form action="Home" method="POST">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="staticBackdropLabel">Créer une discussion</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <input type="text" class="form-control" id="userSearch" name="userSearch"
                                       placeholder="Saisir un nom d'utilisateur">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                <button type="submit" name="addContact" class="btn btn-primary">Ajouter</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%-- Liste des conversations --%>
            <ul class="list-group list-group-flush">
            <%
                List<Conversation> conversations = (List<Conversation>) request.getSession().getAttribute("userConversations");
                assert conversations != null;
                for (Conversation conversation : conversations) {
            %>
                <form action="Home" method="post">
                    <input type="hidden" name="setCurrentConversationId" value="<%= conversation.getId() %>">
                    <button type="submit" class="btn" name="setCurrentConversationId"><%= conversation.getLabel() %></button>
                </form>
            <% } %>
            </ul>
        </div>
        <%-- Affichage messages --%>
        <div class="col h-100" style="background-color: #5c636a">
            <div class="">
                <% Conversation currentConversation = (Conversation) request.getSession().getAttribute("currentConversation"); %>
                <h2><%= currentConversation.getLabel() %></h2>
            </div>
            <div class="container text-center" style="width: 100%; height: 90%; overflow: auto">
                <div class="row">
                    <% for (Message currentMessage : currentConversation.getMessages()) { %>
                    <div>
                        <div class="card text-bg-primary mb-3 w-25">
                            <div class="card-body">
                                <h5 class="card-title"><%= currentMessage.getSenderName() %></h5>
                                <p class="card-text"><%= currentMessage.getContent() %></p>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
            <div style="position: absolute; bottom: 0; width: 50%">
                <form action="Home" method="post">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" id="messageInput" name="messageInput" placeholder="Message">
                        <button class="btn btn-outline-secondary" type="submit" name="sendMessage">Envoyer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
