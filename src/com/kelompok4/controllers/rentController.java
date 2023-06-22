package com.kelompok4.controllers;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.kelompok4.DB;
import com.kelompok4.types.Sewa;
import com.kelompok4.types.Transaksi;

import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import utility.MessageBox;

public class rentController {

    @FXML
    private Pane rootPane;
    @FXML
    private VBox mainContainer;

    @FXML
    private void initialize() {
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
                String nama = resultSet.getString("nama");
                String harga = resultSet.getString("harga");
                int id = resultSet.getInt("id");

                addCard(nama, harga, id);
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
    }

    // cek apakah sesi sudah disewa atau belum
    public boolean sesiDisewa(int lapanganId, int userId, String sesi, LocalDate tanggal) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM sewa WHERE id_lapangan = ? AND sesi = ? AND tanggal = ?");
            statement.setInt(1, lapanganId);
            statement.setString(2, sesi);
            statement.setDate(3, java.sql.Date.valueOf(tanggal));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DB.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } System.out.println("error");
        return true;
    }

    @FXML
    private void sewa(LocalDate tanggal, String sesi, int lapanganId, int harga) {
        int balance = Global.loginUser.getDatabaseBalance();
        int userId = Global.loginUser.getDatabaseId();
        Sewa sewa = new Sewa(0, tanggal, sesi, userId, lapanganId);

        if (sesiDisewa(lapanganId, userId, sesi, tanggal)) {
            MessageBox.show("Sesi sudah disewa!", "Error");
            return;
        }
        if ((balance - harga) < 0) {
            MessageBox.show("Saldo tidak cukup!", "Error");
            return;
        }
        if (sewa.sewaLapangan()) {
            int newBalance = balance - harga;
            Global.loginUser.setDatabaseBalance(newBalance, userId);
            Global.balanceLabel.setText("Rp. " + String.valueOf(Global.loginUser.getDatabaseBalance()));
            Transaksi transaksi = new Transaksi(harga, LocalDate.now(), sewa.getId());
            if (transaksi.tambah()) {
                MessageBox.show("Lapangan berhasil disewa!", "Success");
            }
        } else {
            MessageBox.show("Lapangan gagal disewa!", "Error");
        }
}
    
    @FXML
    private void addCard(String nama, String harga, int id) {
        VBox contentContainer = new VBox();
        contentContainer.getStyleClass().add("contentContainer");

        HBox titleContainer = new HBox();
        titleContainer.getStyleClass().add("titleContainer");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(50.0);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(new File("A:\\Kuliah\\java\\final-project-oop\\src\\com\\kelompok4\\resources\\img\\football-field.png").toURI().toString()));

        Label titleLabel = new Label(nama);
        titleLabel.getStyleClass().add("title");

        Label idLabel = new Label(String.valueOf(id));
        idLabel.setVisible(false);
        idLabel.setManaged(false);

        titleContainer.getChildren().addAll(imageView, titleLabel, idLabel);
        titleContainer.setSpacing(20);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        Label subtitleLabel = new Label("Harga per sesi: Rp. " + harga);
        subtitleLabel.getStyleClass().add("subtitle");

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(20);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList("Sesi 1", "Sesi 2", "Sesi 3"));
        comboBox.setPromptText("Pilih Sesi");
        comboBox.setPrefHeight(35.0);
        comboBox.setPrefWidth(150.0);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefHeight(35.0);
        datePicker.setPrefWidth(150.0);

        // disable tanggal sebelum tanggal hari ini
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        Region buttonSpacerRegion = new Region();
        HBox.setHgrow(buttonSpacerRegion, Priority.ALWAYS);

        Button sewaButton = new Button("Sewa");
        sewaButton.setPrefHeight(35.0);
        sewaButton.setPrefWidth(142.0);
        sewaButton.setMnemonicParsing(false);
        sewaButton.setOnAction(e -> {
            sewa(datePicker.getValue(), comboBox.getValue(), id, Integer.valueOf(harga));
            datePicker.setValue(null);
            comboBox.setValue(null);
        });

        buttonContainer.getChildren().addAll(datePicker, comboBox, buttonSpacerRegion, sewaButton);

        contentContainer.getChildren().addAll(titleContainer, subtitleLabel, buttonContainer);
        contentContainer.setPadding(new Insets(20.0));
        contentContainer.setSpacing(10);

        mainContainer.getChildren().add(contentContainer);
    }
}