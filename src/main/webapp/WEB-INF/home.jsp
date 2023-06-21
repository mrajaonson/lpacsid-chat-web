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

<div class="row w-100 h100">
    <div class="col-2">
        <div class="container text-center">
            <div class="row align-items-center">
                <div class="col">
                    <h4 class="m-4">Express Chat</h4>
                </div>
                <%-- Button trigger modal --%>
                <div class="col-2">
                    <button class="btn btn-outline-success" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">+</button>
                </div>
            </div>
        </div>
        <div class="accordion accordion-flush" id="accordionPanelsStayOpen">
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseOne" aria-expanded="true" aria-controls="panelsStayOpen-collapseOne">
                        Canaux
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseOne" class="accordion-collapse collapse show">
                    <div class="accordion-body">
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseTwo" aria-expanded="false" aria-controls="panelsStayOpen-collapseTwo">
                        Groupes
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseTwo" class="accordion-collapse collapse">
                    <div class="accordion-body">
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#panelsStayOpen-collapseThree" aria-expanded="false" aria-controls="panelsStayOpen-collapseThree">
                        Messages privés
                    </button>
                </h2>
                <div id="panelsStayOpen-collapseThree" class="accordion-collapse collapse">
                    <div class="accordion-body">
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
                </div>
            </div>
        </div>
        <%-- MODAL --%>
        <jsp:include page="chatModal.jsp" />
    </div>

    <div class="col">
        <%-- Affichage messages --%>
        <% Conversation currentConversation = (Conversation) request.getSession().getAttribute("currentConversation"); %>
        <h2><%= currentConversation.getLabel() %></h2>
        <div style="width: 800px">
            <% for (Message currentMessage : currentConversation.getMessages()) { %>
            <div>
                <div class="alert alert-light p-1">
                    <h5 class="card-title"><%= currentMessage.getSenderName() %></h5>
                    <p><%= currentMessage.getDateSent() %></p>
                    <p class="card-text"><%= currentMessage.getContent() %></p>
                </div>
            </div>
            <% } %>
        </div>
        <div style="position: absolute; bottom: 0; width: 800px">
            <form action="Home" method="post">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="messageInput" name="messageInput" placeholder="Message">
                    <button class="btn btn-outline-secondary" type="submit" name="sendMessage">Envoyer</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
