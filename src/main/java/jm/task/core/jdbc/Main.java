package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService service1 = new UserServiceImpl(new UserDaoJDBCImpl());
        UserService service = new UserServiceImpl(new UserDaoHibernateImpl());
        service.dropUsersTable();
        service.createUsersTable();

        service.saveUser("Katya", "last", (byte) 23);
        service.saveUser("Polina", "last1", (byte) 22);
        service.saveUser("Mama", "last2", (byte) 47);
        service.saveUser("AAA", "last3", (byte) 11);
        System.out.println(service.getAllUsers());
        service.removeUserById(3);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
