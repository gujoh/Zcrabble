package com.zcrabblers.zcrabble.Controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class CellView extends ImageView {
    private String defaultImageLocation;

    public CellView(String defaultImageLocation){
        this.defaultImageLocation = defaultImageLocation;
    }

    public void changeToDefaultImage(){
        try {
            FileInputStream stream = new FileInputStream(defaultImageLocation);
            setImage(new Image(stream));
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
