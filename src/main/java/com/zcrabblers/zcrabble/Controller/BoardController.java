package com.zcrabblers.zcrabble.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    @FXML private AnchorPane boardAnchor;
    @FXML private AnchorPane rackAnchor;
    @FXML private Rectangle rackRectangle;

    ArrayList<ImageView> cellList = new ArrayList<>();
    ArrayList<ImageView> rackList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            populate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void populate() throws FileNotFoundException {
        populateBoard();
        populateRack();

    }

    private void populateBoard() throws FileNotFoundException {
        int x = 0;
        int y = 0;
        for(int i = 1; i <= 225; i++){
            ImageView img = new ImageView();
            boardAnchor.getChildren().add(img);
            cellList.add(img);
            img.setFitHeight(33);
            img.setFitWidth(33);
            img.setX(x);
            img.setY(y);
            x+=33;
            if(i % 15 == 0){
                x = 0;
                y+=33;
            }
            img.setImage((new Image(new FileInputStream("src/main/resources/com/zcrabblers/zcrabble/Images/BasicCell.png"))));
        }
    }

    private void populateRack() throws FileNotFoundException {
        double x = rackRectangle.getWidth()/2-((double)33/2);
        double y = rackRectangle.getHeight()/2-((double)33/2);
        int counter = 0;
        for(int i = 1; i <= 8; i++){
            ImageView img = new ImageView();
            rackAnchor.getChildren().add(img);
            rackList.add(img);
            img.setFitHeight(33);
            img.setFitWidth(33);

            img.setX(x);
            img.setY(y);

            if (counter % 2 == 0){
                x += ((-counter)*45);
            }
            else x += (counter*45);
            counter++;

            img.setImage((new Image(new FileInputStream("src/main/resources/com/zcrabblers/zcrabble/Images/BasicCell.png"))));
        }
    }
}
