/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */

function ValueConfig(keyname, value) {
    this.keyname = keyname;
    this.value = value;
}

var valueConfigList = new Array();

var updateInputs = function () {
    var table = $('#value_config_form_table').empty();
    table.append('<tr><th>Key</th><th>Value</th><th class="function_col"></th></tr>');
    for (var i = 0; i < valueConfigList.length; i++) {
        var item = valueConfigList[i];
        $('<tr>')
                .append('<td><input type="text" class="form-control" data-variable="keyname" value="' + item.keyname + '"></td>')
                .append('<td><input type="text" class="form-control" data-variable="value" value="' + item.value + '"></td>')
                .append('<td><a class="btn btn-primary btn-sm" onclick="addLineById(' + i + ')"><span>insert</span></a> <a class="btn btn-danger btn-sm" onclick="delLineById(' + i + ')"><span>delete</span></a></td>')
                .appendTo(table);
    }
};

var updateModel = function () {
    valueConfigList = new Array();
    $('#value_config_form_table tr:has(td)').each(function () {
        var keyname = $('[data-variable=keyname]', this).val();
        var value = $('[data-variable=value]', this).val();
        valueConfigList.push(new ValueConfig(keyname, value));
    });
};

var delLineById = function (id) {
    updateModel();
    valueConfigList.splice(id, 1);
    updateInputs();
};

var addLineById = function (id) {
    updateModel();
    valueConfigList.splice(id, 0, new ValueConfig('', ''));
    updateInputs();
};

var initModel = function (valueConfigArray) {
    valueConfigList = [];
    for (var id in valueConfigArray) {
        var val = valueConfigArray[id];
        var vc = new ValueConfig(val["keyname"], val["value"]);
        valueConfigList.push(vc);
    }
    $(".table_div .btn").attr("disabled", false);
};

var submit = function () {
    updateModel();
    var domain = $("#select_domain").val();
    var action = $("#select_action").val();
    var vcMap = new Object();
    for (var id in valueConfigList) {
        var e = valueConfigList[id];
        var key = e.keyname;
        if (vcMap[key] === undefined) {
            vcMap[key] = [];
        }
        vcMap[key].push(e.value);
    }
    $.post("ValueConfig!update", {
        "valueConfig.domain": domain,
        "valueConfig.action": action,
        "vcMap": JSON.stringify(vcMap)
    }, function (val) {
        var obj = JSON.parse(val);
        if (obj["result"] === 'success') {
            var errorStrring = obj["errorString"];
            $(".alert").remove();
            var alert_div = $('<div>').addClass("alert alert-success alert-dismissible").html("<strong>Update!</strong> success").insertAfter(".container hr");
            $('<button>').addClass("close").attr("type", "button").attr("data-dismiss", "alert").attr("aria-label", "Close").html("<span aria-hidden=\"true\">&times;</span>").appendTo(alert_div);
            console.log(obj);
        } else {
            var errorStrring = obj["errorString"];
            $(".alert").remove();
            var alert_div = $('<div>').addClass("alert alert-danger alert-dismissible").html("<strong>Error!</strong> " + errorStrring).insertAfter(".container hr");
            $('<button>').addClass("close").attr("type", "button").attr("data-dismiss", "alert").attr("aria-label", "Close").html("<span aria-hidden=\"true\">&times;</span>").appendTo(alert_div);
            console.log(obj);
        }
    });
};

$(function () {
    $(document).on("click", "[data-cmd=listValueConfig]", function () {
        var domain = $("#select_domain").val();
        var action = $("#select_action").val();
        $.post("ValueConfig!list", {
            "valueConfig.domain": domain,
            "valueConfig.action": action
        }, function (val) {
            var obj = JSON.parse(val);
            if (obj["result"] === 'success') {
                initModel(obj["valueConfigArray"]);
                updateInputs();
            } else {
                console.log("Error when ValueConfig!list");
            }
        });
    });
    $(document).on("click", "[data-cmd]", function () {
        $(".alert").remove();
    });
    $(document).on("click", "[data-cmd=addLine]", function () {
        updateModel();
        valueConfigList.push(new ValueConfig('keyname', 'value'));
        updateInputs();
    });
    $(document).on("click", "[data-cmd=deleteLine]", function () {
        updateModel();
        if (valueConfigList.length > 0) {
            valueConfigList.length -= 1;
            updateInputs();
        }
    });
    $(document).on("click", "[data-cmd=submit]", function () {
        submit();
    });
});
