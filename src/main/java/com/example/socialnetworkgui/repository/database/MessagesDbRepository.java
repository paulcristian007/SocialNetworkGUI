package com.example.socialnetworkgui.repository.database;

import com.example.socialnetworkgui.domain.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessagesDbRepository {
    String url;
    String username;
    String password;

    public MessagesDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void save(Message message) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sqlScript =
                    "insert into messages(txt, id_friendship, id_user, idx) " +
                            "values(?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlScript);
            statement.setString(1, message.getText());
            statement.setLong(2, message.getIdFriendship());
            statement.setLong(3, message.getIdUser());
            statement.setLong(4, message.getIdx());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(String text) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "delete from messages where txt = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, text);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
