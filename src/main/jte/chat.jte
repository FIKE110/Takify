@param String message

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
        <h2>Testing Streaming with Cohere Ai</h2>
    </div>

    <div class="chat-window" id="chatWindow">
        ${message}
    </div>

    <button id="sendbtn">Click me</button>

</div>
<script src="/resources/chat.js"></script>
</body>
</html>