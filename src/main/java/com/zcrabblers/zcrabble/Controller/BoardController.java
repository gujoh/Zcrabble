package com.zcrabblers.zcrabble.Controller;

import com.zcrabblers.zcrabble.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * BoardController contains
 */
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
    @FXML private AnchorPane newGameMenuBackground;
    @FXML private AnchorPane welcomeScreen;
    @FXML private Spinner playerSpinner;
    @FXML private Spinner botSpinner;
    @FXML private AnchorPane invalidWordBackground;
    @FXML private Label needMorePlayersLabel;
    @FXML private TextArea tutorialTextArea;
    @FXML private AnchorPane tutorialPane;
    @FXML private AnchorPane swapTilesPane;
    @FXML private AnchorPane swapTilesPopupPane;
    @FXML private AnchorPane winnerPane;
    @FXML private Label winnerLabel;

    private List<ImageView> cellList = new ArrayList<>();
    private List<ImageView> rackList = new ArrayList<>();
    private List<ImageView> swapTileList = new ArrayList<>();

    private MenuController menuController;
    private ArrayList<Label> scoreLabelList = new ArrayList<>();

    private final GameManager gameManager = GameManager.getInstance();
    private IGame game;
    Selection selection = new Selection();
    MultiSelection mSelection = new MultiSelection();

    private static final String IMAGE_PATH = "src/main/resources/com/zcrabblers/zcrabble/Images/";

    private boolean draggedFromRack;
    private int startX;
    private int startY;

    Map<Letter, Image> tileImageMap = new HashMap<>();
    Map<Integer, Image> cellImageMap = new HashMap<>();

    private void initImages(){
       try{
           cellImageMap.put(0, new Image(new FileInputStream(IMAGE_PATH + "Middle.png")));
           cellImageMap.put(11, new Image(new FileInputStream(IMAGE_PATH + "11.png")));
           cellImageMap.put(12, new Image(new FileInputStream(IMAGE_PATH + "12.png")));
           cellImageMap.put(13, new Image(new FileInputStream(IMAGE_PATH + "13.png")));
           cellImageMap.put(21, new Image(new FileInputStream(IMAGE_PATH + "21.png")));
           cellImageMap.put(31, new Image(new FileInputStream(IMAGE_PATH + "31.png")));

           for (Letter letter : Letter.values()) {
               tileImageMap.put(letter, new Image(new FileInputStream(IMAGE_PATH + letter.getLetter() + ".png")));
           }
       }catch (FileNotFoundException e) {
           e.printStackTrace();
       }
    }

    /**
     * Initializes the GUI.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initImages();
        initPlayerSpinner();
        initSwapPane();

        menuController = new MenuController(this);
        menuPane.getChildren().add(menuController);
        needMorePlayersLabel.setVisible(false);
        scoreLabelList.add(p1Score);
        scoreLabelList.add(p2Score);
        scoreLabelList.add(p3Score);
        scoreLabelList.add(p4Score);


        gameManager.newGame((int)playerSpinner.getValue(), (int)botSpinner.getValue());
        game = gameManager.getCurrentGame();
        gameManager.addSubscriber(this);

//        endTurnButton.setOnAction(actionEvent -> {
//            gameManager.getCurrentGame().endTurn();
//        });

        updateScores();
        updateTilesLeft();
        try {
            populate();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            initTutorialPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders the board and rack. Also attaches listeners to the ImageViews that represent the cells and tiles.
     * @throws FileNotFoundException when an image cannot be found.
     */
    public void populate() throws FileNotFoundException {
        populateBoard();
        populateRack();
        //makeOneTestTile();
        initDragTile();
        registerBoardEvents(gameAnchor);
    }

    // Hides the ImageView that is used to show what tile the user is dragging.
    private void hideDragTile(){
        dragImageView.setVisible(false);
        //draggedFrom.setImage(dragImageView.getImage());
    }

    //Initializes the spinners that set the number of players and bots.
    private void initPlayerSpinner(){
        SpinnerValueFactory<Integer> playerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,4,2,1);
        SpinnerValueFactory<Integer> botValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2,0,1);
        playerSpinner.setValueFactory(playerValueFactory);
        botSpinner.setValueFactory(botValueFactory);
        playerSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                botSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4-(int)playerSpinner.getValue(),0,1));
            }
        });

    }

    // Initializes the tile that is dragged around.
    private void initDragTile() throws FileNotFoundException {
        dragImageView.setFitWidth(30);
        dragImageView.setFitHeight(30);
        dragImageView.setVisible(false);
        dragImageView.setMouseTransparent(true);
        dragImageView.setImage(new Image(new FileInputStream(IMAGE_PATH + "TestTile.png")));
        if(!gameAnchor.getChildren().contains(dragImageView)){
            gameAnchor.getChildren().add(dragImageView);
        }
        dragImageView.toFront();
    }

    // Help method used to convert a mouse position to a board coordinate/index.
    private int pos2Coord(double x){
        return (int)Math.floor(x / 33); // remove hard coding?
    }

    // Help method used to convert a mouse position to a rack index.
    private int pos2Rack(double x){
        int leftSpacingRemoved = (int)(x - rackList.get(0).getX());
        return leftSpacingRemoved / 45; // remove hard coding?
    }

    // Switches the images of the image that was dragged from and the parameter
    private void switchImages(CellView cellView){
        Image image = selection.getSelectedImage();
        selection.setImage(cellView.getImage());
        cellView.setImage(image);
    }

    // Moves selected's image to cellView and resets cellView
    private void moveImage(CellView cellView){
        cellView.setImage(selection.getSelectedImage());
        selection.changeToDefaultImage();
    }

    // DRAGGING NOT USED NOW
    // Configure the drag image when dragging starts
    private void dragSequence(CellView cell){
        cell.startFullDrag();
        dragImageView.setImage(cell.getImage());
        dragImageView.setVisible(true);
        cell.changeToDefaultImage();
        draggedFrom = cell;
    }

    // DRAGGING NOT USED NOW
    // What happens continuously during a drag event
    private void OnDragged(MouseEvent mouseEvent){
        Point2D point = new Point2D(mouseEvent.getSceneX() - 45, mouseEvent.getSceneY() - 45);
        dragImageView.setX(point.getX());
        dragImageView.setY(point.getY());
    }

    // Makes the currently selected tile follow the mouse.
    private void registerBoardEvents(Node board) {
        board.setOnMouseMoved(event -> {
            if(!selection.hasSelected())
                return;
            dragImageView.setX(event.getX() - 15);
            dragImageView.setY(event.getY() - 15);
        });
    }

    // What happens when user clicks on a cell on the board.
    private void registerBoardCellClickEvent(CellView cellView){
        cellView.setOnMousePressed(event -> {
            int x = pos2Coord(cellView.getX());
            int y = pos2Coord(cellView.getY());

            if(!game.isBoardCellEmpty(y, x))
                return;
            if(selection.hasSelected()){
                if(game.isTempCellEmpty(y, x)){
                    // Rack -> Board(Tom)
                    if(selection.getFromRack()){
                        game.switchRackBoardCells(selection.getStartX(), y, x);
                    }else{
                        // Board -> Board(Tom)
                        game.switchTempCells(selection.getStartY(), selection.getStartX(), y, x);
                    }
                    moveImage(cellView);
                }else if(!game.isTempCellEmpty(y, x)){
                    if(selection.getFromRack()){
                        // Rack -> Board
                        game.switchRackBoardCells(selection.getStartX(), y, x);
                    }
                    else{
                        // Board -> Board
                        game.switchTempCells(selection.getStartY(), selection.getStartX(), y, x);
                    }
                    switchImages(cellView);
                }
                dragImageView.setVisible(false);
                selection.unSelect();
            }else{
                if(game.isBoardCellEmpty(y, x) && !game.isTempCellEmpty(y, x)){
                    selection.setFromRack(false);
                    selection.select(cellView);
                    selection.setStartX(x);
                    selection.setStartY(y);
                    System.out.println("Selected: " + game.getTempBoard().getTile(y, x).getLetter());

                    Point2D p = cellView.localToScene(cellView.getX(), cellView.getY());
                    Point2D p2 = gameAnchor.sceneToLocal(p);
                    dragImageView.setX(p2.getX());
                    dragImageView.setY(p2.getY());
                    dragImageView.setImage(selection.getSelectedImage());
                    dragImageView.setVisible(true);
                }
            }
        });
    }

    // What happens when the user clicks on a cell on the rack.
    private void registerRackCellEvent(CellView cellView){
        cellView.setOnMousePressed(event -> {
            int x = pos2Rack(cellView.getX());

            System.out.println("rack x: " + x);
            if(selection.hasSelected()){
                if(selection.getFromRack()){
                    if(game.isRackEmpty(x)){
                        // rack -> rack(tom)
                        game.switchRackCells(selection.getStartX(), x);

                        moveImage(cellView);

                    }else{
                        // rack -> rack
                        game.switchRackCells(selection.getStartX(), x);

                        switchImages(cellView);
                    }
                }else{
                    if(game.isRackEmpty(x)){
                        // board -> rack(tom)
                        game.switchRackBoardCells(x, selection.getStartY(), selection.getStartX());

                        moveImage(cellView);
                        //cellView.setImage(selection.getSelectedImage());
                        //selection.changeToDefaultImage();
                    }else{
                        // board -> rack
                        game.switchRackBoardCells(x, selection.getStartY(), selection.getStartX());

                        switchImages(cellView);

                    }
                }
                dragImageView.setVisible(false);
                selection.unSelect();
            }else{
                if(!game.isRackEmpty(x)){
                    selection.setFromRack(true);
                    selection.select(cellView);
                    selection.setStartX(x);
                    System.out.println("Selected: " + game.getRack().getTile(x).getLetter());

                    Point2D p = cellView.localToScene(cellView.getX(), cellView.getY());
                    Point2D p2 = gameAnchor.sceneToLocal(p);
                    dragImageView.setX(p2.getX());
                    dragImageView.setY(p2.getY());
                    dragImageView.setImage(selection.getSelectedImage());
                    dragImageView.setVisible(true);
                }
            }
        });
    }

    // What happens when user clicks on rack cells in the swap popup pane
    private void registerSwapRackCellEvent(CellView cellView){
        cellView.setOnMousePressed(event -> {
            if(mSelection.isSelected(cellView))
                mSelection.unSelect(cellView);
            else
                mSelection.select(cellView);
        });
    }

    //Renders the board. Gets called from the populate() method.
    private void populateBoard() {
        int x = 0;
        int y = 0;
        int length = game.getBoardSize();

        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                CellView img;
                if(i == length/2 && j == length/2){
                    img = new CellView(cellImageMap.get(0)); // Middle image
                }
                 else {
                    img = new CellView(cellImageMap.get(game.getBoard().getBoardCells()[i][j].GetCellWordMultiplier() * 10 + game.getBoard().getBoardCells()[i][j].GetCellLetterMultiplier()));
                 }

                boardAnchor.getChildren().add(img);
                cellList.add(img);
                img.setFitHeight(33);
                img.setFitWidth(33);
                img.setX(x);
                img.setY(y);

                x+=33;

                img.changeToDefaultImage();
                registerBoardCellClickEvent(img);
            }
            x = 0;
            y += 33;
        }
    }

    //Renders the rack. Gets called from the populate() method.
    private void populateRack() throws FileNotFoundException {
        rackList.clear();
        double x = rackRectangle.getWidth()/2-((double)33/2);
        double y = rackRectangle.getHeight()/2-((double)33/2);
        int counter = 0;
        int spacing = 45;
        for(int i = 0; i < 7; i++){
            CellView img = new CellView(cellImageMap.get(11)); // Empty cell image
            rackAnchor.getChildren().add(img);
            rackList.add(img);
            img.setFitHeight(33);
            img.setFitWidth(33);

            x += counter * spacing;
            img.setX(x);
            img.setY(y);
            counter++;
            spacing = -spacing;

            registerRackCellEvent(img);
        }
        
        //Sorts the rack in order to match the way the rack is represented in the model.
        sortBasedOnX(rackList);

        //Fills the rack with images.
        setRackImages();
    }

    //Sorts a list of ImageViews based on their x coordinate.
    private void sortBasedOnX(List<ImageView> list){
        for(int i = 0; i < list.size(); i++){
            for(int j = 1; j < list.size()-1; j++){
                if(list.get(j-1).getX() > list.get(i).getX()){
                    double temp = list.get(j-1).getX();
                    list.get(j-1).setX(list.get(i).getX());
                    list.get(i).setX(temp);
                }
            }
        }
    }

    //Adds the correct images to the rack.
    private void setRackImages(){
        for(int i = 0; i < rackList.size(); i++){
            try {
                Image image = new Image(new FileInputStream(IMAGE_PATH + game.getRackLetter(i) + ".png"));
                rackList.get(i).setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //Converts an index in a 2D array to an index in a 1D array.
    private int coordinateToIndex(int x, int y){
        return y + x * game.getBoardSize();
    }

    /**
     * Updates the state of the game using the observer pattern. Gets called at the end of each turn.
     * @param boardList a LetterTuple array that contain information about which tiles were placed last turn.
     */
    @Override
    public void updateState(ArrayList<CellTuple> boardList, boolean isGameOver){
        for (CellTuple cell : boardList){
            try { //TODO cache this
                ImageView currentCell = cellList.get(coordinateToIndex(cell.getI(), cell.getJ()));
                currentCell.setImage(new Image(new FileInputStream(IMAGE_PATH + cell.getCell().getTileLetter() + ".png")));
                //TODO: Make this work with Letter instead of char (see comment below)
                //cellList.get(coordinateToIndex(cell.getI(), cell.getJ())).setImage(tileImageMap.get(cell.getCell().getTileLetter()));
                currentCell.toBack();  //Calling toFront and toBack to force a repaint of this object. Does not work otherwise.
                currentCell.toFront(); //Seems to be some kind of JavaFX bug.
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(isGameOver){
            showWinnerPane(game.getWinner());
        }

        System.out.println("update");
        updateScores();
        updateTilesLeft();
        setRackImages();
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

    private void showWinnerPane(int winningPlayerNumber){
        winnerLabel.setText("Player " + winningPlayerNumber + " is victorious!");
        winnerPane.toFront();
    }

    @FXML
    private void hideWinnerPane(){
        winnerPane.toBack();
    }

    //Starts a new game by calling newGame in GameManager.
    //Amount of players is based on the spinner values in the new game menu.
    @FXML
    private void newGame() throws FileNotFoundException {
        if((int)playerSpinner.getValue() + (int)botSpinner.getValue() <= 1){
            needMorePlayersLabel.setVisible(true);
        }
        else{
            needMorePlayersLabel.setVisible(false);
            gameManager.newGame((int)playerSpinner.getValue(), (int)botSpinner.getValue());
            game = gameManager.getCurrentGame();
            gameManager.addSubscriber(this);
            newGameMenuBackground.toBack();
            rackAnchor.getChildren().clear();
            rackList.clear();
            dragImageView.toFront();

            for(Label label: scoreLabelList){
                label.setText("");
            }

            populate();
            updateScores();
            updateTilesLeft();
        }

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

    //Attempts to end the current turn. If endTurn() returns false, the word is invalid and a modal panel pops up.
    @FXML
    private void endTurn(){
        if(!game.endTurn()){
            invalidWordBackground.toFront();
        }
        selection.unSelect();
    }

    //Closes the modal panel that shows up when the player tries to play an invalid word.
    @FXML
    private void closeInvalidBackground(){
        invalidWordBackground.toBack();
    }

    //Initializes the tutorial pane. Reads a .txt file that contains the rules.
    private void initTutorialPane() throws IOException {
        File file = new File("src/main/resources/ScrabbleRules.txt");
        StringBuilder contents = new StringBuilder();
        BufferedReader reader = null;
        String text;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) {
            assert reader != null;
            if ((text = reader.readLine()) == null) break;
            contents.append(text).append(System.getProperty("line.separator"));
        }
        reader.close();

        tutorialTextArea.setText(contents.toString());
    }

    //Opens the tutorial pane. Gets called from MenuController, hence why it is package private.
    @FXML
    void openTutorialPane(){
        tutorialPane.toFront();
    }

    //Closes the tutorial pane.
    @FXML
    private void closeTutorialPane(){
        tutorialPane.toBack();
    }

    // Init the popup pane where the user can exchange their tile for new ones.
    private void initSwapPane(){
        int tileSideLength = 45;
        double x = swapTilesPopupPane.getPrefWidth()/2-((double)tileSideLength/2);
        double y = swapTilesPopupPane.getPrefHeight()/2-((double)tileSideLength/2);
        int counter = 0;
        int spacing = 50;
        for (int i = 0; i < 7; i++) {
            CellView img = new CellView(cellImageMap.get(11));
            img.toFront();
            img.setFitWidth(tileSideLength);
            img.setFitHeight(tileSideLength);

            swapTilesPopupPane.getChildren().add(img);
            swapTileList.add(img);

            x += counter * spacing;
            img.setX(x);
            img.setY(y);
            counter++;
            spacing = -spacing;
            registerSwapRackCellEvent(img);
        }
        sortBasedOnX(swapTileList);
    }

    // Open the exchange tiles pane.
    @FXML private void openSwapPane(){
        //swapTilesPane.setVisible(true);
        game.returnTilesToRack();
        swapTilesPane.toFront();
        for (int i = 0; i < rackList.size(); i++) {
            ImageView img = swapTileList.get(i);
            img.setImage(rackList.get(i).getImage());
        }
    }

    // Close the exchange tiles pane.
    @FXML private void closeSwapPane(){
        //swapTilesPane.setVisible(false);
        swapTilesPane.toBack();
    }

    // Exchange tiles in the model after it is confirmed by the user in the swapTilesPane.
    @FXML private void swapPaneSwap(){
        for (CellView cV : mSelection.getSelected()) {
            int index = (int) (cV.getX() - swapTileList.get(0).getX());
            index /= 50;
            game.fromRackToBag(index);
        }
        mSelection.unSelectAll();
        game.endTurn();
        closeSwapPane();
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
