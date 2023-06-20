package com.kelompok4.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kelompok4.DB;
import com.kelompok4.types.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.MessageBox;

public class AccountsController {
    @FXML
    private Pane rootPane;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, Integer> balanceCol;
    @FXML
    private TableColumn<User, String> roleCol;

    private ObservableList<User> data;
    
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("balance"));
        roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));

        table.setItems(loadData());
    }

    private ObservableList<User> loadData() {
        data = FXCollections.observableArrayList();

        try {
            DB.loadJDBCDriver();
            DB.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement statement = DB.prepareStatement("SELECT * FROM akun");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String password = resultSet.getString("password");
                int balance = resultSet.getInt("balance");
                String role = resultSet.getString("role");

                data.add(new User(id, name, password, role, balance));
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
    private void toAccountsUI(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/addAkunPageUI.fxml"));
        Parent root = loader.load();
        AddAkunPageController addAkunPageController = loader.getController();
        
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
            addAkunPageController.receiveStatus(buttonText);
        } else {
            User selectedUser = table.getSelectionModel().getSelectedItem();
            addAkunPageController.receiveStatus(buttonText, selectedUser);
        }
    }
    
    public void tambahAkun(String username, String password, String role, int balance) throws IOException {
        int numItems = table.getItems().size();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            MessageBox.show("Mohon isi semua field", "Error");
        } else {
            User user = new User(numItems+1, username, password, role, balance);
            if (user.tambahAkun()) {
                data.add(user);
                table.refresh();
            }
        }
    }

    public void editAkun(String username, String password, User selectedUser, int balance) throws IOException {
        if (selectedUser == null) {
            MessageBox.show("Mohon pilih baris yang ingin diubah", "Error");
            return;
        }
        
        int userId = selectedUser.getDatabaseId();
        User newUser = new User(userId, username, password, balance);
        if (newUser.editAkun(userId)) {
            MessageBox.show("Berhasil mengubah akun", "Success");
            table.setItems(loadData());
        } else {
            MessageBox.show("Gagal mengubah akun", "Error");
        }
    }

    @FXML
    private void deleteRowAkun() {
        User selectedUser = table.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            MessageBox.show("Mohon pilih baris yang ingin dihapus", "Error");
            return;
        }
        
        int userId = selectedUser.getDatabaseId();
        if (selectedUser.hapusAkun(userId)) {
            MessageBox.show("Berhasil menghapus akun", "Success");
            data.remove(selectedUser);
        } else {
            MessageBox.show("Gagal menghapus akun", "Error");
        }
    }
}
