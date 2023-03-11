package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.Friendship;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;

public class FriendsDbRepository {
    String url;
    String username;
    String password;
    public FriendsDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Friendship friendship) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sqlScript =
                    "insert into friendships(id1, id2, time, status) values(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlScript);
            statement.setLong(1, friendship.getId1());
            statement.setLong(2, friendship.getId2());
            statement.setString(3, friendship.getTime());
            statement.setString(4, friendship.getStatus());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Long id1, Long id2) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "delete from friendships where id1 = ? and id2 = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id1);
            statement.setLong(2, id2);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(Long idUser) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "delete from friendships where id1 = ? or id2 = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, idUser);
            statement.setLong(2, idUser);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(Long id) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "update friendships set status = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,  "accepted");
            statement.setLong(2, id);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
