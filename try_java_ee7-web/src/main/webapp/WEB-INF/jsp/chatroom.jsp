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
                        <input type="button" class="btn btn-default" value="logout" />
                    </div>
                </div>
            </div>
            <div class="container">
                <div id="menu" class="col-md-3">
                    <p>select chat room.</p>
                    
                    
                </div>
                <div id="main" class="col-md-9">
                    
                    
                </div>
            </div>
    </body>
</html>
