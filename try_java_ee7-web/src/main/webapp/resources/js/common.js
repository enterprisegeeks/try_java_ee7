$(window).on("popstate",function(event){
    
    if (!event.originalEvent.state){
        
        location.href="../rest/index.html"
    } else {
        location.href=event.originalEvent.state;
    }
  })