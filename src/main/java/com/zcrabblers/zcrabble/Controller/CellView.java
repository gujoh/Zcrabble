package com.zcrabblers.zcrabble.Controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CellView extends ImageView {
    private final Image defaultImage;

    public CellView(Image defaultImage){
        this.defaultImage = defaultImage;
        changeToDefaultImage();
    }

    public void changeToDefaultImage(){
        setImage(defaultImage);
    }
}
