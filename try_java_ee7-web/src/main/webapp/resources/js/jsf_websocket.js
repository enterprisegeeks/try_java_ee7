
function startWebSocket() {
    //if (websocket) return;
    var loc = window.location;
    var websocket = new WebSocket("ws://" + loc.host + "/try_java_ee7-web/chat_notify");
    websocket.onmessage = function (){
        document.getElementById("postForm:hiddenButton").click();
    };
}


