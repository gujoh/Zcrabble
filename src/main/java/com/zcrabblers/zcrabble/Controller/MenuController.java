package com.zcrabblers.zcrabble.Controller;

import com.zcrabblers.zcrabble.HelloApplication;
import com.zcrabblers.zcrabble.Model.GameManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuController extends AnchorPane {

    @FXML private MenuItem menuSkinsZcrabble;
    @FXML private MenuItem menuSkinsDarkMode;
    @FXML private MenuItem menuSkinsCyberpunk;
    @FXML private MenuItem MenuNewGame;
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

        MenuNewGame.setOnAction(event -> {
            GameManager.getInstance().newGame();
        });
    }

    @FXML
    private void darkModeOnClick(){
        parentController.setDarkModeSkin();
    }

    @FXML
    private void zcrabbleSkinOnClick(){
        parentController.setZcrabbleSkin();
    }
}
