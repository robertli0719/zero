/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import robertli.zero.core.JsonService;
import robertli.zero.entity.ValueConfig;
import robertli.zero.service.ValueConfigService;
import robertli.zero.struts2.TextResultSupport;

/**
 *
 * @version 1.0.1 2016-10-12
 * @author Robert Li
 */
public class ValueConfigAction extends ActionSupport implements TextResultSupport {

    @Resource
    private ValueConfigService valueConfigService;

    @Resource
    private JsonService jsonService;

    private ValueConfig valueConfig;
    private List<String> namespaceList;
    private List<String> pageNameList;
    private String vcMap;
    private String textResult;

    @Override
    public String execute() {
        namespaceList = valueConfigService.getNamespaceList();
        return SUCCESS;
    }

    public String listPageName() {
        String namespace = valueConfig.getNamespace();
        pageNameList = valueConfigService.getPageNameList(namespace);
        return execute();
    }

    public String list() {
        String namespace = valueConfig.getNamespace();
        String pageName = valueConfig.getPageName();
        List<ValueConfig> valueConfigList = valueConfigService.getValueConfigList(namespace, pageName);

        JSONArray valueConfigArray = new JSONArray();
        for (ValueConfig vc : valueConfigList) {
            JSONObject element = new JSONObject();
            element.put("namespace", vc.getNamespace());
            element.put("pageName", vc.getPageName());
            element.put("name", vc.getName());
            element.put("val", vc.getVal());
            valueConfigArray.put(element);
        }
        JSONObject result = jsonService.createSuccessResult();
        result.put("valueConfigArray", valueConfigArray);
        textResult = result.toString();
        return TEXT;
    }

    private Map<String, List<String>> makeMap() {
        Map<String, List<String>> map = new HashMap<>();
        JSONObject jsonMap = new JSONObject(vcMap);
        for (String key : jsonMap.keySet()) {
            JSONArray array = jsonMap.getJSONArray(key);
            ArrayList<String> list = new ArrayList<>();
            for (Object val : array.toList()) {
                list.add(val.toString());
            }
            map.put(key, list);
        }
        return map;
    }

    public String update() {
        String namespace = valueConfig.getNamespace();
        String pageName = valueConfig.getPageName();
        boolean fail = valueConfigService.updateValueConfig(namespace, pageName, makeMap());
        if (fail) {
            JSONObject result = jsonService.createFailResult("Fail to update");
            textResult = result.toString();
            return TEXT;
        }
        JSONObject result = jsonService.createSuccessResult();
        textResult = result.toString();
        return TEXT;
    }

    public String insert() {
        String namespace = valueConfig.getNamespace();
        String pageName = valueConfig.getPageName();
        String name = valueConfig.getName();
        String val = valueConfig.getVal();
        boolean fail = valueConfigService.insertValueConfig(namespace, pageName, name, val);
        if (fail) {
            addActionError("Fail to Insert");
        }
        return execute();
    }

    public ValueConfig getValueConfig() {
        return valueConfig;
    }

    public void setValueConfig(ValueConfig valueConfig) {
        this.valueConfig = valueConfig;
    }

    public List<String> getNamespaceList() {
        return namespaceList;
    }

    public void setNamespaceList(List<String> namespaceList) {
        this.namespaceList = namespaceList;
    }

    public List<String> getPageNameList() {
        return pageNameList;
    }

    public void setPageNameList(List<String> pageNameList) {
        this.pageNameList = pageNameList;
    }

    public String getVcMap() {
        return vcMap;
    }

    public void setVcMap(String vcMap) {
        this.vcMap = vcMap;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }

}
