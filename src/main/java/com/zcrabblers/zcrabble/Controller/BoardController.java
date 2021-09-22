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
        rackRectangle.setOnDragDropped(mouseEvent -> {
            dragImageView.setVisible(false);
        });
    }

    private void initDragTile() throws FileNotFoundException {
        dragImageView.setFitWidth(30);
        dragImageView.setFitHeight(30);
        dragImageView.setVisible(false);
        dragImageView.setMouseTransparent(true);
        dragImageView.setImage(new Image(new FileInputStream(IMAGE_PATH + "TestTile.png")));
        boardAnchor.getChildren().add(dragImageView);
    }

    private void makeOneTestTile() throws FileNotFoundException {
        ImageView testTile = new ImageView();
        boardAnchor.getChildren().add(testTile);
        testTile.setFitWidth(30);
        testTile.setFitHeight(30);
        testTile.setX(500);
        testTile.setY(525);
        testTile.setImage(new Image(new FileInputStream(IMAGE_PATH + "TestTile.png")));

        testTile.setOnMouseDragged(mouseEvent -> {
            Point2D point = testTile.screenToLocal(mouseEvent.getScreenX() - 15, mouseEvent.getScreenY() - 15);
            testTile.setX(point.getX());
            testTile.setY(point.getY());
            System.out.println("x: "+mouseEvent.getSceneX()+", y: "+mouseEvent.getSceneY());
        });

        testTile.setOnDragDetected(event -> {
            testTile.startFullDrag();
            testTile.setMouseTransparent(true);
        });

        testTile.setOnMouseDragReleased(event -> {

        });
        testTile.setOnMouseReleased(event -> {
            testTile.setMouseTransparent(false);
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

            img.setOnMouseDragged(mouseEvent -> {
                Point2D point = img.screenToLocal(mouseEvent.getScreenX() - 15, mouseEvent.getScreenY() - 15);
                dragImageView.setX(point.getX());
                dragImageView.setY(point.getY());
            });

            img.setOnDragDetected(event -> {
                img.startFullDrag();
                dragImageView.setVisible(true);
            });

            img.setOnMouseDragReleased(event -> {
                dragImageView.setVisible(false);
            });

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

            img.setImage((new Image(new FileInputStream(IMAGE_PATH + "BasicCell.png"))));

            img.setOnMouseDragged(mouseEvent -> {
                Point2D point = boardAnchor.screenToLocal(mouseEvent.getScreenX() - 15, mouseEvent.getScreenY() - 15);
                dragImageView.setX(point.getX());
                dragImageView.setY(point.getY());
            });

            img.setOnDragDetected(event -> {
                img.startFullDrag();
                dragImageView.setVisible(true);
            });

            img.setOnMouseDragReleased(event -> {
                dragImageView.setVisible(false);
            });
        }
    }
}
