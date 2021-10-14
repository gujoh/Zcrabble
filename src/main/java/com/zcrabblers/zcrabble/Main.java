package com.zcrabblers.zcrabble;

import com.zcrabblers.zcrabble.Model.GameManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(scene);
        stage.setMaxHeight(628);
        stage.setMaxWidth(600);
        stage.setMinWidth(600);
        stage.setMinHeight(628);
        stage.setTitle("Zcrabble!");
        stage.show();
    }

    public static void main(String[] args) {
        //GameManager manager = new GameManager();

        launch();
    }
}
