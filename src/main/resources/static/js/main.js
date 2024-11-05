$(document).ready(function () {
    const $nav = $('#nav');

    // 마우스가 화면 왼쪽 끝으로 이동할 때 메뉴를 표시
    $(document).on('mousemove', function (e) {
        if (e.clientX < 70) { // 화면의 왼쪽 10px 이내에 마우스가 위치할 때
            $nav.css('left', '0'); // 메뉴 표시
        } else if (e.clientX > 200) { // 메뉴 너비(200px)를 벗어날 때
            $nav.css('left', '-200px'); // 메뉴 숨기기
        }
    });
});