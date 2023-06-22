package com.kelompok4.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeaderController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label balanceLabel;
    
    @FXML
    private void initialize() {
        Global.balanceLabel = balanceLabel;
        usernameLabel.setText(Global.loginAs);
        Global.balanceLabel.setText("Rp. " + String.valueOf(Global.loginUser.getDatabaseBalance()));
    }

    // public static void updateBalance() {
    //     balanceLabel.setText("Rp. " + String.valueOf(Global.loginUser.getDatabaseBalance()));
    // }
}
