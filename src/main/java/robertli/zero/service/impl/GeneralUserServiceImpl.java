/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import robertli.zero.controller.RestException;
import robertli.zero.core.EmailMessage;
import robertli.zero.core.EmailSender;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.SecurityService;
import robertli.zero.core.SmsSender;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserMobileBindingTokenDao;
import robertli.zero.dao.UserPasswordResetTokenDao;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.GeneralUserDto;
import robertli.zero.dto.user.MobileBindingApplicationDto;
import robertli.zero.dto.user.MobileBindingDto;
import robertli.zero.dto.user.PasswordResetApplicationDto;
import robertli.zero.dto.user.PasswordResetterDto;
import robertli.zero.dto.user.UserRegisterDto;
import robertli.zero.entity.AccessToken;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserMobileBindingToken;
import robertli.zero.entity.UserPasswordResetToken;
import robertli.zero.entity.UserRegister;
import robertli.zero.service.GeneralUserService;
import robertli.zero.service.UserEmailBuilder;
import robertli.zero.service.UserService;
import robertli.zero.tool.ValidationTool;

@Component("generalUserService")
public class GeneralUserServiceImpl implements GeneralUserService {

    private final int REGISTER_LIFE_MINUTE = 60 * 24;
    private final int RESETPASSWORD_TOKEN_LIFE_MINUTE = 5;
    private final int MOBILE_BINDING_TOKEN_LIFE_MINUTE = 5;

    @Resource
    private RandomCodeCreater randomCodeCreater;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserEmailBuilder userEmailBuilder;

    @Resource
    private EmailSender emailSender;

    @Resource
    private SmsSender smsSender;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private UserRegisterDao userRegisterDao;

    @Resource
    private UserPasswordResetTokenDao userPasswordResetTokenDao;

    @Resource
    private UserMobileBindingTokenDao userMobileBindingTokenDao;

    private GeneralUserDto convert(User user) {
        final String uid = user.getUid();
        final String name = user.getName();
        String email = null;
        String telephone = null;
        for (UserAuth auth : user.getUserAuthList()) {
            final String usernameType = auth.getUsernameType();
            switch (usernameType) {
                case UserService.USERNAME_TYPE_EMAIL:
                    email = auth.getLabel();
                    break;
                case UserService.USERNAME_TYPE_TELEPHONE:
                    telephone = auth.getLabel();
                    break;
            }
        }
        final GeneralUserDto dto = new GeneralUserDto();
        dto.setEmail(email);
        dto.setName(name);
        dto.setTelephone(telephone);
        dto.setUid(uid);
        return dto;
    }

    private List<GeneralUserDto> convert(List<User> userList) {
        final List<GeneralUserDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            final GeneralUserDto dto = convert(user);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private QueryResult<GeneralUserDto> convert(QueryResult<User> userQueryResult) {
        final int offset = userQueryResult.getOffset();
        final int limit = userQueryResult.getLimit();
        final int count = userQueryResult.getCount();
        final List<User> userList = userQueryResult.getResultList();
        final List<GeneralUserDto> resultList = convert(userList);
        return new QueryResult<>(offset, limit, count, resultList);
    }

    @Override
    public QueryResult<GeneralUserDto> getGeneralUserList(int offset, int limit) {
        final String platformName = UserService.USER_PLATFORM_GENERAL;
        final QueryResult<User> userQueryResult = userService.getUserListByPlatform(platformName, offset, limit);
        return convert(userQueryResult);
    }

    @Override
    public GeneralUserDto getGeneralUser(String uid) {
        User user = userDao.getUserByUid(uid);
        return convert(user);
    }

    private String makeAuthId(String email) {
        final String userType = UserService.USER_TYPE_GENERAL;
        final String userPlatform = UserService.USER_PLATFORM_GENERAL;
        final String username = ValidationTool.preprocessEmail(email);
        return userAuthDao.makeAuthId(userType, userPlatform, username);
    }

    private String makeAuthId(UserRegisterDto registerDto) {
        final String email = registerDto.getEmail();
        return makeAuthId(email);
    }

    private UserRegister createUserRegister(UserRegisterDto registerDto) {
        final String authId = makeAuthId(registerDto);
        final String label = registerDto.getEmail().trim();
        final String name = registerDto.getName();

        final String originalPassword = registerDto.getPassword();
        final String salt = securityService.createPasswordSalt();
        final String password = securityService.uglifyPassoword(originalPassword, salt);
        final String verifiedCode = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        final UserRegister userRegister = new UserRegister();
        userRegister.setAuthId(authId);
        userRegister.setAuthLabel(label);
        userRegister.setAuthType(UserService.USERNAME_TYPE_EMAIL);
        userRegister.setName(name);
        userRegister.setPassword(password);
        userRegister.setPasswordSalt(salt);
        userRegister.setSignDate(new Date());
        userRegister.setVerifiedCode(verifiedCode);
        return userRegister;
    }

    @Override
    public void registerByEmail(UserRegisterDto registerDto) {
        userRegisterDao.clear(REGISTER_LIFE_MINUTE);

        final String authId = makeAuthId(registerDto);
        if (userAuthDao.isExist(authId)) {
            String errorDetail = "this user is exist:" + authId;
            throw new RestException("USER_EXIST", "This user is registered before.", errorDetail, HttpStatus.CONFLICT);
        } else if (userRegisterDao.isExist(authId)) {
            String errorDetail = "this register is exist:" + authId;
            throw new RestException("REGISTER_EXIST", "Please check your email to verify your register.", errorDetail, HttpStatus.CONFLICT);
        }
        final UserRegister userRegister = createUserRegister(registerDto);
        userRegisterDao.save(userRegister);
    }

    @Override
    public void sendRegisterVerificationEmail(String email) {
        email = ValidationTool.preprocessEmail(email);
        final String authId = makeAuthId(email);
        if (userAuthDao.isExist(authId)) {
            String errorDetail = "this user is exist:" + authId;
            throw new RestException("USER_EXIST", "This user is registered before.", errorDetail, HttpStatus.CONFLICT);
        } else if (userRegisterDao.isExist(authId) == false) {
            String errorDetail = "this register is not exist:" + authId;
            throw new RestException("REGISTER_NOT_EXIST", "Can't found any register for this email", errorDetail, HttpStatus.FORBIDDEN);
        }
        final UserRegister userRegister = userRegisterDao.get(authId);
        final String name = userRegister.getName();
        final String verifiedCode = userRegister.getVerifiedCode();
        final EmailMessage msg = userEmailBuilder.buildUserRegisterVerificationEmail(email, name, verifiedCode);
        emailSender.send(msg);
    }

    private void addGeneralUser(UserRegister userRegister) {
        final String userPlatformName = UserService.USER_PLATFORM_GENERAL;
        final String username = userRegister.getAuthId().split(":")[2];
        final String usernameType = UserService.USERNAME_TYPE_EMAIL;
        final String label = userRegister.getAuthLabel();
        final String password = userRegister.getPassword();
        final String passwordSalt = userRegister.getPasswordSalt();
        final String name = userRegister.getName();
        userService.addUser(userPlatformName, username, usernameType, label, password, passwordSalt, name, false);
    }

    @Override
    public void verifyRegister(String verifiedCode) {
        if (userRegisterDao.isExistVerifiedCode(verifiedCode) == false) {
            String errorDetail = "this register is not exist for code:" + verifiedCode;
            throw new RestException("REGISTER_NOT_EXIST", "Can't found any register for this verified code", errorDetail, HttpStatus.FORBIDDEN);
        }

        final UserRegister userRegister = userRegisterDao.getByVerifiedCode(verifiedCode);
        final String authId = userRegister.getAuthId();
        if (userAuthDao.isExist(authId)) {
            String errorDetail = "this user is exist:" + authId;
            throw new RestException("USER_EXIST", "This user is registered before.", errorDetail, HttpStatus.CONFLICT);
        }
        addGeneralUser(userRegister);
        userRegisterDao.delete(userRegister);
    }

    @Override
    public void applyPasswordResetToken(PasswordResetApplicationDto applicationDto) {
        final String inputEmail = applicationDto.getEmail();
        final String email = ValidationTool.preprocessEmail(inputEmail);
        final String authId = makeAuthId(email);
        if (userAuthDao.isExist(authId) == false) {
            String errorDetail = "this user is not exist:" + authId;
            throw new RestException("USER_NOT_EXIST", "This user is not exist", errorDetail, HttpStatus.CONFLICT);
        }
        final User user = userService.getUser(UserService.USER_PLATFORM_GENERAL, email);
        final String code = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String name = user.getName();

        final UserPasswordResetToken token = new UserPasswordResetToken();
        token.setCode(code);
        token.setCreateDate(new Date());
        token.setUser(user);
        userPasswordResetTokenDao.clear(RESETPASSWORD_TOKEN_LIFE_MINUTE);
        userPasswordResetTokenDao.save(token);
        final EmailMessage msg = userEmailBuilder.buildPasswordResetTokenEmail(email, name, code);
        emailSender.send(msg);
    }

    @Override
    public void resetPasswordByToken(PasswordResetterDto resetterDto) {
        final String code = resetterDto.getCode();
        final String originalPassword = resetterDto.getPassword();
        userPasswordResetTokenDao.clear(RESETPASSWORD_TOKEN_LIFE_MINUTE);
        if (userPasswordResetTokenDao.isExist(code) == false) {
            String errorDetail = "This is a invalid code: " + code;
            throw new RestException("FORBIDDEN", "This link is invalide or timeout.", errorDetail, HttpStatus.FORBIDDEN);
        }
        final UserPasswordResetToken token = userPasswordResetTokenDao.get(code);
        final User user = token.getUser();
        final String passwordSalt = securityService.createPasswordSalt();
        final String password = securityService.uglifyPassoword(originalPassword, passwordSalt);
        user.setPassword(password);
        user.setPasswordSalt(passwordSalt);
    }

    private void validateAccessToken(String accessToken) {
        if (accessToken == null || accessTokenDao.isExist(accessToken) == false) {
            final String errorMsg = "For reset your password, you should login first";
            final String errorDetail = "accessToken is null or not exist";
            throw new RestException("UNAUTHORIZED", errorMsg, errorDetail, HttpStatus.UNAUTHORIZED);
        }
    }

    private User pickUser(String accessToken) {
        validateAccessToken(accessToken);
        final AccessToken token = accessTokenDao.get(accessToken);
        return token.getUser();
    }

    @Override
    public void applyToBindingMobilePhone(String accessToken, MobileBindingApplicationDto applicationDto) {
        final User user = pickUser(accessToken);
        final int countryCode = applicationDto.getCountryCode();
        final String phoneNumber = applicationDto.getPhoneNumber();
        final String combinedPhoneNumber = "+" + countryCode + phoneNumber;
        final String code = randomCodeCreater.createRandomCode(7, RandomCodeCreater.CodeType.NUMBERS);

        final UserMobileBindingToken bindingToken = new UserMobileBindingToken();
        bindingToken.setCode(code);
        bindingToken.setCreateDate(new Date());
        bindingToken.setPhoneNumber(combinedPhoneNumber);
        bindingToken.setUser(user);

        userMobileBindingTokenDao.clear(MOBILE_BINDING_TOKEN_LIFE_MINUTE);
        userMobileBindingTokenDao.save(bindingToken);
        smsSender.send(combinedPhoneNumber, "Your verification code for validation is: " + code + ".");
    }

    private void validateBindingToken(User user, UserMobileBindingToken bindingToken) {
        final String accessUserUid = user.getUid();
        final String bindingTokenUid = bindingToken.getUser().getUid();
        if (accessUserUid == null || accessUserUid.equals(bindingTokenUid) == false) {
            final String errorMsg = "This verified code is not belong to you.";
            final String errorDetail = "accessToken.user.uid!=bindingToken.user.uid";
            throw new RestException("FORBIDDEN", errorMsg, errorDetail, HttpStatus.FORBIDDEN);
        }
    }

    private void bindingMobilePhone(User user, UserMobileBindingToken bindingToken) {
        final String userPlatform = UserService.USER_PLATFORM_GENERAL;
        final String userType = UserService.USERNAME_TYPE_TELEPHONE;
        final String username = bindingToken.getPhoneNumber();
        final String label = username;

        final String authId = userAuthDao.makeAuthId(userType, username, username);
        if (userAuthDao.isExist(authId)) {
            final String errorMsg = "This phoneNumber has been registered.";
            final String errorDetail = "authId is exist: " + authId;
            throw new RestException("CONFLICT", errorMsg, errorDetail, HttpStatus.CONFLICT);
        }
        userAuthDao.saveUserAuth(userPlatform, username, label, userType, user);
    }

    @Override
    public void bindingMobilePhone(String accessToken, MobileBindingDto bindingDto) {
        final User user = pickUser(accessToken);
        final int countryCode = bindingDto.getCountryCode();
        final String phoneNumber = bindingDto.getPhoneNumber();
        final String combinedPhoneNumber = "+" + countryCode + phoneNumber;

        final String verifiedCode = bindingDto.getVerifiedCode();
        userMobileBindingTokenDao.clear(MOBILE_BINDING_TOKEN_LIFE_MINUTE);
        if (userMobileBindingTokenDao.isExist(combinedPhoneNumber, verifiedCode) == false) {
            final String errorMsg = "Your verified code is timeout, please check again.";
            final String errorDetail = "can't found this code in database.";
            throw new RestException("FORBIDDEN", errorMsg, errorDetail, HttpStatus.FORBIDDEN);
        }
        final UserMobileBindingToken bindingToken = userMobileBindingTokenDao.get(combinedPhoneNumber, verifiedCode);
        validateBindingToken(user, bindingToken);
        bindingMobilePhone(user, bindingToken);
    }

}
