package utility;

import java.io.IOException;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class InputDialog {
    public String[] show() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inputDialogUI.fxml"));
        Parent root = loader.load();
        InputDialogController controller = loader.getController();
        
        Stage stage = new Stage();
        stage.setTitle("title");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);

        stage.setScene(new Scene(root));
        stage.showAndWait();

        return controller.getData();
    }
}
