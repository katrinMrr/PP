package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    Connection connection = getConnection();

    public void createUsersTable() {         //Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
        String sql = "CREATE TABLE user (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(30),age TINYINT,PRIMARY KEY (id))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица не была создана");
        }
    }

    @Override
    public void dropUsersTable() {               //Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
        String sql = "DROP TABLE user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Таблица не была удалена");
        }

    }

    public void saveUser(String name, String lastName, byte age) {      //Добавление User в таблицу
        String sql = "INSERT INTO user (name, lastName, age) VALUES ( ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println(" User с именем " + name + " добавлен");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {       //Удаление User из таблицы ( по id )
        String sql = "delete from user where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
            System.out.println("User с id " + id + " удален");
        } catch (SQLException e) {
        }
    }

    public List<User> getAllUsers() {            // Получение всех User(ов) из таблицы
        List<User> userList = new ArrayList<>();
        String sql = "select * from user";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }
            System.out.println("List пользователей собран");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {         //Очистка содержания таблицы
        String sql = "truncate table user";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
        }
    }
}
