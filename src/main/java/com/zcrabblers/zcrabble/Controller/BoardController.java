package com.zcrabblers.zcrabble.Controller;

import com.zcrabblers.zcrabble.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    @FXML private AnchorPane newGamePane;
    @FXML private AnchorPane newGameMenuBackground;

    private ArrayList<ImageView> cellList = new ArrayList<>();
    private ArrayList<ImageView> rackList = new ArrayList<>();
    private ArrayList<Tile> tempTiles = new ArrayList<>();
    private ArrayList<Cell> newCells = new ArrayList<>();
    private MenuController menuController;
    private ArrayList<Label> scoreLabelList = new ArrayList<>();

    private final GameManager game = GameManager.getInstance();

    private static final String IMAGE_PATH = "src/main/resources/com/zcrabblers/zcrabble/Images/";

    private boolean draggedFromRack;
    private int startX;
    private int startY;

    public BoardController() throws FileNotFoundException {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuController = new MenuController(this);
        menuPane.getChildren().add(menuController);
        scoreLabelList.add(p1Score);
        scoreLabelList.add(p2Score);
        scoreLabelList.add(p3Score);
        scoreLabelList.add(p4Score);

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
        dragImageView.toFront();
    }

    private int pos2Coord(double x){
        return (int)Math.floor(x / 33);
    }

    private int pos2Rack(double x){
        return (int)Math.floor(x / (rackAnchor.getWidth() / 7));
    }

    // Switches the images of the image that was dragged from and the parameter
    private void switchImages(CellView cellView){
        draggedFrom.setImage(cellView.getImage());
        cellView.setImage(dragImageView.getImage());
    }

    // Configure the drag image when dragging starts
    private void dragSequence(CellView cell){
        cell.startFullDrag();
        dragImageView.setImage(cell.getImage());
        dragImageView.setVisible(true);
        cell.changeToDefaultImage();
        draggedFrom = cell;
    }

    // What happens continuously during a drag event
    private void OnDragged(MouseEvent mouseEvent){
        Point2D point = new Point2D(mouseEvent.getSceneX() - 45, mouseEvent.getSceneY() - 45);
        dragImageView.setX(point.getX());
        dragImageView.setY(point.getY());
    }

    // Sets how tiles dragged from the rack behaves
    private void registerRackCellEvents(CellView cellView){
        // starting a drag event from a rack tile
        cellView.setOnDragDetected(event -> {
            // get index based on mouse position
            startX = pos2Rack(event.getX());
            //System.out.println(startX);

            // if not empty start drag sequence
            if(!game.getCurrentGame().isRackEmpty(startX)){
                draggedFromRack = true;
                dragSequence(cellView);
            }
        });

        cellView.setOnMouseDragged(mouseEvent -> OnDragged(mouseEvent));

        // ending a drag on a rack tile (could be started from a rack or board tile)
        cellView.setOnMouseDragReleased(event -> {
            dragImageView.setVisible(false);
            int x = pos2Rack(event.getX());
            if(draggedFromRack){
                // switch tiles in the rack
                game.getRack().switchTiles(startX, x);

                // switch images
                switchImages(cellView);
            }else{
                if(game.getCurrentGame().getRack().isEmpty(x)){
                    // remove tile from tempboard
                    // image will be reset when the drag started
                    //TODO: LAW OF DEMETER FIX THIS
                    Tile tile = game.getCurrentGame().getTempBoard().getTile(startX, startY);
                    game.getCurrentGame().getTempBoard().removeTile(startX, startY);
                    // add tile to rack
                    // add image to rack
                    game.getRack().set(x, tile);
                    cellView.setImage(dragImageView.getImage());
                }else{
                    // dragging from the tempboard to the rack switches the tiles
                    Tile tile = game.getCurrentGame().getTempBoard().getTile(startX, startY);
                    game.getCurrentGame().getTempBoard().placeTile(startX, startY, game.getRack().getTile(x));
                    game.getRack().set(x, tile);
                    switchImages(cellView);
                    //draggedFrom.setImage(dragImageView.getImage());
                }
            }
        });
    }

    // Sets how tiles dragged from the board behaves
    private void registerBoardCellEvents(CellView cellView){
        // starting a drag event from a board tile
        cellView.setOnDragDetected(event -> {
            startX = pos2Coord(event.getX());
            startY = pos2Coord(event.getY());
            //System.out.println("X: " + startX + ", Y: " + startY);
            // can only start a drag if the board cell is empty and the tempboard cell isn't
            if(game.getCurrentGame().isCellEmpty(startX, startY) &&
                    !game.getCurrentGame().isTempCellEmpty(startX, startY)) {
                draggedFromRack = false;
                dragSequence(cellView);
            }
        });

        cellView.setOnMouseDragged(mouseEvent -> OnDragged(mouseEvent));

        // ending a drag on a board tile (could be started from a rack or board tile)
        cellView.setOnMouseDragReleased(event -> {
            dragImageView.setVisible(false);

            int x = pos2Coord(event.getX());
            int y = pos2Coord(event.getY());
            //System.out.println("X: " + x + ", Y: " + y);
            IGame current = game.getCurrentGame();
            if(current.isCellEmpty(x, y) && current.isTempCellEmpty(x, y)){
                if(draggedFromRack){
                    //get tile from rack
                    Tile tile = game.getRack().getTile(startX);
                    //remove tile from rack
                    game.getRack().remove(startX);
                    //add tile to tempboard
                    current.getTempBoard().placeTile(x, y, tile);
                    //rack image was reset on drag start
                    //set board image
                }else{
                    //switch tiles on tempboard
                    current.getTempBoard().switchTiles(startX, startY, x, y);
                    //switch images
                    draggedFrom.changeToDefaultImage();
                    //switchImages(cellView);
                }
                cellView.setImage(dragImageView.getImage());
            }else if(current.isCellEmpty(x, y) && !current.isTempCellEmpty(x, y)){
                if(draggedFromRack){
                    Tile tile = game.getRack().getTile(startX);
                    game.getRack().set(startX, game.getCurrentGame().getTempBoard().getTile(x, y));
                    current.getTempBoard().placeTile(x, y, tile);
                }else{
                    current.getTempBoard().switchTiles(startX, startY, x, y);
                }
                switchImages(cellView);
            }
            else{
                //cell dragged to wasn't empty so reset image
                draggedFrom.setImage(dragImageView.getImage());
            }
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
                 else img = new CellView(IMAGE_PATH + game.getBoardCells()[i][j].GetCellWordMultiplier() + "" + game.getBoardCells()[i][j].GetCellLetterMultiplier() + ".png");
                boardAnchor.getChildren().add(img);
                cellList.add(img);
                img.setFitHeight(33);
                img.setFitWidth(33);
                img.setX(x);
                img.setY(y);
                x+=33;

                //img.setImage((new Image(new FileInputStream(IMAGE_PATH + game.getBoard().Matrix()[i][j].GetCellWordMultiplier() + "" + game.getBoard().Matrix()[i][j].GetCellLetterMultiplier() + ".png"))));
                img.changeToDefaultImage();
                registerBoardCellEvents(img);
            }
            x = 0;
            y += 33;
        }
    }

    private void populateRack() throws FileNotFoundException {
        double x = rackRectangle.getWidth()/2-((double)33/2);
        double y = rackRectangle.getHeight()/2-((double)33/2);
        int counter = 0;
        int spacing = 45;
        for(int i = 0; i < 7; i++){
            CellView img = new CellView(IMAGE_PATH + "11.png");
            rackAnchor.getChildren().add(img);
            rackList.add(img);
            img.setFitHeight(33);
            img.setFitWidth(33);

            x += counter * spacing;
            img.setX(x);
            img.setY(y);
            counter++;
            spacing = -spacing;

            img.setImage(new Image(new FileInputStream(IMAGE_PATH + game.getRack().getTile(i).getLetter() + ".png")));

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
        for(int i = 0; i < game.getPlayers().size(); i++){
            scoreLabelList.get(i).setText(String.valueOf(game.getPlayerScore(i)));
        }
    }

    @FXML
    private void shuffleRack(){
        Random rand = new Random(); //dont use random without a parameter, maybe use a singleton that returns a seed
        for(int i = 0; i < rackList.size()-2; i++){
            int randomIndex = rand.nextInt(rackList.size());
            int tempX = (int)rackList.get(randomIndex).getX();
            rackList.get(randomIndex).setX(rackList.get(i).getX());
            rackList.get(i).setX(tempX);
            Collections.swap(rackList, i, randomIndex); //Do not know if we need this.
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

        //Maybe newCells can be located in Game, and this method updates that list

    }

    void setDarkModeSkin(){
        gameAnchor.setStyle("-fx-background-color: #808080");
        rackAnchor.setStyle("-fx-background-color: #000000");
        shuffleButton.setStyle("fx-background-color: #ffffff");
        shuffleButton.setTextFill(Color.WHITE);
        endTurnButton.setStyle("fx-background-color: #ffffff");
        endTurnButton.setTextFill(Color.WHITE);
    }

    void setZcrabbleSkin(){
        gameAnchor.setStyle("-fx-background-color: #68BB59");
        rackAnchor.setStyle("-fx-background-color: #5C4425");
        shuffleButton.setStyle("fx-background-color: #ffffff");
        shuffleButton.setTextFill(Color.WHITE);
        endTurnButton.setStyle("fx-background-color: #ffffff");
        endTurnButton.setTextFill(Color.WHITE);
    }

    void setCyberpunkSkin(){
        gameAnchor.setStyle("-fx-background-color: #711c91");
        rackAnchor.setStyle("-fx-background-color: #133e7c");
        shuffleButton.setStyle("-fx-background-color: #fff200");
        shuffleButton.setTextFill(Color.BLACK);
        endTurnButton.setStyle("-fx-background-color: #fff200");
        endTurnButton.setTextFill(Color.BLACK);
    }
}
