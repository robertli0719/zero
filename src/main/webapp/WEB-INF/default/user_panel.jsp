<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<div class="user_panel">
    <s:if test="user">
        <p>user name: <s:property value="user.name"/></p>
        <p>user id: <s:property value="user.id"/></p>
        <a href="UserLogout">Logout</a>
    </s:if>
    <s:else>
        <a href="UserLogin!input">Login</a>
        <a href="UserRegister!input">Register</a>
    </s:else>
</div>