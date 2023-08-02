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
        const messagesContainer = document.getElementById("messagesContainer");
        const message = JSON.parse(event.data);
        const conversation = document.getElementById("currentConversationId").textContent;

        // send and read message only if in the same conversation
        if (parseInt(conversation) === message.conversation) {
            // Create a new div element with class "alert alert-light p-1"
            const newDiv = document.createElement('div');
            newDiv.className = 'alert alert-light p-1';

            // Create a new p element with class "card-title"
            const cardTitleElement = document.createElement('p');
            cardTitleElement.className = 'card-title';

            // Create a new strong element and set its content to "title"
            const strongElement = document.createElement('strong');
            strongElement.textContent = message.sender.username;

            // Create a new small element and set its content to " - subtitle"
            const smallElement = document.createElement('small');
            smallElement.textContent = ' - ' + message.date;

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
        }
    };
}

/**
 * Send message over websocket
 */
function send() {
    const content = document.getElementById("messageInput").value;
    const conversation = parseInt(document.getElementById("currentConversationId").textContent);

    const json = JSON.stringify({
        "conversation": conversation,
        "content": content
    });

    ws.send(json);
}

/**
 * Catch the Enter key press
 * @param event
 */
function runSendMessage(event) {
    if (event.keyCode === 13) {
        send()
    }
}
