package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection connection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "Application";
        String userName = "postgres";
        String password = "admin123";
        return connection(hostName, dbName, userName, password);
    }
    public static Connection connection(String hostName, String dbName, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String connectionURL = "jdbc:postgresql://" + hostName + ":5432/" + dbName;
        Connection connection = DriverManager.getConnection(connectionURL, userName, password);
        return connection;
    }
}
