package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public static final String CREATE_SQL =
            """
                    CREATE TABLE IF NOT EXISTS userTable (
                                id SERIAL PRIMARY KEY,
                                first_name VARCHAR(255) NOT NULL,
                                last_name VARCHAR(255) NOT NULL,
                                age INT NOT NULL)
                    """;

    public static final String DROP_SQL = """
            DROP TABLE IF EXISTS userTable
            """;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        Session session = Util.getConnection().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        Util.shutdown();

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getConnection().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        Util.shutdown();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getConnection().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User user1 = new User(name, lastName, age);

            session.save(user1);

            transaction.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        Util.shutdown();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getConnection().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            transaction.commit();
            System.out.println("User с id=" + id + " удален из базы данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        Util.shutdown();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Session session = Util.getConnection().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            users = session.createQuery("from User").getResultList();

            transaction.commit();


        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        Util.shutdown();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getConnection().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.createQuery("delete from User").executeUpdate();

            transaction.commit();
            System.out.println("Таблица очищена");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
