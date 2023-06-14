package utility;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputDialogController {
    @FXML
    private TextField namaInput;
    @FXML
    private TextField hargaInput;
    @FXML
    private TextField statusInput;

    @FXML
    private Label title;

    static public String[] inputData = new String[3];

    @FXML
    private void okButtonPressed() {
        String nama = namaInput.getText();
        String harga = hargaInput.getText();
        String status = statusInput.getText();

        inputData[0] = nama;
        inputData[1] = harga;
        inputData[2] = status;
        
        Stage stage = (Stage) namaInput.getScene().getWindow();
        stage.close();
    }

    public String[] getData() {
        return inputData;
    }
}
