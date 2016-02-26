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
public class TetriminoL extends Tetrimino {
    
    public TetriminoL() {
        blocksX[0] = new SimpleIntegerProperty(4);
        blocksY[0] = new SimpleIntegerProperty(1);
        for (int i = 4; i < 7; i++) {
            blocksX[i - 3] = new SimpleIntegerProperty(i);
            blocksY[i - 3] = new SimpleIntegerProperty(0);
        }
    }

    @Override
    public void rotateClockWise(boolean[][] board) {
        if (!boardIsGood(board))
            return;
        switch (super.getRotation()) {
            case 0:
                int[] xRotVal0 = {0, 1, 0, -1}; //
                int[] yRotVal0 = {-2, -1, 0, 1};//
                rotateByValues(xRotVal0, yRotVal0, board, true);
                break;
            case 90:
                int[] xRotVal90 = {2, 1, 0, -1};//
                int[] yRotVal90 = {0, 1, 0, -1};//
                rotateByValues(xRotVal90, yRotVal90, board, true);
                break;
            case 180:                    
                int[] xRotVal180 = {0, -1, 0, 1};//
                int[] yRotVal180 = {2, 1, 0, -1};//
                rotateByValues(xRotVal180, yRotVal180, board, true);
                break;
            case 270:
                int[] xRotVal270 = {-2, -1, 0, 1};//
                int[] yRotVal270 = {0, -1, 0, 1};//
                rotateByValues(xRotVal270, yRotVal270, board, true);                
                break;
        }
    }

    @Override
    public void rotateCounterClockWise(boolean[][] board) {
        if (!boardIsGood(board)) 
            return;
        switch (super.getRotation()) {
            case 0:
                int[] xRotVal0 = {2, 1, 0, -1};
                int[] yRotVal0 = {0, 1, 0, -1};
                rotateByValues(xRotVal0, yRotVal0, board, false);
                break;
            case 90:
                int[] xRotVal90 = {0, -1, 0, 1};//
                int[] yRotVal90 = {2, 1, 0, -1};//
                rotateByValues(xRotVal90, yRotVal90, board, false);
                break;
            case 180:
                int[] xRotVal180 = {-2, -1, 0, 1};//
                int[] yRotVal180 = {0, -1, 0, 1};//
                rotateByValues(xRotVal180, yRotVal180, board, false);
                break;
            case 270:
                int[] xRotVal270 = {0, 1, 0, -1}; //
                int[] yRotVal270 = {-2, -1, 0, 1};//
                rotateByValues(xRotVal270, yRotVal270, board, false);
                break;
        }
    }

    @Override
    public blockType getBlockType() {
        return blockType.L;
    }

    public static BackgroundImage getBackground() {
        return new BackgroundImage(new Image("/OrangeBlock.png"), BackgroundRepeat.ROUND, 
                BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    }
}
