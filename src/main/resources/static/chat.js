// const btn=document.getElementById("sendbtn")
// btn.addEventListener('click',e=>{
//     e.preventDefault()
//     console.log("Button is clicked")
//     getSSE("Hello")
// })


function getSSE(message){
    const eventSource = new EventSource(`http://localhost:8080/chat?chat=${message}`);
    const dot=document.getElementById(`dot-${currentAiChat}`)
    eventSource.onmessage = (event) => {
        try{
        const data = JSON.parse(event.data);
            if(data.type==="content-start" || data.type==="content-delta"){
                console.log(dot)
                dot.style.display='none'
                CurrentAiChatBox.innerHTML=
                    formatText(
                        CurrentAiChatBox.innerHTML+data.delta.message.content.text
                    )
              // speakText(CurrentAiChatBox.textContent)
            }
        }
        catch(e){
            console.log("Could not parse as json")
        }
        // You can process each event's data here
    };

    eventSource.onerror = (error) => {
        console.error('Error with the event stream:', error);
        eventSource.close();  // Close the connection on error
    };

    chatWindow.scrollTop = chatWindow.scrollHeight;
}

function getStream(message){
    fetch(`http://localhost:8080/chat?chat=${message}`)
    .then(
        res=>{
            const reader=res.body.getReader()
            let data=""
            const decoder=new TextDecoder()
            function read(){
                return reader.read().then(({done,value})=>{
                    console.log(value)
                    if(done){
                        console.log(data)
                        return
                    }

                    data+=decoder.decode(value,{stream:true})
                    return read()
                })

            }

           return read()
        }

    )
    .catch(err=>console.log("Error while fetching data"))
}

function speakText(message){
    if(window.SpeechSynthesisUtterance){
        const utterance=new SpeechSynthesisUtterance()
        utterance.text=message
        window.speechSynthesis.speak(utterance)
    }
}

function formatText(input) {
    return input.replace(/```(.*?)```/gs, '<pre>$1</pre>');
}