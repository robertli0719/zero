/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

function Link(title, url, imgUrl, comment) {
    this.title = title;
    this.url = url;
    this.imgUrl = imgUrl;
    this.comment = comment;
}

function LinkGroup(namespace, pageName, name, comment, picWidth, picHeight, linkArray) {
    this.namespace = namespace;
    this.pageName = pageName;
    this.name = name;
    this.comment = comment;
    this.picWidth = picWidth;
    this.picHeight = picHeight;
    this.linkArray = linkArray;
}

var linkGroup = null;

var updateModel = function () {
    console.log("updateModel");
    linkGroup.linkArray = [];
    $("[data-component=link-div]").each(function () {
        var title = $('[data-input=title]', this).val();
        var url = $('[data-input=url]', this).val();
        var imgUrl = $('[data-input=img]', this).attr("src");
        var comment = $('[data-input=comment]', this).val();
        linkGroup.linkArray.push(new Link(title, url, imgUrl, comment));
    });
};

var initLinkGroup = function (json) {
    linkGroup = json;
};

var createLinkGroupInfoDiv = function () {
    var namespace = linkGroup["namespace"];
    var pageName = linkGroup["pageName"];
    var name = linkGroup["name"];
    var comment = linkGroup["comment"];
    var picWidth = linkGroup["picWidth"];
    var picHeight = linkGroup["picHeight"];
    var title = namespace + " -> " + pageName + " -> " + name;

    var infoDiv = $("<div>").addClass("panel panel-primary");
    $("<div>").addClass("panel-heading").html(title).appendTo(infoDiv);
    var panelBody = $("<div>").addClass("panel-body").appendTo(infoDiv);
    $("<p>").html("comment: " + comment).appendTo(panelBody);
    $("<p>").html("image resolution: " + picWidth + "*" + picHeight).appendTo(panelBody);
    return infoDiv;
};

var createFormGroupDiv = function (labelName, name, val) {
    var tagId = $.randString();
    var div = $("<div>").addClass("form-group");
    $("<label>").attr("for", tagId).html(labelName).appendTo(div);
    $("<input>").attr("id", tagId).attr("type", "text").attr("data-input", name).addClass("form-control").attr("placeholder", labelName).val(val).appendTo(div);
    return div;
};

var createLinkDiv = function (id, link) {
    var div = $("<div>").addClass("panel panel-primary").attr("data-component", "link-div");
    $("<div>").addClass("panel-heading").html("Link").appendTo(div);
    var panelBody = $("<div>").addClass("panel-body row").appendTo(div);

    $("<img>").addClass("col-md-4 img-responsive").attr("src", link.imgUrl).attr("data-input", "img").appendTo(panelBody);
    var formDiv = $("<div>").addClass("col-md-8").appendTo(panelBody);
    createFormGroupDiv("Title", "title", link.title).appendTo(formDiv);
    createFormGroupDiv("URL", "url", link.url).appendTo(formDiv);
    createFormGroupDiv("Comment", "comment", link.comment).appendTo(formDiv);

    var panelFooter = $("<div>").addClass("panel-footer clearfix").appendTo(div);
    var btnPanel = $("<div>").addClass("pull-right").appendTo(panelFooter);
    $("<a>").addClass("btn btn-success").attr("data-cmd", "reset-link-image").attr("data-id", id).html("Reset Image").appendTo(btnPanel);
    $("<span>").html(" ").appendTo(btnPanel);
    $("<a>").addClass("btn btn-primary").attr("data-cmd", "add-link-by-id").attr("data-id", id).html("Insert").appendTo(btnPanel);
    $("<span>").html(" ").appendTo(btnPanel);
    $("<a>").addClass("btn btn-danger").attr("data-cmd", "remove-link-by-id").attr("data-id", id).html("Delete").appendTo(btnPanel);
    return div;
};

var createButtonPanel = function () {
    var panel = $("<div>");
    $("<a>").addClass("btn btn-primary").attr("data-cmd", "add-link").html("add line").appendTo(panel);
    $("<span>").html(" ").appendTo(panel);
    $("<a>").addClass("btn btn-danger").attr("data-cmd", "remove-link").html("remove line").appendTo(panel);
    $("<span>").html(" ").appendTo(panel);
    $("<a>").addClass("btn btn-success").attr("data-cmd", "submit-link-group").html("submit").appendTo(panel);
    return panel;
};

var updateInputs = function () {
    console.log("updateInputs");
    var panel = $('#link_group_panel').empty();
    createLinkGroupInfoDiv().appendTo(panel);
    for (var id in linkGroup.linkArray) {
        var link = linkGroup.linkArray[id];
        createLinkDiv(id, link).appendTo(panel);
    }
    createButtonPanel().appendTo(panel);
};

$(function () {
    $(document).on("click", "[data-cmd=reset-link-image]", function () {
        var id = $(this).data("id");
        var width = linkGroup.picWidth;
        var height = linkGroup.picHeight;
        $.uploadFixedImage(width, height, function (imgUrl) {
            updateModel();
            linkGroup.linkArray[id].imgUrl = imgUrl;
            updateInputs();
        });
    });
    $(document).on("click", "[data-cmd=add-link]", function () {
        var link = new Link("title", "#", "../img/common/nopic.jpg", "");
        updateModel();
        linkGroup.linkArray.push(link);
        updateInputs();
    });
    $(document).on("click", "[data-cmd=remove-link-by-id]", function () {
        var id = $(this).data("id");
        updateModel();
        linkGroup.linkArray.splice(id, 1);
        updateInputs();
    });
    $(document).on("click", "[data-cmd=add-link-by-id]", function () {
        var id = $(this).data("id");
        updateModel();
        linkGroup.linkArray.splice(id, 0, new Link('title', '#', '../img/common/nopic.jpg', ''));
        updateInputs();
    });
    $(document).on("click", "[data-cmd=remove-link]", function () {
        updateModel();
        if (linkGroup.linkArray.length > 0) {
            linkGroup.linkArray.length -= 1;
            updateInputs();
        }
    });
    $(document).on("click", "[data-cmd=submit-link-group]", function () {
        updateModel();
        var json = JSON.stringify(linkGroup);
        $.post("Link!updateLinkGroup", {
            linkGroupJson: json
        }, function (val) {
            console.log(val);
        });
    });
    $(document).on("click", "[data-cmd=listLink]", function () {
        var namespace = $("#select_namespace").val();
        var pageName = $("#select_page_name").val();
        var name = $("#select_name").val();
        $.post("Link!loadLinkGroup", {
            "linkGroup.namespace": namespace,
            "linkGroup.pageName": pageName,
            "linkGroup.name": name
        }, function (feedback) {
            var json = JSON.parse(feedback);
            if (json["result"] === 'success') {
                initLinkGroup(json["linkGroup"]);
                updateInputs();
            } else {
                console.log(feedback);
            }
        });
    });
});