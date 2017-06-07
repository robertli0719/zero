/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.UserRegisterDto;
import robertli.zero.dto.user.GeneralUserDto;
import robertli.zero.dto.user.MobilePhoneBindingApplicationDto;
import robertli.zero.dto.user.PasswordResetApplicationDto;
import robertli.zero.dto.user.PasswordResetterDto;

/**
 *
 * @version 1.0.3 2017-06-06
 * @author Robert Li
 */
public interface GeneralUserService {

    public QueryResult<GeneralUserDto> getGeneralUserList(int offset, int limit);

    public GeneralUserDto getGeneralUser(String uid);

    public void registerByEmail(UserRegisterDto registerDto);

    public void sendRegisterVerificationEmail(String email);

    public void verifyRegister(String verifiedCode);

    public void applyPasswordResetToken(PasswordResetApplicationDto applicationDto);

    public void resetPasswordByToken(PasswordResetterDto resetterDto);

    public void applyToBindingMobilePhone(String accessToken, MobilePhoneBindingApplicationDto applicationDto);

    public void bindingMobilePhone();

}
