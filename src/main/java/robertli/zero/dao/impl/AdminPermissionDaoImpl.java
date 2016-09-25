/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.AdminPermissionDao;
import robertli.zero.entity.AdminPermission;

@Component("adminPermissionDao")
public class AdminPermissionDaoImpl extends GenericHibernateDao<AdminPermission, String> implements AdminPermissionDao {

}
