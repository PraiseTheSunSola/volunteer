$(function(){
    $("#search").on('keyup', search);
});

function search(){
    var value=$(this).val();
    $("#myHistory").filter(function(){
        $(this).toggle($(this).text().indexOf(value)!=-1);
    });
}