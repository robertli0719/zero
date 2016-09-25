/* 
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 *
 * you need add:
 <meta name="google-signin-scope" content="profile email">
 <meta name="google-signin-client_id" content="your-signin-client-id-here">
 <script src="https://apis.google.com/js/platform.js" async defer></script>
 before you use this file
 */
/*
 * so far, I use a temporary solution:
 * after google login, this js send token to our system to make our system login. After our system login, google logout at once.
 * 
 * I want to change to under solution when I have time:
 *  https://developers.google.com/identity/sign-in/web/backend-auth
 */

function onSignIn(googleUser) {
    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();
    console.log("ID: " + profile.getId()); // Don't send this directly to your server!
    console.log('Full Name: ' + profile.getName());
    console.log('Given Name: ' + profile.getGivenName());
    console.log('Family Name: ' + profile.getFamilyName());
    console.log("Image URL: " + profile.getImageUrl());
    console.log("Email: " + profile.getEmail());

    var id_token = googleUser.getAuthResponse().id_token;
    $.post("json/User!loginByGoogle", {
        token: id_token
    }, function (json) {
        if (json["result"] === "success") {
            console.log("login success");
        }
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut().then(function () {
            console.log('google user signed out.');
            location.reload();
        });
    });
}