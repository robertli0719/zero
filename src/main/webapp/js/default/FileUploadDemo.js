/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */


$(function () {
    $(document).on("click", "[data-cmd=uploadImage]", function () {
        $.uploadImage(function (url) {
            console.log("FileUploadDemo get:" + url);
            $("<img>").attr("src", url).appendTo(".container");
        });
    });

    $(document).on("click", "[data-cmd=uploadCroppedImage]", function () {
        $.uploadCroppedImage(function (url) {
            console.log("FileUploadDemo get:" + url);
            $("<img>").attr("src", url).appendTo(".container");
        });
    });

    $(document).on("click", "[data-cmd=uploadFixedImage]", function () {
        var width = $(this).data("width");
        var height = $(this).data("height");
        $.uploadFixedImage(width, height, function (url) {
            console.log("FileUploadDemo get:" + url);
            $("<img>").attr("src", url).appendTo(".container");
        });
    });

});

