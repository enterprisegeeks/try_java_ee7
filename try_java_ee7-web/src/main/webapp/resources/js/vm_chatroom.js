// chatroom.jsp Vue model用のjs
var authHeader = {"authorization":sessionStorage.token};
var main = new Vue({
    el : "#main",
    data : {
        open : false,
        roomId:null,
        roomName :"",
        chats:[],
        content:"",
        content_error_css:"",
        content_message:"",
        last:null
    },
    methods:{
        getChat:function(roomId, from){
            var $data = main.$data;
            var _from = from ? from : 0;
            $.ajax({url:"chatroom/chat", 
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

            $.ajax({url:"chatroom/chat", 
                    type:"PUT", 
                    data:JSON.stringify({roomId:$data.roomId, content:$data.content}),
                    dataType: 'json',
                    contentType: 'application/json',
                    headers: authHeader,
                    vm:function(){return $data;}
                    }
            ).success(function(data){
                $data.content = "";
                that.$options.methods.getChat($data.roomId, $data.last);
            });
        }
    }

});
var menu = new Vue({
    el:"#menu",
    data :{
        rooms:[]
    },
    created : function() {
        var that = this;
        $.ajax({url:"chatroom/rooms", 
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
            main.$data.roomName = room.name;
            main.$data.roomId = room.id;
            main.$data.open = true;
            main.$options.methods.getChat(room.id);
        }
    }
});