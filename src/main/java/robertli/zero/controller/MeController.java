/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.entity.User;
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

        String token = "sdfw34fgh";
        User user = userService.getUserProfile(token);
        
        return new UserProfileDto();
    }
}
