package com.example.socialnetworkgui.service;

import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.repository.database.FriendsDbRepository;
import com.example.socialnetworkgui.sqlQueries.FriendshipSqlQuery;
import com.example.socialnetworkgui.sqlQueries.UserSqlQuery;
import com.example.socialnetworkgui.validation.ValidationException;
import com.example.socialnetworkgui.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceRequests {
    private final FriendsDbRepository friends;
    private UserSqlQuery usersQuery;
    private FriendshipSqlQuery friendsQuery;
    private final Validator<Friendship> val;
    private Long idUser;


    public ServiceRequests(Long idUser, FriendsDbRepository friends, UserSqlQuery usersQuery,
                          FriendshipSqlQuery friendsQuery, Validator<Friendship> val) {
        this.idUser = idUser;
        this.friends = friends;
        this.usersQuery = usersQuery;
        this.friendsQuery = friendsQuery;
        this.val = val;
    }

    public void sendRequest(String firstName, String lastName, String time) throws ValidationException, RepositoryException {

        Long idFriend = usersQuery.getId(firstName, lastName);

        /// 1) you have sent no request
        if (!friendsQuery.exists(idUser, idFriend)) {
            Friendship friendship = new Friendship(idUser, idFriend, time, "pending");
            val.validate(friendship);
            friends.save(friendship);
        }

        /// 2) you have already sent a request
        else if (friendsQuery.getFriendship(idUser, idFriend).getStatus().equals("pending"))
            throw new RepositoryException("You have already sent a friendship request to " + firstName + " " + lastName);

        /// 3) you are already friend with the user -> exception
        else
            throw new RepositoryException("You are already friend with " + firstName + " " + lastName);
    }

    public void dropRequest(String firstName, String lastName) throws RepositoryException {
        Long idFriend = usersQuery.getId(firstName, lastName);

        /// 1) you sent no request
        if (!friendsQuery.exists(idUser, idFriend))
            throw new RepositoryException("You have not sent a friendship request to " + firstName + " " + lastName);

            /// 2) your request will be dropped
        else if (friendsQuery.getFriendship(idUser, idFriend).getStatus().equals("pending"))
            friends.delete(idUser, idFriend);

            /// 3) your request has already been accepted
        else
            throw new RepositoryException("You are already friend with " + firstName + " " + lastName);
    }

    public void acceptRequest(String firstName, String lastName) throws RepositoryException {
        Long idFriend = usersQuery.getId(firstName, lastName);

        /// 1) you received no request
        if (!friendsQuery.exists(idUser, idFriend))
            throw new RepositoryException(firstName + " " + lastName + "has not sent you a friendship request");

        /// 2) you accept the request
        else if (friendsQuery.getFriendship(idUser, idFriend).getStatus().equals("pending"))
            friends.updateStatus(friendsQuery.getFriendship(idUser, idFriend).getId());

        /// 3) you have already accepted the request
        else
            throw new RepositoryException("You are already friend with " + firstName + " " + lastName);
    }

    public void rejectRequest(String firstName, String lastName) throws RepositoryException {
        Long idFriend = usersQuery.getId(firstName, lastName);

        /// 1) no request has been sent
        if (!friendsQuery.exists(idUser, idFriend))
            throw new RepositoryException(firstName + " " + lastName + "has not sent you a friendship request");

        /// 2) the request will be dropped
        if (friendsQuery.getFriendship(idUser, idFriend).getStatus().equals("pending"))
            friends.delete(idUser, idFriend);

        /// 3) the request has already been accepted
        else
            throw new RepositoryException("You are already friend with " + firstName + " " + lastName);
    }


    public List<User> getAllRequests() {
        List<User> myFriends = new ArrayList<>();
        for (Friendship friendship : friendsQuery.getAllByStatus(idUser, "pending")) {
            Long id1 = friendship.getId1();
            Long id2 = friendship.getId2();
            if (Objects.equals(id2, idUser))
                myFriends.add(usersQuery.getUser(id1));
        }
        return myFriends;
    }
}
