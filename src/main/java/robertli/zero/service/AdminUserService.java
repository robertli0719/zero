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
 * @version 1.0.3 2017-01-11
 * @author Robert Li
 */
public interface AdminUserService {

    public List<AdminUserDto> getAdminUserList();

    public List<AdminUserDto> getAdminRootUserList();

    public AdminUserDto getAdminUser(String username);

    public boolean isAdminUser(String username);

    public boolean isRoot(String username);

    public int addAdminUser(AdminUserDto adminUserDto);

    public void deleteAdminUser(String username);

    public void addRootRole(String username);

    public void deleteRootRole(String username);

    public void resetPassword(String username, String password);

    public void lockAdminUser(String username);

    public void unlockAdminUser(String username);

}
