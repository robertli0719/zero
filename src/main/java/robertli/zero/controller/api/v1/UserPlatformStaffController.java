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
import robertli.zero.dto.user.StaffUserDto;
import robertli.zero.dto.user.StaffUserPasswordDto;
import robertli.zero.service.StaffUserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/user-platforms/{userPlatformName}/staffs")
public class UserPlatformStaffController {

    @Resource
    private StaffUserService staffUserService;

    @RequestMapping(method = RequestMethod.GET)
    public List<StaffUserDto> getStaffs(@PathVariable String userPlatformName, Boolean root, @RequestAttribute PagingModal pagingModal) {
        final int offset = pagingModal.getOffset();
        final int limit = pagingModal.getLimit();
        QueryResult queryResult;
        int count;
        if (root != null && root) {
            queryResult = staffUserService.getStaffRootUserList(userPlatformName, offset, limit);
            count = queryResult.getCount();
        } else {
            queryResult = staffUserService.getStaffUserList(userPlatformName, offset, limit);
            count = queryResult.getCount();
        }
        pagingModal.placeHeaders(count);
        return queryResult.getResultList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postStaff(@PathVariable String userPlatformName, @Valid @RequestBody StaffUserDto staffUserDto) {
        staffUserService.addStaffUser(userPlatformName, staffUserDto);
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    public StaffUserDto getStaff(@PathVariable String userPlatformName, @PathVariable String username) {
        return staffUserService.getStaffUser(userPlatformName, username);
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.DELETE)
    public void deleteStaff(@PathVariable String userPlatformName, @PathVariable String username) {
        staffUserService.deleteStaffUser(userPlatformName, username);
    }

    @RequestMapping(path = "/{username}/root", method = RequestMethod.GET)
    public boolean isStaffRoot(@PathVariable String userPlatformName, @PathVariable String username) {
        return staffUserService.isPlatformRoot(userPlatformName, username);
    }

    @RequestMapping(path = "/{username}/root", method = RequestMethod.PUT)
    public void putStaffRoot(@PathVariable String userPlatformName, @PathVariable String username) {
        staffUserService.addPlatformRootRole(userPlatformName, username);
    }

    @RequestMapping(path = "/{username}/root", method = RequestMethod.DELETE)
    public void deleteStaffRoot(@PathVariable String userPlatformName, @PathVariable String username) {
        staffUserService.deletePlatformRootRole(userPlatformName, username);
    }

    @RequestMapping(path = "/{username}/password", method = RequestMethod.PUT)
    public void putPassword(@PathVariable String userPlatformName, @PathVariable String username, @Valid @RequestBody StaffUserPasswordDto staffUserPasswordDto) {
        if (staffUserPasswordDto.getUsername().equals(username) == false) {
            String errorDetail = "the username in DTO is not equals to the username in uri";
            throw new RestException("CONFLICT", "username conflict", errorDetail, HttpStatus.CONFLICT);
        }
        staffUserService.resetPassword(userPlatformName, username, staffUserPasswordDto.getPassword());
    }
}
