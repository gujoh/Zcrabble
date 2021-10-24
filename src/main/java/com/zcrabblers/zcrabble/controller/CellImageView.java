package com.zcrabblers.zcrabble.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * CellView is like an ImageView but also has a so-called default image.
 * @author Niklas Axelsson
 * used by: BoardImageView
 */
public class CellImageView extends ImageView {
    private final Image defaultImage;

    public CellImageView(Image defaultImage){
        this.defaultImage = defaultImage;
        changeToDefaultImage();
    }

    /**
     * Change the current image displayed by the ImageView to the default image.
     */
    public void changeToDefaultImage(){
        setImage(defaultImage);
    }
}
