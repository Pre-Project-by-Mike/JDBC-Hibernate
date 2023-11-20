package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    // реализуйте настройку соеденения с БД
    //Hibernate config
    private static final String hibernate_show_sql = "true";

    private static Configuration getPostgresSqlHibernateConfiguration(String hostName, String dbName,
                                                                      String userName, String password) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://" + hostName + ":5432/" + dbName);
        configuration.setProperty("hibernate.connection.username", userName);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        return configuration;
    }

    public static Configuration getPostgresSqlHibernateConfiguration() {
        String hostName = "localhost";
        String dbName = "Application";
        String userName = "postgres";
        String password = "admin123";
        return getPostgresSqlHibernateConfiguration(hostName, dbName, userName,password);
    }


    public static SessionFactory createSessionFactory(Configuration configuration) {
        return configuration.buildSessionFactory();
    }

}
