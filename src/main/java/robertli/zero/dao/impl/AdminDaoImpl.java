/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AdminDao;
import robertli.zero.entity.Admin;

/**
 *
 * @author Robert Li
 */
@Component("adminDao")
public class AdminDaoImpl extends GenericHibernateDao<Admin, String> implements AdminDao {

    @Resource
    private SecurityService securityService;

    @Override
    public void initAdmin(String username, String orginealPassword) {
        Admin checkResult = get(username);
        if (checkResult != null) {
            return;
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setPasswordSalt(salt);
        save(admin);
    }

}
