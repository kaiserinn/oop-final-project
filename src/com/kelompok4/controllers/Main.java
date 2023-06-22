package com.kelompok4.controllers;
import java.io.IOException;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox vbox = new VBox();

        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("1", "2", "3"));
        comboBox.setPromptText("pilih angka");
        Button button2 = new Button("Button 2");
        button2.setOnAction(e -> {
            comboBox.setValue("pilih angka");
            System.out.println(comboBox.getValue());
        });

        vbox.getChildren().addAll(comboBox, button2);
        
        primaryStage.setTitle("Aplikasi Penyewaan Lapangan Futsal");
        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();
    }
}