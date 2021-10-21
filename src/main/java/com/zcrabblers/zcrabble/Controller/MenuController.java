package com.zcrabblers.zcrabble.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MenuController extends AnchorPane {

    private BoardController parentController;

    public MenuController(BoardController parentController){
        this.parentController = parentController;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/zcrabblers/zcrabble/Menu_Controller.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    //Calls openNewGameMenu in parentController (BoardController).
    @FXML
    private void newGame(){
        parentController.openNewGameMenu();
    }

    //Calls setDarkModeSkin in parentController (BoardController).
    @FXML
    private void darkModeOnClick(){
        parentController.setDarkModeSkin();
    }

    //Calls setZcrabbleSkin in parentController (BoardController).
    @FXML
    private void zcrabbleSkinOnClick(){
        parentController.setZcrabbleSkin();
    }

    //Calls setCyberpunkSkin in parentController (BoardController).
    @FXML
    private void cyberPunkSkinOnClick(){
        parentController.setCyberpunkSkin();
    }

    //Calls openTutorialPane in parentController (BoardController).
    @FXML
    private void openTutorialPane(){
        parentController.openTutorialPane();
    }

    //Calls setWindowSize in parentController (BoardController). Sets the window size to 1.03 * initial size.
    @FXML
    private void setWindowSize100(){
        parentController.setWindowSize(1.03); //1 does not work on Windows for some reason. 1.03 is the smallest number that works.
    }

    //Calls setWindowSize in parentController (BoardController). Sets the window size to 1.03 * initial size.
    @FXML
    private void setWindowSize125(){
        parentController.setWindowSize(1.25);
    }

    //Calls setWindowSize in parentController (BoardController). Sets the window size to 1.5 * initial size.
    @FXML
    private void setWindowSize150(){
        parentController.setWindowSize(1.5);
    }

    //Calls setWindowSize in parentController (BoardController). Sets the window size to 1.7 * initial size.
    @FXML
    private void setWindowSize175(){
        parentController.setWindowSize(1.7);
    }

    //Calls setWindowSize in parentController (BoardController). Sets the window size to 2 * initial size.
    @FXML
    private void setWindowSize200(){
        parentController.setWindowSize(2);
    }
}
