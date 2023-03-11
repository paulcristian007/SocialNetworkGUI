package com.example.socialnetworkgui.controllers;

import com.example.socialnetworkgui.Main;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.service.ServiceFriends;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class FriendsListController extends Controller {
    public Button removeFriendButton;
    protected static String username, firstName, lastName;
    @FXML
    private Button backButton;
    @FXML
    private Button openChatButton;

    @FXML
    private ListView<String> friendsListView;

    private ServiceFriends srvFriends;

    ObservableList<String> model = FXCollections.observableArrayList();

    @Override
    protected void prepare() {
        srvFriends = createServiceFriends();
        for (User user: srvFriends.getAllFriends())
            model.add(user.getFirstName() + " " + user.getLastName());

        friendsListView.setItems(model);
    }

    @FXML
    void friendsListClicked() {
        username = friendsListView.getSelectionModel().getSelectedItem();
        String[] parts = username.split(" ");
        firstName = parts[0];
        lastName = parts[1];

        removeFriendButton.setDisable(false);
        openChatButton.setDisable(false);

    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        switchScenes(backButton, "AccountView.fxml", idUser);
    }

    @FXML
    void removeFriendClicked(ActionEvent event) {
        try {
            srvFriends.deleteFriendship(firstName, lastName);
            model.remove(username);
            removeFriendButton.setDisable(true);
        }

        catch (RepositoryException ex) {
            showAlert("Error", "Fail", ex.getMessage());
        }
    }

    @FXML
    void openChatClicked(ActionEvent event) throws IOException {
        switchScenes(openChatButton, "ChatView.fxml", idUser);
    }

}
