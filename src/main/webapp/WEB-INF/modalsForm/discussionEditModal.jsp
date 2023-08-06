<%--
  User: rajaonson
  Date: 05/08/2023
  Time: 15:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div
        class="modal fade"
        id="discussionEditModal"
        data-bs-backdrop="static"
        data-bs-keyboard="false"
        tabindex="-1"
        aria-labelledby="staticBackdropLabel"
        aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="Conversation" method="POST">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Modifier le label de la discussion</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating mb-2">
                        <input
                                type="text"
                                class="form-control"
                                id="changeDiscussionLabel"
                                name="changeDiscussionLabel"
                                required
                                autocomplete="off">
                        <label for="changeDiscussionLabel">Label</label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="submit" name="changeDiscussionLabel" class="btn btn-outline-primary">Modifier</button>
                </div>
            </form>
        </div>
    </div>
</div>
