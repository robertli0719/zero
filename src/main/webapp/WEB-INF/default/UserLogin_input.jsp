<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id" content="<s:property value="googleSigninClientId"/>">
        <script src="plugin/jquery/jquery-1.11.3.min.js"></script>
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <script src="js/default/google-login.js"></script>

        <title>User Login Page</title>
    </head>
    <body>
        <h1>User Login</h1>
        <s:if test="user==null">

            <s:actionerror/>
            <s:form action="UserLogin">
                <s:textfield name="authId" label="username/email"/>
                <s:password name="password" label="password"/>
                <s:submit value="login"/>
            </s:form>
            <a href="UserForgetPassword">Forget password</a>
            <h3>Use Google login</h3>
            <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
        </s:if>
        <s:else>
            <p>user id: <s:property value="user.id"/></p>
            <p>user name: <s:property value="user.name"/></p>
        </s:else>


    </body>
</html>
