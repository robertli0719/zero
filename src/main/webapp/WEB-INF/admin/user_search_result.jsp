<%-- 
    Document   : user_search_result
    Created on : Sep 27, 2016, 11:32:32 PM
    Author     : Robert Li
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<table class="table">
    <tr>
        <th>id</th>
        <th>name</th>
        <th>authorization Id</th>
    </tr>
    <s:iterator value="userSearchResult.list">
        <tr>
            <td><s:property value="id"/></td>
            <td><s:property value="name"/></td>
            <td>
                <s:iterator value="userAuthList">
                    <s:property value="label"/><br />
                </s:iterator>
            </td>
        </tr>
    </s:iterator>
</table>
