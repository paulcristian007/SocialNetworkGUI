package com.example.socialnetworkgui.controllers;

import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.service.ServiceRequests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

public class FriendRequestsController extends Controller {
    private String username, firstName, lastName;

    ObservableList<String> model = FXCollections.observableArrayList();

    @FXML
    private Button backButton;
    @FXML
    private Button acceptButton;

    @FXML
    private Button rejectButton;

    @FXML
    private ListView<String> friendRequests;
    private ServiceRequests srv;

    @Override
    protected void prepare() {
        srv = createServiceRequests();
        for (User user: srv.getAllRequests())
            model.add(user.getFirstName() + " " + user.getLastName());

        friendRequests.setItems(model);
    }

    @FXML
    public void friendRequestsClicked() {
        username = friendRequests.getSelectionModel().getSelectedItem();
        String[] parts = username.split(" ");
        firstName = parts[0];
        lastName = parts[1];
        username = firstName + " " + lastName;
        acceptButton.setDisable(false);
        rejectButton.setDisable(false);
    }

    @FXML
    void acceptFriendshipClicked(ActionEvent event) {
        try {
            srv.acceptRequest(firstName, lastName);
            model.remove(username);
            acceptButton.setDisable(true);
            rejectButton.setDisable(true);
        }

        catch (RepositoryException ex) {
            showAlert("Error", "Fail", ex.getMessage());
        }
    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        switchScenes(backButton, "AccountView.fxml", idUser);
    }

    @FXML
    void rejectFriendshipClicked(ActionEvent event) {
        try {
            srv.rejectRequest(firstName, lastName);
            model.remove(username);
            acceptButton.setDisable(true);
            rejectButton.setDisable(true);
        }

        catch (RepositoryException ex) {
            showAlert("Error", "Fail", ex.getMessage());
        }

    }


}
