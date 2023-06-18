package main.java.controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import main.java.types.Akun;
import main.java.types.Lapangan;
import utility.MessageBox;

public class AccountsController {
    @FXML
    private Pane rootPane;
    @FXML
    private TableView<Akun> table;
    @FXML
    private TableColumn<Akun, Integer> idCol;
    @FXML
    private TableColumn<Akun, String> usernameCol;
    @FXML
    private TableColumn<Akun, String> passwordCol;
    @FXML
    private TableColumn<Akun, String> roleCol;

    private ObservableList<Akun> data;
    
    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Akun, Integer>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Akun, String>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Akun, String>("password"));
        roleCol.setCellValueFactory(new PropertyValueFactory<Akun, String>("role"));

        table.setItems(loadData());
    }

    private ObservableList<Akun> loadData() {
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
                String role = resultSet.getString("role");

                data.add(new Akun(id, name, password, role));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/views/addAkunPageUI.fxml"));
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
            addAkunPageController.receiveStatus(buttonText, table.getSelectionModel().getSelectedIndices().get(0));
        }
    }
    
    public void tambahAkun(String username, String password, String role) throws IOException {
        int numItems = table.getItems().size();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            MessageBox.show("Mohon isi semua field", "Error");
        } else {
            try {
                DB.loadJDBCDriver();
                DB.connect();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                PreparedStatement stm = DB.prepareStatement("INSERT INTO akun (username, password, role) VALUES (?, ?, ?)");
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, role);
                if (stm.executeUpdate() > 0) {
                    data.add(new Akun(numItems+1, username, password, role));
                    table.refresh();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    DB.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }
    }

    // @FXML
    // public void editAkun(String nama, String harga, String status, int index) throws IOException {
    //     System.out.println("editAkun");
    //     ObservableList<Akun> items = table.getItems();

    //     if (index < 1) {
    //         return;
    //     }

    //     for (Akun Akun : items) {
    //         if (Akun.getId() == index+1) {
    //             if (nama.isEmpty() || harga.isEmpty() || status.isEmpty()) {
    //                 MessageBox.show("Mohon isi semua field", "Error");
    //             } else {
    //                 Akun.setNama(nama);
    //                 Akun.setHarga(harga);
    //                 Akun.setStatus(status);
    //                 table.refresh();
    //             }
    //         }
    //     }

    //     for (Akun Akun: items) {
    //         System.out.println(Akun.getId() + " " + Akun.getNama() + " " + Akun.getHarga() + " " + Akun.getStatus());
    //     }
    // }

    // public void deleteRowAkun() {
    //     Akun selectedAkun = table.getSelectionModel().getSelectedItem();

    //     if (selectedAkun != null) {
    //         data.remove(selectedAkun);
    //     }
    // }
}
