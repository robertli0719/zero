/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

function getUploadUrl() {
    var imageActionUrl = $("meta[name=image-action-url]").attr("content");
    if (imageActionUrl === undefined) {
        console.log("FileUpload doesn't work.");
        console.log("tips: you need to set image-action-url in <meta>");
    }
    return imageActionUrl + "!upload";
}

$(function () {
    var files = null;

    var uploadUrl = getUploadUrl();
    
    $(document).on("click", "[data-cmd=uploadImage]", function () {
        $.uploadImage(800, 600, function (url) {
            console.log(url);
        });
    });

    $(document).on("change", "[data-cmd=upload-image]", function (event) {
        files = event.target.files;
    });

    $(document).on("click", "[data-cmd=upload]", function () {
        if (files === null) {
            return;
        }
        var data = new FormData();
        $.each(files, function (key, value) {
            console.log(key + "->" + value);
            data.append("img", value);
        });
        $.ajax({
            url: uploadUrl,
            type: 'POST',
            data: data,
            processData: false,
            contentType: false,
            success: function (result) {
                files = null;
                $("[data-cmd=upload-image]").val(null);
                console.log("\n\n RESSULT:");
                uploadImageCallback(result);
            },
            error: function (result) {
                console.log("error");
                console.log(result);
                alert(result);
            }
        });
    });
});

function uploadImageCallback(result) {
    console.log(result);
    var obj = JSON.parse(result);
    var result = obj["result"];
    if (result === "fail") {
        var error = obj["errorString"];
        alert(error);
        console.log(error);
        return;
    }
    var urlList = obj["urlList"];
    for (var id in urlList) {
        var url = urlList[id];
        console.log(url);
        var img = $("<img>").attr("src", url).addClass("img-responsive");
        $("#img_content").append(img);
    }
}