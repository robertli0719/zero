<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Forget Password</title>
    </head>
    <body>

        <s:if test="result!=null">
            <h3>result:</h3>
            <p><s:property value="result"/></p>
        </s:if>
        <s:else>
            <h1>Get email to reset your password:</h1>
            <s:form action="UserForgetPassword!askToken">
                <s:textfield name="email" label="Email"/>
                <s:submit value="send"/>
            </s:form>
        </s:else>
    </body>
</html>
