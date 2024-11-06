$(document).ready(function () {
    /* 좌우 메뉴 표시/숨기기 설정 */
    const $nav = $('#nav');         // 왼쪽 메뉴 요소 선택
    const $adminNav = $('#admin-nav'); // 오른쪽 관리자 메뉴 요소 선택

    $(document).on('mousemove', function (e) {
        // 마우스 위치를 감지해 왼쪽 메뉴 표시 조건을 설정
        if (e.clientX < 70) {
            $nav.css('left', '0'); // 마우스가 왼쪽 가장자리로 이동 시 메뉴 표시
        } else if (e.clientX > 200) {
            $nav.css('left', '-200px'); // 마우스가 메뉴 영역을 벗어나면 메뉴 숨기기
        }

        // 마우스 위치를 감지해 오른쪽 메뉴 표시 조건을 설정
        if (e.clientX > window.innerWidth - 70) {
            $adminNav.css('right', '0'); // 마우스가 오른쪽 가장자리로 이동 시 관리자 메뉴 표시
        } else if (e.clientX < window.innerWidth - 200) {
            $adminNav.css('right', '-200px'); // 마우스가 메뉴 영역을 벗어나면 관리자 메뉴 숨기기
        }
    });

    /* 슬라이드 쇼 (캐러셀) 설정 */
    let currentSlide = 0; // 현재 슬라이드 인덱스 설정

    const $slides = $('.slide'); // 모든 슬라이드 요소 선택
    const $dots = $('.dot');     // 모든 페이지네이션 점 요소 선택

    // 특정 슬라이드로 이동하는 함수 정의
    function goToSlide(index) {
        $('.slides').css('transform', `translateX(-${index * 100}%)`); // 해당 슬라이드로 컨테이너 이동
        currentSlide = index; // 현재 슬라이드 인덱스 업데이트

        // 모든 페이지네이션 점에서 'active' 클래스 제거 후 현재 슬라이드 점에만 추가
        $dots.removeClass('active');
        $dots.eq(index).addClass('active');
    }

    // 자동으로 다음 슬라이드로 넘어가는 함수 정의
    function nextSlide() {
        // 현재 슬라이드 인덱스에 1을 추가하여 다음 슬라이드로 이동
        const nextIndex = (currentSlide + 1) % $slides.length;
        goToSlide(nextIndex); // 다음 슬라이드로 이동
    }

    // 페이지네이션 점 클릭 시 해당 슬라이드로 이동
    $dots.click(function () {
        const index = $(this).index(); // 클릭한 페이지네이션 점의 인덱스 가져오기
        goToSlide(index); // 해당 인덱스의 슬라이드로 이동
    });

    // 일정 시간(3초)마다 자동으로 슬라이드를 넘기기 위한 타이머 설정
    setInterval(nextSlide, 5000);

    // 슬라이드 클릭 시 해당 콘텐츠로 이동
    $slides.click(function () {
        const slideContentUrl = $(this).data('content-url'); // 클릭한 슬라이드의 URL 가져오기
        if (slideContentUrl) {
            window.location.href = slideContentUrl; // URL로 이동
        }
    });
});