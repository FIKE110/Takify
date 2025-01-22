<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beautiful Chat Page</title>
    <link rel="stylesheet" href="/resources/styles.css">
</head>
<body>
<div class="chat-container">
    <div class="chat-header">
        <h2>Testing Streams with Cohere Ai and SpringAi</h2>
    </div>

    <div class="chat-window" id="chatWindow">
        <!-- Messages will be appended here -->
    </div>

    <div class="chat-input-container">
        <input type="text" id="messageInput" placeholder="Type your message..." />
        <button  onclick="sendMessage()">Send</button>
    </div>
</div>

<script src="/resources/script.js"></script>
<script src="/resources/chat.js"></script>
</body>
</html>
