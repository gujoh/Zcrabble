package com.zcrabblers.zcrabble;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * Main is responsible for actually running the application.
 * Main creates a Scene using an .fxml file and adds it to the stage.
 * Further, it contains an inner record SceneSizeChangeListener that is used to scale the application using letterboxing.
 * @author Gustaf Jonasson.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(628);
        stage.setTitle("Zcrabble!");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/com/zcrabblers/zcrabble/Images/z.png"))));
        letterbox(scene, (AnchorPane) scene.getRoot());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //Letterboxing is a method used to scale GUI applications, for instance.
    private void letterbox(final Scene scene, final AnchorPane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    //SceneSizeChangeListener listens to change in the size of a scene and scales it accordingly.
    private record SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth,
                                           AnchorPane rootPane) implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor = newWidth / newHeight > ratio ? newHeight / initHeight : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                rootPane.setPrefWidth(newWidth / scaleFactor);
                rootPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                rootPane.setPrefWidth(Math.max(initWidth, newWidth));
                rootPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
}

