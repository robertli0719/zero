/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.auth.UserEmailRegisterDto;
import robertli.zero.dto.auth.UserVerifiyRegisterDto;
import robertli.zero.entity.User;
import robertli.zero.service.UserRegisterService;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("auth")
public class AuthController extends GenericRestController {

    @Resource
    private UserService userService;

    @Resource
    private UserRegisterService userRegisterService;

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
    public Map<String, Object> register(@Valid @RequestBody UserEmailRegisterDto userRegisterDto) {
        UserRegisterService.UserRegisterResult result = userRegisterService.registerByEmail(userRegisterDto);

        switch (result) {
            case SUBMIT_SUCCESS:
                Map<String, Object> map = new HashMap<>();
                map.put("result", result);
                return map;
            case USER_EXIST:
                throw new RestException(result.name(), "E-mail address already in use", "You indicated you are a new customer, but an account already exists with this e-mail", HttpStatus.CONFLICT);
            case REGISTER_EXIST:
                throw new RestException(result.name(), "You had submitted this register.", "You use this email to submit register more than ones. Please check your emailbox.", HttpStatus.CONFLICT);
            default:
                System.out.println(result.name());
                throw new RestException(result.name(), "Submit Fail", "The controller can't recognize the error", HttpStatus.FORBIDDEN);
        }
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
    public Map<String, Object> verifiyRegister(@Valid @RequestBody UserVerifiyRegisterDto userVerifiyRegisterDto) {
        String verifiedCode = userVerifiyRegisterDto.getVerifiedCode();
        Map<String, Object> map = new HashMap<>();
        Object result = userRegisterService.verifiyRegister(verifiedCode);
        map.put("result", result);
        return map;
    }

}
