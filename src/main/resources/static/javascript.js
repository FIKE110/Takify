// Connect to the WebSocket server
const socket = new WebSocket('ws://localhost:8080/ws/message');

// Get DOM elements
const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');
const messages = document.getElementById('messages');

// Function to handle incoming messages
socket.onmessage = function(event) {
    let message = event.data;
    const messageElement = document.createElement('div');
    message=JSON.parse(message).message
    const content=JSON.parse(message)
    console.log("hello")
    messageElement.textContent = content.message;
    messages.appendChild(messageElement);
    messages.scrollTop = messages.scrollHeight; // Auto-scroll to the latest message
};

// Function to send messages
sendButton.addEventListener('click', function() {
    const message = messageInput.value;
    if (message) {
        const newMessage=JSON.stringify({
            "message":message,
            "timestamp":new Date().getDate(),
            "senderId":"2",
            "receiverId":"1"
        })
        socket.send(newMessage); // Send message via WebSocket
        messageInput.value = ''; // Clear input
    }
});

// Handle Enter key press
messageInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        sendButton.click();
    }
});
