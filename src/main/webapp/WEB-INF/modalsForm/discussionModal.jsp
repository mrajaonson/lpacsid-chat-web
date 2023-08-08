<%--
  User: rajaonson
  Date: 21/06/2023
  Time: 01:22
--%>
<%@ page import="java.util.List" %>
<%@ page import="fr.lpacsid.chat.beans.User" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal fade"
     id="discussionModal"
     data-bs-backdrop="static"
     data-bs-keyboard="false"
     tabindex="-1"
     aria-labelledby="discussionModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="discussionModalLabel">Créer une discussion</h1>
                <button type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                        id="closeDiscussionModalButton">
                </button>
            </div>
            <div class="modal-body">
                <select id="discussionSelectedUsers" name="discussionSelectedUsers" class="form-select form-select-sm" aria-label="Small select example">
                    <option selected></option>
                    <%
                        List<User> users = (List<User>) request.getSession().getAttribute("users");
                        if (users != null) {
                            for (User user: users) {
                    %>
                        <option value="<%= user.getId() %>"><%= user.getUsername() %></option>"
                    <% } } %>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
                <button type="submit" name="createDiscussion" onclick="createDiscussion()" class="btn btn-outline-primary">Créer</button>
            </div>
        </div>
    </div>
</div>
