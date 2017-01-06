/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserPlatformDao;
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.service.AdminUserService;
import robertli.zero.service.UserService;

@Component("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserPlatformDao userPlatformDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleItemDao userRoleItemDao;

    @Resource
    private UserAuthDao userAuthDao;

    @PostConstruct
    public void init() {

        Converter userToRootConverter = (Converter<User, Boolean>) (MappingContext<User, Boolean> mc) -> {
            User user = mc.getSource();
            if (user == null || user.getUserRoleItemList() == null) {
                throw new RuntimeException("Converter can't get UserRoleItemList");
            }
            for (UserRoleItem item : user.getUserRoleItemList()) {
                if (item.getUserRole().getName().equals(UserService.USER_ROLE_ADMIN_ROOT)) {
                    return true;
                }
            }
            return false;
        };

        modelMapper.addMappings(new PropertyMap<User, AdminUserDto>() {

            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setUsername(source.getName());
                map().setLocked(source.isLocked());
                map().setPassword(source.getPassword());
                // Note: Since a source object is given, the "false"value passed to set[Method] is unused.
                using(userToRootConverter).map(source).setRoot(false);
            }
        });

    }

    @Override
    public List<AdminUserDto> getAdminUserList() {
        List<User> userList = userDao.getUserListByPlatform(UserService.USER_PLATFORM_ADMIN);
        System.out.println("getAdminUserList userList size:" + userList.size());
        List<AdminUserDto> dtoList = modelMapper.map(userList, new TypeToken<List<AdminUserDto>>() {
        }.getType());
        return dtoList;
    }

    @Override
    public List<AdminUserDto> getAdminRootUserList() {
        final String roleName = UserService.USER_ROLE_ADMIN_ROOT;
        List<User> userList = userDao.getUserListByRole(roleName);
        return modelMapper.map(userList, new TypeToken<List<AdminUserDto>>() {
        }.getType());
    }

    @Override
    public AdminUserDto getAdminUser(int userId) {
        System.out.println("getAdminUser() run");
        if (isAdminUser(userId) == false) {
            return null;
        }
        User user = userDao.get(userId);
        return modelMapper.map(user, AdminUserDto.class);
    }

    @Override
    public boolean isAdminUser(int userId) {
        User user = userDao.get(userId);
        if (user == null) {
            return false;
        }
        UserPlatform userPlatform = user.getUserPlatform();
        if (userPlatform == null || userPlatform.getName() == null) {
            return false;
        }
        return userPlatform.getName().equals(UserService.USER_PLATFORM_ADMIN);
    }

    @Override
    public boolean isRoot(int userId) {
        if (isAdminUser(userId) == false) {
            return false;
        }
        return userRoleItemDao.isExist(userId, UserService.USER_ROLE_ADMIN_ROOT);
    }

    @Override
    public int addAdminUser(AdminUserDto adminUserDto) {
        String orginealPassword = adminUserDto.getPassword();
        String username = adminUserDto.getUsername();
        boolean locked = adminUserDto.isLocked();

        String userPlatformName = UserService.USER_PLATFORM_ADMIN;
        String usernameType = UserService.USERNAME_TYPE_STRING;
        String name = username;
        String label = username;
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);

        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        User adminUser = new User();
        adminUser.setName(name);
        adminUser.setPassword(password);
        adminUser.setPasswordSalt(salt);
        adminUser.setUserPlatform(userPlatform);
        adminUser.setLocked(locked);
        userDao.save(adminUser);

        userAuthDao.saveUserAuth(userPlatformName, username, label, usernameType, adminUser);
        return adminUser.getId();
    }

    @Override
    public void deleteAdminUser(int userId) {
        if (isAdminUser(userId) == false) {
            return;
        }
        User user = userDao.get(userId);
        for (UserAuth userAuth : user.getUserAuthList()) {
            userAuthDao.delete(userAuth);
        }
        for (UserRoleItem userRoleItem : user.getUserRoleItemList()) {
            userRoleItemDao.delete(userRoleItem);
        }
        userDao.delete(user);
    }

    @Override
    public void addRootRole(int userId) {
        if (isAdminUser(userId) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        userRoleItemDao.put(userId, UserService.USER_ROLE_ADMIN_ROOT);
    }

    @Override
    public void removeRootRole(int userId) {
        if (isAdminUser(userId) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        userRoleItemDao.remove(userId, UserService.USER_ROLE_ADMIN_ROOT);
    }

    @Override
    public void resetPassword(int userId, String orginealPassword) {
        if (isAdminUser(userId) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        User user = userDao.get(userId);
        user.setPassword(password);
        user.setPasswordSalt(salt);
    }

    @Override
    public void lockAdminUser(int userId) {
        if (isAdminUser(userId) == false) {
            return;
        }
        User user = userDao.get(userId);
        user.setLocked(true);
    }

    @Override
    public void unlockAdminUser(int userId) {
        if (isAdminUser(userId) == false) {
            return;
        }
        User user = userDao.get(userId);
        user.setLocked(false);
    }

}
