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

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AdminDao;
import robertli.zero.dao.AdminOnlineDao;
import robertli.zero.entity.Admin;
import robertli.zero.entity.AdminOnline;
import robertli.zero.service.AdminService;

@Component("adminService")
public class AdminServiceImpl implements AdminService {
    
    public static final int SESSION_LIFE_MINUTE = 60;
    
    @Resource
    private AdminDao adminDao;
    
    @Resource
    private AdminOnlineDao adminOnlineDao;
    
    @Resource
    private SecurityService securityService;
    
    @Resource
    private AppConfiguration appConfiguration;
    
    @Override
    public void init() {
        String name = appConfiguration.getInitAdminName();
        String password = appConfiguration.getInitAdminPassword();
        adminDao.initAdmin(name, password);
    }
    
    @Override
    public Admin getCurrentAdmin(String sessionId) {
        adminOnlineDao.clear(SESSION_LIFE_MINUTE);
        AdminOnline adminOnline = adminOnlineDao.get(sessionId);
        if (adminOnline != null) {
            adminOnline.setLastActiveDate(new Date());
            return adminOnline.getAdmin();
        }
        return null;
    }
    
    @Override
    public AdminLoginResult login(String sessionId, String username, String orginealPassword) {
        try {
            Admin admin = adminDao.get(username);
            if (admin == null) {
                return AdminLoginResult.PASSWORD_WRONG;
            }
            String salt = admin.getPasswordSalt();
            String password = securityService.uglifyPassoword(orginealPassword, salt);
            if (admin.getPassword().equals(password) == false) {
                return AdminLoginResult.PASSWORD_WRONG;
            } else if (admin.isSuspended()) {
                return AdminLoginResult.SUSPENDED_STATUS;
            }
            adminOnlineDao.clear(SESSION_LIFE_MINUTE);
            if (adminOnlineDao.isExist(sessionId)) {
                return AdminLoginResult.ADMIN_LOGINED;
            }
            adminOnlineDao.saveAdminOnline(sessionId, admin);
            return AdminLoginResult.SUCCESS;
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AdminLoginResult.DATABASE_EXCEPTION;
        }
    }
    
    @Override
    public boolean logout(String sessionId) {
        try {
            AdminOnline adminOnline = adminOnlineDao.get(sessionId);
            if (adminOnline != null) {
                adminOnlineDao.delete(adminOnline);
            }
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }
    
    @Override
    public AdminResetPasswordResult resetPassword(String sessionId, String oldPassword, String newPassword, String newPasswordAgain) {
        Admin admin = getCurrentAdmin(sessionId);
        if (admin == null) {
            return AdminResetPasswordResult.NEED_LOGIN;
        }
        if (newPassword == null || newPassword.length() < 8) {
            return AdminResetPasswordResult.NEW_PASSWORD_IS_TOO_EASY;
        } else if (newPassword.equals(newPasswordAgain) == false) {
            return AdminResetPasswordResult.PASSWORD_AGAIN_ERROR;
        }
        String salt = admin.getPasswordSalt();
        String password = securityService.uglifyPassoword(oldPassword, salt);
        if (admin.getPassword().equals(password) == false) {
            return AdminResetPasswordResult.OLD_PASSWORD_WRONG;
        }
        
        String newSalt = securityService.createPasswordSalt();
        String newPasswordVal = securityService.uglifyPassoword(newPassword, newSalt);
        
        admin.setPassword(newPasswordVal);
        admin.setPasswordSalt(newSalt);
        return AdminResetPasswordResult.SUCCESS;
    }
    
}
