package main.java.controllers;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import utility.MessageBox;

public class LoginController {
    @FXML 
    private Button loginButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Pane rootPane;

    public static boolean loginState = false;

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("admin")) {
            try {
                loginState = true;
                Parent root = FXMLLoader.load(getClass().getResource("../lapangan/lapanganUI.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MessageBox.show("Invalid username or password", "Error");
            usernameField.clear();
            passwordField.clear();
        }
    }
}
