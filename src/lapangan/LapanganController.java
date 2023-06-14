package lapangan;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;

import utility.InputDialog;
import utility.MessageBox;
import lapangan.Lapangan;

public class LapanganController {

    @FXML
    private TableView<Lapangan> table;

    @FXML
    private TableColumn<Lapangan, Integer> idCol;
    
    @FXML
    private TableColumn<Lapangan, String> namaCol;
    
    @FXML
    private TableColumn<Lapangan, String> hargaCol;

    @FXML
    private TableColumn<Lapangan, String> statusCol;

    private ObservableList<Lapangan> data;
    private InputDialog inputDialog;
    private String[] inputData = new String[3];
    
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
    private void tambahLapangan() throws IOException {
        System.out.println("Tambah Lapangan");
        inputDialog = new InputDialog();
        inputData = inputDialog.show();
        System.out.println(inputData);
        String nama = inputData[0];
        String harga = inputData[1];
        String status = inputData[2];

        int numItems = table.getItems().size();

        if (nama.isEmpty() || harga.isEmpty() || status.isEmpty()) {
            MessageBox.show("Mohon isi semua field", "Error");
        } else {
            data.add(new Lapangan(numItems+1, nama, harga, status));
        }
    }
}
