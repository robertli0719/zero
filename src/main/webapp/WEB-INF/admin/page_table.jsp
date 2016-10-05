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
    <th>category</th>
    <th>title</th>
    <th>description</th>
    <th></th>
</tr>
<s:iterator value="pageList">
    <tr>
    <td><s:property value="id"/></td>
    <td><s:property value="category.name"/></td>
    <td>
        <s:if test="opened">
            <a href="../Page?id=<s:property value="id"/>" target="_blank" >
                <s:property value="title"/>
            </a>
        </s:if>
        <s:else>
            <s:property value="title"/>
        </s:else>
    </td>
    <td><s:property value="description"/></td>
    <td>
        <a class="btn btn-primary btn-xs" href="PageUpdate?id=<s:property value="id"/>">edit</a>
        <a class="btn btn-danger btn-xs" href="Page!delete?id=<s:property value="id"/>">delete</a>
        <s:if test="opened">
            <a class="btn btn-success btn-xs" href="Page!resetStatus?id=<s:property value="id"/>&opened=false">opened</a>
        </s:if>
        <s:else>
            <a class="btn btn-warning btn-xs" href="Page!resetStatus?id=<s:property value="id"/>&opened=true">closed</a>
        </s:else>
    </td>
</tr>
</s:iterator>
</table>