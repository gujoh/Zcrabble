package com.zcrabblers.zcrabble.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    @FXML private AnchorPane boardAnchor;
    ArrayList<ImageView> cellList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void populate() throws FileNotFoundException {
        int x = 0;
        int y = 0;
        for(int i = 1; i <= 225; i++){
            ImageView img = new ImageView();
            boardAnchor.getChildren().add(img);
            img.setFitHeight(33);
            img.setFitWidth(33);
            img.setX(x);
            img.setY(y);
            x+=33;
            if(i % 15 == 0){
                x = 0;
                y+=33;
            }
            cellList.add(new ImageView());
            img.setImage((new Image(new FileInputStream("src/main/resources/com/zcrabblers/zcrabble/Images/BasicCell.png"))));

        }

    }
}
