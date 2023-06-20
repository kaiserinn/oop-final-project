package com.kelompok4.controllers;

import java.io.IOException;

import com.kelompok4.types.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.MessageBox;

public class AddAkunPageController {
    @FXML
    private Pane rootPane;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private TextField roleInput;
    @FXML
    private TextField balanceInput;

    private String buttonStatus;
    private User selectedUser;

    @FXML
    private void saveButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/accountsUI.fxml"));
        Parent root = loader.load();
        AccountsController accountsController = loader.getController();
        
        String username = usernameInput.getText(); 
        String password = passwordInput.getText();
        String role = roleInput.getText();
        int balance;
        try {
            balance = Integer.valueOf(balanceInput.getText());
        } catch (Exception e) {
            MessageBox.show("Saldo harus berupa angka", "Error");
            return;
        }

        try {
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (buttonStatus.equals("Tambah")) {
            accountsController.tambahAkun(username, password, role, balance);
        } 
        else {
            accountsController.editAkun(username, password, selectedUser, balance);
        }
    }

    public void receiveStatus(String status) {
        buttonStatus = status;
    }

    public void receiveStatus(String status, User selectedUser) {
        buttonStatus = status;
        this.selectedUser = selectedUser;
    }
}
