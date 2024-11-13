document.addEventListener("DOMContentLoaded", function() {
    const categorySelect = document.getElementById("categorySelect");

    categorySelect.addEventListener("change", function() {
        const selectedCategory = categorySelect.options[categorySelect.selectedIndex].text;
        console.log("선택한 카테고리:", selectedCategory);
    });
});
