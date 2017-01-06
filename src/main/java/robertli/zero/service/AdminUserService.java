/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.dto.user.AdminUserDto;

/**
 *
 * @version 1.0.1 2017-01-04
 * @author Robert Li
 */
public interface AdminUserService {

    public List<AdminUserDto> getAdminUserList();

    public List<AdminUserDto> getAdminRootUserList();

    public AdminUserDto getAdminUser(int userId);

    public boolean isAdminUser(int userId);

    public boolean isRoot(int userId);

    public int addAdminUser(AdminUserDto adminUserDto);

    public void deleteAdminUser(int userId);

    public void addRootRole(int userId);

    public void removeRootRole(int userId);

    public void resetPassword(int userId, String password);

    public void lockAdminUser(int userId);

    public void unlockAdminUser(int userId);

}
