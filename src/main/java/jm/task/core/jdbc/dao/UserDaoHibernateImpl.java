package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionF = getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionF.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user" +
                            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(30) NOT NULL, lastName VARCHAR(30) NOT NULL," +
                            "age TINYINT NOT NULL)").addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = sessionF.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица была удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionF.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println(" User с именем " + name + " добавлен");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionF.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("delete from user where id = :param")
                    .setParameter("param", id).executeUpdate();
            transaction.commit();
            System.out.println("User с id " + id + " удален");
        }
    }


    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionF.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> userList = session.createSQLQuery("SELECT * FROM user")
                    .addEntity(User.class).list();
            transaction.commit();
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionF.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("truncate table user")
                    .executeUpdate();
            transaction.commit();
        }
    }
}
