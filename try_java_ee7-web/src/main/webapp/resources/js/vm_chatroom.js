$("#logout").click(function(){
    sessionStorage.removeItem("token");
    location.href = "index.html";
})
// TODO コンポーネント化
// chatroom.jsp Vue model用のjs
var authHeader = {"authorization":sessionStorage.token};
var Main = Vue.extend({
    //extendの場合、dataは関数。
    data : function(){return {
        open : false,
        roomId:null,
        roomName :"",
        chats:[],
        content:"",
        content_error_css:"",
        content_message:"",
        last:null}
    },
    created:function(){
        // イベント登録を行う。
        // メニュー選択 -> compで受け取り、initialChat という流れ。
        this.$on("initialChat", function(room){
            this.$data.roomName = room.name;
            this.$data.roomId = room.id;
            this.$data.open = true;
            this.getChat(room.id);
        })
        // websocket
        var loc = window.location;
        var websocket = new WebSocket("ws://" + loc.host + "/try_java_ee7-web/chat_notify");
        var that = this;
        websocket.onmessage = function (){
                that.getChat(that.$data.roomId, that.$data.last);
        }
    },
    methods:{
        getChat:function(roomId, from){
            var $data = this.$data;
            var _from = from ? from : 0;
            $.ajax({url:"../rs/chatroom/chat", 
                    method:"GET", 
                    data:{roomId:roomId, from:_from},
                    headers: authHeader }
            ).success(function(data){
                $data.last = data.last;
                // from指定ありなら追記、そうでないなら再作成。
                if (from) {
                    data.chats.forEach(function(e){ $data.chats.push(e); });
                } else {
                    $data.chats = data.chats;
                }
            });
        },
        submit:function(e) {
            e.preventDefault();
            var $data = this.$data;
            var that = this;
            $data.content_error_css = "";
            $data.content_message = "";

            $.ajax({url:"../rs/chatroom/chat", 
                    type:"PUT", 
                    data:JSON.stringify({roomId:$data.roomId, content:$data.content}),
                    dataType: 'json',
                    contentType: 'application/json;charset=UTF-8',
                    headers: authHeader,
                    vm:function(){return $data;}
                    }
            ).success(function(data){
                $data.content = "";
            });
        }
    }

});
var Menu = Vue.extend({
    // コンポーネントの場合、data, elは関数。
    data :function(){
        return {rooms:[]}
    },
    created : function() {
        var that = this;
        $.ajax({url:"../rs/chatroom/rooms", 
                method:"GET", 
                headers: authHeader }
        ).success(function(data){
            data.rooms.forEach(function(e){
                that.rooms.push({id:e.id, name:e.name, select:false});
            })         
        });
        
    },
    methods : {
        selectMenu : function(e, index) {
            e.preventDefault();
            var rooms = this.$data.rooms;
            for (var i = 0; i <rooms.length; i++) {
                    rooms[i].select = i === index;
            };

            var room = rooms[index];
            // イベント連携を行い、別のVMのデータ初期化を行う。
            this.$dispatch("selectMenu", room);
        }
    }
});
// 取りまとめコンポーネント。 
// menuとmainそれぞれで発生するイベントを仲介する役割を持つ。
// menuが直接mainを持つ構成でも良いと思われる。
var comp = new Vue({
    el:"#comp",
    components: {
        menu:Menu,
        main:Main
    },
    created : function() {
        // メニュー選択時に実行するイベント処理を記述する。
        this.$on("selectMenu", function(room){
            // 別のイベントを起動し、イベントを受け取ったオブジェクト側で処理させる。
            this.$broadcast("initialChat", room);
        })
    }
    
})