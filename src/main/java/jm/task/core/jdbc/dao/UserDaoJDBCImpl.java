package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

   private final Connection connection = getConnection();

    public void createUsersTable() {         //Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(30),age TINYINT,PRIMARY KEY (id))");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Таблица не была создана");
        }
    }

    @Override
    public void dropUsersTable() {               //Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Таблица не была удалена");
        }

    }

    public void saveUser(String name, String lastName, byte age) {      //Добавление User в таблицу
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES ( ?, ?, ?)")) {
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
        try (PreparedStatement ps = connection.prepareStatement("delete from user where id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
            System.out.println("User с id " + id + " удален");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {            // Получение всех User(ов) из таблицы
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from user")) {
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
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("truncate table user");
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
