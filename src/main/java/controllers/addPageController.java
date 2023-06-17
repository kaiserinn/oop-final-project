package main.java.controllers;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class addPageController {
    @FXML
    private Pane rootPane;
    @FXML
    private TextField namaInput;
    @FXML
    private TextField hargaInput;
    @FXML
    private TextField statusInput;

    @FXML
    private void saveButtonClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/lapanganUI.fxml"));
        Parent root = loader.load();
        LapanganController lapanganController = loader.getController();
        
        String nama = namaInput.getText(); 
        String harga = hargaInput.getText();
        String status = statusInput.getText();

        try {
            Scene scene = new Scene(root);
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lapanganController.tambahLapangan(nama, harga, status);
    }
}
