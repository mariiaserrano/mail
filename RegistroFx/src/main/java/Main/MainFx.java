package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loaderMenu = new FXMLLoader(
                getClass().getResource("/fxml/principal.fxml"));
        BorderPane root = loaderMenu.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Registro con Mail");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}


