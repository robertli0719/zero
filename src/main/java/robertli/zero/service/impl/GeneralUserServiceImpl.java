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
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.GeneralUserDto;
import robertli.zero.dto.user.UserRegisterDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserRegister;
import robertli.zero.service.GeneralUserService;
import robertli.zero.service.UserEmailBuilder;
import robertli.zero.service.UserService;
import robertli.zero.tool.ValidationTool;

@Component("generalUserService")
public class GeneralUserServiceImpl implements GeneralUserService {

    public final int REGISTER_LIFE_MINUTE = 60 * 24;

    @Resource
    private RandomCodeCreater randomCodeCreater;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserEmailBuilder userEmailBuilder;

    @Resource
    private EmailSender emailSender;

    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private UserRegisterDao userRegisterDao;

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
            throw new RestException("REGISTER_EXIST", "Please check your email to verifiy your register.", errorDetail, HttpStatus.CONFLICT);
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
    public void verifiyRegister(String verifiedCode) {
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

}
