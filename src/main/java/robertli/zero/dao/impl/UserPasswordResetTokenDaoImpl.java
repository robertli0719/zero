/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserPasswordResetTokenDao;
import robertli.zero.entity.UserPasswordResetToken;

/**
 *
 * @author Robert Li
 */
@Component("userPasswordResetTokenDao")
public class UserPasswordResetTokenDaoImpl extends GenericHibernateDao<UserPasswordResetToken, String> implements UserPasswordResetTokenDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clear(int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from UserPasswordResetToken where createDate<=:endDate");
        query.setParameter("endDate", endDate);
        query.executeUpdate();
    }

}
