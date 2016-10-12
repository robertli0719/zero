/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

/* global tinymce, tinyMCE */

tinymce.init({
    selector: 'textarea',
    height: 500,
    plugins: [
        'advlist autolink lists link image charmap print preview anchor',
        'searchreplace visualblocks code fullscreen',
        'insertdatetime media table contextmenu paste code'
    ],
    toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
    content_css: '//www.tinymce.com/css/codepen.min.css',
    relative_urls: false,
    remove_script_host: false,
    entity_encoding : "raw"
});

$(function () {
    $(document).on("click", "[data-cmd=addImageToEditor]", function () {
        $.uploadImage(function (url) {
            var tag = '<img src="' + url + '">';
            tinymce.activeEditor.execCommand('mceInsertContent', false, tag);
        });
    });

    $(document).on("click", "[data-cmd=uploadCroppedImageToEditor]", function () {
        $.uploadCroppedImage(function (url) {
            var tag = '<img src="' + url + '">';
            tinymce.activeEditor.execCommand('mceInsertContent', false, tag);
        });
    });

});
