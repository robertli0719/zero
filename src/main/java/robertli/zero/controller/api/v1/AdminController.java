/*
 * Copyright 2017 Robert Li.
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
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.service.AdminUserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/admin-users")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AdminUserDto> getAdmins(Boolean root) {
        if (root != null && root) {
            return adminUserService.getAdminRootUserList();
        }
        return adminUserService.getAdminUserList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postAdmin(@Valid @RequestBody AdminUserDto adminUserDto) {
        adminUserService.addAdminUser(adminUserDto);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public AdminUserDto getAdmin(@PathVariable int userId) {
        return adminUserService.getAdminUser(userId);
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE)
    public void deleteAdmin(@PathVariable int userId) {
        adminUserService.deleteAdminUser(userId);
    }

    @RequestMapping(path = "/{userId}/root", method = RequestMethod.GET)
    public boolean isAdminRoot(@PathVariable int userId) {
        return adminUserService.isRoot(userId);

    }

    @RequestMapping(path = "/{userId}/root", method = RequestMethod.PUT)
    public void putAdminRoot(@PathVariable int userId) {
        adminUserService.addRootRole(userId);
    }

    
}
