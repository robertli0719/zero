<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<table class="table">
    <tr>
        <th>username</th>
        <th>status</th>
        <th></th>
    </tr>
    <s:iterator value="adminList">
        <tr>
            <td><s:property value="username"/></td>
            <td>
                <s:if test="suspended">
                    <span class="text-danger">suspended</span>
                </s:if>
                <s:else>
                    <span class="text-success">active</span>
                </s:else>
            </td>
            <td>
                <s:if test="username.equals('root')==false">
                    <a class="btn btn-danger btn-xs" href="Admin!delete?username=<s:property value="username"/>">delete</a>
                    <s:if test="suspended">
                        <a class="btn btn-success btn-xs" href="Admin!resetSuspended?suspended=false&username=<s:property value="username"/>">active</a>
                    </s:if>
                    <s:else>
                        <a class="btn btn-danger btn-xs" href="Admin!resetSuspended?suspended=true&username=<s:property value="username"/>">suspend</a>
                    </s:else>
                </s:if>
            </td>
        </tr>
    </s:iterator>
</table>