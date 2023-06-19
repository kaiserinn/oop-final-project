package com.kelompok4.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;
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
    private TableColumn<Lapangan, String> namaCol, hargaCol;
    @FXML
    private Button tambahButton, sewaButton;

    private ObservableList<Lapangan> data;

    public static String nama;
    public static String harga;
    
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Lapangan, Integer>("id"));
        namaCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("nama"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<Lapangan, String>("harga"));

        table.setItems(loadData());
    }

    private ObservableList<Lapangan> loadData() {
        data = FXCollections.observableArrayList();

        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM lapangan");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama");
                String harga = resultSet.getString("harga");

                data.add(new Lapangan(id, nama, harga));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return data;
    }

    @FXML
    private void toAddPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/addPageUI.fxml"));
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
        }

        if (buttonText.equals("Tambah")) {
            addPageController.receiveStatus(buttonText);
        } else {
            addPageController.receiveStatus(buttonText, table.getSelectionModel().getSelectedIndices().get(0));
        }
    }
    
    public void tambahLapangan(String nama, String harga) throws IOException {
        int numItems = table.getItems().size();

        if (nama.isEmpty() || harga.isEmpty()) {
            MessageBox.show("Mohon isi semua field", "Error");
        } else {
            Lapangan lapangan = new Lapangan(nama, harga);
            if (lapangan.tambahLapangan()) {
                data.add(new Lapangan(numItems+1, nama, harga));
                table.refresh();
            }
        }
    }

    @FXML
    public void editLapangan(String nama, String harga, int index) throws IOException {
        ObservableList<Lapangan> items = table.getItems();

        if (index < 1) {
            return;
        }

        for (Lapangan lapangan : items) {
            if (lapangan.getId() == index+1) {
                if (nama.isEmpty() || harga.isEmpty()) {
                    MessageBox.show("Mohon isi semua field", "Error");
                } else {
                    lapangan.setNama(nama);
                    lapangan.setHarga(harga);
                    table.refresh();
                }
            }
        }
    }

    public void deleteRowLapangan() {
        Lapangan selectedLapangan = table.getSelectionModel().getSelectedItem();

        if (selectedLapangan == null) {
            MessageBox.show("Mohon pilih baris yang ingin dihapus", "Error");
            return;
        }
        
        int lapanganId = selectedLapangan.getDatabaseId();
        if (selectedLapangan.hapusLapangan(lapanganId)) {
            MessageBox.show("Berhasil menghapus lapangan", "Success");
            data.remove(selectedLapangan);
        } else {
            MessageBox.show("Gagal menghapus lapangan", "Error");
        }
    }
}
