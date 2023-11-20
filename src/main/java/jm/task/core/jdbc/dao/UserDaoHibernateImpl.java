package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.createSessionFactory;
import static jm.task.core.jdbc.util.Util.getPostgresSqlHibernateConfiguration;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;
    private Transaction transaction;
    static String createTable =
            "CREATE TABLE IF NOT EXISTS profiles " +
            "(" +
            "id serial primary key," +
            "name varchar(15) NOT NULL," +
            "lastname varchar(15) NOT NULL," +
            "age smallint  NOT NULL" +
            ")";

    public UserDaoHibernateImpl() {
    }

    public static UserDaoHibernateImpl getInstance() {
        return new UserDaoHibernateImpl();
    }
    @Override
    public void createUsersTable() {
        try {
            session = createSessionFactory(getPostgresSqlHibernateConfiguration()).openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(createTable)
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableUser = "DROP TABLE IF EXISTS profiles";
        try {
            session = createSessionFactory(getPostgresSqlHibernateConfiguration()).openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(dropTableUser)
                    .executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            //user.setId(1L);
            session = createSessionFactory(getPostgresSqlHibernateConfiguration()).openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = createSessionFactory(getPostgresSqlHibernateConfiguration()).openSession();
            transaction = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            session = createSessionFactory(getPostgresSqlHibernateConfiguration()).openSession();
            users = session.createQuery("SELECT a FROM User a", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE profiles";
        try {
            session = createSessionFactory(getPostgresSqlHibernateConfiguration()).openSession();
            session.beginTransaction();
            session.createSQLQuery(cleanTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
