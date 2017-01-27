/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.server.ServerHttpRequest;

/**
 * The clients of our API should use a access_token to access the system. For
 * native app or server to server app, it works well. However, web app or
 * website font-end can not keep the token in a safe way. Hackers could use
 * developer tools to see what the access_token is, so that we can't send the
 * access_token to client directly. Instead, we set access_token_o to the
 * cookies of the client. the access_token_o is the ciphertext of access_token,
 * and the salt in sever is the secret. As a result, client can't know the real
 * access_token from access_token_o but sever can. For prevent from XSS, the
 * access_token_o is http-only, so that there is no js code can get it. We also
 * set access_token_p to the cookies. This is the ciphertext of access_token
 * too, but the client js code can control it. We save the access_token_p
 * without http-only, so that client js code can delete it without a connection
 * with sever when user need to logout.
 *
 * access_token_o = randomString(32)<br>
 * access_token = md5(access_token_o+salt)<br>
 * access_token_p = md5(access_token+salt)<br>
 *
 * The sever only keep the access_token and the salt, but the client should keep
 * both access_token_o and access_token_p. Without the salt, the client can't
 * know the real access_token, but the sever know the salt.
 *
 * @version 2017-01-26 1.0.1
 * @author Robert Li
 */
public interface ClientAccessTokenManager {

    /**
     * count accessToken based on accessTokenO
     *
     * @param accessTokenO access_token_o
     * @return accessToken
     */
    public String countAccessToken(String accessTokenO);

    /**
     * count accessTokenP based on accessToken
     *
     * @param accessToken access_token
     * @return accessTokenP
     */
    public String countAccessTokenP(String accessToken);

    /**
     * get accessToken from cookies.
     *
     * @param request HttpServletRequest
     * @return accessToken or null
     */
    public String getAccessToken(HttpServletRequest request);
    
    /**
     * get accessToken from cookies.
     *
     * @param request ServerHttpRequest
     * @return accessToken or null
     */
    public String getAccessToken(ServerHttpRequest request);

    /**
     * use accessToken0 to save accessToken to cookies
     *
     * @param response HttpServletResponse
     * @param accessTokenO accessToken0
     */
    public void putAccessTokenO(HttpServletResponse response, String accessTokenO);

    /**
     * delete accessToken on cookies
     *
     * @param response HttpServletResponse
     */
    public void deleteAccessToken(HttpServletResponse response);

}
