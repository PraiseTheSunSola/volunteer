document.addEventListener("DOMContentLoaded", () => {
    // 검색 기능 관련 요소들
    const searchInput = document.getElementById("search"); // 검색 입력 필드
    const searchBtn = document.getElementById("searchBT"); // 검색 버튼
    const noResults = document.getElementById("no-results"); // 결과가 없을 때 표시할 메시지
    const allCards = document.querySelectorAll(".myHistory"); // 검색할 모든 요소

    // 카드 검색 기능
    function searchCards() {
        const query = searchInput.value.trim().toLowerCase(); // 검색어를 가져와서 소문자로 변환
        let hasResults = false; // 일치하는 결과가 있는지 확인할 플래그

        // 각 카드의 제목이 검색어와 일치하는지 확인
        allCards.forEach(card => {
            const title = card.querySelector(".title").textContent.toLowerCase();
            if (title.includes(query)) {
                card.style.display = "flex"; // 검색어와 일치하면 카드 표시
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
});