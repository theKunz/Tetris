/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.*;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author Aaron
 */
public class TetrisModel {

    private final boolean[][] board = new boolean[Tetrimino.GAME_WIDTH][Tetrimino.GAME_HEIGHT];
    private final Tetrimino.blockType[][] blockGrid = new Tetrimino.blockType[Tetrimino.GAME_WIDTH][Tetrimino.GAME_HEIGHT];
    private Tetrimino currentTet;
    private final BackgroundImage emptyTile = new BackgroundImage(new Image("/BackgroundBlock.png", false), BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

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
    
    public Tetrimino.blockType[][] getBlockColors() {
        return blockGrid;
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
    
    public void setBlockColors(int x, int y, Tetrimino.blockType blockType) {
        try {
            blockGrid[x][y] = blockType;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("setBlockColors failed");
            e.printStackTrace();
        }
    }

    public final void generateRandomTetrimino() {

        Random rand = new Random();
        int next = rand.nextInt(7);
        //TODO: Implement a "smart random" so no more than 2 in a row occur
        switch (next) {
            case 0:
                currentTet = new TetriminoSquare();
                break;
            case 1:
                currentTet = new TetriminoLine();
                break;
            case 2:
                currentTet = new TetriminoT();
                break;
            case 3:
                currentTet = new TetriminoL();
                break;
            case 4:
                currentTet = new TetriminoBL();
                break;
            case 5:
                currentTet = new TetriminoBZ();
                break;
            case 6:
                currentTet = new TetriminoZ();
                break;
        }

    }

    public Tetrimino getActiveTetrimino() {
        return currentTet;
    }
    
    public BackgroundImage getEmptyTile() {
        return emptyTile;
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
