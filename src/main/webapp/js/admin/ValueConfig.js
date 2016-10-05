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
    console.log("updateInputs");
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
    for (var id in valueConfigArray) {
        var val = valueConfigArray[id];
        var vc = new ValueConfig(val["keyname"], val["value"]);
        console.log(val["keyname"]);
        console.log(val["value"]);
        valueConfigList.push(vc);
    }
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
    $(document).on("click", "[data-cmd=addLine]", function () {
        updateModel();
        valueConfigList.push(new ValueConfig('keyname', 'value'));
        updateInputs();
    });
    $(document).on("click", "[data-cmd=deleteLine]", function () {
        updateModel();
        valueConfigList.length -= 1;
        updateInputs();
    });
    updateInputs();
});
