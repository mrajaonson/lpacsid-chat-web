<%--
  User: rajaonson
  Date: 05/08/2023
  Time: 15:20
--%>
<%@ page import="fr.lpacsid.chat.beans.Conversation" %>
<%@ page import="fr.lpacsid.chat.beans.Participation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div
        class="modal fade"
        id="discussionMembersModal"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabindex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Liste des membres de la discussion</h1>
          <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close">
          </button>
        </div>
        <div class="modal-body">
          <%
            Conversation currentConversationModal = (Conversation) request.getSession().getAttribute("currentConversation");
            if (currentConversationModal != null) {
              for (Participation participation: currentConversationModal.getParticipations()) {
          %>
            <li class="list-group-item"><%= participation.getUser().getUsername() %></li>
          <% } } %>
        </div>
    </div>
  </div>
</div>
