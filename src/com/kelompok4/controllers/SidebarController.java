package com.kelompok4.controllers;

import java.io.IOException;

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
    private Button daftarLapangan, tambahLapangan, daftarAkun, tambahAkun;

    @FXML
    private void initialize() {
        if (Global.loginState) {
            if (Global.loginRole.equals("user")) {
                daftarLapangan.setVisible(false);
                daftarLapangan.setManaged(false);
                tambahLapangan.setVisible(false);
                tambahLapangan.setManaged(false);
                daftarAkun.setVisible(false);
                daftarAkun.setManaged(false);
                tambahAkun.setVisible(false);
                tambahAkun.setManaged(false);
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
    private void toHomePage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/homeUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toDaftarLapangan() throws IOException {
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
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/addLapanganPageUI.fxml"));
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
    private void toAddAccountsUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/addAkunPageUI.fxml"));
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

    @FXML
    private void toTableRent() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../resources/views/tableRentUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
