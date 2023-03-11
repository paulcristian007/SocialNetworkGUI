package com.example.socialnetworkgui.sqlQueries;

import com.example.socialnetworkgui.domain.Friendship;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendshipSqlQuery {
    String url;
    String username;
    String password;
    public FriendshipSqlQuery(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Friendship createFriendship(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            Long id1 = resultSet.getLong("id1");
            Long id2 = resultSet.getLong("id2");
            String time = resultSet.getString("time");
            String status = resultSet.getString("status");
            Friendship friendship =  new Friendship(id1, id2, time, status);
            friendship.setId(id);
            return friendship;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Friendship getFriendship(Long id1, Long id2) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT* from friendships where id1 = ? and id2 = ? or id1 = ? and id2 = ?");
            statement.setLong(1, id1);
            statement.setLong(2, id2);
            statement.setLong(3, id2);
            statement.setLong(4, id1);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) return null;
            return createFriendship(resultSet);
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean exists(Long id1, Long id2) {
        return getFriendship(id1, id2) != null;
    }

    public List<Friendship> getAllByStatus(Long idUser, String status) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT* from friendships where (id1 = ? or id2 = ?) and status = ?");
            statement.setLong(1, idUser);
            statement.setLong(2, idUser);
            statement.setString(3, status);

            List<Friendship> friendships = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                friendships.add(createFriendship(resultSet));
            return friendships;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Friendship> getAll() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT* from friendships where status = ?");

            List<Friendship> friendships = new ArrayList<>();
            statement.setString(1, "accepted");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                friendships.add(createFriendship(resultSet));
            return friendships;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
