/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MeController extends GenericRestController {

    @Resource
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public UserProfileDto getMe() {
        Logger logger = Logger.getLogger("MeController");
        logger.log(Level.INFO, "init getMe");

//        User user = userService.getUserProfile(token);
        //先写到这，睡了
        return new UserProfileDto();
    }

    @RequestMapping(path = "auth", method = RequestMethod.PUT)
    public void putAuth(@Valid @RequestBody UserAuthDto userAuthDto, HttpServletResponse response) {
        System.out.println("putAuth");
        Cookie cookie = new Cookie("access_token", "hello1234567");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(5 * 365 * 24 * 3600);
        cookie.setPath("/");
        response.addCookie(cookie);
        //cookie实验成功，等待完成UserService
    }

    @RequestMapping(path = "auth", method = RequestMethod.DELETE)
    public void deleteAuth(@CookieValue("access_token") String accessToken, HttpServletResponse response) {
        System.out.println("access_token:" + accessToken);
        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        //cookie实验成功，等待完成UserService
    }

}
