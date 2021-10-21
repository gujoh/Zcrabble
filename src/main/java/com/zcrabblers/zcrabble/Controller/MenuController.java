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

    @FXML
    private void newGame(){
        parentController.openNewGameMenu();
    }

    @FXML
    private void darkModeOnClick(){
        parentController.setDarkModeSkin();
    }

    @FXML
    private void zcrabbleSkinOnClick(){
        parentController.setZcrabbleSkin();
    }

    @FXML
    private void cyberPunkSkinOnClick(){
        parentController.setCyberpunkSkin();
    }

    @FXML
    private void openTutorialPane(){
        parentController.openTutorialPane();
    }

    @FXML
    private void setWindowSize100(){
        parentController.setWindowSize(1);
    }

    @FXML
    private void setWindowSize125(){
        parentController.setWindowSize(1.25);
    }

    @FXML
    private void setWindowSize150(){
        parentController.setWindowSize(1.5);
    }

    @FXML
    private void setWindowSize175(){
        parentController.setWindowSize(1.7);
    }

    @FXML
    private void setWindowSize200(){
        parentController.setWindowSize(2);
    }
}
