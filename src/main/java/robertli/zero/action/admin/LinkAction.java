/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONObject;
import robertli.zero.core.JsonService;
import robertli.zero.entity.LinkGroup;
import robertli.zero.service.LinkService;
import robertli.zero.service.LinkService.AddLinkGroupResult;
import robertli.zero.struts2.TextResultSupport;

/**
 *
 * @version 1.0.1 2016-10-19
 * @author Robert Li
 */
public class LinkAction extends ActionSupport implements TextResultSupport {

    @Resource
    private LinkService linkService;

    @Resource
    private JsonService jsonService;

    private LinkGroup linkGroup;
    private List<String> namespaceList;
    private List<String> pageNameList;
    private List<String> nameList;
    private String textResult;
    private String linkGroupJson;

    @Override
    public String execute() {
        namespaceList = linkService.getNamespaceList();
        return SUCCESS;
    }

    public String listPageName() {
        if (linkGroup != null) {
            String namespace = linkGroup.getNamespace();
            pageNameList = linkService.getPageNameList(namespace);
        }
        return execute();
    }

    public String listName() {
        String namespace = linkGroup.getNamespace();
        String pageName = linkGroup.getPageName();
        pageNameList = linkService.getPageNameList(namespace);
        nameList = linkService.getNameList(namespace, pageName);
        return execute();
    }

    private void showResultForAddLinkGroup(AddLinkGroupResult result) {
        switch (result) {
            case SUCCESS:
                addActionMessage("add successful");
                break;
            case DATABASE_FAIL:
                addActionError("add failed");
                break;
            case DUPLICATE_GROUP:
                addActionError("this group exists");
                break;
            case WRONG_WIDTH_ERROR:
                addActionError("the width is wrong");
                break;
            case WRONG_HEIGHT_ERROR:
                addActionError("the height is wrong");
                break;
            case NO_NAMESPACE_ERROR:
                addActionError("namespace can't be empty");
                break;
            case NO_PAGE_NAME_ERROR:
                addActionError("page name can't be empty");
                break;
            case NO_NAME_ERROR:
                addActionError("name can't be empty");
                break;
            case NO_COMMENT_ERROR:
                addActionError("comment can't be empty");
                break;
            default:
                addActionError("can't identity this result");
        }
    }

    public String addLinkGroup() {
        String namespace = linkGroup.getNamespace();
        String pageName = linkGroup.getPageName();
        String name = linkGroup.getName();
        String comment = linkGroup.getComment();
        int picWidth = linkGroup.getPicWidth();
        int picHeight = linkGroup.getPicHeight();
        AddLinkGroupResult result = linkService.addLinkGroup(namespace, pageName, name, comment, picWidth, picHeight);
        showResultForAddLinkGroup(result);
        return execute();
    }

    public String deleteLinkGroup() {
        String namespace = linkGroup.getNamespace();
        String pageName = linkGroup.getPageName();
        String name = linkGroup.getName();
        linkService.deleteLinkGroup(namespace, pageName, name);
        return execute();
    }

    public String loadLinkGroup() {
        String namespace = linkGroup.getNamespace();
        String pageName = linkGroup.getPageName();
        String name = linkGroup.getName();
        JSONObject linkGroupJSON = linkService.getLinkGroupJSON(namespace, pageName, name);
        JSONObject result = jsonService.createSuccessResult();
        result.put("linkGroup", linkGroupJSON);
        textResult = result.toString();
        return TEXT;
    }

    //we just update link list
    public String updateLinkGroup() {
        JSONObject json = new JSONObject(linkGroupJson);        
        linkService.updateLinkGroupByJSON(json);
        
        JSONObject result = jsonService.createSuccessResult();
        //result.put("linkGroup", linkGroupJSON);
        textResult = result.toString();
        return TEXT;
    }

    public LinkGroup getLinkGroup() {
        return linkGroup;
    }

    public void setLinkGroup(LinkGroup linkGroup) {
        this.linkGroup = linkGroup;
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

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }

    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }

    public String getLinkGroupJson() {
        return linkGroupJson;
    }

    public void setLinkGroupJson(String linkGroupJson) {
        this.linkGroupJson = linkGroupJson;
    }

}
