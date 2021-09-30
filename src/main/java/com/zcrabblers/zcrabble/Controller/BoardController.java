package com.zcrabblers.zcrabble.Controller;

import com.zcrabblers.zcrabble.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public class BoardController implements Initializable, ILetterObservable {
    @FXML private AnchorPane boardAnchor;
    @FXML private AnchorPane rackAnchor;
    @FXML private Rectangle rackRectangle;
    @FXML private ImageView dragImageView;
    private CellView draggedFrom;
    @FXML private AnchorPane menuPane;
    @FXML private Button shuffleButton;
    @FXML private Button endTurnButton;
    @FXML private Label tilesLeftLabel;
    @FXML private AnchorPane gameAnchor;
    @FXML private Label p1Score;
    @FXML private Label p2Score;
    @FXML private Label p3Score;
    @FXML private Label p4Score;

    private ArrayList<ImageView> cellList = new ArrayList<>();
    private ArrayList<ImageView> rackList = new ArrayList<>();
    private ArrayList<Tile> tempTiles = new ArrayList<>();
    private ArrayList<Cell> newCells = new ArrayList<>();
    private MenuController menuController;

    private final GameManager game = GameManager.getInstance();

    private static final String IMAGE_PATH = "src/main/resources/com/zcrabblers/zcrabble/Images/";

    public BoardController() throws FileNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuController = new MenuController(this);
        menuPane.getChildren().add(menuController);

        game.newGame();

        endTurnButton.setOnAction(actionEvent -> {
            game.getCurrentGame().endTurn();
        });

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
        //rackAnchor.setMouseTransparent(true);
    }

    private void hideDragTile(){
        dragImageView.setVisible(false);
    }

    private void resetDragTile(){
        dragImageView.setVisible(false);
        draggedFrom.setImage(dragImageView.getImage());
    }

    private void initDragTile() throws FileNotFoundException {
        dragImageView.setFitWidth(30);
        dragImageView.setFitHeight(30);
        dragImageView.setVisible(false);
        dragImageView.setMouseTransparent(true);
        dragImageView.setImage(new Image(new FileInputStream(IMAGE_PATH + "TestTile.png")));
        boardAnchor.getChildren().add(dragImageView);
    }

    private int pos2Coord(double x){
        return (int)Math.floor(x / 33);
    }

    private int pos2Rack(double x){
        return (int)Math.floor(x / (rackAnchor.getWidth() / 7));
    }

    private void registerRackCellEvents(CellView cellView){
        cellView.setOnDragDetected(event -> {
            int x = pos2Rack(event.getX());
            System.out.println(x);
        });
        cellView.setOnMouseDragged(event -> {

        });
        cellView.setOnMouseDragReleased(event -> {

        });
    }

    private void registerCellEvents(CellView cellView){
        cellView.setOnDragDetected(event -> {

            int x = (int) Math.floor(event.getX() / 33);
            int y = (int) Math.floor(event.getY() / 33);
            System.out.println("X: " + x + ", Y: " + y);
            if(!game.getCurrentGame().isCellEmpty(x, y)) {
                cellView.startFullDrag();
                dragImageView.setImage(cellView.getImage());
                dragImageView.setVisible(true);
                cellView.changeToDefaultImage();
            }
            draggedFrom = cellView;
        });

        cellView.setOnMouseDragged(mouseEvent -> {
            Point2D point = new Point2D(mouseEvent.getSceneX() - 45, mouseEvent.getSceneY() - 45);
            dragImageView.setX(point.getX());
            dragImageView.setY(point.getY());
        });

        cellView.setOnMouseDragReleased(event -> {
            dragImageView.setVisible(false);

            int x = (int) Math.floor(event.getX() / 33);
            int y = (int) Math.floor(event.getY() / 33);
            System.out.println("X: " + x + ", Y: " + y);
            if(game.getCurrentGame().isCellEmpty(x, y))
                cellView.setImage(dragImageView.getImage());
            else
                draggedFrom.setImage(dragImageView.getImage());
        });
    }

    private void populateBoard() throws FileNotFoundException {
        int x = 0;
        int y = 0;
        int length = game.getBoardSize();
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                CellView img;
                if(i == length/2 && j == length/2){
                     img = new CellView(IMAGE_PATH + "Middle.png");
                }
                 else img = new CellView(IMAGE_PATH + game.getBoard()[i][j].GetCellWordMultiplier() + "" + game.getBoard()[i][j].GetCellLetterMultiplier() + ".png");
                boardAnchor.getChildren().add(img);
                cellList.add(img);
                img.setFitHeight(33);
                img.setFitWidth(33);
                img.setX(x);
                img.setY(y);
                x+=33;

                //img.setImage((new Image(new FileInputStream(IMAGE_PATH + game.getBoard().Matrix()[i][j].GetCellWordMultiplier() + "" + game.getBoard().Matrix()[i][j].GetCellLetterMultiplier() + ".png"))));
                img.changeToDefaultImage();
                registerCellEvents(img);
            }
            x = 0;
            y += 33;
        }
    }

    private void populateRack() throws FileNotFoundException {
        double x = rackRectangle.getWidth()/2-((double)33/2);
        double y = rackRectangle.getHeight()/2-((double)33/2);
        int counter = 0;
        for(int i = 1; i <= 8; i++){
            CellView img = new CellView(IMAGE_PATH + "11.png");
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

            //TODO: Think we must add a constructor to Rack first, right?
            //img.setImage(new Image(new FileInputStream(IMAGE_PATH + game.getRack().getTile(i).getLetter() + ".png")));
            img.setImage((new Image(new FileInputStream(IMAGE_PATH + "a.png"))));

            registerRackCellEvents(img);
        }
    }

    //Converts index in 2D array to index in 1D array.
    private int coordinateToIndex(int x, int y){
        return x + y*game.getBoardSize();
    }

    @Override
    public void update(LetterTuple[] boardList, LetterTuple[] rackList){
        for (LetterTuple letter : boardList){
            try {
                cellList.get(coordinateToIndex(letter.getX(), letter.getY())).setImage(new Image(new FileInputStream(IMAGE_PATH + letter.getLetter() + ".png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (LetterTuple letter : rackList){
            try {
                this.rackList.get(letter.getX()).setImage(new Image(new FileInputStream(IMAGE_PATH + letter.getLetter() + ".png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void shuffleRack(){
        Random rand = new Random();
        for(int i = 0; i < rackList.size(); i++){
            int randomIndex = rand.nextInt(rackList.size());
            int tempX = (int)rackList.get(randomIndex).getX();
            rackList.get(randomIndex).setX(rackList.get(i).getX());
            rackList.get(i).setX(tempX);
            Collections.swap(rackList, i, randomIndex);
        }
    }

    private void addNewCell(){
        //Get the index from the tile you want to move. From there figure out what letter is on the tile.
        //When you place your tile, get the index of that position. Make a copy of the cell that is located there.
        //Then add the tile to the copy of the cell, and add the cell to newCells.
        //When a tile that was previously placed on the board is being moved, remove the cell from newCells. 
        //Somehow send this list of new cells to the Model when endTurn has been called.
        //Other methods will check if you are able to move a certain tile when you try to move it.
        //The same goes for when you want to place a tile. The game will only let you place a tile in a
        //valid position. This will also be checked by another method.

    }

    public void setDarkModeSkin(){
        gameAnchor.setStyle("-fx-background-color: #808080");
        rackAnchor.setStyle("-fx-background-color: #000000");
        shuffleButton.setStyle("fx-background-color: #ffffff");
        shuffleButton.setTextFill(Color.WHITE);
        endTurnButton.setStyle("fx-background-color: #ffffff");
        endTurnButton.setTextFill(Color.WHITE);
    }

    public void setZcrabbleSkin(){
        gameAnchor.setStyle("-fx-background-color: #68BB59");
        rackAnchor.setStyle("-fx-background-color: #5C4425");
        shuffleButton.setStyle("fx-background-color: #ffffff");
        shuffleButton.setTextFill(Color.WHITE);
        endTurnButton.setStyle("fx-background-color: #ffffff");
        endTurnButton.setTextFill(Color.WHITE);
    }

    public void setCyberpunkSkin(){
        gameAnchor.setStyle("-fx-background-color: #711c91");
        rackAnchor.setStyle("-fx-background-color: #133e7c");
        shuffleButton.setStyle("-fx-background-color: #fff200");
        shuffleButton.setTextFill(Color.BLACK);
        endTurnButton.setStyle("-fx-background-color: #fff200");
        endTurnButton.setTextFill(Color.BLACK);
    }
}
