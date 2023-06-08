package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();

        service.saveUser("Katya", "last", (byte) 23);
        service.saveUser("Polina", "last1", (byte) 22);
        service.saveUser("Mama", "last2", (byte) 47);
        service.saveUser("AAA", "last3", (byte) 11);

        service.removeUserById(3);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
        // реализуйте алгоритм здесь
    }
}
