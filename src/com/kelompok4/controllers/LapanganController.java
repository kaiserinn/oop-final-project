package com.kelompok4.controllers;

import java.io.IOException;

import com.kelompok4.types.Lapangan;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.MessageBox;

public class LapanganController {

    @FXML
    private Pane rootPane;
    @FXML
    private TableView<Lapangan> table;
    @FXML
    private TableColumn<Lapangan, Integer> idCol;
    @FXML
    private TableColumn<Lapangan, String> namaCol, hargaCol, statusCol;
    @FXML
    private Button tambahButton, sewaButton;

    private ObservableList<Lapangan> data;

    public static String nama;
    public static String harga;
    public static String status;
    
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Lapangan, Integer>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("nama"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("harga"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("status"));

        table.setItems(loadData());
    }

    private ObservableList<Lapangan> loadData() {
        data = FXCollections.observableArrayList();

        data.add(new Lapangan(1, "Lapangan 1", "10.000", "Tersedia"));
        data.add(new Lapangan(2, "Lapangan 2", "30.000", "Tersedia"));
        data.add(new Lapangan(3, "Lapangan 3", "20.000", "Tersedia"));
        data.add(new Lapangan(4, "Lapangan 4", "35.000", "Tersedia"));
        data.add(new Lapangan(5, "Lapangan 5", "40.000", "Tersedia"));
        
        return data;
    }

    @FXML
    private void toAddPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/addPageUI.fxml"));
        Parent root = loader.load();
        AddPageController addPageController = loader.getController();
        
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String buttonText = "";
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            buttonText = clickedButton.getText();
            System.out.println("Button clicked: " + buttonText);
        }

        if (buttonText.equals("Tambah")) {
            addPageController.receiveStatus(buttonText);
        } else {
            addPageController.receiveStatus(buttonText, table.getSelectionModel().getSelectedIndices().get(0));
        }
    }
    
    public void tambahLapangan(String nama, String harga, String status) throws IOException {
        int numItems = table.getItems().size();

        if (nama.isEmpty() || harga.isEmpty() || status.isEmpty()) {
            MessageBox.show("Mohon isi semua field", "Error");
        } else {
            data.add(new Lapangan(numItems+1, nama, harga, status));
        }
    }

    @FXML
    public void editLapangan(String nama, String harga, String status, int index) throws IOException {
        System.out.println("editLapangan");
        ObservableList<Lapangan> items = table.getItems();

        if (index < 1) {
            return;
        }

        for (Lapangan lapangan : items) {
            if (lapangan.getId() == index+1) {
                if (nama.isEmpty() || harga.isEmpty() || status.isEmpty()) {
                    MessageBox.show("Mohon isi semua field", "Error");
                } else {
                    lapangan.setNama(nama);
                    lapangan.setHarga(harga);
                    lapangan.setStatus(status);
                    table.refresh();
                }
            }
        }

        for (Lapangan lapangan: items) {
            System.out.println(lapangan.getId() + " " + lapangan.getNama() + " " + lapangan.getHarga() + " " + lapangan.getStatus());
        }
    }

    public void deleteRowLapangan() {
        Lapangan selectedLapangan = table.getSelectionModel().getSelectedItem();

        if (selectedLapangan != null) {
            data.remove(selectedLapangan);
        }
    }
}
