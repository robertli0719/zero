/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.GeneralUserDto;

/**
 *
 * @version 1.0.0 2017-06-01
 * @author Robert Li
 */
public interface GeneralUserService {

    public QueryResult<GeneralUserDto> getGeneralUserList(int offset, int limit);

    public GeneralUserDto getGeneralUser();

    public void registerByEmail();

    public void registerByTelephone();
}
