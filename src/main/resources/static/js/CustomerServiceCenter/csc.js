$(document).ready(function () {
    // 검색 기능 관련 요소들 선택
    const $searchInput = $('#search-input'); // 검색 입력 필드
    const $searchBtn = $('#search-btn'); // 검색 버튼
    const $noResults = $('#no-results'); // 결과가 없을 때 표시할 메시지
    const $allCards = $('.memberCardList-item, .contentCardList-item, .productCardList-item'); // 검색할 모든 카드 요소들

    // 카테고리 버튼 및 콘텐츠 영역 선택
    const $categoryButtons = $('.item-button'); // 모든 카테고리 버튼 선택
    const $categoryContents = $('.category-content'); // 모든 카테고리 콘텐츠 섹션 선택

    // 모달 관련 요소들
    const $modal = $('#modal'); // HTML에 이미 존재하는 모달 요소 선택

    // 카드 검색 기능 - 검색어와 일치하는 카드를 필터링하여 표시
    function searchCards() {
        const query = $searchInput.val().trim().toLowerCase(); // 검색어를 가져와 소문자로 변환
        let hasResults = false; // 일치하는 결과가 있는지 확인할 플래그

        $allCards.each(function () {
            const $card = $(this); // 개별 카드를 선택
            const title = $card.find('.memberCard-title, .contentCard-title, .productCard-title').text().toLowerCase(); // 카드의 제목을 가져와 소문자로 변환

            // 검색어가 제목에 포함되어 있는 경우 해당 카드를 표시하고, 그렇지 않으면 숨김
            if (title.includes(query)) {
                $card.show();
                hasResults = true; // 일치하는 결과가 있으면 플래그를 true로 설정
            } else {
                $card.hide();
            }
        });

        // 일치하는 카드가 없으면 '검색 결과 없음' 메시지를 표시
        $noResults.toggle(!hasResults);
    }

    // 검색 버튼 클릭 및 'Enter' 키 이벤트 처리
    $searchBtn.on('click', searchCards); // 검색 버튼 클릭 시 검색 함수 실행
    $searchInput.on('keydown', function (event) {
        if (event.key === 'Enter') {
            searchCards(); // 'Enter' 키를 누를 때 검색 실행
        }
    });

    // 카테고리 버튼 클릭 시 해당 카테고리의 카드만 표시
    $categoryButtons.on('click', function () {
        const $button = $(this); // 클릭된 버튼을 선택

        $categoryButtons.removeClass('active'); // 모든 버튼에서 'active' 클래스 제거
        $button.addClass('active'); // 클릭된 버튼에 'active' 클래스 추가

        $categoryContents.hide(); // 모든 카테고리 콘텐츠 숨기기

        const category = $button.data('category'); // 클릭된 버튼의 카테고리 속성 가져오기
        $(`.category-content[data-category="${category}"]`).show(); // 선택된 카테고리만 표시
    });

    // 페이지 로드 시 '전체' 카테고리를 기본 활성화
    $('.item-button.all').click();

    // 카드 클릭 시 모달 표시
    $allCards.on('click', function (event) {
        event.preventDefault(); // 링크 클릭의 기본 동작을 방지

        const $card = $(this); // 클릭된 카드를 선택
        const title = $card.find('.productCard-title, .contentCard-title, .memberCard-title').text(); // 카드의 제목을 가져오기
        const description = $card.find('.product-desc, .content-desc, .member-desc').text(); // 카드의 설명을 가져오기

        $('#modal-title').text(title); // 모달 제목 설정
        $('#modal-description').text(description); // 모달 설명 설정

        $modal.show(); // 모달을 화면에 표시
    });

    // 모달 닫기 버튼 클릭 시 모달 닫기
    $('#close-btn').on('click', function () {
        $modal.hide(); // 모달을 숨김
    });

    // 모달 외부 클릭 시 모달 닫기
    $(window).on('click', function (event) {
        if ($(event.target).is($modal)) {
            $modal.hide(); // 모달 외부 클릭 시 모달 숨김
        }
    });
});