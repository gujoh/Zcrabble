package com.zcrabblers.zcrabble.controller;

import com.zcrabblers.zcrabble.model.*;
import com.zcrabblers.zcrabble.model.gameBoard.CellTuple;
import com.zcrabblers.zcrabble.model.observers.ILetterObservable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * BoardController is the controller class for the board.fxml file.
 * It renders the game and interacts with the model.
 */
public class BoardController implements Initializable, ILetterObservable {
    @FXML private AnchorPane boardAnchor;
    @FXML private AnchorPane rackAnchor;
    @FXML private Rectangle rackRectangle;
    @FXML private ImageView dragImageView;
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
    @FXML private Spinner<Integer> playerSpinner;
    @FXML private Spinner<Integer> botSpinner;
    @FXML private AnchorPane invalidWordBackground;
    @FXML private Label needMorePlayersLabel;
    @FXML private TextArea tutorialTextArea;
    @FXML private AnchorPane tutorialPane;
    @FXML private AnchorPane swapTilesPane;
    @FXML private AnchorPane swapTilesPopupPane;
    @FXML private AnchorPane winnerPane;
    @FXML private Label winnerLabel;
    @FXML private Button swapButton;
    @FXML private AnchorPane rootPane;
    @FXML private Button returnToRackButton;

    private final List<CellView> cellList = new ArrayList<>();
    private final List<ImageView> rackList = new ArrayList<>();
    private final List<ImageView> swapTileList = new ArrayList<>();
    private final List<Label> scoreLabelList = new ArrayList<>();
    private final List<Button> buttonsChangedBySkinsList = new ArrayList<>();

    private final GameManager gameManager = GameManager.getInstance();
    private Game game;
    private final Selection selection = new Selection();
    private final MultiSelection mSelection = new MultiSelection();

    private final static int IMAGE_SIZE = 33;
    private static final String IMAGE_PATH = "src/main/resources/com/zcrabblers/zcrabble/Images/";

    private final Map<Letter, Image> tileImageMap = new HashMap<>();
    private final Map<Integer, Image> cellImageMap = new HashMap<>();

    //Images get added to a HashMap here.
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
        initLists();


        MenuController menuController = new MenuController(this);
        menuPane.getChildren().add(menuController);
        needMorePlayersLabel.setVisible(false);
        gameManager.newGame(1,1);
        game = gameManager.getCurrentGame();
        game.addSubscriber(this);

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
        initDragTile();
        registerBoardEvents(gameAnchor);
    }

    //Adds some buttons to a couple of lists.
    private void initLists(){
        scoreLabelList.add(p1Score);
        scoreLabelList.add(p2Score);
        scoreLabelList.add(p3Score);
        scoreLabelList.add(p4Score);

        buttonsChangedBySkinsList.add(shuffleButton);
        buttonsChangedBySkinsList.add(swapButton);
        buttonsChangedBySkinsList.add(returnToRackButton);
        buttonsChangedBySkinsList.add(endTurnButton);
    }

    //Initializes the spinners that set the number of players and bots.
    private void initPlayerSpinner(){
        //TODO: make it possible to only have bots play each other, currently it does not render correctly.
        SpinnerValueFactory<Integer> playerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4,2,1);
        SpinnerValueFactory<Integer> botValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2,0,1);
        playerSpinner.setValueFactory(playerValueFactory);
        botSpinner.setValueFactory(botValueFactory);
        playerSpinner.valueProperty().addListener(
                (observable, oldValue, newValue) -> botSpinner.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4-(int)playerSpinner.getValue(),0,1)));
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

    //Initializes the tutorial pane. Reads a .txt file that contains the rules.
    private void initTutorialPane() throws IOException {
        File file = new File("src/main/resources/ZcrabbleRules.txt");
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
                img.setFitHeight(IMAGE_SIZE);
                img.setFitWidth(IMAGE_SIZE);
                img.setX(x);
                img.setY(y);

                x+=IMAGE_SIZE;

                img.changeToDefaultImage();
                registerBoardCellClickEvent(img);
            }
            x = 0;
            y += IMAGE_SIZE;
        }
    }

    //Renders the rack. Gets called from the populate() method.
    private void populateRack() {
        rackList.clear();
        double x = rackRectangle.getWidth()/2-((double)IMAGE_SIZE/2);
        double y = rackRectangle.getHeight()/2-((double)IMAGE_SIZE/2);
        int counter = 0;
        int spacing = 45;
        for(int i = 0; i < 7; i++){
            CellView img = new CellView(cellImageMap.get(11)); // Empty cell image
            rackAnchor.getChildren().add(img);
            rackList.add(img);
            img.setFitHeight(IMAGE_SIZE);
            img.setFitWidth(IMAGE_SIZE);

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
            if(game.isGameOver()){
                return;
            }
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
                selection.setSelectedOpacity(1);
                selection.unSelect();
            }else{
                if(game.isBoardCellEmpty(y, x) && !game.isTempCellEmpty(y, x)){
                    selection.setFromRack(false);
                    selection.select(cellView);
                    selection.setStartX(x);
                    selection.setStartY(y);

                    Point2D p = cellView.localToScene(cellView.getX(), cellView.getY());
                    Point2D p2 = gameAnchor.sceneToLocal(p);
                    dragImageView.setX(p2.getX());
                    dragImageView.setY(p2.getY());
                    dragImageView.setImage(selection.getSelectedImage());
                    dragImageView.setVisible(true);

                    cellView.setOpacity(0.25);
                }
            }
        });
    }

    // What happens when the user clicks on a cell on the rack.
    private void registerRackCellEvent(CellView cellView){
        cellView.setOnMousePressed(event -> {
            if(game.isGameOver()){
                return;
            }
            int x = pos2Rack(cellView.getX());

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
                selection.setSelectedOpacity(1);
                selection.unSelect();
            }else{
                if(!game.isRackEmpty(x)){
                    selection.setFromRack(true);
                    selection.select(cellView);
                    selection.setStartX(x);

                    Point2D p = cellView.localToScene(cellView.getX(), cellView.getY());
                    Point2D p2 = gameAnchor.sceneToLocal(p);
                    dragImageView.setX(p2.getX());
                    dragImageView.setY(p2.getY());
                    dragImageView.setImage(selection.getSelectedImage());
                    dragImageView.setVisible(true);

                    cellView.setOpacity(0.25);
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

    //Adds the correct images to the rack.
    //TODO: use cached map instead of reading a file
    private void setRackImages(){
        for(int i = 0; i < rackList.size(); i++){
            Image image;
            try {
                if(game.getRackLetter(i) == ' '){
                    image = cellImageMap.get(11);
                }
                else{
                    image = new Image(new FileInputStream(IMAGE_PATH + game.getRackLetter(i) + ".png"));
                }
                rackList.get(i).setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // sets the images on the board based on the model
    //TODO: use cached map instead of reading a file
    private void setBoardImages(){
        int y = 0;
        int x = 0;
        for (CellView cellView : cellList) {
            if (game.getBoard().isCellEmpty(y, x)) {
                cellView.changeToDefaultImage();
            } else {
                Image img;
                try {
                    img = new Image(new FileInputStream(IMAGE_PATH + game.getBoard().getTile(y, x).getLetter() + ".png"));
                } catch (FileNotFoundException e) {
                    img = cellImageMap.get(11);
                }
                cellView.setImage(img);
            }
            x++;
            if (x > 14) {
                y++;
                x = 0;
            }
            if (y > 14) y = 0;
        }
    }

    /**
     * Updates the state of the game using the observer pattern. Gets called at the end of each turn.
     * @param boardList a LetterTuple array that contain information about which tiles were placed last turn.
     */
    @Override
    public void updateState(List<CellTuple> boardList, boolean isGameOver){
        for (CellTuple cell : boardList){
            try {
                ImageView currentCell = cellList.get(coordinateToIndex(cell.getI(), cell.getJ()));
                if(cell.getCell().getTileLetter() == ' '){
                    currentCell.setImage(cellImageMap.get(11));
                }
                else{
                    currentCell.setImage(new Image(new FileInputStream(IMAGE_PATH + cell.getCell().getTileLetter() + ".png")));
                }

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

        updateScores();
        updateTilesLeft();
        setRackImages();
    }

    //Updates all the players' scores by getting them from the Game object.
    //Gets called from update() and initialize().
    private void updateScores(){
        for(int i = 0; i < game.getPlayers().size(); i++){
            if(i == game.getCurrentPlayerIndex()){
                scoreLabelList.get(i).setTextFill(Color.RED);
            }
            else scoreLabelList.get(i).setTextFill(Color.BLACK);
            scoreLabelList.get(i).setText("P" + (i+1) + ": " + game.getPlayerScore(i));
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
        if(game.isGameOver()){
            return;
        }
        game.shuffleCurrentRack();
        setRackImages();
    }

    //Starts a new game by calling newGame in GameManager.
    //Amount of players is based on the spinner values in the new game menu.
    @FXML
    private void newGame() throws FileNotFoundException {
        if(playerSpinner.getValue() + botSpinner.getValue() <= 1){
            needMorePlayersLabel.setVisible(true);
        }
        else{
            needMorePlayersLabel.setVisible(false);
            gameManager.newGame(playerSpinner.getValue(), botSpinner.getValue());
            game = gameManager.getCurrentGame();
            game.addSubscriber(this);
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

    //Attempts to end the current turn. If endTurn() returns false, the word is invalid and a modal panel pops up.
    @FXML
    private void endTurn(){
        if(game.isGameOver()){
            return;
        }
        if(!game.endTurn()){
            invalidWordBackground.toFront();
        }
        selection.unSelect();
    }

    //Returns tiles on the board to the rack.
    @FXML
    private void returnToRack(){
        game.returnTilesToRack();
        setRackImages();
        setBoardImages();
    }

    //Converts an index in a 2D array to an index in a 1D array.
    private int coordinateToIndex(int x, int y){
        return y + x * game.getBoardSize();
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

    // Help method used to convert a mouse position to a board coordinate/index.
    private int pos2Coord(double x){
        return (int)Math.floor(x / IMAGE_SIZE); // remove hard coding?
    }

    // Help method used to convert a mouse position to a rack index.
    private int pos2Rack(double x){
        int leftSpacingRemoved = (int)(x - rackList.get(0).getX());
        return leftSpacingRemoved / 45; // remove hard coding?
    }

    //Opens the winner pane.
    private void showWinnerPane(int winningPlayerNumber){
        winnerLabel.setText("Player " + winningPlayerNumber + " is victorious!");
        winnerPane.toFront();
    }

    //Hides the winner pane.
    @FXML
    private void hideWinnerPane(){
        winnerPane.toBack();
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

    //Closes the modal panel that shows up when the player tries to play an invalid word.
    @FXML
    private void closeInvalidBackground(){
        invalidWordBackground.toBack();
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

    // Open the exchange tiles pane.
    @FXML
    private void openSwapPane(){
        if(game.isGameOver() || game.getRemainingTiles() <= 7){
            return;
        }
        returnToRack();
        swapTilesPane.toFront();
        for (int i = 0; i < rackList.size(); i++) {
            ImageView img = swapTileList.get(i);
            img.setImage(rackList.get(i).getImage());
        }
    }

    // Close the exchange tiles pane.
    @FXML
    private void closeSwapPane(){
        swapTilesPane.toBack();
    }

    // Exchange tiles in the model after it is confirmed by the user in the swapTilesPane.
    @FXML
    private void swapPaneSwap(){
        for (CellView cV : mSelection.getSelected()) {
            int index = (int) (cV.getX() - swapTileList.get(0).getX());
            index /= 50;
            game.fromRackToBag(index);
        }
        mSelection.unSelectAll();
        game.endTurn();
        closeSwapPane();
    }

    //Sets the size of the GUI based on the scaleFactor.
    void setWindowSize(double scaleFactor){
        int initialWindowWidth = 600;
        int initialWindowHeight = 628;
        rootPane.getScene().getWindow().setWidth(initialWindowWidth * scaleFactor);
        rootPane.getScene().getWindow().setHeight(initialWindowHeight * scaleFactor);
    }

    //Sets the Zcrabble theme dark mode. Gets called from the MenuController class.
    void setDarkModeSkin(){
        gameAnchor.setStyle("-fx-background-color: #808080");
        rackAnchor.setStyle("-fx-background-color: #000000");
        for(Button button : buttonsChangedBySkinsList){
            button.setStyle("fx-background-color: #ffffff");
            button.setTextFill(Color.WHITE);
        }
    }

    //Sets the Zcrabble theme the original Zcrabble skin. Gets called from the MenuController class.
    void setZcrabbleSkin(){
        gameAnchor.setStyle("-fx-background-color: #68BB59");
        rackAnchor.setStyle("-fx-background-color: #5C4425");
        for(Button button : buttonsChangedBySkinsList){
            button.setStyle("fx-background-color: #ffffff");
            button.setTextFill(Color.WHITE);
        }
    }

    //Sets the Zcrabble theme to cyberpunk. Gets called from the MenuController class.
    void setCyberpunkSkin(){
        gameAnchor.setStyle("-fx-background-color: #711c91");
        rackAnchor.setStyle("-fx-background-color: #133e7c");
        for(Button button : buttonsChangedBySkinsList){
            button.setStyle("-fx-background-color: #fff200");
            button.setTextFill(Color.BLACK);
        }
    }
}
