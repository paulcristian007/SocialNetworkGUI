package com.example.socialnetworkgui.sqlQueries;

import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.repository.RepositoryException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserSqlQuery {
    String url;
    String username;
    String password;

    public UserSqlQuery(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private User createUser(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String password = resultSet.getString("password");
            int age = resultSet.getInt("age");

            User user = new User(firstName, lastName, password, age);
            user.setId(id);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getId(String firstName, String lastName) throws RepositoryException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select id from users where first_name = ? and last_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) return resultSet.getLong("id");
            throw new RepositoryException("No user with this name!");
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public User getUser(Long id) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select* from users where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            return createUser(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean exists(String firstName, String lastName) {
        try {
            Long id = getId(firstName, lastName);
            System.out.println(id + " " + firstName + " " + lastName);
            return true;
        }
        catch (RepositoryException ex) {
            return false;
        }
    }

    public List<User> getAll() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select* from users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();
            while (resultSet.next())
                users.add(createUser(resultSet));

            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
