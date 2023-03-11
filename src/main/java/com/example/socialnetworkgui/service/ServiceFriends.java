package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.repository.*;
import com.example.socialnetworkgui.repository.database.FriendsDbRepository;
import com.example.socialnetworkgui.sqlQueries.FriendshipSqlQuery;
import com.example.socialnetworkgui.sqlQueries.UserSqlQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceFriends {

    private final FriendsDbRepository friends;
    private  UserSqlQuery usersQuery;
    private FriendshipSqlQuery friendsQuery;
    private Long idUser;


    public ServiceFriends(Long idUser, FriendsDbRepository friends, UserSqlQuery usersQuery,
                          FriendshipSqlQuery friendsQuery) {
        this.idUser = idUser;
        this.friends = friends;
        this.usersQuery = usersQuery;
        this.friendsQuery = friendsQuery;
    }

    public void deleteFriendship(String firstName, String lastName) throws RepositoryException {
        Long idFriend = usersQuery.getId(firstName, lastName);

        /// 1) delete the friendship
        if (friendsQuery.exists(idUser, idFriend)) {
            if (friendsQuery.getFriendship(idUser, idFriend).getStatus().equals("accepted")) {
                friends.delete(idUser, idFriend);
                friends.delete(idFriend, idUser);
            }
        }

        /// 2) no friendship
        else
            throw new RepositoryException("You are not a friend with" + firstName + " " + lastName);
    }

    /// returns the list containing all user's friends
    public List<User> getAllFriends() {
        List<User> myFriends = new ArrayList<>();
        for (Friendship friendship: friendsQuery.getAllByStatus(idUser, "accepted")) {
            Long id1 = friendship.getId1();
            Long id2 = friendship.getId2();
            String status = friendship.getStatus();
            if (Objects.equals(id1, idUser))
                myFriends.add(usersQuery.getUser(id2));
            else if (Objects.equals(id2, idUser))
                myFriends.add(usersQuery.getUser(id1));
        }
        return myFriends;
    }



}
