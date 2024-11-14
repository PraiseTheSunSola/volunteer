$(document).ready(function() {
    // 활동 요일 필터링
    function setWeekday() {
        const weekdays = ["mon", "tus", "wed", "thurs", "fri", "sat", "sun"];
        return weekdays.filter(day => $("#" + day).is(":checked"));
    }

    // 성인-미성년 가능 여부 설정
    function setAgeOption() {
        const adultOnly = $("#adultOnly").is(":checked");
        const youthOnly = $("#youthOnly").is(":checked");
        const $ageOptionHidden = $("#ageOptionHidden");

        if (adultOnly && youthOnly) {
            $ageOptionHidden.val("both");
        } else if (adultOnly) {
            $ageOptionHidden.val("adultOnly");
        } else if (youthOnly) {
            $ageOptionHidden.val("youthOnly");
        } else {
            $ageOptionHidden.val("");
        }
    }

    // 최소 입력 필수 조건 체크
    function validateForm() {
        let filledCount = 0;

        const $fields = $('input[type="checkbox"]:checked, input[type="search"], input[type="text"], input[type="range"]');
        $fields.each(function() {
            if ($(this).val().trim() !== "") filledCount++;
        });

        if (filledCount < 3) {
            alert("최소 3개 이상의 조건을 입력해야 합니다.");
            return false;
        }

        return true;
    }

    // 폼 제출 이벤트 설정
    function setupFormValidation() {
        const $form = $("form");
        $form.on("submit", function(event) {
            setAgeOption();
            if (!validateForm()) event.preventDefault();
        });
    }

    // 폼 검증 설정 함수 호출
    setupFormValidation();
});
