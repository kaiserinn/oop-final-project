package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SidebarController {
    @FXML
    private Pane rootPane;
    
    @FXML
    private void logout() {
        try {
            LoginController.loginState = false;
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/views/loginUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toLapangan() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/views/lapanganUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toWow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/views/addPageUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
