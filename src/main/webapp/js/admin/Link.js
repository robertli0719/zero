/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */


$(function () {
    $(document).on("click", "[data-cmd=listLink]", function () {
        var namespace = $("#select_namespace").val();
        var pageName = $("#select_page_name").val();
        var name = $("#select_name").val();
        $.post("Link!loadLinkGroup", {
            "linkGroup.namespace": namespace,
            "linkGroup.pageName": pageName,
            "linkGroup.name": name
        }, function (feedback) {
            console.log(feedback);
        });
    });
});