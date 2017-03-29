/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.AdminUserDto;

/**
 *
 * @version 1.0.4 2017-03-28
 * @author Robert Li
 */
public interface AdminUserService {

    public QueryResult<AdminUserDto> getAdminUserList(int offset, int limit);

    public QueryResult<AdminUserDto> getAdminRootUserList(int offset, int limit);

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
