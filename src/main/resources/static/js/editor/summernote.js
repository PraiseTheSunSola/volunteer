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
    });
    $('#summernote').summernote('fontName', '맑은 고딕');
    $('#summernote').summernote('fontSize', 14);
    $('#summernote').summernote('fontSizeUnit', 'pt');
});