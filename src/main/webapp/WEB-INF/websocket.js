let ws;

window.onload = function () {
    connect()
}

/**
 * Connect to websocket
 */
function connect() {
    const username = document.getElementById("userSessionName").textContent;

    const host = document.location.host;
    // const pathname = document.location.pathname;
    const pathname = "/chat_war_exploded";

    ws = new WebSocket("ws://" +host  + pathname + "/chat/" + username);

    ws.onmessage = function(event) {
        const websocketMessage = JSON.parse(event.data);
        const currentConversationId = document.getElementById("currentConversationId");

        // Message
        if (websocketMessage.type === "MESSAGE") {
            const messagesContainer = document.getElementById("messagesContainer");

            // send and read websocketMessage only if in the same conversation
            if (parseInt(currentConversationId.textContent) === websocketMessage.conversation) {
                const newMessageDiv = `
                    <div class="alert alert-light p-1">
                        <p class="card-title">
                            <strong>${websocketMessage.sender.username}</strong>
                            <small> - ${formatDate(websocketMessage.date)}</small>
                        </p>
                        <p class="card-text">${websocketMessage.content}</p>
                    </div>
                `
                messagesContainer.innerHTML += newMessageDiv

                // Scroll the container to the bottom on page load
                const container = document.getElementById('messagesContainer');
                container.scrollTop = container.scrollHeight;

                // Clear the input
                const content = document.getElementById("messageInput")
                content.value = ''
            }
        } else if (websocketMessage.type === "CHANNEL") {
            const channelUserList = document.getElementById("channelUserList")
            const channelDiv = `
                <input type="hidden" name="setCurrentConversationId" value="${websocketMessage.conversation}">
                <button
                        type="submit"
                        class="btn list-group-item-action p-0 <%= buttonClass %>"
                        name="setCurrentConversationId">
                        ${websocketMessage.conversationLabel}
                </button>
            `
            channelUserList.innerHTML += channelDiv

            const closeChannelModalButton = document.getElementById("closeChannelModalButton");
            closeChannelModalButton.click();

            const channelSelectedUsers = document.querySelectorAll('input[name="channelSelectedUsers"]');
            channelSelectedUsers.forEach(checkbox => {
                checkbox.checked = false;
            });

        }
    };
}

/**
 * Send message over websocket
 */
function sendMessage() {
    const content = document.getElementById("messageInput").value;
    const conversation = parseInt(document.getElementById("currentConversationId").textContent);

    if (content.length > 0) {
        const json = JSON.stringify({
            "type": "MESSAGE",
            "conversation": conversation,
            "content": content
        });
        ws.send(json);
    }
}

/**
 * Catch the Enter key press
 * @param event
 */
function runSendMessage(event) {
    if (event.keyCode === 13) {
        sendMessage()
    }
}

/**
 * Retourne la date en paramètre sous le format dd/MM/yyyy HH:mm
 * @param date
 */
function formatDate(date) {
    const dateTime = new Date(date);

    const day = String(dateTime.getDate()).padStart(2, '0');
    const month = String(dateTime.getMonth() + 1).padStart(2, '0');
    const year = dateTime.getFullYear();
    const hours = String(dateTime.getHours()).padStart(2, '0');
    const minutes = String(dateTime.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} ${hours}:${minutes}`;
}

function createChannel() {
    const participations = []

    const channelSelectedUsers = document.querySelectorAll('input[name="channelSelectedUsers"]');
    channelSelectedUsers.forEach(function(checkbox) {
        const isChecked = checkbox.checked;
        const value = checkbox.value;

        if (isChecked) {
            participations.push(value)
        }
    });

    if (participations.length > 0) {
        const json = JSON.stringify({
            "type": "CHANNEL",
            "participations": participations,
            "content": ""
        });
        ws.send(json);
    }
}
