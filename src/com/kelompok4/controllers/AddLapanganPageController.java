package com.kelompok4.controllers;

import java.io.IOException;

import com.kelompok4.types.Lapangan;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddLapanganPageController {
    @FXML
    private Pane rootPane;
    @FXML
    private TextField namaInput;
    @FXML
    private TextField hargaInput;

    private String buttonStatus = "Tambah";
    private Lapangan lapangan;

    @FXML
    private void saveButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/lapanganUI.fxml"));
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
            lapanganController.editLapangan(nama, harga, lapangan);
            buttonStatus = "Tambah";
        }
    }

    public void receiveStatus(String status) {
        buttonStatus = status;
    }

    public void receiveStatus(String status, Lapangan lapangan) {
        buttonStatus = status;
        this.lapangan = lapangan;
    }
}
