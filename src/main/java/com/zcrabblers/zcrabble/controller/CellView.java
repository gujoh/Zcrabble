package com.zcrabblers.zcrabble.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
