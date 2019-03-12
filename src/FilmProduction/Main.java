package FilmProduction;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/authorization.fxml"));
        primaryStage.setTitle("Фильмотека");
        primaryStage.setScene(new Scene(root, 1200, 650));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}