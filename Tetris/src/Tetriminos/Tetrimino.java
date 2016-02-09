/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tetriminos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
/**
 *
 * @author Aaron
 */
public abstract class Tetrimino {
    //why are these in the Tetrimino class? This abstract tetrimino class is not
    //designed to be an all-purpose generic Tetrimino class, but rather to work
    //in the context of a game of Tetris. By storing the values here, we can
    //apply some implementation in this abstract class that otherwise could not
    //be done.
    public static final int GAME_WIDTH = 10;
    public static final int GAME_HEIGHT = 20;
    
    private final SimpleIntegerProperty pivotX = new SimpleIntegerProperty();
    private final SimpleIntegerProperty pivotY = new SimpleIntegerProperty();
    private final SimpleIntegerProperty rotation = new SimpleIntegerProperty();
    
    protected final SimpleIntegerProperty[] blocksX = new SimpleIntegerProperty[4];
    protected final SimpleIntegerProperty[] blocksY = new SimpleIntegerProperty[4];
    
    
    
    public Tetrimino(int x, int y) {
        setPivotPoint(x, y);
        //instantiate and set the initial values of the blocks in child instances
        rotation.set(0);
    }
    
    public static enum blockType {
        LINE, L, bL, T, SQUARE, Z, bZ
    }     
    
    public abstract void rotateClockWise(boolean[][] board);
    
    public abstract void rotateCounterClockWise(boolean[][] board);
    
    public abstract blockType getBlockType();
    
    protected boolean boardIsGood(boolean[][] board) {
        return (board != null && board.length == GAME_WIDTH && board[0].length == GAME_HEIGHT);
    }
    
    public int getRotation() {
        return rotation.get();
    }
    
    public void setRotation(int rotate) {
        int a = (rotate == 0 || rotate == 90 || rotate == 180 || rotate == 270) ? rotate : rotation.get();
        rotation.set(a);
    }
    
    /**
     * The coordinate that the tetrimino is rotating around. Note that
     * none of 4 blocks are necessarily on the pivot point. 
     * @return index 0 = X, index 1 = Y
     */
    public SimpleIntegerProperty[] getPivotPoint() {
        SimpleIntegerProperty[] piv = {pivotX, pivotY};
        return piv;
    }
    
    /**
     * The coordinates of the actual blocks that the tetrimino current resides
     * in. Each dimension has a length of 4;
     * @return [i][j] where i is X coordinate and j is Y coordinate
     */
    public SimpleIntegerProperty[][] getBlockLocations() {
        SimpleIntegerProperty[][] blo = {blocksX, blocksY};
        return blo;
    }
    
    public final void setPivotPoint(int x, int y) {
        if (x < 0) 
            x = 0;
        else if (x >= GAME_WIDTH) 
            x = GAME_WIDTH - 1;
        
        if (y < 0) 
            y = 0;
        else if (y >= GAME_HEIGHT) 
            y = GAME_HEIGHT - 1;
        
        pivotX.set(x);
        pivotY.set(y);
    }
    
    public void movePivotPointUp(int y) {
        if (y < 0)
            return;
        int result = (pivotY.get() - y >= 0) ? pivotX.get() - y : 0;
        pivotX.set(result);
    }
    
    public void movePivotPointDown(int y) {
        if (y < 0)
            return;
        int result = (pivotY.get() + y < GAME_HEIGHT) ? pivotY.get() + y : GAME_HEIGHT - 1;
        pivotY.set(result);
    }
    
    public void movePivotPointRight(int x) {
        if (x < 0)
            return;
        int result = (pivotX.get() + x < GAME_WIDTH) ? pivotX.get() + x : GAME_WIDTH - 1;
        pivotX.set(result);
    }
    
    public void movePivotPointLeft(int x) {
        if (x < 0)
            return;
        int result = (pivotX.get() - x >= 0) ? pivotX.get() - x : 0;
        pivotX.set(result);
    }
    
    public boolean shiftUp(int delta, boolean[][] board) {
        if (!boardIsGood(board) || delta < 1)
            return false;
        boolean willMove = true;
        for (int i = 0; i < 4; i++) {
            if (blocksY[i].get() <= 0 || 
                    blocksY[i].get() - delta < 0 ||
                    board[blocksX[i].get()][blocksY[i].get() - delta]) {
                willMove = false;
                break;
            }
        }
        if (willMove) {
            for (int i = 0; i < 4; i++) {
                blocksY[i].set(blocksY[i].get() - delta);
            }
        }
        this.movePivotPointUp(delta);
        return willMove;
    }

    public boolean shiftDown(int delta, boolean[][] board) {
        if (!boardIsGood(board) || delta < 1)
            return false;
        boolean willMove = true;
        for (int i = 0; i < 4; i++) {
            if (blocksY[i].get() >= GAME_HEIGHT - 1 || 
                    blocksY[i].get() + delta >= GAME_HEIGHT ||
                    board[blocksX[i].get()][blocksY[i].get() + delta]) {
                willMove = false;
                break;
            }
        }
        if (willMove) {
            for (int i = 0; i < 4; i++) {
                blocksY[i].set(blocksY[i].get() + delta);
            }
        }
        this.movePivotPointDown(delta);
        return willMove; 
    }

    public boolean shiftRight(int delta, boolean[][] board) {
        if (!boardIsGood(board) || delta < 1)
            return false;
        boolean willMove = true;
        for (int i = 0; i < 4; i++) {
            if (blocksX[i].get() >= GAME_WIDTH - 1 || 
                    blocksX[i].get() + delta >= GAME_WIDTH ||
                    board[blocksX[i].get() + delta][blocksY[i].get()]) {
                willMove = false;
                break;
            }
        }
        if (willMove) {
            for (int i = 0; i < 4; i++) {
                blocksX[i].set(blocksX[i].get() + delta);
            }
        }
        this.movePivotPointRight(delta);
        return willMove; 
    }

    public boolean shiftLeft(int delta, boolean[][] board) {
        if (!boardIsGood(board) || delta < 1)
            return false;
        boolean willMove = true;
        for (int i = 0; i < 4; i++) {
            if (blocksX[i].get() <= 0 || 
                    blocksX[i].get() - delta < 0 ||
                    board[blocksX[i].get() - delta][blocksY[i].get()]) {
                willMove = false;
                break;
            }
        }
        if (willMove) {
            for (int i = 0; i < 4; i++) {
                blocksX[i].set(blocksX[i].get() - delta);
            }
        }
        this.movePivotPointLeft(delta);
        return willMove;
    }
    
    protected boolean newPositionIsValid(int x, int y, boolean[][] board) {
        return (x >= 0 && y >= 0 && x < GAME_WIDTH && y < GAME_HEIGHT && !board[x][y]);
    }
    
    protected void rotateByValues(int[] delX, int delY[], boolean[][] board, boolean isClockwise) {
        boolean validRotation = true;
        for (int i = 0; i < 4; i++) {
            validRotation &= newPositionIsValid(blocksX[i].get() + delX[i], blocksY[i].get() + delY[i], board);
        }
        if (validRotation) {
            for (int i = 0; i < 4; i++) {
                blocksX[i].set(blocksX[i].get() + delX[i]);
                blocksY[i].set(blocksY[i].get() + delY[i]);
            }
            if (isClockwise) {
                rotation.set((rotation.get() + 90) % 360);
            }
            else {
                rotation.set((rotation.get() + 270) % 360);
            }
        }           
    }
}

