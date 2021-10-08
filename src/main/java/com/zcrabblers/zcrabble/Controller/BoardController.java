package com.zcrabblers.zcrabble.Controller;

import com.zcrabblers.zcrabble.Model.*;
import com.zcrabblers.zcrabble.Model.Cell;
import com.zcrabblers.zcrabble.Utils.RandomSeed;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
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
    @FXML private AnchorPane welcomeScreen;
    @FXML private Spinner playerSpinner;
    @FXML private Spinner botSpinner;

    private ArrayList<Button> cellList = new ArrayList<>();
    private ArrayList<Button> rackList = new ArrayList<>();
    private ArrayList<Tile> tempTiles = new ArrayList<>();
    private ArrayList<Cell> newCells = new ArrayList<>();
    private MenuController menuController;
    private ArrayList<Label> scoreLabelList = new ArrayList<>();

    private final GameManager gameManager = GameManager.getInstance();
    private IGame game;

    private static final String IMAGE_PATH = "src/main/resources/com/zcrabblers/zcrabble/Images/";

    private boolean draggedFromRack;
    private int startX;
    private int startY;

    public BoardController() throws FileNotFoundException {
    }

    /**
     * Initializes the GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuController = new MenuController(this);
        menuPane.getChildren().add(menuController);
        scoreLabelList.add(p1Score);
        scoreLabelList.add(p2Score);
        scoreLabelList.add(p3Score);
        scoreLabelList.add(p4Score);

        gameManager.addSubscriber(this);

        SpinnerValueFactory<Integer> playerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,4,2,1);
        SpinnerValueFactory<Integer> botValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2,0,1);
        playerSpinner.setValueFactory(playerValueFactory);
        botSpinner.setValueFactory(botValueFactory);
        playerSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                botSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4-(int)playerSpinner.getValue(),0,1));
            }
        });

        gameManager.newGame((int)playerSpinner.getValue(), (int)botSpinner.getValue());
        game = gameManager.getCurrentGame();

//        endTurnButton.setOnAction(actionEvent -> {
//            gameManager.getCurrentGame().endTurn();
//        });

        try {
            populate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        updateScores();
        updateTilesLeft();
    }

    /**
     * Renders the board and rack. Also attaches listeners to the ImageViews that represent the cells and tiles.
     * @throws FileNotFoundException when an image cannot be found.
     */
    public void populate() throws FileNotFoundException {
        populateBoard();
        populateRack();
        //makeOneTestTile();
        //initDragTile();
        //rackRectangle.setOnMouseDragReleased(mouseEvent -> hideDragTile());
        //boardAnchor.setOnMouseDragReleased(mouseEvent -> hideDragTile());
        //rackAnchor.setOnMouseDragReleased(mouseEvent -> hideDragTile());
        //rackAnchor.setMouseTransparent(true);

        //gameAnchor.setOnMouseDragReleased(event -> hideDragTile());
        //gameAnchor.setOnMouseDragReleased(mouseDragEvent -> {
        //    if(mouseDragEvent.isConsumed())
        //        return;
        //    dragImageView.setVisible(false);
        //    draggedFrom.setImage(dragImageView.getImage());
        //    mouseDragEvent.setDragDetect(false);
        //    System.out.println("reset");
        //    System.out.println(mouseDragEvent.getTarget().toString());
        //});
    }

    private void hideDragTile(){
        dragImageView.setVisible(false);
        //draggedFrom.setImage(dragImageView.getImage());
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
        //draggedFrom.setImage(cellView.getImage());
        //cellView.setImage(dragImageView.getImage());
        Image image = selection.getSelectedImage();
        selection.setImage(cellView.getImage());
        cellView.setImage(image);
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


    Selection selection = new Selection();
    private void registerBoardCellClickEvent(CellView cellView){
        cellView.setOnMousePressed(mouseEvent -> {
            int x = pos2Coord(mouseEvent.getX());
            int y = pos2Coord(mouseEvent.getY());
            if(selection.hasSelected()){
                if(game.isBoardCellEmpty(x, y) && game.isTempCellEmpty(x, y)){
                    if(selection.getFromRack()){
                        Tile tile = game.getRack().getTile(selection.getStartX());
                        game.getRack().remove(selection.getStartX());
                        game.getTempBoard().placeTile(x, y, tile);
                        cellView.setImage(selection.getSelectedImage());
                        selection.changeToDefaultImage();
                    }else{
                        game.switchTempCells(selection.getStartX(), selection.getStartY(), x, y);
                        switchImages(cellView);
                    }
                }else if(game.isBoardCellEmpty(x, y) && !game.isTempCellEmpty(x, y)){
                    if(selection.getFromRack())
                        game.switchRackBoardCells(selection.getStartX(), x, y);
                    else
                        game.switchTempCells(selection.getStartX(), selection.getStartY(), x, y);
                    switchImages(cellView);
                }
                selection.unSelect();
            }else{
                if(game.isBoardCellEmpty(x, y) && !game.isTempCellEmpty(x, y)){
                    selection.setFromRack(false);
                    selection.select(cellView);
                    selection.setStartX(pos2Coord(x));
                    selection.setStartY(pos2Coord(y));
                }
            }
            mouseEvent.consume();
        });

    }
    private void registerRackCellEvent(CellView cellView){
        cellView.setOnMousePressed(mouseEvent -> {
            int x = pos2Rack(mouseEvent.getX());
            if(selection.hasSelected()){
                if(selection.getFromRack()){
                    // rack -> rack
                    game.switchRackCells(selection.getStartX(), x);
                    switchImages(cellView);
                }else{
                    // board -> rack
                    game.switchRackBoardCells(x, selection.getStartX(), selection.getStartY());
                    switchImages(cellView);
                }
                selection.unSelect();
            }else{
                if(!game.isRackEmpty(x)){
                    selection.setFromRack(true);
                    selection.select(cellView);
                    selection.setStartX(x);
                }
            }
            mouseEvent.consume();
        });
    }

    // Sets how tiles dragged from the rack behaves
    private void registerRackCellEvents(CellView cellView){
        // starting a drag event from a rack tile
        cellView.setOnDragDetected(event -> {
            // get index based on mouse position
            startX = pos2Rack(event.getX());
            //System.out.println(startX);

            // if not empty start drag sequence
            if(!game.isRackEmpty(startX)){
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
                game.switchRackCells(startX, x);

                // switch images
                switchImages(cellView);
            }else{
                game.switchRackBoardCells(x, startX, startY);
                if(game.isRackEmpty(x)){
                    cellView.setImage(dragImageView.getImage());
                }else{
                    // dragging from the tempboard to the rack switches the tiles
                    switchImages(cellView);
                }
            }
            //event.setDragDetect(false);
            event.consume();
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
            if(game.isBoardCellEmpty(startX, startY) &&
                    !game.isTempCellEmpty(startX, startY)) {
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
            if(game.isBoardCellEmpty(x, y) && game.isTempCellEmpty(x, y)){
                if(draggedFromRack){
                    //get tile from rack
                    Tile tile = game.getRack().getTile(startX);
                    //remove tile from rack
                    game.getRack().remove(startX);
                    //add tile to tempboard
                    game.getTempBoard().placeTile(x, y, tile);
                    //rack image was reset on drag start
                    //set board image
                }else{
                    //switch tiles on tempboard
                    game.switchTempCells(startX, startY, x, y);
                    //switch images
                    draggedFrom.changeToDefaultImage();
                    //switchImages(cellView);
                }
                cellView.setImage(dragImageView.getImage());
            }else if(game.isBoardCellEmpty(x, y) && !game.isTempCellEmpty(x, y)){
                if(draggedFromRack){
                    game.switchRackBoardCells(startX, x, y);
                }else{
                    game.switchTempCells(startX, startY, x, y);
                }
                switchImages(cellView);
            }
            else{
                //cell dragged to wasn't empty so reset image
                draggedFrom.setImage(dragImageView.getImage());
            }
            //event.setDragDetect(false);
            event.consume();
        });
    }

    //Renders the board. Gets called from the populate() method.
    private void populateBoard() throws FileNotFoundException {
        int x = 0;
        int y = 0;
        int length = gameManager.getBoardSize();
        BackgroundSize backgroundSize = new BackgroundSize(33,33,false,false,true,false);
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                Button img;
                img = new Button();
                if(i == length/2 && j == length/2){
                    //img.setGraphic(new ImageView(new Image(new FileInputStream(IMAGE_PATH + "Middle.png"))));
                    img.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(IMAGE_PATH + "Middle.png")),null,null,null, backgroundSize)));
                }
                 else {
                    //img.setGraphic(new ImageView(new Image(new FileInputStream(IMAGE_PATH + gameManager.getBoardCells()[i][j].GetCellWordMultiplier() + "" + gameManager.getBoardCells()[i][j].GetCellLetterMultiplier() + ".png"))));
                    img.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(IMAGE_PATH + gameManager.getBoardCells()[i][j].GetCellWordMultiplier() + "" + gameManager.getBoardCells()[i][j].GetCellLetterMultiplier() + ".png")),null,null,null,backgroundSize)));

                }

                img.getStyleClass().remove("button");
                img.getStyleClass().add("image-view");

                boardAnchor.getChildren().add(img);
                cellList.add(img);
                img.setContentDisplay(ContentDisplay.CENTER);
                img.setPrefSize(33,33);
//                img.setFitHeight(33);
//                img.setFitWidth(33);
//                img.setLayoutX(x);
//                img.setLayoutY(y);
                img.relocate(x,y);
                //img.getGraphic().setScaleX((double)33/50);
                //img.getGraphic().setScaleY((double)33/50);
//                img.setX(x);
//                img.setY(y);
                x+=33;

                //img.setImage((new Image(new FileInputStream(IMAGE_PATH + game.getBoard().Matrix()[i][j].GetCellWordMultiplier() + "" + game.getBoard().Matrix()[i][j].GetCellLetterMultiplier() + ".png"))));
               // img.changeToDefaultImage();
                //registerBoardCellEvents(img);
               // registerBoardCellClickEvent(img);
            }
            x = 0;
            y += 33;
        }
    }

    //Renders the rack. Gets called from the populate() method.
    private void populateRack() throws FileNotFoundException {
        double x = rackRectangle.getWidth()/2-((double)33/2);
        double y = rackRectangle.getHeight()/2-((double)33/2);
        int counter = 0;
        int spacing = 45;
        BackgroundSize backgroundSize = new BackgroundSize(33,33,false,false,true,false);
        for(int i = 0; i < 7; i++){
            Button img = new Button();
            img.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream(IMAGE_PATH + "BasicCell.png")),null,null,null, backgroundSize)));
           // img.setGraphic(new ImageView(new Image(new FileInputStream(IMAGE_PATH + "11.png"))));

            img.getStyleClass().remove("button");
            img.getStyleClass().add("image-view");

            rackAnchor.getChildren().add(img);
            rackList.add(img);
            img.setContentDisplay(ContentDisplay.CENTER);
            img.setPrefSize(33,33);
//            img.setFitHeight(33);
//            img.setFitWidth(33);

            x += counter * spacing;
            img.relocate(x,y);
//            img.setX(x);
//            img.setY(y);
            counter++;
            spacing = -spacing;
            img.setPickOnBounds(true);
            //registerRackCellEvents(img);
           // registerRackCellEvent(img);
        }
        
//        //Sorts the rack in order to match the way the rack is represented in the model.
//        for(int i = 0; i < rackList.size(); i++){
//            for(int j = 1; j < rackList.size()-1; j++){
//                if(rackList.get(j-1).getX() > rackList.get(i).getX()){
//                    double temp = rackList.get(j-1).getX();
//                    rackList.get(j-1).setX(rackList.get(i).getX());
//                    rackList.get(i).setX(temp);
//                }
//            }
//        }
        //Fills the rack with images.
        setRackImages();
    }

    //Adds the correct images to the rack.
    private void setRackImages(){
        for(int i = 0; i < rackList.size(); i++){
            try {
                ImageView image = new ImageView(new Image(new FileInputStream(IMAGE_PATH + game.getRackLetter(i) + ".png")));
                image.setFitHeight(33);
                image.setFitWidth(33);
                rackList.get(i).setGraphic(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //rackList.get(i).getGraphic().resize(33,33);
            //rackList.get(i).autosize();
        }
    }

    //Converts an index in a 2D array to an index in a 1D array.
    private int coordinateToIndex(int x, int y){
        return x + y* gameManager.getBoardSize();
    }

    /**
     * Updates the state of the game using the observer pattern. Gets called at the end of each turn.
     * @param boardList a LetterTuple array that contain information about which tiles were placed last turn.
     * @param rackList a LetterTuple array that contains information about which tiles were added to a player's rack.
     */
    @Override
    public void update(ArrayList<LetterTuple> boardList, ArrayList<LetterTuple> rackList){
//        for (LetterTuple letter : boardList){
//            try {
//                cellList.get(coordinateToIndex(letter.getX(), letter.getY())).setImage(new Image(new FileInputStream(IMAGE_PATH + letter.getLetter() + ".png")));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (LetterTuple letter : rackList){
//            try {
//                this.rackList.get(letter.getX()).setImage(new Image(new FileInputStream(IMAGE_PATH + letter.getLetter() + ".png")));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        updateScores();
//        updateTilesLeft();
    }

    //Updates all the players' scores by getting them from the Game object.
    //Gets called from update() and initialize().
    private void updateScores(){
        for(int i = 0; i < game.getPlayers().size(); i++){
            scoreLabelList.get(i).setText(String.valueOf(game.getPlayerScore(i)));
        }
    }

    //Updates the tilesLeftLabel by getting the remaining tiles from the Game object.
    //Gets called from update() and initialize().
    private void updateTilesLeft(){
        tilesLeftLabel.setText(String.valueOf(game.getRemainingTiles()));
    }

    //Shuffles the current player's rack.
    @FXML
    private void shuffleRack(){
        game.shuffleCurrentRack();
        setRackImages();
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

    @FXML
    private void newGame() throws FileNotFoundException {
        gameManager.newGame((int)playerSpinner.getValue(), (int)botSpinner.getValue());
        game = gameManager.getCurrentGame();
        gameManager.addSubscriber(this);
        newGameMenuBackground.toBack();
        rackAnchor.getChildren().clear();
        rackList.clear();

        for(Label label: scoreLabelList){
            label.setText("");
        }

        populate();
        updateScores();
        updateTilesLeft();
    }

    //Closes the welcome screen by calling the toBack() method on the AnchorPane.
    @FXML
    private void closeWelcomeScreen(){
        welcomeScreen.toBack();
    }

    //Opens the new game menu screen by calling the toFront() method on the AnchorPane.
    @FXML
    void openNewGameMenu(){
        newGameMenuBackground.toFront();
    }

    //Closes the new game menu screen by calling the toBack() method on the AnchorPane.
    @FXML
    private void closeNewGameMenu(){
        newGameMenuBackground.toBack();
    }

    @FXML private void endTurn(){
        game.endTurn();
    }

    //Sets the Zcrabble theme dark mode. Gets called from the MenuController class.
    void setDarkModeSkin(){
        gameAnchor.setStyle("-fx-background-color: #808080");
        rackAnchor.setStyle("-fx-background-color: #000000");
        shuffleButton.setStyle("fx-background-color: #ffffff");
        shuffleButton.setTextFill(Color.WHITE);
        endTurnButton.setStyle("fx-background-color: #ffffff");
        endTurnButton.setTextFill(Color.WHITE);
    }

    //Sets the Zcrabble theme the original Zcrabble skin. Gets called from the MenuController class.
    void setZcrabbleSkin(){
        gameAnchor.setStyle("-fx-background-color: #68BB59");
        rackAnchor.setStyle("-fx-background-color: #5C4425");
        shuffleButton.setStyle("fx-background-color: #ffffff");
        shuffleButton.setTextFill(Color.WHITE);
        endTurnButton.setStyle("fx-background-color: #ffffff");
        endTurnButton.setTextFill(Color.WHITE);
    }

    //Sets the Zcrabble theme to cyberpunk. Gets called from the MenuController class.
    void setCyberpunkSkin(){
        gameAnchor.setStyle("-fx-background-color: #711c91");
        rackAnchor.setStyle("-fx-background-color: #133e7c");
        shuffleButton.setStyle("-fx-background-color: #fff200");
        shuffleButton.setTextFill(Color.BLACK);
        endTurnButton.setStyle("-fx-background-color: #fff200");
        endTurnButton.setTextFill(Color.BLACK);
    }
}
