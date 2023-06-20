package com.kelompok4.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.kelompok4.DB;
import com.kelompok4.types.Sewa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utility.MessageBox;

public class TableRentController {
    @FXML
    private TableView<Sewa> table;
    @FXML
    private TableColumn<Sewa, Integer> idCol, lapanganIdCol, userIdCol;
    @FXML
    private TableColumn<Sewa, String> sesiCol;
    @FXML
    private TableColumn<Sewa, LocalDate> tanggalCol;
    @FXML
    private Button hapusButton;

    private ObservableList<Sewa> data;
    
    @FXML
    private void initialize() {
        if (LoginController.loginState) {
            if (LoginController.loginUser.getRole().equals("user")) {
                hapusButton.setVisible(false);
                hapusButton.setManaged(false);
            } 
        }
        
        idCol.setCellValueFactory(new PropertyValueFactory<Sewa, Integer>("id"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Sewa, LocalDate>("tanggal"));
        sesiCol.setCellValueFactory(new PropertyValueFactory<Sewa, String>("sesi"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<Sewa, Integer>("userId"));
        lapanganIdCol.setCellValueFactory(new PropertyValueFactory<Sewa, Integer>("lapanganId"));

        table.setItems(loadData());
    }

    private ObservableList<Sewa> loadData() {
        data = FXCollections.observableArrayList();

        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM sewa");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDate tanggal = resultSet.getDate("tanggal").toLocalDate();
                String sesi = resultSet.getString("sesi");
                int userId = resultSet.getInt("id_user");
                int lapanganId = resultSet.getInt("id_lapangan");

                data.add(new Sewa(id, tanggal, sesi, userId, lapanganId));
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
    private void deleteRowSewa() {
        Sewa selectedSewa = table.getSelectionModel().getSelectedItem();

        if (selectedSewa == null) {
            MessageBox.show("Mohon pilih baris yang ingin dihapus", "Error");
            return;
        }
        
        int sewaId = selectedSewa.getId();
        if (selectedSewa.hapus(sewaId)) {
            MessageBox.show("Berhasil menghapus akun", "Success");
            data.remove(selectedSewa);
        } else {
            MessageBox.show("Gagal menghapus akun", "Error");
        }
    }
}
