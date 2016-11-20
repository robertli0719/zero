/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.entity.User;
import robertli.zero.service.UserRegisterService;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    
    @Resource
    private UserService userService;
    
    @Resource
    private UserRegisterService userRegisterService;
    
    @RequestMapping(path = "test", method = RequestMethod.POST)
    public String test(HttpServletResponse response, Integer code) {
        if (code != null) {
            response.setStatus(code);
        }
        System.out.println(code);
        return null;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public User getCurrentUser(HttpSession session) {
        String sessionId = session.getId();
        return userService.getCurrentUser(sessionId);
    }
    
    @RequestMapping(path = "login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpSession session, String authName, String password) {
        Map<String, Object> map = new HashMap<>();
        String sessionId = session.getId();
        Object result = userService.login(sessionId, authName, password);
        map.put("result", result);
        return map;
    }
    
    @RequestMapping(path = "loginByGoogle", method = RequestMethod.POST)
    public Map<String, Object> loginByGoogle(HttpSession session, String token) {
        Map<String, Object> map = new HashMap<>();
        String sessionId = session.getId();
        Object result = userService.loginByGoogle(sessionId, token);
        map.put("result", result);
        return map;
    }
    
    @RequestMapping(path = "logout", method = RequestMethod.POST)
    public Map<String, Object> logout(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        String sessionId = session.getId();
        Object result = userService.logout(sessionId);
        map.put("result", result);
        return map;
    }
    
    @RequestMapping(path = "register", method = RequestMethod.POST)
    public Map<String, Object> register(String email, String password, String passwordAgain, String name) {
        Map<String, Object> map = new HashMap<>();
        Object result = userRegisterService.registerByEmail(email, password, passwordAgain, name);
        map.put("result", result);
        return map;
    }
    
    @RequestMapping(path = "register/sendEmail", method = RequestMethod.POST)
    public Map<String, Object> sendRegisterVerificationEmail(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        String sessionId = session.getId();
        Object result = userRegisterService.sendRegisterVerificationEmail(sessionId);
        map.put("result", result);
        return map;
    }
    
    @RequestMapping(path = "register/verifiy", method = RequestMethod.POST)
    public Map<String, Object> verifiyRegister(HttpSession session, String verifiedCode) {
        Map<String, Object> map = new HashMap<>();
        String sessionId = session.getId();
        Object result = userRegisterService.verifiyRegister(sessionId);
        map.put("result", result);
        return map;
    }
    
}
