/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

/**
 *
 * @author Aaron
 */
public class TetrisController {
    
    TetrisView view;
    TetrisModel model;
    
    Tetrimino currentTet;
    GridPane gameView;
    Pane[][] paneGrid;
    boolean[][] gameStatus;
    Tetrimino.blockType[][] blockGrid;
    boolean stopGameLoop = false;
    Background emptyTile = new Background(new BackgroundImage(new Image("/BackgroundBlock.png", true), BackgroundRepeat.REPEAT, 
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
    
    public TetrisController(TetrisModel model, TetrisView view) {
        this.view = view;
        this.model = model;
        //currentTet = model.getActiveTetrimino();
        paneGrid = view.getPaneGrid();
        gameStatus = model.getBoardState();
        blockGrid = model.getBlockColors();
        reset();
        /*for (int i = 0; i < 4; i++) {
            addBlockListeners(i);  
        }*/
        
        Scene scene = view.getScene();
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    currentTet.shiftUp(1, gameStatus);
                    break;
                case DOWN:
                    if(!currentTet.shiftDown(1, gameStatus)) { 
                        removeLines();
                        //Generate a new Tetrimino
                        model.generateRandomTetrimino();
                        currentTet = model.getActiveTetrimino();
                        for (int i = 0; i < 4; i++) {
                            addBlockListeners(i);  
                        }
                        redrawBoard(gameStatus, currentTet.getBlockLocations());
                    }
                    break;
                case LEFT:
                    currentTet.shiftLeft(1, gameStatus);
                    break;
                case RIGHT:
                    currentTet.shiftRight(1, gameStatus);
                    break;
                case Z:
                    currentTet.rotateCounterClockWise(gameStatus);
                    break;
                case X:
                    currentTet.rotateClockWise(gameStatus);
                    break;
            }
        });
        Button newGame = view.getNewGameButton();
        newGame.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                reset();
            }
        });
        
        beginGameLoop();
    }
    
    private void reset() {
        for (int i = 0; i < Tetrimino.GAME_WIDTH; i++) {
            for (int j = 0; j < Tetrimino.GAME_HEIGHT; j++) {
                gameStatus[i][j] = false;
                blockGrid[i][j] = null;
                paneGrid[i][j].setBackground(emptyTile);
            }
        }
        model.generateRandomTetrimino();
        currentTet = model.getActiveTetrimino();
        redrawBlocks(currentTet.getBlockLocations());
        for (int i = 0; i < 4; i++)
            addBlockListeners(i);
        //TODO: Reset the score counter
        //TODO: once implementation starts generate new random next (possibly implement this as part of model instead)
    }
    
    private void addBlockListeners(int i) {
        SimpleIntegerProperty[][] blocks = currentTet.getBlockLocations();
        
        blocks[0][i].addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object old_val, Object new_val) {
                listenerAction(blocks, i, old_val, new_val, true);
            }
        });
        blocks[1][i].addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object old_val, Object new_val) {
                listenerAction(blocks, i, old_val, new_val, false);
            }
        });
    }
    
    private void listenerAction(SimpleIntegerProperty[][] blocks, int blockNum, Object old_val, Object new_val, boolean isX) {

        int oldv = Integer.parseInt(old_val.toString());
        int yval = blocks[1][blockNum].get();
        int xval = blocks[0][blockNum].get();
        
        if (isX) {
            paneGrid[oldv][yval].setBackground(emptyTile);
        }
        else {
            paneGrid[xval][oldv].setBackground(emptyTile);           
        }
        redrawBoard(gameStatus, blocks);
    }
    
    private void redrawBlocks(SimpleIntegerProperty[][] blocks) {
        //then redraw the active tetrimino
        for (int i = 0; i < 4; i++) {
            Background b = emptyTile;
                switch (currentTet.getBlockType()) {
                    case bL:
                    b = TetriminoBL.getBackground();
                    break;
                case bZ:
                    b = TetriminoBZ.getBackground();
                    break;
                case LINE:
                    b = TetriminoLine.getBackground();
                    break;
                case L:
                    b = TetriminoL.getBackground();
                    break;
                case Z:
                    b = TetriminoZ.getBackground();
                    break;
                case SQUARE:
                    b = TetriminoSquare.getBackground();
                    break;
                case T:
                    b = TetriminoT.getBackground();
                    break;
            }
            paneGrid[blocks[0][i].get()][blocks[1][i].get()].setBackground(b);
        } 
    }
    
    private void redrawBoard(boolean[][] board, SimpleIntegerProperty[][] blocks) {
        //first redraw the board environment
        for (int i = 0; i < Tetrimino.GAME_WIDTH; i++) {
            for (int j = 0; j < Tetrimino.GAME_HEIGHT; j++) {
                if (blockGrid[i][j] != null) {
                    Background b = emptyTile;
                    switch (blockGrid[i][j]) {
                        case bL:
                            b = TetriminoBL.getBackground();
                            break;
                        case bZ:
                            b = TetriminoBZ.getBackground();
                            break;
                        case LINE:
                            b = TetriminoLine.getBackground();
                            break;
                        case L:
                            b = TetriminoL.getBackground();
                            break;
                        case Z:
                            b = TetriminoZ.getBackground();
                            break;
                        case SQUARE:
                            b = TetriminoSquare.getBackground();
                            break;
                        case T:
                            b = TetriminoT.getBackground();
                            break;
                    }
                    if (!gameStatus[i][j]) {
                        b = emptyTile;
                    }
                    paneGrid[i][j].setBackground(b);
                }
                else {
                    //TODO: add a custom background image
                    paneGrid[i][j].setBackground(emptyTile);
                }
            }
        }
        redrawBlocks(blocks);
    }
    
    private void beginGameLoop() {  
        int interval = 1;
        
        Timer timer = new Timer();
   
        timer.schedule(new TimerTask() {
            int frameCounter = 0;
            int loopUpdatePeriod = 1000;
            @Override
            public void run() {
                if (frameCounter == loopUpdatePeriod) {
                    if (!currentTet.shiftDown(1, gameStatus)) {
                        removeLines();

                        //Generate a new Tetrimino
                        model.generateRandomTetrimino();
                        currentTet = model.getActiveTetrimino();
                        for (int i = 0; i < 4; i++) {
                            addBlockListeners(i);  
                        }
                        redrawBoard(gameStatus, currentTet.getBlockLocations());
                    }
                    frameCounter = 0;
                }
                else {
                    frameCounter++;
                }
                redrawBlocks(currentTet.getBlockLocations());
                //System.out.println("In Game loop");
                if (stopGameLoop) {
                    timer.cancel();
                }
            }
        }, 0, interval);
    }
    
    public void stopGameLoop() {
        stopGameLoop = true;
    }
    
    private void removeLines() {
        //Update the board
        int lowest = -1;
        for (int i = 0; i < 4; i++) {
            if (currentTet.getBlockLocations()[1][i].get() > lowest) {
                lowest = currentTet.getBlockLocations()[1][i].get();
            }
            gameStatus[currentTet.getBlockLocations()[0][i].get()][currentTet.getBlockLocations()[1][i].get()] = true;
            blockGrid[currentTet.getBlockLocations()[0][i].get()][currentTet.getBlockLocations()[1][i].get()] = currentTet.getBlockType();
        }

        //Remove full rows and adjust game board
        ArrayList<Integer> rowRem = new ArrayList<>();
        for (; lowest > lowest - 4 && lowest >= 0; lowest--) {
            boolean rowStatus = true;
            for (int xval = 0; xval < Tetrimino.GAME_WIDTH; xval++) {
                rowStatus &= gameStatus[xval][lowest];
            }
            if (rowStatus) {
                rowRem.add(lowest);
            }
        }
        while (!rowRem.isEmpty()) {
            int currentRow = rowRem.remove(rowRem.size() - 1);
            for (int xval = 0; xval < Tetrimino.GAME_WIDTH; xval++) {
                for (int i = currentRow; i >= 0; i--) {
                    if (i == 0) {
                        gameStatus[xval][i] = false;
                        blockGrid[xval][i] = null;
                        paneGrid[xval][i].setBackground(emptyTile);
                    } else {
                        gameStatus[xval][i] = gameStatus[xval][i - 1];
                        blockGrid[xval][i] = blockGrid[xval][i - 1];
                        paneGrid[xval][i].setBackground(paneGrid[xval][i - 1].getBackground());
                    }
                }
            }
        }    
    }
}
