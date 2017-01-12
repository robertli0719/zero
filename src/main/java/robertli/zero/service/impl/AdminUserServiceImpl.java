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
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.service.AdminUserService;
import robertli.zero.service.UserService;

@Component("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private UserService userService;

    @Resource
    private UserRoleItemDao userRoleItemDao;

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
        List<User> userList = userService.getUserListByPlatform(UserService.USER_PLATFORM_ADMIN);
        return modelMapper.map(userList, new TypeToken<List<AdminUserDto>>() {
        }.getType());
    }

    @Override
    public List<AdminUserDto> getAdminRootUserList() {
        List<User> userList = userService.getUserListByRole(UserService.USER_ROLE_ADMIN_ROOT);
        return modelMapper.map(userList, new TypeToken<List<AdminUserDto>>() {
        }.getType());
    }

    @Override
    public AdminUserDto getAdminUser(String username) {
        if (isAdminUser(username) == false) {
            return null;
        }
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
        return modelMapper.map(user, AdminUserDto.class);
    }

    @Override
    public boolean isAdminUser(String username) {
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
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
    public boolean isRoot(String username) {
        if (isAdminUser(username) == false) {
            return false;
        }
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
        return userRoleItemDao.isExist(user.getId(), UserService.USER_ROLE_ADMIN_ROOT);
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

        User adminUser = userService.addUser(userPlatformName, username, usernameType, label, orginealPassword, name, null, locked);
        return adminUser.getId();
    }

    @Override
    public void deleteAdminUser(String username) {
        if (isAdminUser(username) == false) {
            return;
        }
        userService.deleteUser(UserService.USER_PLATFORM_ADMIN, username);
    }

    @Override
    public void addRootRole(String username) {
        if (isAdminUser(username) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
        userRoleItemDao.put(user.getId(), UserService.USER_ROLE_ADMIN_ROOT);
    }

    @Override
    public void deleteRootRole(String username) {
        if (isAdminUser(username) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
        userRoleItemDao.delete(user.getId(), UserService.USER_ROLE_ADMIN_ROOT);
    }

    @Override
    public void resetPassword(String username, String orginealPassword) {
        if (isAdminUser(username) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        userService.resetPassword(UserService.USER_PLATFORM_ADMIN, username, orginealPassword);
    }

    @Override
    public void lockAdminUser(String username) {
        if (isAdminUser(username) == false) {
            return;
        }
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
        user.setLocked(true);
    }

    @Override
    public void unlockAdminUser(String username) {
        if (isAdminUser(username) == false) {
            return;
        }
        User user = userService.getUser(UserService.USER_PLATFORM_ADMIN, username);
        user.setLocked(false);
    }

}
