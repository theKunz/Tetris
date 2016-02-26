/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetriminos;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author Aaron
 */
public class TetriminoSquare extends Tetrimino {
    
    //we will assume that the open spot is open, as checking for open area
    //will be done before actually spawning the next tetrimino
    public TetriminoSquare() {
        for (int i = 0; i < 4; i++) {
            blocksX[i] = new SimpleIntegerProperty((i % 2) + 4); //because 0 based, 3 is slot 4
            blocksY[i] = new SimpleIntegerProperty((int)(i / 2));
        }
    }

    @Override
    @SuppressWarnings("UnnecessaryReturnStatement")
    public void rotateClockWise(boolean[][] board) {
        return; //can't rotate the square block
    }

    @Override
    
    public void rotateCounterClockWise(boolean[][] board) {
        return; //can't rotate the square block
    }

    @Override
    public blockType getBlockType() {
        return blockType.SQUARE;
    }

    public static BackgroundImage getBackground() {
        return new BackgroundImage(new Image("/YellowBlock.png"), BackgroundRepeat.ROUND, 
                BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    } 
}
