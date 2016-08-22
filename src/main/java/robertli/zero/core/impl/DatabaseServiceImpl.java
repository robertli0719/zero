package robertli.zero.core.impl;

import java.util.List;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.reflections.Reflections;
import robertli.zero.core.DatabaseService;

/**
 * This interface is designed for implementing database service.<br>
 * This service will build and hold the session factory for Hibernate.
 *
 * @author Robert Li v1.0 2016-08-21
 */
public class DatabaseServiceImpl implements DatabaseService {

    private final SessionFactory factory;

    public DatabaseServiceImpl(DatabaseConfiguration dbConfiguration) {
        factory = createSessionFactory(dbConfiguration);
    }

    private void mappingEntityClasses(Configuration configuration, String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(javax.persistence.Entity.class);
        for (Class<?> clazz : classes) {
            configuration.addAnnotatedClass(clazz);
        }
    }

    private void mappingEntityClasses(Configuration configuration, List<String> packageList) {
        if (packageList == null) {
            return;
        }
        for (String packageName : packageList) {
            mappingEntityClasses(configuration, packageName);
        }
    }

    private void overwriteHibernateConfiguration(Configuration configuration, DatabaseConfiguration dbConfiguration) {
        String dbHost = dbConfiguration.getHost();
        String dbPort = dbConfiguration.getPort();
        String dbName = dbConfiguration.getName();
        String dbUsername = dbConfiguration.getUsername();
        String dbPassword = dbConfiguration.getPassword();

        String dbURL = null;
        if (dbHost != null && dbPort != null && dbName != null) {
            dbURL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        }
        if (dbURL != null) {
            configuration.setProperty("hibernate.connection.url", dbURL);
        }
        if (dbUsername != null) {
            configuration.setProperty("hibernate.connection.username", dbUsername);
        }
        if (dbPassword != null) {
            configuration.setProperty("hibernate.connection.password", dbPassword);
        }
    }

    private SessionFactory createSessionFactory(DatabaseConfiguration dbConfiguration) {
        Configuration configuration = new Configuration();
        configuration.configure();

        overwriteHibernateConfiguration(configuration, dbConfiguration);
        List<String> packageList = dbConfiguration.getEntityPackageList();
        mappingEntityClasses(configuration, packageList);

        ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
        srBuilder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public SessionFactory getSessionFactory() {
        return factory;
    }

}
