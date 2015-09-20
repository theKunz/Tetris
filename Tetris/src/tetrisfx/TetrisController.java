/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Aaron
 */
public class TetrisController {
    
    Tetrimino currentTet;
    GridPane gameView;
    Pane[][] paneGrid;
    boolean[][] gameStatus; //used later
    boolean stopGameLoop = false;
    
    public TetrisController(TetrisModel model, TetrisView view) {
        currentTet = model.getActiveTetrimino();
        paneGrid = view.getPaneGrid();
        gameStatus = model.getBoardState();
        
        for (int i = 0; i < 4; i++) {
            addBlockListeners(i);  
        }
        
        //testing----------
        redrawBlocks(currentTet.getBlockLocations());
        gameStatus[5][10] = true;
        paneGrid[5][10].setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
        //--/testing--------
        Scene scene = view.getScene();
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    System.out.println("UP");
                    currentTet.shiftUp(1, gameStatus);
                    break;
                case DOWN:
                    System.out.println("DOWN");
                    currentTet.shiftDown(1, gameStatus);
                    break;
                case LEFT:
                    System.out.println("LEFT");
                    currentTet.shiftLeft(1, gameStatus);
                    break;
                case RIGHT:
                    System.out.println("RIGHT");
                    currentTet.shiftRight(1, gameStatus);
                    break;
            }
        });
        beginGameLoop();
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
            paneGrid[oldv][yval].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            redrawBlocksMovement(blocks);
        }
        else {
            paneGrid[xval][oldv].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));           
            redrawBlocksMovement(blocks);
        }
    }
    
    //wrapper for when the tetrimino moves, so that over-drawing doesn't occur
    private static int blockUpdateCount = 0; 
    private void redrawBlocksMovement(SimpleIntegerProperty[][] blocks) {
        blockUpdateCount++;
        if (blockUpdateCount == 4) {
            redrawBlocks(blocks);
            blockUpdateCount = 0;
        }
    }
    
    private void redrawBlocks(SimpleIntegerProperty[][] blocks) {
        for (int i = 0; i < 4; i++) {
            paneGrid[blocks[0][i].get()][blocks[1][i].get()].setBackground(
                new Background(new BackgroundFill(Color.YELLOW, null, null)));
            //TODO: add switch for tetrimino type to color
        } 
    }
    
    private void beginGameLoop() {  
        int interval = 1000;  
        Timer timer = new Timer();
   
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTet.shiftDown(1, gameStatus);
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
}
