<%--
  User: rajaonson
  Date: 21/06/2023
  Time: 01:22
--%>
<%@ page import="java.util.List" %>
<%@ page import="fr.lpacsid.chat.beans.User" %>

<%@ page contentType="text/html;charset=UTF-8" %>
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
                    <select name="selectedParticipant" class="form-select form-select-sm" aria-label="Small select example">
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
                    <button type="submit" name="addContact" class="btn btn-outline-primary">Ajouter</button>
                </div>
            </form>
        </div>
    </div>
</div>
