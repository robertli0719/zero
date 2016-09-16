package robertli.zero.service;

import org.hibernate.SessionFactory;

/**
 * This interface is designed for implementing database service.<br>
 * This service will build and hold the session factory for Hibernate.
 *
 * @version 1.0 2016-08-16
 * @author Robert Li
 */
public interface DatabaseService {

    public SessionFactory getSessionFactory();
}
