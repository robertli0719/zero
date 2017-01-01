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
 * @version 1.0.1 2016-12-31
 * @author Robert Li
 */
public interface StaffUserService {

    public List<StaffUserDto> getStaffUserList(String userPlatformName);

    public boolean isStaffUser(String userPlatformName, int userId);

    public boolean isPlatformRoot(String userPlatformName, int userId);

    public void addStaffUser(StaffUserDto staffUserDto);

    public void deleteStaffUser(String userPlatformName, int userId);

    public void addPlatformRootRole(String userPlatformName, int userId);

    public void removePlatformRootRole(String userPlatformName, int userId);

    public void resetPassword(String userPlatformName, int userId, String password);

    public void lockStaffUser(String userPlatformName, int userId);

    public void unlockStaffUser(String userPlatformName, int userId);

}
