$(document).ready(function() {
    $('#summernote').summernote({
        height: 550,
        lang: 'ko-KR',
        toolbar: [
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['color', ['color']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture','link','video']],
            ['view', ['fullscreen', 'help']],
            ['table', ['table']]
        ],
        fontNames: ['맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],

        // 이미지 업로드 기능 활성화
        callbacks: {
            onImageUpload: function(files) {
                // 파일 업로드 함수 호출
                sendFile(files[0]);
            }
        }
    });
    $('#summernote').summernote('fontName', '맑은 고딕');
    $('#summernote').summernote('fontSize', 14);
    $('#summernote').summernote('fontSizeUnit', 'pt');
});

// 이미지를 서버로 전송하는 함수
function sendFile(file) {
    var data = new FormData();
    data.append("file", file);

    $.ajax({
        url: '/campaign/uploadImage',  // 이미지 업로드 처리 URL
        method: 'POST',
        data: data,
        contentType: false,
        processData: false,
        success: function(response) {
            // 서버에서 반환된 이미지 URL을 에디터에 삽입
            $('#summernote').summernote('insertImage', response.imageUrl);
        },
        error: function() {
            alert('이미지 업로드 실패');
        }
    });
}
