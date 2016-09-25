/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.AdminLogDao;
import robertli.zero.entity.AdminLog;

/**
 *
 * @author Robert Li
 */
@Component("adminLogDao")
public class AdminLogDaoImpl extends GenericHibernateDao<AdminLog, Integer> implements AdminLogDao {

}
