package com.zcrabblers.zcrabble.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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
    @FXML private ImageView dragImageView;

    ArrayList<ImageView> cellList = new ArrayList<>();
    ArrayList<ImageView> rackList = new ArrayList<>();

    private static final String IMAGE_PATH = "src/main/resources/com/zcrabblers/zcrabble/Images/";

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
        //makeOneTestTile();
        initDragTile();
        rackRectangle.setOnMouseDragReleased(mouseEvent -> hideDragTile());
        boardAnchor.setOnMouseDragReleased(mouseEvent -> hideDragTile());
        rackAnchor.setOnMouseDragReleased(mouseEvent -> hideDragTile());
    }

    private void hideDragTile(){
        dragImageView.setVisible(false);
    }

    private void initDragTile() throws FileNotFoundException {
        dragImageView.setFitWidth(30);
        dragImageView.setFitHeight(30);
        dragImageView.setVisible(false);
        dragImageView.setMouseTransparent(true);
        dragImageView.setImage(new Image(new FileInputStream(IMAGE_PATH + "TestTile.png")));
        boardAnchor.getChildren().add(dragImageView);
    }

    private void registerCellEvents(ImageView img){
        img.setOnDragDetected(event -> {
            img.startFullDrag();
            dragImageView.setImage(img.getImage());
            dragImageView.setVisible(true);
        });

        img.setOnMouseDragged(mouseEvent -> {
            Point2D point = new Point2D(mouseEvent.getSceneX() - 45, mouseEvent.getSceneY() - 45);

            dragImageView.setX(point.getX());
            dragImageView.setY(point.getY());
        });

        img.setOnMouseDragReleased(event -> {
            dragImageView.setVisible(false);
            img.setImage(dragImageView.getImage());
        });
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
            img.setImage((new Image(new FileInputStream(IMAGE_PATH + "BasicCell.png"))));
            registerCellEvents(img);
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

            img.setImage((new Image(new FileInputStream(IMAGE_PATH + "a.png"))));

            registerCellEvents(img);
        }
    }
}
