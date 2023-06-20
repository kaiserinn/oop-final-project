package com.kelompok4.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.kelompok4.DB;
import com.kelompok4.types.Transaksi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utility.MessageBox;

public class TransaksiController {
    @FXML
    private TableView<Transaksi> table;
    @FXML
    private TableColumn<Transaksi, Integer> idCol, nominalCol, sewaIdCol;
    @FXML
    private TableColumn<Transaksi, LocalDate> tanggalCol;

    private ObservableList<Transaksi> data;
    
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Transaksi, Integer>("id"));
        nominalCol.setCellValueFactory(new PropertyValueFactory<Transaksi, Integer>("nominal"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Transaksi, LocalDate>("tanggal"));
        sewaIdCol.setCellValueFactory(new PropertyValueFactory<Transaksi, Integer>("sewaId"));

        table.setItems(loadData());
    }

    private ObservableList<Transaksi> loadData() {
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
                int nominal = resultSet.getInt("nominal");
                LocalDate tanggal = resultSet.getDate("tanggal").toLocalDate();
                int sewaId = resultSet.getInt("id_sewa");

                data.add(new Transaksi(id, nominal, tanggal, sewaId));
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
    private void deleteRowTransaksi() {
        Transaksi selectedTransaksi = table.getSelectionModel().getSelectedItem();

        if (selectedTransaksi == null) {
            MessageBox.show("Mohon pilih baris yang ingin dihapus", "Error");
            return;
        }
        
        int transaksiId = selectedTransaksi.getId();
        if (selectedTransaksi.hapus(transaksiId)) {
            MessageBox.show("Berhasil menghapus akun", "Success");
            data.remove(selectedTransaksi);
        } else {
            MessageBox.show("Gagal menghapus akun", "Error");
        }
    }
}
