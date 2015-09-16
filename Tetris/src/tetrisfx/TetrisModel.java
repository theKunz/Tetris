/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.Tetrimino;
import Tetriminos.TetriminoSquare;

/**
 *
 * @author Aaron
 */
public class TetrisModel {

    private final boolean[][] board = new boolean[Tetrimino.GAME_WIDTH][Tetrimino.GAME_HEIGHT];
    private Tetrimino currentTet;
    
    public TetrisModel() {
        for (boolean[] board1 : board) {
            for (int j = 0; j < board[0].length; j++) {
                board1[j] = false;
            }
        }
        generateRandomTetrimino();
    }
    
    public boolean[][] getBoardState() {
        return board;
    }
    
    public void setBoardState(int x, int y, boolean val) {
        try {
            board[x][y] = val;
        } catch (IndexOutOfBoundsException e) {
            //This is dirty, exception handling generally shouldn't be made of println's
            System.out.println("setBoardState failed");
            e.printStackTrace();
        }
    }
    
    public final void generateRandomTetrimino() {
        //TODO: implement random generation
        currentTet = new TetriminoSquare(3, 0);
    }
    
    public Tetrimino getActiveTetrimino() {
        return currentTet;
    }
}
