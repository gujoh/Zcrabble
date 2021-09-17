package com.zcrabblers.zcrabble.Controller;

import com.zcrabblers.zcrabble.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BoardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void populate() throws FileNotFoundException {
        //image.setImage(new Image(new FileInputStream("src/main/resources/com/zcrabblers/zcrabble/Images/aircraft1.jpg")));
    }
}
