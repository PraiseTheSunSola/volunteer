$(document).ready(function () {
    // 2진수를 7자리로 변환
    function padBinary(binaryString) {
        return binaryString.padStart(7, '0');
    }

    // 변환된 2진수를 체크박스에 반영
    function applyBinaryToCheckboxes(binaryString) {
        const paddedBinary = padBinary(binaryString);
        $('#selectWkdy .weekday').each(function (index) {
            $(this).prop('checked', paddedBinary.charAt(index) === '1');
        });
    }

    // 검색 항목 폼 토글
    function toggleOption(id) {
        $('.options').hide(); // 모든 폼 숨기기
        $('#' + id).show(); // 선택된 폼만 보이기
    }

    // 버튼 활성화 상태 업데이트
    function updateButtonState() {
        let filledCount = 0;

        $('#searchForm input').each(function () {
            if ($(this).is(':checkbox, :radio') && $(this).is(':checked')) {
                filledCount++;
            } else if (!$(this).is(':checkbox, :radio') && $(this).val().trim() !== '') {
                filledCount++;
            }
        });

        const $submitButton = $('button[type="submit"]');
        if (filledCount >= 3) {
            $submitButton.addClass('active'); // 활성화 스타일 추가
        } else {
            $submitButton.removeClass('active'); // 활성화 스타일 제거
        }
    }

    // 폼 제출 이벤트 처리
    $('#searchForm').on('submit', function (event) {
        if (!$('button[type="submit"]').hasClass('active')) {
            event.preventDefault(); // 활성화되지 않은 상태에서 제출 방지
            alert('최소 3개 이상의 조건을 입력해야 합니다.');
        }
    });

    // 입력 값 변경 시 버튼 상태 업데이트
    $('#searchForm input').on('input change', updateButtonState);

    // 검색 항목 폼 토글 이벤트 설정
    $('[data-target]').on('click', function () {
        const targetId = $(this).data('target'); // data-target 속성에서 ID 추출
        if (targetId) {
            toggleOption(targetId);
        }
    });

    // 초기 버튼 상태 설정
    updateButtonState();

    // 성인-미성년 가능 여부 설정
    function setAgeOption() {
       const adultOnly = $('#adultOnly').is(':checked');
       const youthOnly = $('#youthOnly').is(':checked');
       const $ageOptionHidden = $('#ageOptionHidden');

       if (adultOnly && youthOnly) {
           $ageOptionHidden.val('both');
       } else if (adultOnly) {
           $ageOptionHidden.val('adultOnly');
       } else if (youthOnly) {
           $ageOptionHidden.val('youthOnly');
       } else {
           $ageOptionHidden.val('');
       }
    }
});
