<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<table class="table">
    <tr>
    <th>name</th>
    <th>description</th>
    <th></th>
</tr>
<s:iterator value="pageCategoryList">
    <tr>
    <td>
        <s:property value="name"/>
    </td>
    <td>
        <s:property value="description"/>
    </td>
    <td>
        <a class="btn btn-danger btn-xs" href="PageCategory!delete?name=<s:property value="name"/>">delete</a>
    </td>
</tr>
</s:iterator>
</table>