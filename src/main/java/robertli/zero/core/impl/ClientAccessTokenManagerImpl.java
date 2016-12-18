/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.ClientAccessTokenManager;
import robertli.zero.tool.MD5;

/**
 *
 * @see ClientAccessTokenManager
 * @version 2016-12-18 1.0
 * @author Robert Li
 */
@Component("clientAccessTokenManager")
public class ClientAccessTokenManagerImpl implements ClientAccessTokenManager {

    @Resource
    private AppConfiguration appConfiguration;

    private static final String COOKIE_ACCESS_TOKEN_O = "access_token_o";
    private static final String COOKIE_ACCESS_TOKEN_P = "access_token_p";
    private static final int ACCESS_KEY_MAX_AGE = 5 * 365 * 24 * 3600;

    private Map<String, String> getCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<>();
        Cookie[] cookieArray = request.getCookies();
        if (cookieArray == null) {
            return cookieMap;
        }
        for (Cookie cookie : cookieArray) {
            String key = cookie.getName();
            String val = cookie.getValue();
            cookieMap.put(key, val);
        }
        return cookieMap;
    }

    @Override
    public String countAccessToken(String accessTokenO) {
        String salt = appConfiguration.getMd5Salt();
        return MD5.count(accessTokenO + salt);
    }

    @Override
    public String countAccessTokenP(String accessToken) {
        String salt = appConfiguration.getMd5Salt();
        return MD5.count(accessToken + salt);
    }

    private boolean isValidAccessToken(String accessTokenO, String accessTokenP) {
        if (accessTokenO == null || accessTokenP == null) {
            return false;
        }
        String accessToken = countAccessToken(accessTokenO);
        String salt = appConfiguration.getMd5Salt();
        String p = MD5.count(accessToken + salt);
        return p.equals(accessTokenP);
    }

    @Override
    public String getAccessToken(HttpServletRequest request) {
        Map<String, String> cookieMap = getCookieMap(request);

        //get access_token directly, so just use it.
        String accessToken = cookieMap.get("access_token");
        if (accessToken != null) {
            return accessToken;
        }

        String accessTokenO = cookieMap.get("access_token_o");
        String accessTokenP = cookieMap.get("access_token_p");
        if (isValidAccessToken(accessTokenO, accessTokenP)) {
            return countAccessToken(accessTokenO);
        }
        return null;
    }

    private void putCookie(HttpServletResponse response, String name, String val, boolean httpOnly) {
        Cookie cookie = new Cookie(name, val);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(ACCESS_KEY_MAX_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void putAccessTokenO(HttpServletResponse response, String accessTokenO) {
        String accessToken = countAccessToken(accessTokenO);
        String accessTokenP = countAccessTokenP(accessToken);
        putCookie(response, COOKIE_ACCESS_TOKEN_O, accessTokenO, true);
        putCookie(response, COOKIE_ACCESS_TOKEN_P, accessTokenP, false);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void deleteAccessToken(HttpServletResponse response) {
        deleteCookie(response, COOKIE_ACCESS_TOKEN_O);
        deleteCookie(response, COOKIE_ACCESS_TOKEN_P);
    }

}
