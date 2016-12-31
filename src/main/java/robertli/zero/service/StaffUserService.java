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
 * @version 1.0 2016-12-30
 * @author Robert Li
 */
public interface StaffUserService {

    public List<StaffUserDto> getStaffUserList();

    public boolean isStaffUser(int userId);

    public boolean isPlatformRoot(int userId);

    public void addStaffUser(StaffUserDto staffUserDto);

    public void deleteStaffUser(int userId);

}
