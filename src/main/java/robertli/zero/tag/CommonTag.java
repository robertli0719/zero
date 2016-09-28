/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a common HTML tag for creating JSP Tags.
 *
 * @version 1.0 2016-09-28
 * @author Robert Li
 */
public class CommonTag {

    private final String name;
    private final Map<String, String> arrMap;
    private final List<CommonTag> bodyTagList;
    private String html;

    public CommonTag(String name) {
        this.name = name;
        this.arrMap = new HashMap<>();
        this.bodyTagList = new ArrayList<>();
        this.html = "";
    }

    public void addAttr(String name, String val) {
        arrMap.put(name, val);
    }

    public void addChild(CommonTag tag) {
        bodyTagList.add(tag);
    }

    public void setHtml(String html) {
        this.html = html;
    }

    private void writeHead(Writer out) throws IOException {
        out.write("<" + name);
        for (String key : arrMap.keySet()) {
            String val = arrMap.get(key);
            out.write(" " + key + "=\"" + val + "\"");
        }
        out.write(">");
    }

    private void writeTail(Writer out) throws IOException {
        out.write("</" + name + ">");
    }

    public void write(Writer out) throws IOException {
        writeHead(out);
        if (html.isEmpty() == false) {
            out.write(html);
        }
        if (bodyTagList.isEmpty() == false) {
            for (CommonTag tag : bodyTagList) {
                tag.write(out);
            }
        }
        writeTail(out);
    }

}
