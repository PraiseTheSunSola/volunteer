document.addEventListener("DOMContentLoaded", function() {
    const categorySelect = document.getElementById("categorySelect");
    const startDateInput = document.getElementById("campaing-start");
    const endDateInput = document.getElementById("campaing-end");
    const submitButton = document.getElementById("submitBT");

    // 카테고리 변경 시 처리
    categorySelect.addEventListener("change", function() {
        const selectedCategory = categorySelect.options[categorySelect.selectedIndex].text;
        console.log("선택한 카테고리:", selectedCategory);

        // 카테고리에 따라 추가 동작을 할 수 있음
        // 예시: 선택된 카테고리에 따라 추가 입력 항목을 표시/숨기기
        if (selectedCategory === "서명") {
            // 서명 카테고리 관련 추가 작업
        } else if (selectedCategory === "동참") {
            // 동참 카테고리 관련 추가 작업
        } else if (selectedCategory === "지난 캠페인") {
            // 지난 캠페인 카테고리 관련 추가 작업
        }
    });

    // 시작일 변경 시 종료일을 시작일 이후로 자동 조정
    startDateInput.addEventListener("change", function() {
        endDateInput.setAttribute("min", startDateInput.value);
    });

    // 종료일이 시작일 이후인지 확인하는 함수
    function validateDates() {
        const startDate = new Date(startDateInput.value);
        const endDate = new Date(endDateInput.value);

        if (endDate <= startDate) {
            alert("종료일은 시작일 이후여야 합니다.");
            return false; // 종료일이 시작일 이전일 경우 제출을 막음
        }
        return true;
    }

    // 폼 제출 전 유효성 검사
    submitButton.addEventListener("click", function(event) {
        if (!validateDates()) {
            event.preventDefault(); // 날짜 유효성 검사를 통과하지 못하면 폼 제출을 막음
        } else {
            // 날짜를 ISO 형식으로 변환하여 서버에 전달
            const startDateISO = new Date(startDateInput.value).toISOString();
            const endDateISO = new Date(endDateInput.value).toISOString();

            // 서버에 전달할 데이터에 ISO 형식 날짜 추가
            const formData = new FormData();
            formData.append("campaignStart", startDateISO);
            formData.append("campaignEnd", endDateISO);

            // 폼 제출 처리 (서버로 데이터를 보낼 수 있음)
            fetch("/campaign/campaignWriteSave", {
                method: "POST",
                body: formData
            }).then(response => response.json())
              .then(data => console.log("서버 응답:", data))
              .catch(error => console.error("서버 오류:", error));
        }
    });
});
