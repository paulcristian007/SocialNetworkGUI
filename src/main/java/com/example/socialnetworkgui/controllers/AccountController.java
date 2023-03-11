package com.example.socialnetworkgui.controllers;

import com.example.socialnetworkgui.Main;
import com.example.socialnetworkgui.service.ServiceUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController extends Controller {

    @FXML
    private Button signOutButton;
    @FXML
    private Button searchFriendsButton;
    @FXML
    private Label welcomeText;

    @Override
    protected void prepare() {
        ServiceUsers service = createServiceUsers();
        service.setIdUser(idUser);
        welcomeText.setText("Welcome, " + service.getUsername() + "!");
    }

    @FXML
    void FriendshipRequestsClicked(ActionEvent event) throws IOException{
        switchScenes(signOutButton, "FriendRequestsView.fxml", idUser);
    }

    @FXML
    void friendsListClicked(ActionEvent event) throws IOException {
        switchScenes(signOutButton, "FriendsListView.fxml", idUser);
    }

    @FXML
    void searchFriendsClicked(ActionEvent event) throws IOException {
        switchScenes(signOutButton, "SearchFriends.fxml", idUser);
    }

    @FXML
    void signOutClicked(ActionEvent event) throws IOException {
        switchScenes(signOutButton, "SignUpView.fxml", idUser);
    }

}
