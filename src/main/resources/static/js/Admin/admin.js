$(document).ready(function(){
    //페이지를 불러왔을 때 카테고리 선택에 따라 화면 변경
    var category = $("#categorySelect option:selected").val();

    if(category=='volunteer'){
        $(".volunteer").show();
        $(".donation").hide();
    }else if(category=='donation'){
        $(".volunteer").hide();
        $(".donation").show();
    }

    $("select[name=categorySelect]").change(function(){
        //카테고리 선택을 수정할 때마다 화면 변경
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