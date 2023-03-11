package com.example.socialnetworkgui.controllers;

import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.service.ServiceFriends;
import com.example.socialnetworkgui.service.ServiceRequests;
import com.example.socialnetworkgui.validation.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchFriendsController extends Controller {

    public static Long idUser;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    @FXML
    private Button dropRequestButton;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;
    @FXML
    private Button sendRequestButton;

    @FXML
    void returnToMenuClicked(ActionEvent event) throws IOException {
        switchScenes(sendRequestButton, "AccountView.fxml", idUser);
    }

    @FXML
    void sendRequestClicked(ActionEvent event) {
        ServiceRequests service = createServiceRequests();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String time = LocalDateTime.now().format(DATE_TIME_FORMATTER);

        firstNameField.clear();
        lastNameField.clear();

        try {
            service.sendRequest(firstName, lastName, time);
            showAlert("Message", "Success", "You have sent a friendship request to "
                                                             + firstName + " " + lastName);
        }

        catch (RepositoryException | ValidationException ex) {
            showAlert("Error", "Fail", ex.getMessage());

        }
    }


    @FXML
    void dropRequestClicked(ActionEvent event) {
        ServiceRequests service = createServiceRequests();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        try {
            service.dropRequest(firstName, lastName);
            showAlert("Message", "Success", "Your friendship request to "
                    + firstName + " " + lastName + "has been dropped");
        }

        catch (RepositoryException  ex) {
            showAlert("Error", "Fail", ex.getMessage());
        }
    }

}
