/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.UserRegisterDto;
import robertli.zero.dto.user.GeneralUserDto;

/**
 *
 * @version 1.0.1 2017-06-02
 * @author Robert Li
 */
public interface GeneralUserService {

    public QueryResult<GeneralUserDto> getGeneralUserList(int offset, int limit);

    public GeneralUserDto getGeneralUser(String uid);

    public void registerByEmail(UserRegisterDto registerDto);

    public void sendRegisterVerificationEmail(String email);

    public void verifiyRegister(String verifiedCode);

}
