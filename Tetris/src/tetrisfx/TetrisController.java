/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

/**
 *
 * @author Aaron
 */
public class TetrisController {
    
    Tetrimino currentTet;
    GridPane gameView;
    Pane[][] paneGrid;
    boolean[][] gameStatus; //used later
    
    public TetrisController(TetrisModel model, TetrisView view) {
        currentTet = model.getActiveTetrimino();
        paneGrid = view.getPaneGrid();
        gameStatus = model.getBoardState();
        
        for (int i = 0; i < 4; i++) {
            addBlockListeners(i);  
        }

        //testing----------
        for (int i = 4; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                paneGrid[i][j].setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
            }
        }
        gameStatus[5][10] = true;
        paneGrid[5][10].setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, null, null)));
        //--/testing--------
        Scene scene = view.getScene();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
            }
        });
        
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
        int newv = Integer.parseInt(new_val.toString());
        int yval = blocks[1][blockNum].get();
        int xval = blocks[0][blockNum].get();
      
        if (isX) {
            paneGrid[oldv][yval].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            redrawBlock(blocks);
        }
        else {
            paneGrid[xval][oldv].setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));           
            redrawBlock(blocks);
        }
    }
    
    private static int blockUpdateCount = 0; //used by 
    private void redrawBlock(SimpleIntegerProperty[][] blocks) {
        blockUpdateCount++;
        if (blockUpdateCount == 4) {
            for (int i = 0; i < 4; i++) {
                paneGrid[blocks[0][i].get()][blocks[1][i].get()].setBackground(
                        new Background(new BackgroundFill(Color.YELLOW, null, null)));
            }
            blockUpdateCount = 0;
        }
    }
    
}
