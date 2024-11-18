function replaceItem(id) {
    $.ajax({
        url: `/volunteer/replace/${id}`,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // 특정 row를 업데이트
            const $row = $(`#row-${id}`);
            $row.html(`
                <td>${data.progrmSj}</td>
                <td><a href="/volunteer/detail/${data.ID}" target="_blank">바로가기</a></td>
                <td><span class="replace-btn" onclick="replaceItem(${data.ID})">교체</span></td>
            `);
        },
        error: function (xhr, status, error) {
            console.error("Error:", error);
        }
    });
}

function replaceAll() {
    $.ajax({
        url: '/volunteer/replace-all',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // tbody 전체를 업데이트
            const $tbody = $('tbody');
            $tbody.empty(); // 기존 내용 제거
            data.forEach(function (activity) {
                $tbody.append(`
                    <tr id="row-${activity.ID}">
                        <td>${activity.progrmSj}</td>
                        <td><a href="/volunteer/detail/${activity.ID}" target="_blank">바로가기</a></td>
                        <td><span class="replace-btn" onclick="replaceItem(${activity.ID})">교체</span></td>
                    </tr>
                `);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error:", error);
        }
    });
}
