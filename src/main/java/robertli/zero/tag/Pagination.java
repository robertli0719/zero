/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.tag;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.struts2.ServletActionContext;
import robertli.zero.model.SearchResult;

/**
 * This is a JSP tag for paging.
 *
 * @version 1.0 2016-09-28
 * @author Robert Li
 */
public class Pagination extends SimpleTagSupport {

    private String result;

    //the number of button
    private final int RANGE_SIZE = 5;

    private boolean resetParamItem(String itemStr, String name, String val, StringBuilder sb) {
        String p[] = itemStr.split("=");
        if (p[0].equals(name)) {
            sb.append(name).append("=").append(val);
            return true;
        }
        sb.append(itemStr);
        return false;
    }

    private String resetParam(String queryString, String name, String val) {
        if (queryString == null || queryString.isEmpty()) {
            return name + "=" + val;
        }
        String part[] = queryString.split("&");
        boolean replaced = false;
        StringBuilder sb = new StringBuilder();
        for (String str : part) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            if (resetParamItem(str, name, val, sb)) {
                replaced = true;
            }
        }
        if (replaced == false) {
            sb.append(sb.length() > 0 ? "&" : "");
            sb.append(name).append("=").append(val);
        }
        return sb.toString();
    }

    private String getPath(int page) {
        String extension = ServletActionContext.getActionMapping().getExtension();
        String method = ServletActionContext.getActionMapping().getMethod();
        String name = ServletActionContext.getActionMapping().getName();

        String path = name;
        if (method != null) {
            path += "!" + method;
        }
        if (extension != null) {
            path += "." + extension;
        }
        String queryString = ServletActionContext.getRequest().getQueryString();
        queryString = resetParam(queryString, "page", page + "");
        return path + "?" + queryString;
    }

    private CommonTag createPreviousBtn(boolean disable, int page) {
        CommonTag aTag = new CommonTag("a");
        aTag.addAttr("aria-label", "Previous");
        aTag.addAttr("href", disable ? "#" : getPath(page));
        aTag.setHtml("<span aria-hidden=\"true\">&laquo;</span>");
        CommonTag liTag = new CommonTag("li");
        liTag.addChild(aTag);
        if (disable) {
            liTag.addAttr("class", "disabled");
        }
        return liTag;
    }

    private CommonTag createNextBtn(boolean disable, int page) {
        CommonTag aTag = new CommonTag("a");
        aTag.addAttr("aria-label", "Next");
        aTag.addAttr("href", disable ? "#" : getPath(page));
        aTag.setHtml("<span aria-hidden=\"true\">&raquo;</span>");
        CommonTag liTag = new CommonTag("li");
        liTag.addChild(aTag);
        if (disable) {
            liTag.addAttr("class", "disabled");
        }
        return liTag;
    }

    private CommonTag CommonTagBtn(boolean active, int page) {
        CommonTag aTag = new CommonTag("a");
        aTag.addAttr("href", getPath(page));
        aTag.setHtml("" + page);
        CommonTag liTag = new CommonTag("li");
        liTag.addChild(aTag);
        if (active) {
            liTag.addAttr("class", "active");
        }
        return liTag;
    }

    private int countFirstBtnNumber(SearchResult searchResult) {
        if (searchResult.getPageSize() <= RANGE_SIZE) {
            return 1;
        }
        final int pageId = searchResult.getPageId();
        final int lastPageId = searchResult.getPageSize();
        final int leftNum = RANGE_SIZE / 2;
        final int rightNum = RANGE_SIZE - leftNum - 1;
        //if not enough btn in the end, so add btn in th fount
        if (pageId + rightNum > lastPageId) {
            return lastPageId - RANGE_SIZE + 1;
        }
        int num = pageId - leftNum;
        return num >= 1 ? num : 1;
    }

    private int countLastBtnNumber(SearchResult searchResult) {
        int num = countFirstBtnNumber(searchResult) + RANGE_SIZE - 1;
        int maxPageId = searchResult.getPageSize();
        return num <= maxPageId ? num : maxPageId;
    }

    private CommonTag createUl(SearchResult searchResult) {
        int size = searchResult.getPageSize();
        int page = searchResult.getPageId();
        int first = countFirstBtnNumber(searchResult);
        int last = countLastBtnNumber(searchResult);

        CommonTag ulTag = new CommonTag("ul");
        ulTag.addAttr("class", "pagination");

        CommonTag previousBtn = createPreviousBtn(page <= 1, page - 1);
        ulTag.addChild(previousBtn);
        for (int p = first; p <= last; p++) {
            CommonTag tag = CommonTagBtn(page == p, p);
            ulTag.addChild(tag);
        }
        CommonTag nextBtn = createNextBtn(page >= size, page + 1);
        ulTag.addChild(nextBtn);
        return ulTag;
    }

    @Override
    public void doTag() throws IOException {
        Writer out = getJspContext().getOut();
        Object obj = getJspContext().findAttribute(result);
        if (obj instanceof SearchResult == false) {
            return;
        }
        SearchResult searchResult = (SearchResult) obj;
        CommonTag navTag = new CommonTag("nav");
        navTag.addAttr("aria-label", "Page navigation");
        navTag.addChild(createUl(searchResult));
        navTag.write(out);
    }

    public void setResult(String result) {
        this.result = result;
    }

}
