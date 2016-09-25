/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.service.impl;

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
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setPasswordSalt(password);
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

}
