document.addEventListener("DOMContentLoaded", () => {
    // 검색 기능 관련 요소들
    const searchInput = document.getElementById("search-input"); // 검색 입력 필드
    const searchBtn = document.getElementById("search-btn"); // 검색 버튼
    const noResults = document.getElementById("no-results"); // 결과가 없을 때 표시할 메시지
    const allCards = document.querySelectorAll(".memberCardList-item, .contentCardList-item, .productCardList-item"); // 검색할 모든 카드 요소들

    // 카테고리 버튼 요소들
    const categoryButtons = document.querySelectorAll('.item-button'); // 모든 카테고리 버튼 선택
    const categoryContents = document.querySelectorAll('.category-content'); // 모든 카테고리 콘텐츠 섹션 선택

    // 카드 검색 기능
    function searchCards() {
        const query = searchInput.value.trim().toLowerCase(); // 검색어를 가져와서 소문자로 변환
        let hasResults = false; // 일치하는 결과가 있는지 확인할 플래그

        // 각 카드의 제목이 검색어와 일치하는지 확인
        allCards.forEach(card => {
            const title = card.querySelector(".memberCard-title, .contentCard-title, .productCard-title").textContent.toLowerCase();
            if (title.includes(query)) {
                card.style.display = "block"; // 검색어와 일치하면 카드 표시
                hasResults = true; // 일치하는 결과가 있으면 플래그를 true로 설정
            } else {
                card.style.display = "none"; // 검색어와 일치하지 않으면 카드 숨기기
            }
        });

        // 일치하는 카드가 없으면 '검색 결과 없음' 메시지 표시
        noResults.style.display = hasResults ? "none" : "block";
    }

    // 검색 버튼 클릭 및 'Enter' 키 이벤트
    searchBtn.addEventListener("click", searchCards); // 버튼 클릭 시 검색 실행
    searchInput.addEventListener("keydown", (event) => {
        if (event.key === "Enter") {
            searchCards(); // 'Enter' 키를 누를 때 검색 실행
        }
    });

    // 카테고리 버튼 클릭 처리
    categoryButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            // 모든 버튼에서 'active' 클래스 제거
            categoryButtons.forEach(function(btn) {
                btn.classList.remove('active');
            });

            // 클릭된 버튼에 'active' 클래스 추가
            button.classList.add('active');

            // 모든 카테고리 콘텐츠 숨기기
            categoryContents.forEach(function(content) {
                content.style.display = 'none';
            });

            // 클릭된 카테고리에 맞는 콘텐츠만 표시
            const category = button.getAttribute('data-category');
            const activeContent = document.querySelector(`.category-content[data-category="${category}"]`);
            if (activeContent) {
                activeContent.style.display = 'block';
            }
        });
    });

    // 페이지 로드 시 기본으로 '전체' 카테고리 활성화
    document.querySelector('.item-button.all').click();
});