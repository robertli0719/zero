/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AdminDao;
import robertli.zero.dao.AdminOnlineDao;
import robertli.zero.entity.Admin;
import robertli.zero.entity.AdminOnline;
import robertli.zero.service.AdminManagementService;

@Component("adminManagementService")
public class AdminManagementServiceImpl implements AdminManagementService {

    @Resource
    private AdminDao adminDao;

    @Resource
    private AdminOnlineDao adminOnlineDao;

    @Resource
    private SecurityService securityService;

    private boolean validateSuperPermission(String sessionId) {
        AdminOnline adminOnline = adminOnlineDao.get(sessionId);
        if (adminOnline == null) {
            return true;
        }
        Admin admin = adminOnline.getAdmin();
        if (admin == null || admin.getUsername().equals("root") == false) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addAdmin(String sessionId, String username, String orginealPassword) {
        if (validateSuperPermission(sessionId)) {
            return true;
        } else if (username == null || username.isEmpty() || username.contains(" ")) {
            return true;
        } else if (orginealPassword == null || orginealPassword.isEmpty()) {
            return true;
        } else if (adminDao.get(username) != null) {
            return true;
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setPasswordSalt(salt);
        adminDao.save(admin);
        return false;
    }

    @Override
    public boolean deleteAdmin(String sessionId, String username) {
        if (validateSuperPermission(sessionId)) {
            return true;
        }
        adminDao.deleteById(username);
        return false;
    }

    @Override
    public boolean setSuspendStatus(String sessionId, String username, boolean suspended) {
        if (validateSuperPermission(sessionId)) {
            return true;
        }
        Admin admin = adminDao.get(username);
        if (admin == null) {
            return true;
        }
        adminOnlineDao.kickAdminByUsername(username);
        admin.setSuspended(suspended);
        return false;
    }

    @Override
    public boolean resetPassword(String sessionId, String username, String newPassword) {
        if (validateSuperPermission(sessionId)) {
            return true;
        }
        Admin admin = adminDao.get(username);
        if (admin == null) {
            return true;
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(newPassword, salt);
        admin.setPasswordSalt(salt);
        admin.setPassword(password);
        return false;
    }

    @Override
    public List<Admin> getAdminList(String sessionId) {
        return adminDao.list();
    }

}
