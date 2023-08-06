<%--
  User: rajaonson
  Date: 24/06/2023
  Time: 12:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="fr.lpacsid.chat.beans.Conversation" %>
<%@ page import="fr.lpacsid.chat.beans.Message" %>

<%  Conversation currentConversation = (Conversation) request.getSession().getAttribute("currentConversation");
    if (currentConversation != null) {
%>
<%-- conversation header --%>
<div class="header">
    <div style="display: none" id="currentConversationId"><%= currentConversation.getId() %></div>
    <div class="row justify-content-between">
        <div class="col">
            <h4><%= currentConversation.getLabel() %></h4>
        </div>
        <div class="col">
            <button
                    type="button"
                    class="btn btn-outline-success"
                    data-bs-toggle="modal"
                    data-bs-target="#discussionEditModal">
<%--                    data-bs-toggle="tooltip"--%>
<%--                    data-bs-placement="bottom"--%>
<%--                    data-bs-title="Modifier le label"--%>
                <i class="bi bi-pencil"></i>
            </button>
            <button
                    type="button"
                    class="btn btn-outline-success"
                    data-bs-toggle="modal"
                    data-bs-target="#discussionMembersModal">
<%--                    data-bs-toggle="tooltip"--%>
<%--                    data-bs-placement="bottom"--%>
<%--                    data-bs-title="Afficher les membres de la discussion"--%>
                <i class="bi bi-people-fill"></i>
            </button>
            <button
                    type="button"
                    class="btn btn-outline-success"
                    data-bs-toggle="modal"
                    data-bs-target="#discussionDeleteModal">
<%--                    data-bs-toggle="tooltip"--%>
<%--                    data-bs-placement="bottom"--%>
<%--                    data-bs-title="Supprimer la discussion"--%>
                <i class="bi bi-trash-fill"></i>
            </button>
        </div>
    </div>
</div>

<script>
    const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
    const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
</script>

<%-- conversation content --%>
<div class="content p-2" id="messagesContainer">
    <% for (Message currentMessage : currentConversation.getMessages()) { %>
    <div class="alert alert-light p-1">
        <p class="card-title">
            <strong><%= currentMessage.getSender().getUsername() %></strong>
            <small> - <%= currentMessage.getFormattedDate() %></small>
        </p>
        <p class="card-text"><%= currentMessage.getContent() %></p>
    </div>
    <% } %>
</div>
<%-- conversation footer --%>
<div class="conversation-footer">
    <div class="input-group mb-3">
        <input
                type="text"
                class="form-control"
                id="messageInput"
                name="messageInput"
                placeholder="Message"
                autocomplete="off"
                onkeypress="return runSendMessage(event)">
        <button class="btn btn-outline-secondary" onclick="send();" name="sendMessage">Envoyer</button>
    </div>
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
