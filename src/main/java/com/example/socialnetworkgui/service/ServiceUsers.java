package com.example.socialnetworkgui.service;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.repository.database.FriendsDbRepository;
import com.example.socialnetworkgui.repository.database.UserDbRepository;
import com.example.socialnetworkgui.sqlQueries.UserSqlQuery;
import com.example.socialnetworkgui.validation.ValidationException;
import com.example.socialnetworkgui.validation.Validator;

import java.util.List;

public class ServiceUsers {
    private UserDbRepository users;
    private UserSqlQuery query;
    private FriendsDbRepository friends;
    private Validator<User> val;
    private Long idUser;

    public ServiceUsers(UserDbRepository users, UserSqlQuery query,
                        FriendsDbRepository friends, Validator<User> val) {
        this.users = users;
        this.query = query;
        this.friends = friends;
        this.val = val;
    }

    /// after the user has successfully logged into his account
    /// we identify the user that has logged in
    public void setIdUser(Long id) {
        this.idUser = id;
    }

    public String getUsername() {
        User user = query.getUser(idUser);
        return user.getFirstName() + " " + user.getLastName();
    }

    /// find the id of the user with the given name(if there is one)
    /// compare the given password with the real password
    /// if both successful, return the ID of the user
    public Long confirmLogin(String firstName, String lastName, String password) throws LogException, RepositoryException {
        Long id = query.getId(firstName, lastName);
        User user = query.getUser(id);
        if (!user.getPassword().equals(password))
            throw new LogException("Wrong password");
        return id;
    }

    /// assert there is no other user with the same name
    /// validate the input data for the new account
    /// if both successful, STORE the user in the database
    public Long addUser(String firstName, String lastName, String password, int age)
    throws RepositoryException, ValidationException {
        if (query.exists(firstName, lastName))
            throw new RepositoryException("Username already used");
        User user = new User(firstName, lastName, password, age);
        val.validate(user);


        users.save(user);
        return query.getId(firstName, lastName);
    }

    /// delete all his friendships( because of foreign key constraints)
    /// delete the user's account
    public void deleteUser()  {
        friends.deleteAll(idUser);
        users.delete(idUser);
    }


    /// check if there is another user with the same name, apart from himself
    /// validate the new data
    /// if both successful, UPDATE the user
    public void updateUser(String firstName, String lastName, String password, int age)
    throws ValidationException, RepositoryException {

        User oldUser = query.getUser(idUser);
        if (query.exists(firstName, lastName) &&
                !(firstName.equals(oldUser.getFirstName()) && lastName.equals(oldUser.getLastName())))
                    throw new RepositoryException("New username already used");

        User newUser = new User(firstName, lastName, password, age);
        val.validate(newUser);

        users.update(idUser, newUser);
    }

    public List<User> getAll() {
        return query.getAll();
    }

}
