package com.kelompok4.controllers;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddPageController {
    @FXML
    private Pane rootPane;
    @FXML
    private TextField namaInput;
    @FXML
    private TextField hargaInput;

    private String buttonStatus;
    private int rowIndex;

    @FXML
    private void saveButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/lapanganUI.fxml"));
        Parent root = loader.load();
        LapanganController lapanganController = loader.getController();
        
        String nama = namaInput.getText(); 
        String harga = hargaInput.getText();

        try {
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (buttonStatus.equals("Tambah")) {
            lapanganController.tambahLapangan(nama, harga);
        } else {
            System.out.println("edit");
            lapanganController.editLapangan(nama, harga, rowIndex);
        }
        System.out.println("not working: "+ buttonStatus);
    }

    public void receiveStatus(String status) {
        buttonStatus = status;
    }

    public void receiveStatus(String status, int index) {
        buttonStatus = status;
        rowIndex = index;
    }
}
