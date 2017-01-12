/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.dto.user.StaffUserDto;

/**
 *
 * @version 1.0.2 2017-01-10
 * @author Robert Li
 */
public interface StaffUserService {

    public List<StaffUserDto> getStaffUserList(String userPlatformName);

    public List<StaffUserDto> getStaffRootUserList(String userPlatformName);

    public StaffUserDto getStaffUser(String userPlatformName, String username);

    public boolean isStaffUser(String userPlatformName, String username);

    public boolean isPlatformRoot(String userPlatformName, String username);

    public void addStaffUser(String userPlatformName,StaffUserDto staffUserDto);

    public void deleteStaffUser(String userPlatformName, String username);

    public void addPlatformRootRole(String userPlatformName, String username);

    public void deletePlatformRootRole(String userPlatformName, String username);

    public void resetPassword(String userPlatformName, String username, String password);

    public void lockStaffUser(String userPlatformName, String username);

    public void unlockStaffUser(String userPlatformName, String username);

}
