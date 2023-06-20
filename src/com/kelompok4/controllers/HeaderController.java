package com.kelompok4.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderController {
    @FXML
    private Label balanceLabel, usernameLabel;
    
    @FXML
    private void initialize() {
        balanceLabel.setText("Rp. " + String.valueOf(LoginController.loginUser.getDatabaseBalance()));
        usernameLabel.setText(LoginController.loginAs);
    }
}
