<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>chatRoom(JAX-RS)</title>
        <meta charset="UTF-8"/>
        
        <link rel="stylesheet" href="../webjars/bootstrap/3.3.1/css/bootstrap.min.css" />
        <link rel="stylesheet" href="../resources/css/style.css" />
        <script type='text/javascript' src="../webjars/jquery/2.1.1/jquery.min.js"></script>
        <script type='text/javascript' src="../webjars/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        <script type='text/javascript' src="../webjars/vue/0.11.0/vue.min.js"></script>
        
    </head>
    <body>
        <div id="navbar" class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <div><span class="navbar-brand" >try chat</span></div>
                </div>
                <div class="navbar-header">
                    <div>
                        <img src="${it.gravatar}" class="header-thumb"/>

                        <span class="navbar-brand">
                        ${it.name}
                        </span>
                    </div>
                </div>
                <div class="navbar-right">
                    <input type="button" class="btn btn-default" value="logout" />
                </div>
            </div>
        </div>
            <div class="container">
                <div  class="col-md-3">
                    <p>select chat room.</p>
                    <ul id="menu" class="nav nav-pills nav-stacked">
                        <li class="{{select ? 'active' : ''}}"
                            v-repeat="rooms" v-on="click:selectMenu($event,$index)">
                            <a href="#">{{name}}</a>
                        </li>
                    </ul>
                    
                </div>
                <div id="main" class="col-md-9" v-show="open">
                    <h1>{{roomName}}</h1>
                    <div>
                        <div v-repeat="chats" class="media">
                            <a class="media-left" href="#">
                                <img src="{{gravatar}}"/>
                            </a>
                            <div class="media-body">
                                <h4 class="media-heading">{{name}}</h4>
                                <span>{{content}}</span>
                            </div>
                        </div>

                    </div>
                    <div>
                    <form id="chatform" v-on="submit:submit">
                        <div id="emailFg" class="form-group {{content_error_css}}">
                            <label for="email" class="control-label ">chat <span>{{content_message}}</span></label>
                            <textarea id="content" name="chat"  placeholder="chat" cols="6" rows="4"
                                      class="form-control" v-model="content"></textarea>
                        </div>
                        <input type="submit" class="btn btn-default" value="post" />
                    </form>
                    </div>
                    
                </div>
            </div>
        <script>
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
                                headers: authHeader }
                        ).success(function(data){
                            $data.content = "";
                            that.$options.methods.getChat($data.roomId, $data.last);
                        }).fail(function(jqXhr, status, thrown){
                                if (jqXhr.status !== 400) {
                                    alert(jqXhr.responseText);
                                } else {
                                    var data = jqXhr.responseJSON;
                                    data.messages.forEach(function(e){
                                        $data[e.key +"_message"] = e.message;
                                        if (e.key !== "error") {
                                            $data[e.key + "_error_css"] = "has-error";
                                        }
                                    });
                                }
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
                        main.$options.methods.getChat(room.id)
                    }
                }
            });
        </script>
    </body>
</html>
