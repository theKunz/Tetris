/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetriminos;

/**
 *
 * @author Aaron
 */
public class TetriminoT extends Tetrimino {
    
    public TetriminoT(int x, int y) {
        super(x, y);
    }

    @Override
    public void rotateClockWise(boolean[][] board) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rotateCounterClockWise(boolean[][] board) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public blockType getBlockType() {
        return blockType.T;
    }
    
}
