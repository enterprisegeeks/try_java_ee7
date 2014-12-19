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
        <script type='text/javascript' src="../resources/js/common.js"></script>
        
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
                    <input id="logout" type="button" class="btn btn-default" value="logout" />
                </div>
            </div>
        </div>
            <div class="container" id="comp">
                <div  class="col-md-3">
                    <p>select chat room.</p>
                    <ul id="menu" class="nav nav-pills nav-stacked" v-component="menu">
                        <li class="{{select ? 'active' : ''}}"
                            v-repeat="rooms" v-on="click:selectMenu($event,$index)">
                            <a href="#">{{name}}</a>
                        </li>
                    </ul>
                    
                </div>
                <div id="main" class="col-md-9" v-show="open"  v-component="main">
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
        
        <script type='text/javascript' src="../resources/js/vm_chatroom.js"></script>
    </body>
</html>
