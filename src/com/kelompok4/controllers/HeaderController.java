package com.kelompok4.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderController {
    @FXML
    private Label usernameLabel;
    
    @FXML
    private void initialize() {
        usernameLabel.setText(LoginController.loginAs);
    }
}
