/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("me")
public class MeController {
    
    @Resource
    private UserService userService;
    
    private static final String COOKIE_ACCESS_TOKEN = "access_token";
    
    @RequestMapping(method = RequestMethod.GET)
    public UserProfileDto getMe(@CookieValue(COOKIE_ACCESS_TOKEN) String accessToken) {
        return userService.getUserProfile(accessToken);
    }
    
    @RequestMapping(path = "auth", method = RequestMethod.PUT)
    public void putAuth(@Valid @RequestBody UserAuthDto userAuthDto, HttpServletResponse response) {
        String accessToken = userService.putAuth(userAuthDto);
        
        Cookie cookie = new Cookie(COOKIE_ACCESS_TOKEN, accessToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(5 * 365 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    @RequestMapping(path = "auth", method = RequestMethod.DELETE)
    public void deleteAuth(@CookieValue(COOKIE_ACCESS_TOKEN) String accessToken, HttpServletResponse response) {
        userService.deleteAuth(accessToken);
        Cookie cookie = new Cookie(COOKIE_ACCESS_TOKEN, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
}
