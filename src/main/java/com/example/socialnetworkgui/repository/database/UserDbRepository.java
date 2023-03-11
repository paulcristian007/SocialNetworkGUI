package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDbRepository  {
    String url;
    String username;
    String password;

    public UserDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public void save(User user) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sqlScript =
                    "insert into users(first_name, last_name, password, age) " +
                    "values(? , ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlScript);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getAge());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Long id) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "delete from users where users.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Long id, User user) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "update users set "
            + "first_name = ?, "
            + "last_name = ?, "
            + "password = ?, "
            + "age = ? "
            + "where id = ?";


            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getAge());
            statement.setLong(5, id);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
