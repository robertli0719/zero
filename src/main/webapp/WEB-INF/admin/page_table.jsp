<%-- 
    Document   : page_table
    Created on : Oct 3, 2016, 1:03:33 AM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<table class="table">
    <tr>
    <th>id</th>
    <th>title</th>
    <th>description</th>
</tr>
<s:iterator value="pageList">
    <tr>
    <td><s:property value="id"/></td>
    <td><s:property value="title"/></td>
    <td><s:property value="description"/></td>
</tr>
</s:iterator>
</table>