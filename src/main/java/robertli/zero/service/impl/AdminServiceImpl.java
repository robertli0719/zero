/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
