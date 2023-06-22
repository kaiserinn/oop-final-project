package utility;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class MessageBox {
    public static void show(String message, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);
        
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-size: 16px;");

        Button button = new Button();
        button.setText("OK");
        button.setPrefWidth(100);
        button.setOnAction(e -> stage.close());
        button.setStyle("-fx-font-size: 16px;");

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, button);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}