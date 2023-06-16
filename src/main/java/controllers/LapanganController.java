package main.java.controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Callback;
import main.java.types.Lapangan;
import utility.InputDialog;
import utility.MessageBox;

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
    private TableViewSelectionModel<Lapangan> selectionModel;
    
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Lapangan, Integer>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("nama"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("harga"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("status"));

        table.setItems(loadData());
        inputDialog = new InputDialog();
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
        inputData = inputDialog.show("Tambah Lapangan");

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

    @FXML
    private void editLapangan() throws IOException {
        selectionModel = table.getSelectionModel();
        ObservableList<Lapangan> items = table.getItems();
        int rowIndex = 0;

        if (selectionModel.getSelectedIndices().size() > 0) {
            rowIndex = selectionModel.getSelectedIndices().get(0);
        } else {
            return;
        }

        for (Lapangan lapangan : items) {
            if (lapangan.getId() == rowIndex+1) {
                inputData = inputDialog.show("Edit Lapangan");

                String nama = inputData[0];
                String harga = inputData[1];
                String status = inputData[2];

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
    }

    public void deleteRowLapangan() {
        selectionModel = table.getSelectionModel();
        Lapangan selectedLapangan = table.getSelectionModel().getSelectedItem();

        if (selectedLapangan != null) {
            data.remove(selectedLapangan);
        }
    }

    // private void addButtonToTable() {
    //     btnCol = new TableColumn("Button Column");

    //     Callback<TableColumn<Lapangan, Void>, TableCell<Lapangan, Void>> cellFactory = new Callback<TableColumn<Lapangan, Void>, TableCell<Lapangan, Void>>() {
    //         @Override
    //         public TableCell<Lapangan, Void> call(final TableColumn<Lapangan, Void> param) {
    //             final TableCell<Lapangan, Void> cell = new TableCell<Lapangan, Void>() {

    //                 private final Button btn1 = new Button("Hapus");

    //                 {
    //                     btn1.setMinWidth(1.7976931348623157E308);
    //                     btn1.setOnAction((ActionEvent event) -> {
    //                         deleteRowLapangan();
    //                     });
    //                 }

    //                 @Override
    //                 public void updateItem(Void item, boolean empty) {
    //                     super.updateItem(item, empty);
    //                     if (empty) {
    //                         setGraphic(null);
    //                     } else {
    //                         setGraphic(btn1);
    //                     }
    //                 }
    //             };
    //             return cell;
    //         }
    //     };
    //     btnCol.setCellFactory(cellFactory);
    //     table.getColumns().add(btnCol);
    // }


}
