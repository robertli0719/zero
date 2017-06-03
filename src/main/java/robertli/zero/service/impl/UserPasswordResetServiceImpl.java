/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.EmailMessage;
import robertli.zero.core.EmailSender;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserPasswordResetTokenDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPasswordResetToken;
import robertli.zero.service.UserEmailBuilder;
import robertli.zero.service.UserPasswordResetService;
import robertli.zero.tool.ValidationTool;

/**
 * User can use this service to reset their password<br>
 * There are three ways to reset user's password.<br>
 *
 * 1.after user login, the user can reset own password<br>
 * 2.user can use a token to reset password<br>
 * 3.user can use a token to ask system to create a random password<br>
 *
 * no matter if the user has login, the user can ask a token for resetting
 * password. The token is single use, and each token has a one day long life.
 * user can own more than one token at the same time.
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
@Component("userPasswordResetService")
public class UserPasswordResetServiceImpl implements UserPasswordResetService {

    public final int TOKEN_LIFE_MINUTE = 60 * 24;
    public final int PASSWORD_RESET_TOKEN_LENGTH = 30;

    @Resource
    private UserPasswordResetTokenDao userPasswordResetTokenDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private UserEmailBuilder userEmailBuilder;

    @Resource
    private SecurityService securityService;

    @Resource
    private EmailSender emailSender;

    @Resource
    private RandomCodeCreater randomCodeCreater;

    private CreatePasswordResetTokenResult createAndSaveToken(String email, String code) {
        userPasswordResetTokenDao.clear(TOKEN_LIFE_MINUTE);
        UserAuth userAuth = userAuthDao.get(email);
        if (userAuth == null) {
            return CreatePasswordResetTokenResult.USER_NOT_FOUND;
        }

        User user = userAuth.getUser();
        UserPasswordResetToken token = new UserPasswordResetToken();
        token.setCode(code);
        token.setCreateDate(new Date());
        token.setUser(user);
        userPasswordResetTokenDao.save(token);
        return null;
    }

    private void sendPasswordResetTokenEmail(String email, String name, String token) {
        EmailMessage message = userEmailBuilder.buildPasswordResetTokenEmail(email, name, token);
        emailSender.sendWithBlocking(message);
    }

    @Override
    public CreatePasswordResetTokenResult createPasswordResetToken(String email) {
        email = ValidationTool.preprocessEmail(email);
        if (ValidationTool.checkEmail(email) == false) {
            return CreatePasswordResetTokenResult.EMAIL_FORMAT_ERROR;
        }
        String code = randomCodeCreater.createRandomCode(PASSWORD_RESET_TOKEN_LENGTH, RandomCodeCreater.CodeType.MIX);
        CreatePasswordResetTokenResult result = createAndSaveToken(email, code);
        if (result != null) {
            return result;
        }
        UserAuth userAuth = userAuthDao.get(email);
        String name = userAuth.getLabel();
        sendPasswordResetTokenEmail(email, name, code);
        return CreatePasswordResetTokenResult.SUCCESS;
    }

    @Override
    public ResetPasswordResult resetPassword(String tokenCode, String originalPassword, String passwordAgain) {
        if (originalPassword == null || originalPassword.length() < 8) {
            return ResetPasswordResult.PASSWORD_SHORTER_THAN_8;
        } else if (originalPassword.equals(passwordAgain) == false) {
            return ResetPasswordResult.PASSWORD_AGAIN_ERROR;
        }
        try {
            userPasswordResetTokenDao.clear(TOKEN_LIFE_MINUTE);
            UserPasswordResetToken token = userPasswordResetTokenDao.get(tokenCode);
            if (token == null) {
                return ResetPasswordResult.CODE_WORING;
            }
            final String passwordSalt = securityService.createPasswordSalt();
            final String password = securityService.uglifyPassoword(originalPassword, passwordSalt);

            token.getUser().setPassword(password);
            token.getUser().setPasswordSalt(passwordSalt);
            return ResetPasswordResult.SUCCESS;
        } catch (RuntimeException re) {
            return ResetPasswordResult.DATABASE_EXCEPTION;
        }
    }

}
