package com.example.socialnetworkgui.controllers;

import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.service.LogException;
import com.example.socialnetworkgui.service.ServiceUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController extends Controller {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    void loginClick(ActionEvent event) throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        firstNameField.clear();
        lastNameField.clear();
        passwordField.clear();

        try {
            ServiceUsers service = createServiceUsers();
            idUser = service.confirmLogin(firstName, lastName, password);
            System.out.println("hello " + idUser);
            switchScenes(loginButton, "AccountView.fxml", idUser);
        }

        catch (LogException | RepositoryException ex) {
            showAlert("Error", "Fail", ex.getMessage());
        }
    }
}
