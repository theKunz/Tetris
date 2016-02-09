/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.*;
import java.util.Random;

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
        //TODO: implement random generation once other Tetrimino child classes are fleshed out
        //printBoardState();
        Random rand = new Random();
        int next = rand.nextInt(7);
        switch (next) {
            case 0:
                currentTet = new TetriminoSquare(4, 0);
                break;
            case 1:
                currentTet = new TetriminoLine(4, 0);
                break;
            case 2:
                currentTet = new TetriminoSquare(4, 0);
                break;
            case 3:
                currentTet = new TetriminoLine(4, 0);
                break;
            case 4:
                currentTet = new TetriminoSquare(4, 0);
                break;
            case 5:
                currentTet = new TetriminoLine(4, 0);
                break;
            case 6:
                currentTet = new TetriminoSquare(4, 0);
                break;
        }

    }

    public Tetrimino getActiveTetrimino() {
        return currentTet;
    }
    
    private void printBoardState() {
        System.out.println("------------------");
        for (int i = 0; i < 20; i++) {
            for (int k = 0; k < 10; k++) {
                int out = (board[k][i]) ? 1 : 0;
                System.out.print(out + " ");
            }
            System.out.print("\n");
        }
        System.out.println("------------------");
    }
}
