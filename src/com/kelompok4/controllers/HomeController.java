package com.kelompok4.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import com.kelompok4.types.Sewa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController {
    @FXML
    private TableView<Sewa> table;
    @FXML
    private TableColumn<Sewa, Integer> idCol, lapanganIdCol, userIdCol;
    @FXML
    private TableColumn<Sewa, String> sesiCol;
    @FXML
    private TableColumn<Sewa, LocalDate> tanggalCol;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label balanceLabel;

    private ObservableList<Sewa> data;
    
    @FXML
    private void initialize() {
        Global.balanceLabel = balanceLabel;
        usernameLabel.setText(Global.loginAs);
        Global.balanceLabel.setText("Rp. " + String.valueOf(Global.loginUser.getDatabaseBalance()));
        
        idCol.setCellValueFactory(new PropertyValueFactory<Sewa, Integer>("id"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<Sewa, LocalDate>("tanggal"));
        sesiCol.setCellValueFactory(new PropertyValueFactory<Sewa, String>("sesi"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<Sewa, Integer>("userId"));
        lapanganIdCol.setCellValueFactory(new PropertyValueFactory<Sewa, Integer>("lapanganId"));

        table.setItems(loadData());
    }

    private ObservableList<Sewa> loadData() {
        data = FXCollections.observableArrayList();

        ArrayList<Sewa> sewaList = Global.loginUser.getListSewa();
        for (Sewa sewa : sewaList) {
            data.add(sewa);
        }
        
        return data;
    }
}
