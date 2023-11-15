package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static String createTableUser = "CREATE TABLE IF NOT EXISTS users " +
                    "(" +
                    "id serial primary key," +
                    "name varchar(15) NOT NULL," +
                    "lastname varchar(15) NOT NULL," +
                    "age smallint  NOT NULL" +
                    ")";

    private static String dropTableUser = "DROP TABLE IF EXISTS users";
    private static String  saveUser = "INSERT INTO users (\"name\", \"lastname\", \"age\") VALUES ('";
    private static String getAllUsers = "SELECT * FROM users";
    private static String deleteUser = "DELETE FROM users WHERE (id = ";
    private static String cleanTable = "TRUNCATE users";

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    public UserDaoJDBCImpl() {
    }
    public static UserDaoJDBCImpl getInstance() {
        return new UserDaoJDBCImpl();
    }
    public void createUsersTable() {
        try {
            connection = Util.connection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(createTableUser);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException es) {
                es.printStackTrace();
            }
        } finally {
            try{
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try {
            connection = Util.connection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(dropTableUser);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException  | NullPointerException es) {
                es.printStackTrace();
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveUser(String name, String lastName, byte age) {
        try {
            connection = Util.connection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(saveUser  + name + "', '" + lastName + "', '" + age + "')");
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException es) {
                es.printStackTrace();
            }
        } finally {
            try{
                connection.setAutoCommit(true);
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            connection = Util.connection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(deleteUser  + id + ")");
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException es) {
                es.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch(SQLException e) {
                  e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            connection = Util.connection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public void cleanUsersTable() {

        try {
            connection = Util.connection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(cleanTable);
            connection.commit();
        } catch (SQLException | ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException es) {
                es.printStackTrace();
            }
        } finally {
            try{
                connection.setAutoCommit(true);
                if (statement != null)
                    connection.close();
            } catch(SQLException se) {

            }
            try{
                if (connection != null)
                    connection.close();
            } catch(SQLException es){
                es.printStackTrace();
            }
        }
    }
}
