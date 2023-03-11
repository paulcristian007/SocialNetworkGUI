package com.example.socialnetworkgui.controllers;

import com.example.socialnetworkgui.Main;
import com.example.socialnetworkgui.observer.Observer;
import com.example.socialnetworkgui.observer.Subject;
import com.example.socialnetworkgui.repository.database.FriendsDbRepository;
import com.example.socialnetworkgui.repository.database.MessagesDbRepository;
import com.example.socialnetworkgui.repository.database.UserDbRepository;
import com.example.socialnetworkgui.service.ServiceFriends;
import com.example.socialnetworkgui.service.ServiceMessages;
import com.example.socialnetworkgui.service.ServiceRequests;
import com.example.socialnetworkgui.service.ServiceUsers;
import com.example.socialnetworkgui.sqlQueries.FriendshipSqlQuery;
import com.example.socialnetworkgui.sqlQueries.MessagesSqlQuery;
import com.example.socialnetworkgui.sqlQueries.UserSqlQuery;
import com.example.socialnetworkgui.utils.Db;
import com.example.socialnetworkgui.validation.FriendshipValidator;
import com.example.socialnetworkgui.validation.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements Observer {

    protected Long idUser;
    protected static Subject subject = new Subject();


    protected void setId(Long id) {
        idUser = id;
    }

    protected void prepare() {

    }

    @Override
    public void update() {

    }



    protected ServiceUsers createServiceUsers() {
        UserValidator userVal = new UserValidator();
        // repository
        UserDbRepository dbUsers = new UserDbRepository(Db.url, Db.passwd, Db.username);
        FriendsDbRepository dbFriends = new FriendsDbRepository(Db.url, Db.passwd, Db.username);
        UserSqlQuery usersQuery = new UserSqlQuery(Db.url, Db.passwd, Db.username);

        return new ServiceUsers(dbUsers, usersQuery, dbFriends, userVal);
    }

    protected ServiceFriends createServiceFriends() {
        // repository
        FriendsDbRepository dbFriends = new FriendsDbRepository(Db.url, Db.passwd, Db.username);
        UserSqlQuery usersQuery = new UserSqlQuery(Db.url, Db.passwd, Db.username);
        FriendshipSqlQuery friendsQuery = new FriendshipSqlQuery(Db.url, Db.passwd, Db.username);

        return new ServiceFriends(idUser, dbFriends, usersQuery, friendsQuery);
    }

    protected ServiceMessages createServiceMessages() {
        MessagesDbRepository messages = new MessagesDbRepository(Db.url, Db.passwd, Db.username);
        MessagesSqlQuery messagesQuery = new MessagesSqlQuery(Db.url, Db.passwd, Db.username);
        UserSqlQuery usersQuery = new UserSqlQuery(Db.url, Db.passwd, Db.username);
        FriendshipSqlQuery friendsQuery = new FriendshipSqlQuery(Db.url, Db.passwd, Db.username);

        return new ServiceMessages(messages, messagesQuery, usersQuery, friendsQuery, idUser);
    }

    protected ServiceRequests createServiceRequests() {
        FriendshipValidator val = new FriendshipValidator();
        FriendsDbRepository dbFriends = new FriendsDbRepository(Db.url, Db.passwd, Db.username);;
        UserSqlQuery usersQuery = new UserSqlQuery(Db.url, Db.passwd, Db.username);
        FriendshipSqlQuery friendsQuery = new FriendshipSqlQuery(Db.url, Db.passwd, Db.username);

        return new ServiceRequests(idUser, dbFriends, usersQuery, friendsQuery, val);
    }


    protected void switchScenes(Button btn, String path, Long id) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setId(id);
        controller.prepare();

        Stage stage = (Stage) btn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    protected void showAlert(String title, String header, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.show();
    }
}
