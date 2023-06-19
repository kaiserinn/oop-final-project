package com.kelompok4.controllers;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class rentController {

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

                addCard(nama, harga);
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
    
    @FXML
    private void addCard(String nama, String harga) {
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

        titleContainer.getChildren().addAll(imageView, titleLabel);
        titleContainer.setSpacing(20);
        titleContainer.setAlignment(Pos.CENTER_LEFT);

        Label subtitleLabel = new Label("Rp. " + harga);
        subtitleLabel.getStyleClass().add("subtitle");

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(
                "Sesi 1", "Sesi 2", "Sesi 3"));
        comboBox.setPromptText("Pilih Sesi");
        comboBox.setPrefHeight(35.0);
        comboBox.setPrefWidth(150.0);

        Region buttonSpacerRegion = new Region();
        HBox.setHgrow(buttonSpacerRegion, Priority.ALWAYS);

        Button sewaButton = new Button("Sewa");
        sewaButton.setPrefHeight(35.0);
        sewaButton.setPrefWidth(142.0);
        sewaButton.setMnemonicParsing(false);

        buttonContainer.getChildren().addAll(comboBox, buttonSpacerRegion, sewaButton);

        contentContainer.getChildren().addAll(titleContainer, subtitleLabel, buttonContainer);
        contentContainer.setPadding(new Insets(20.0));
        contentContainer.setSpacing(10);

        mainContainer.getChildren().add(contentContainer);
    }
}