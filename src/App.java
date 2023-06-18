import java.io.IOException;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main/resources/views/loginUI.fxml"));
        primaryStage.setTitle("Aplikasi Penyewaan Lapangan Futsal");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}