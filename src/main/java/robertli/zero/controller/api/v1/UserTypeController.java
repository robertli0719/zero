/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.user.UserTypeDto;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/user-types")
public class UserTypeController {

    @Resource
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserTypeDto> getUserTypes() {
        return userService.getUserTypeList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postUserType(@Valid @RequestBody UserTypeDto userTypeDto) {
        String userTypeName = userTypeDto.getName();
        userService.addUserType(userTypeName);
    }

    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public void deleteUserType(@PathVariable String name) {
        userService.deleteUserType(name);
    }
}
