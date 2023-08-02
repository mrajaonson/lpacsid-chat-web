<%--
  User: rajaonson
  Date: 12/05/2023
  Time: 00:26
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.lpacsid.chat.beans.Conversation" %>
<%@ page import="fr.lpacsid.chat.beans.Message" %>
<%@ page import="fr.lpacsid.chat.beans.User" %>
<%@ page import="java.util.Objects" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style><jsp:include page="../css/bootstrap.min.css"/></style>
    <style><jsp:include page="../css/bootstrap-icons.css"/></style>
    <script><jsp:include page="../js/bootstrap.bundle.min.js"/></script>
    <style>
        html, body {
            height: 100%;
            margin: 0;
        }

        .header {
            position: sticky;
            top: 0;
            padding: 10px;
        }

        .content {
            position: sticky;
            overflow-y: auto;
            height: 82%;
        }

        .footer {
            position: sticky;
            bottom: 0;
            padding: 10px;
        }

        .stickyOverflow {
            position: sticky;
            overflow-y: auto;
            top: 0;
            height: 100%
        }
    </style>
    <title>Home</title>
</head>
<body>

<nav class="navbar navbar-expand-md fixed-top bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Express Chat</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
            </ul>
            <form class="d-flex" method="post" action="Home">
                <input type="hidden" name="logout" value="logout">
                <button class="btn btn-outline-success" type="submit">Se déconnecter</button>
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid h-100" style="padding-top: 4.5rem">
    <div class="row h-100">
        <div class="col-2 stickyOverflow">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <h6 class="d-flex justify-content-between align-items-center mt-4 mb-1 text-body-secondary text-uppercase">
                        Canaux
                        <div class="col-2">
                            <button class="btn btn-outline-success btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">+</button>
                        </div>
                    </h6>
                    <ul class="list-group list-group-flush">
                    </ul>
                </li>
                <li class="list-group-item">
                    <h6 class="d-flex justify-content-between align-items-center mt-4 mb-1 text-body-secondary text-uppercase">
                        Groupes
                        <div class="col-2">
                            <button class="btn btn-outline-success btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">+</button>
                        </div>
                    </h6>
                    <ul class="list-group list-group-flush">
                    </ul>
                </li>
                <li class="list-group-item">
                    <h6 class="d-flex justify-content-between align-items-center mt-4 mb-1 text-body-secondary text-uppercase">
                        Messages privés
                        <%-- Button trigger modal --%>
                        <div class="col-2">
                            <button class="btn btn-outline-success btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#staticBackdrop">+</button>
                        </div>
                    </h6>
                    <%-- Liste des conversations --%>
                    <ul class="list-group list-group-flush">
                        <%
                            List<Conversation> conversations = (List<Conversation>) request.getSession().getAttribute("userConversations");
                            assert conversations != null;
                            Conversation getCurrentConversation = (Conversation) request.getSession().getAttribute("currentConversation");
                            for (Conversation conversation : conversations) {
                                String buttonClass = getCurrentConversation != null && Objects.equals(getCurrentConversation.getId(), conversation.getId()) ? "active" : "";
                        %>
                            <form action="Home" method="post" id="<%= conversation.getId() %>">
                                <input type="hidden" name="setCurrentConversationId" value="<%= conversation.getId() %>">
                                <button
                                        type="submit"
                                        class="btn list-group-item-action p-0 <%= buttonClass %>"
                                        name="setCurrentConversationId">
                                    <%= conversation.getLabel() %>
                                </button>
                            </form>
                        <% } %>
                    </ul>
                </li>
            </ul>

<%--            <script><jsp:include page="websocket.js" /></script>--%>
            <script>
                let ws;

                window.onload = function () {
                    connect()
                }

                function connect() {
                    const username = document.getElementById("userSessionName").textContent;

                    const host = document.location.host;
                    // const pathname = document.location.pathname;
                    const pathname = "/chat_war_exploded";

                    ws = new WebSocket("ws://" +host  + pathname + "/chat/" + username);

                    ws.onmessage = function(event) {
                        const messagesContainer = document.getElementById("messagesContainer");
                        const message = JSON.parse(event.data);

                        // Create a new div element with class "alert alert-light p-1"
                        const newDiv = document.createElement('div');
                        newDiv.className = 'alert alert-light p-1';

                        // Create a new p element with class "card-title"
                        const cardTitleElement = document.createElement('p');
                        cardTitleElement.className = 'card-title';

                        // Create a new strong element and set its content to "title"
                        const strongElement = document.createElement('strong');
                        strongElement.textContent = message.from;

                        // Create a new small element and set its content to " - subtitle"
                        const smallElement = document.createElement('small');
                        smallElement.textContent = ' - ' + message.from;

                        // Append the strong and small elements to the cardTitleElement
                        cardTitleElement.appendChild(strongElement);
                        cardTitleElement.appendChild(smallElement);

                        // Create a new p element with class "card-text" and set its content to "text"
                        const cardTextElement = document.createElement('p');
                        cardTextElement.className = 'card-text';
                        cardTextElement.textContent = message.content;

                        // Append the cardTitleElement and cardTextElement to the newDiv
                        newDiv.appendChild(cardTitleElement);
                        newDiv.appendChild(cardTextElement);

                        // Append the new div to the container
                        messagesContainer.appendChild(newDiv);

                        // Scroll the container to the bottom on page load
                        const container = document.getElementById('messagesContainer');
                        container.scrollTop = container.scrollHeight;

                        // Clear the input
                        const content = document.getElementById("messageInput")
                        content.value = ''
                    };
                }

                function send() {
                    const content = document.getElementById("messageInput").value;
                    const json = JSON.stringify({
                        "from": "",
                        "content": content
                    });

                    ws.send(json);
                }
            </script>

        </div>
        <%-- MODAL --%>
        <jsp:include page="chatModal.jsp" />
        <div class="col stickyOverflow">
            <%-- Affichage messages --%>
            <%  Conversation currentConversation = (Conversation) request.getSession().getAttribute("currentConversation");
                if (currentConversation != null) {
            %>
            <div class="header">
                <% User userSession = (User) request.getSession().getAttribute("userSession");%>
                <div style="display: none" id="userSessionName"><%= userSession.getLogin() %></div>
                <h4><%= currentConversation.getLabel() %></h4>
            </div>
            <div class="content p-2" id="messagesContainer">
                <% for (Message currentMessage : currentConversation.getMessages()) { %>
                <div class="alert alert-light p-1">
                    <p class="card-title">
                        <strong><%= currentMessage.getSenderName() %></strong>
                        <small> - <%= currentMessage.getDateSent() %></small>
                    </p>
                    <p class="card-text"><%= currentMessage.getContent() %></p>
                </div>
                <% } %>
            </div>
            <div class="footer">
<%--                <form action="Home" method="post">--%>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" id="messageInput" name="messageInput" placeholder="Message" autocomplete="off">
<%--                        <button class="btn btn-outline-secondary" type="submit" name="sendMessage">Envoyer</button>--%>
                        <button class="btn btn-outline-secondary" onclick="send();" name="sendMessage">Envoyer</button>
                    </div>
<%--                </form>--%>
            </div>
            <script>
                // Focus on the input
                const inputElement = document.getElementById('messageInput');
                inputElement.focus();

                // Scroll the container to the bottom on page load
                document.addEventListener('DOMContentLoaded', function() {
                    const container = document.getElementById('messagesContainer');
                    container.scrollTop = container.scrollHeight;
                });
            </script>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
