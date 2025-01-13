const messageInput = document.getElementById("messageInput");
var chatWindow = document.getElementById("chatWindow");
var CurrentAiChatBox=null
var currentAiChat=0

messageInput.addEventListener('keydown',e=>{
    if(e.key==="Enter"){
        sendMessage()
    }
})


function sendMessage() {
    if (messageInput.value.trim() !== "") {
        const message=messageInput.value
        addNewChatBox(message,true)
        addNewChatBox("",false)
        messageInput.value = "";
        getSSE(message)
    }
}

function addNewChatBox(message,user){
    const messageElement = document.createElement("div");
    const preElement=document.createElement("pre")
    messageElement.classList.add("message", user ? "user" : "system");
    if(user) messageElement.textContent=message
    chatWindow.appendChild(messageElement);
    chatWindow.scrollTop = chatWindow.scrollHeight;
    if(!user) {
        currentAiChat++
        messageElement.innerHTML=`<div id='dot-${currentAiChat}'` + "class=\"chat-bubble dots-animation\">\n" +
            "            <div class=\"dots\">\n" +
            "                <span></span><span></span><span></span>\n" +
            "            </div>\n" +
            "        </div>"
        CurrentAiChatBox=messageElement
    }
}
