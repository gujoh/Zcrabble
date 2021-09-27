package com.zcrabblers.zcrabble.Controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CellView extends ImageView {
    private Image defaultImage;

    public CellView(String defaultImageLocation){
        try {
            FileInputStream stream = new FileInputStream(defaultImageLocation);
            defaultImage = new Image(stream);
            setImage(defaultImage);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isDeafultImage(){
        return defaultImage == getImage();
    }

    public void changeToDefaultImage(){
        setImage(defaultImage);
    }
}
