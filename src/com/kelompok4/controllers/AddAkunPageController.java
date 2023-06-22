package com.kelompok4.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.kelompok4.types.User;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.MessageBox;

public class AddAkunPageController implements Initializable {
    @FXML
    private Pane rootPane;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private ComboBox<String> roleInput;
    @FXML
    private TextField balanceInput;

    private String buttonStatus = "Tambah";
    private User selectedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleInput.setItems(FXCollections.observableArrayList("Admin", "User"));
    }

    @FXML
    private void saveButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/accountsUI.fxml"));
        Parent root = loader.load();
        AccountsController accountsController = loader.getController();
        
        String username = usernameInput.getText(); 
        String password = passwordInput.getText();
        String role = roleInput.getValue().toLowerCase();
        int balance;
        try {
            balance = Integer.valueOf(balanceInput.getText());
        } catch (Exception e) {
            MessageBox.show("Saldo harus berupa angka", "Error");
            return;
        }

        if (username.equals("") || password.equals("") || role.equals("")) {
            MessageBox.show("Mohon isi semua field", "Error");
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
            buttonStatus = "Tambah";
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
