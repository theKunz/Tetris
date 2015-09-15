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
public class TetriminoLine extends Tetrimino{

    public TetriminoLine(int x, int y) {
        super(x, y);
    }
            
    @Override
    public void rotateClockWise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rotateCounterClockWise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public blockType getBlockType() {
        return blockType.LINE;
    }
    
}
