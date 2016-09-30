/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
tinymce.init({
    selector: 'textarea',
    height: 500,
    plugins: [
        'advlist autolink lists link image charmap print preview anchor',
        'searchreplace visualblocks code fullscreen',
        'insertdatetime media table contextmenu paste code'
    ],
    toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
    content_css: '//www.tinymce.com/css/codepen.min.css'
});

$(function () {
    $(document).on("click", "[data-cmd=run]", function () {
        tinymce.activeEditor.execCommand('mceInsertContent', false, "some text");
        console.debug(tinyMCE.activeEditor.getContent());
    });
});