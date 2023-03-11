package com.example.socialnetworkgui.controllers;
import com.example.socialnetworkgui.Main;
import com.example.socialnetworkgui.repository.RepositoryException;
import com.example.socialnetworkgui.service.ServiceUsers;
import com.example.socialnetworkgui.validation.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController extends Controller {
    @FXML
    private Button loginButton;

    @FXML
    private Button duplicateButton;

    @FXML
    private TextField age;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private PasswordField password;

    @FXML
    protected void loginClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void signUpClick(ActionEvent event) throws IOException {
        String FirstName = firstName.getText();
        String LastName = lastName.getText();
        String Password = password.getText();
        int Age = Integer.parseInt(age.getText());
        firstName.clear();
        lastName.clear();
        password.clear();
        age.clear();

        try {
            ServiceUsers service = createServiceUsers();
            idUser = service.addUser(FirstName, LastName, Password, Age);
            switchScenes(loginButton, "AccountView.fxml", idUser);
        }
        catch (ValidationException | RepositoryException ex) {
            showAlert("Error", "Fail", ex.getMessage());

        }
    }

    @FXML
    void duplicateClicked(ActionEvent event) throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUpView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        newStage.setScene(scene);
        newStage.show();
    }

}

