
$(window).on("load",function(event){
    
    if (sessionStorage.token) {
        location.href ="chatroom.html"
    }   
})

// index.html Vue model用のjs
var model = new Vue({
    el : "#form",
    data : {
        name :"" ,
        email :"",
        name_message :"",
        email_message : "",
        name_error_css : "",
        email_error_css :"",
        error_message :""
    },
    methods:{
        join: function(e){
            e.preventDefault();
            var $data = this.$data;
            Object.keys($data).forEach(function(prop){
                if (prop.indexOf("_message")>0 || prop.indexOf("_error_css")>0) {
                    $data[prop] = "";
                }
            });
            $.ajax({url:"../rs/join", 
                    data:JSON.stringify({"name":$data.name, "email":$data.email}),
                    method:"POST", contentType: 'application/json',
                    vm:function(){ return $data} }
            ).success(function(data){
                sessionStorage.setItem("token", data.token);
                location.href ="chatroom.html"
            });
        }
    }
});