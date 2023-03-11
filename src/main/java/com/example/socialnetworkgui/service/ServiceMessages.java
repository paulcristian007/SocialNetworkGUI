package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.repository.database.MessagesDbRepository;
import com.example.socialnetworkgui.sqlQueries.FriendshipSqlQuery;
import com.example.socialnetworkgui.sqlQueries.MessagesSqlQuery;
import com.example.socialnetworkgui.sqlQueries.UserSqlQuery;

import java.util.List;

public class ServiceMessages {
    private MessagesDbRepository messages;
    private MessagesSqlQuery messagesQuery;
    private UserSqlQuery usersQuery;
    private FriendshipSqlQuery friendsQuery;

    private Long idFriendship;
    private Long id1;


    public ServiceMessages(MessagesDbRepository messages, MessagesSqlQuery messagesQuery,
                           UserSqlQuery usersQuery, FriendshipSqlQuery friendsQuery, Long id1) {
        this.messages = messages;
        this.messagesQuery = messagesQuery;
        this.usersQuery = usersQuery;
        this.friendsQuery = friendsQuery;
        this.id1 = id1;
    }

    public void setIdFriendship(Long id1, String firstName, String lastName)  {
        try {
            Long id2 = usersQuery.getId(firstName, lastName);
            idFriendship = friendsQuery.getFriendship(id1, id2).getId();
        }
        catch (RepositoryException e) {
            e.printStackTrace();
        }
    }



    public void sendMessage(String text) {
        Message msg = new Message(text, idFriendship, id1);
        msg.setIdx(messagesQuery.getChatLength(idFriendship));
        messages.save(msg);
    }

    public List<Message> receiveMessages(Long idx) {
        return messagesQuery.getMessages(idFriendship, idx);
    }
}
