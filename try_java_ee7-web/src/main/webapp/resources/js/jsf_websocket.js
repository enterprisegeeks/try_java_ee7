
function startWebSocket() {
    //if (websocket) return;
    var loc = window.location;
    var websocket = new WebSocket("ws://" + loc.host + "/try_java_ee7-web/chat_notify");
    
    websocket.onopen = function(){ 
        console.log("open ws");
        websocket.send("");
    };

    websocket.onerror = function(event){ alert(event.data);};
    websocket.onmessage = function (){
        document.getElementById("postForm:hiddenButton").click();
    };
}


