// rest resouce用の共通js

// ajax共通エラーハンドリング
$( document ).ajaxError(function(event, jqXhr, settings, thrownError){
    if (jqXhr.status !== 400 || !settings.vm) {
        alert(jqXhr.responseText);
    } else {
        var data = jqXhr.responseJSON;
        var $data = settings.vm();
        data.messages.forEach(function(e){
            $data[e.key +"_message"] = e.message;
            if (e.key !== "error") {
                $data[e.key + "_error_css"] = "has-error";
            }
        });
    }
});
