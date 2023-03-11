package com.example.socialnetworkgui.sqlQueries;

import com.example.socialnetworkgui.domain.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesSqlQuery {
    String url;
    String username;
    String password;

    public MessagesSqlQuery(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    Message createMessage(ResultSet resultSet)  {
        try {
            String text = resultSet.getString("txt");
            Long idUser = resultSet.getLong("id_user");
            Long idFriendship = resultSet.getLong("id_friendship");
            Long idx = resultSet.getLong("idx");
            Message msg = new Message(text, idFriendship, idUser);
            msg.setIdx(idx);
            return msg;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long getChatLength(Long idFriendship) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "select count(*) as cnt from messages where id_friendship = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1, idFriendship);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            return resultSet.getLong("cnt");
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Message> getMessages(Long idFriendship, Long idx) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            String sql = "select* from messages where id_friendship = ? and idx > ?" +
                     " order by idx";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, idFriendship);
            statement.setLong(2, idx);
            ResultSet resultSet = statement.executeQuery();

            List<Message> ans = new ArrayList<>();
            while (resultSet.next())
                ans.add(createMessage(resultSet));

            return ans;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
