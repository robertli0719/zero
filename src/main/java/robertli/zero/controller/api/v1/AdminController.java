/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.controller.RestException;
import robertli.zero.dto.PagingModal;
import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.dto.user.AdminUserPasswordDto;
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
    public List<AdminUserDto> getAdmins(Boolean root, @RequestAttribute PagingModal pagingModal) {
        final int offset = pagingModal.getOffset();
        final int limit = pagingModal.getLimit();
        final QueryResult queryResult;
        if (root != null && root) {
            queryResult = adminUserService.getAdminRootUserList(offset, limit);
        } else {
            queryResult = adminUserService.getAdminUserList(offset, limit);
        }
        final int count = queryResult.getCount();
        pagingModal.placeHeaders(count);
        return queryResult.getResultList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postAdmin(@Valid @RequestBody AdminUserDto adminUserDto) {
        adminUserService.addAdminUser(adminUserDto);
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public AdminUserDto getAdmin(@PathVariable String username) {
        return adminUserService.getAdminUser(username);
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.DELETE)
    public void deleteAdmin(@PathVariable String username) {
        adminUserService.deleteAdminUser(username);
    }

    @RequestMapping(path = "/{username}/root", method = RequestMethod.GET)
    public boolean isAdminRoot(@PathVariable String username) {
        return adminUserService.isRoot(username);

    }

    @RequestMapping(path = "/{username}/root", method = RequestMethod.PUT)
    public void putAdminRoot(@PathVariable String username) {
        adminUserService.addRootRole(username);
    }

    @RequestMapping(path = "/{username}/root", method = RequestMethod.DELETE)
    public void deleteAdminRoot(@PathVariable String username) {
        adminUserService.deleteRootRole(username);
    }

    @RequestMapping(path = "/{username}/password", method = RequestMethod.PUT)
    public void putPassword(@PathVariable String username, @Valid @RequestBody AdminUserPasswordDto adminUserPasswordDto) {
        if (adminUserPasswordDto.getUsername().equals(username) == false) {
            String errorDetail = "the username in DTO is not equals to the username in uri";
            throw new RestException("CONFLICT", "username conflict", errorDetail, HttpStatus.CONFLICT);
        }
        adminUserService.resetPassword(username, adminUserPasswordDto.getPassword());
    }

}
