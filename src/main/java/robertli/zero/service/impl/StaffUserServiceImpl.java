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
import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.StaffUserDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.service.StaffUserService;
import robertli.zero.service.UserService;

@Component("staffUserService")
public class StaffUserServiceImpl implements StaffUserService {

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
                if (item.getUserRole().getName().equals(UserService.USER_ROLE_PLATFORM_ROOT)) {
                    return true;
                }
            }
            return false;
        };

        modelMapper.addMappings(new PropertyMap<User, StaffUserDto>() {

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

    private QueryResult<StaffUserDto> convert(QueryResult<User> userQueryResult) {
        final int offset = userQueryResult.getOffset();
        final int limit = userQueryResult.getLimit();
        final List<User> userList = userQueryResult.getResultList();
        final List<StaffUserDto> resultList = modelMapper.map(userList, new TypeToken<List<StaffUserDto>>() {
        }.getType());
        final int count = userQueryResult.getCount();
        return new QueryResult<>(offset, limit, count, resultList);
    }

    @Override
    public QueryResult<StaffUserDto> getStaffUserList(final String userPlatformName, int offset, int limit) {
        final QueryResult<User> userQueryResult = userService.getUserListByPlatform(userPlatformName, offset, limit);
        return convert(userQueryResult);
    }

    @Override
    public QueryResult<StaffUserDto> getStaffRootUserList(String userPlatformName, int offset, int limit) {
        final QueryResult<User> userQueryResult = userService.getUserListByRole(UserService.USER_ROLE_PLATFORM_ROOT, offset, limit);
        return convert(userQueryResult);
    }

    @Override
    public StaffUserDto getStaffUser(String userPlatformName, String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            return null;
        }
        User user = userService.getUser(userPlatformName, username);
        return modelMapper.map(user, StaffUserDto.class);
    }

    @Override
    public boolean isStaffUser(final String userPlatformName, final String username) {
        User user = userService.getUser(userPlatformName, username);
        if (user == null) {
            return false;
        }
        UserPlatform userPlatform = user.getUserPlatform();
        if (userPlatform == null || userPlatform.getName() == null) {
            return false;
        }
        return userPlatform.getName().equals(userPlatformName);
    }

    @Override
    public boolean isPlatformRoot(final String userPlatformName, final String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            return false;
        }
        User user = userService.getUser(userPlatformName, username);
        return userRoleItemDao.isExist(user.getId(), UserService.USER_ROLE_PLATFORM_ROOT);
    }

    @Override
    public void addStaffUser(String userPlatformName, final StaffUserDto staffUserDto) {
        String originalPassword = staffUserDto.getPassword();
        String username = staffUserDto.getUsername();
        boolean locked = staffUserDto.isLocked();
        String usernameType = UserService.USERNAME_TYPE_STRING;
        String name = username;
        String label = username;
        userService.addUser(userPlatformName, username, usernameType, label, originalPassword, name, locked);
    }

    @Override
    public void deleteStaffUser(final String userPlatformName, final String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            throw new RuntimeException("no this staff in this platform");
        }
        userService.deleteUser(userPlatformName, username);
    }

    @Override
    public void addPlatformRootRole(final String userPlatformName, final String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        User user = userService.getUser(userPlatformName, username);
        userRoleItemDao.put(user.getId(), UserService.USER_ROLE_PLATFORM_ROOT);
    }

    @Override
    public void deletePlatformRootRole(final String userPlatformName, final String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        User user = userService.getUser(userPlatformName, username);
        userRoleItemDao.delete(user.getId(), UserService.USER_ROLE_PLATFORM_ROOT);
    }

    @Override
    public void resetPassword(final String userPlatformName, final String username, final String originalPassword) {
        if (isStaffUser(userPlatformName, username) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        userService.resetPassword(userPlatformName, username, originalPassword);
    }

    @Override
    public void lockStaffUser(final String userPlatformName, final String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        User user = userService.getUser(userPlatformName, username);
        user.setLocked(true);
    }

    @Override
    public void unlockStaffUser(String userPlatformName, final String username) {
        if (isStaffUser(userPlatformName, username) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        User user = userService.getUser(userPlatformName, username);
        user.setLocked(false);
    }

}
