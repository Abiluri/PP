package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NAME = "`pp_1_1_3-4`.`users`";
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static String getName() {
        return NAME;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty("hibernate.connection.driver_class", DRIVER);
            configuration.setProperty("hibernate.connection.url", URL);
            configuration.setProperty("hibernate.connection.username", USERNAME);
            configuration.setProperty("hibernate.connection.password", PASSWORD);
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("show_sql", "true");

            configuration.addAnnotatedClass(User.class);

            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            return configuration.buildSessionFactory(serviceRegistryBuilder.build());
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
