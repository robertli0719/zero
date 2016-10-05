/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
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
 * @version 1.0 2016-10-05
 * @author Robert Li
 */
public class ValueConfigAction extends ActionSupport implements TextResultSupport {

    @Resource
    private ValueConfigService valueConfigService;

    @Resource
    private JsonService jsonService;

    private ValueConfig valueConfig;
    private List<String> domainList;
    private List<String> actionList;
    private Map<String, List<String>> vcMap;
    private String textResult;

    @Override
    public String execute() {
        domainList = valueConfigService.getDomainList();
        return SUCCESS;
    }

    public String listAction() {
        String domain = valueConfig.getDomain();
        actionList = valueConfigService.getActionList(domain);
        return execute();
    }

    public String list() {
        String domain = valueConfig.getDomain();
        String action = valueConfig.getAction();
        List<ValueConfig> valueConfigList = valueConfigService.getValueConfigList(domain, action);

        JSONArray valueConfigArray = new JSONArray();
        for (ValueConfig vc : valueConfigList) {
            JSONObject element = new JSONObject();
            element.put("domain", vc.getDomain());
            element.put("action", vc.getAction());
            element.put("keyname", vc.getKeyname());
            element.put("value", vc.getValue());
            valueConfigArray.put(element);
        }
        JSONObject result = jsonService.createSuccessResult();
        result.put("valueConfigArray", valueConfigArray);
        textResult = result.toString();
        return TEXT;
    }

    public String update() {
        String domain = valueConfig.getDomain();
        String action = valueConfig.getAction();
        boolean fail = valueConfigService.updateValueConfig(domain, action, vcMap);
        if (fail) {
            addActionError("Fail to update");
        }
        return list();
    }

    public String insert() {
        String domain = valueConfig.getDomain();
        String action = valueConfig.getAction();
        String keyname = valueConfig.getKeyname();
        String value = valueConfig.getValue();
        boolean fail = valueConfigService.insertValueConfig(domain, action, keyname, value);
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

    public List<String> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<String> domainList) {
        this.domainList = domainList;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    public Map<String, List<String>> getVcMap() {
        return vcMap;
    }

    public void setVcMap(Map<String, List<String>> vcMap) {
        this.vcMap = vcMap;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }

}
