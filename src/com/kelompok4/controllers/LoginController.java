package com.kelompok4.controllers;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import com.kelompok4.types.User;
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
    public static String loginAs = "";
    public static User loginUser;

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);
        
        if (user.login()) {
            try {
                loginState = true;
                loginAs = username;
                loginUser = user;
                Parent root = FXMLLoader.load(getClass().getResource("../resources/views/lapanganUI.fxml"));
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

    @FXML
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);

        if (user.tambahAkun()) {
            MessageBox.show("Register success", "Success");
            usernameField.clear();
            passwordField.clear();
        } else {
            MessageBox.show("Register failed", "Error");
            usernameField.clear();
            passwordField.clear();
        }
    }

}
