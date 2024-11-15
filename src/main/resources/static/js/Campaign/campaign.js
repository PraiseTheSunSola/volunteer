document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("search-input");
    const searchBtn = document.getElementById("search-btn");
    const noResults = document.getElementById("no-results");
    const allCards = document.querySelectorAll(".cardList-item");
    const categoryButtons = document.querySelectorAll('.item-button');

    // 카드 검색 기능
    function searchCards() {
        const query = searchInput.value.trim().toLowerCase();
        let hasResults = false;

        allCards.forEach(card => {
            const title = card.querySelector(".card-title").textContent.toLowerCase();
            if (title.includes(query)) {
                card.style.display = "block";
                hasResults = true;
            } else {
                card.style.display = "none";
            }
        });

        noResults.style.display = hasResults ? "none" : "block";
    }

    searchBtn.addEventListener("click", searchCards);
    searchInput.addEventListener("keydown", (event) => {
        if (event.key === "Enter") {
            searchCards();
        }
    });

    // 카테고리 버튼 클릭 처리
    categoryButtons.forEach(button => {
        button.addEventListener('click', () => {
            // 모든 카테고리 버튼에서 active 클래스 제거
            categoryButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');

            const category = button.getAttribute('data-category');

            // 모든 카드를 순회하면서 해당 카테고리의 카드만 표시
            allCards.forEach(card => {
                const cardCategory = card.getAttribute('data-category');
                if (category === 'all' || cardCategory === category) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });

            // 카테고리 필터 적용 후 결과가 없으면 메시지 표시
            const visibleCards = Array.from(allCards).some(card => card.style.display === 'block');
            noResults.style.display = visibleCards ? 'none' : 'block';
        });
    });
});
