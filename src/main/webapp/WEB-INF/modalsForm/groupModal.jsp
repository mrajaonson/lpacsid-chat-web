<%--
  User: rajaonson
  Date: 03/08/2023
  Time: 21:57
--%>
<%@ page import="java.util.List" %>
<%@ page import="fr.lpacsid.chat.beans.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="groupModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <form action="Conversation" method="POST">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Créer un groupe de discussion</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal"
                  aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <%
              List<User> users = (List<User>) request.getSession().getAttribute("users");
              if (users != null) {
                for (User user: users) {
            %>
            <div class="form-check">
              <input class="form-check-input" name="groupSelectedUsers" type="checkbox" value="<%= user.getId() %>" id="<%= user.getUsername() %>">
              <label class="form-check-label" for="<%= user.getUsername() %>">
                <%= user.getUsername() %>
              </label>
            </div>
            <% } } %>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
          <button type="submit" name="createGroup" class="btn btn-outline-primary">Ajouter</button>
        </div>
      </form>
    </div>
  </div>
</div>
