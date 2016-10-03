<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<table class="table">
    <tr>
    <th>name</th>
    <th>description</th>
</tr>
<s:iterator value="pageCategoryList">
    <tr>
    <td>
        <s:property value="name"/>
    </td>
    <td>
        <s:property value="description"/>
    </td>
</tr>
</s:iterator>
</table>