<%--
  User: rajaonson
  Date: 05/08/2023
  Time: 15:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div
        class="modal fade"
        id="discussionDeleteModal"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabindex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="Conversation" method="POST">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Supprimer la discussion</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Voulez-vous vraiment supprimer cette discussion ?</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="submit" name="deleteCurrentDiscussion" class="btn btn-outline-primary">Supprimer</button>
                </div>
            </form>
        </div>
    </div>
</div>
