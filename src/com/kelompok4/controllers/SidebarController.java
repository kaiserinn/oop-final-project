package com.kelompok4.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SidebarController {
    @FXML
    private Pane rootPane;
    @FXML
    private Button daftarLapangan, tambahLapangan, daftarAkun;

    @FXML
    private void initialize() {
        if (LoginController.loginState) {
            if (LoginController.loginUser.getRole().equals("user")) {
                daftarLapangan.setVisible(false);
                daftarLapangan.setManaged(false);
                tambahLapangan.setVisible(false);
                tambahLapangan.setManaged(false);
                daftarAkun.setVisible(false);
                daftarAkun.setManaged(false);
            } 
        }
    }
    
    @FXML
    private void logout() {
        try {
            LoginController.loginState = false;
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/loginUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toDaftarLapangan() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/lapanganUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toAddPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/addPageUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toAccountsUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/accountsUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toRentPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/rentUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
