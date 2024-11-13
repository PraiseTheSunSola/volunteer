$(document).ready(function(){
    var category = $("#categorySelect option:selected").val();

    if(category=='volunteer'){
        $(".volunteer").show();
        $(".donation").hide();
    }else if(category=='donation'){
        $(".volunteer").hide();
        $(".donation").show();
    }

    $("select[name=categorySelect]").change(function(){
        category = $("#categorySelect option:selected").val();

        if(category=='volunteer'){
            $(".volunteer").show();
            $(".donation").hide();
        }else if(category=='donation'){
            $(".volunteer").hide();
            $(".donation").show();
        }

    });

});