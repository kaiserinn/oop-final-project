package com.kelompok4.controllers;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.kelompok4.DB;
import com.kelompok4.types.Sewa;

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

    public boolean sesiDisewa(int lapanganId, int userId, String sesi, LocalDate tanggal) {
        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM sewa WHERE id_lapangan = ? AND id_user = ? AND sesi = ? AND tanggal = ?");
            statement.setInt(1, lapanganId);
            statement.setInt(2, userId);
            statement.setString(3, sesi);
            statement.setDate(4, java.sql.Date.valueOf(tanggal));
            ResultSet resultSet = statement.executeQuery();

            System.out.println(lapanganId + " " + sesi + " " + tanggal);
            if (resultSet.next()) {
                System.out.println("test");
                return true;
            } else {
                System.out.println("tesss");
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
    private void sewa(LocalDate tanggal, String sesi, int lapanganId) {
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/accountsUI.fxml"));
        // Parent root = loader.load();
        // AccountsController accountsController = loader.getController();
        
        int userId = LoginController.loginUser.getId();
        Sewa sewa = new Sewa(0, tanggal, sesi, lapanganId,  userId);

        // try {
        //     Scene scene = new Scene(root);
        //     Stage stage = (Stage) rootPane.getScene().getWindow();
        //     stage.setScene(scene);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        if (sesiDisewa(lapanganId, userId, sesi, tanggal)) {
            MessageBox.show("Sesi sudah disewa!", "Error");
            return;
        }
        
        if (sewa.sewaLapangan()) {
            MessageBox.show("Lapangan berhasil disewa!", "Success");
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
        imageView.setImage(new Image(new File("A:\\Kuliah\\java\\final-project-oop\\img\\football-field.png").toURI().toString()));

        Label titleLabel = new Label(nama);
        titleLabel.getStyleClass().add("title");

        Label idLabel = new Label(String.valueOf(id));
        idLabel.setVisible(false);
        idLabel.setManaged(false);

        titleContainer.getChildren().addAll(imageView, titleLabel, idLabel);
        titleContainer.setSpacing(20);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        Label subtitleLabel = new Label("Harga per sesi:Rp. " + harga);
        subtitleLabel.getStyleClass().add("subtitle");

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(20);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList("Sesi 1", "Sesi 2", "Sesi 3"));
        comboBox.setValue("Pilih Sesi");
        comboBox.setPrefHeight(35.0);
        comboBox.setPrefWidth(150.0);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setPrefHeight(35.0);
        datePicker.setPrefWidth(150.0);

        Region buttonSpacerRegion = new Region();
        HBox.setHgrow(buttonSpacerRegion, Priority.ALWAYS);

        Button sewaButton = new Button("Sewa");
        sewaButton.setPrefHeight(35.0);
        sewaButton.setPrefWidth(142.0);
        sewaButton.setMnemonicParsing(false);
        sewaButton.setOnAction(e -> {
            sewa(datePicker.getValue(), comboBox.getValue(), id);
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