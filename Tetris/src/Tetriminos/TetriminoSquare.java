/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetriminos;

import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Aaron
 */
public class TetriminoSquare extends Tetrimino {
    
    //we will assume that the open spot is open, as checking for open area
    //will be done before actually spawning the next tetrimino
    public TetriminoSquare(int x, int y) {
        super(x, y);
        //(4,0) (5,0) (4,1) (5,1) //in a 1-based coordinate system
        for (int i = 0; i < 4; i++) {
            blocksX[i] = new SimpleIntegerProperty((i % 2) + 3); //because 0 based, 3 is slot 4
            blocksY[i] = new SimpleIntegerProperty((int)(i / 2));
        }
    }

    @Override
    @SuppressWarnings("UnnecessaryReturnStatement")
    public void rotateClockWise() {
        return; //can't rotate the square block
    }

    @Override
    
    public void rotateCounterClockWise() {
        return; //can't rotate the square block
    }

    @Override
    public blockType getBlockType() {
        return blockType.SQUARE;
    }


    

    
}