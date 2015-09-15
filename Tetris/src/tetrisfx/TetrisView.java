/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;



/**
 *
 * @author Aaron
 */
public class TetrisView {
    private static final double BOX_HEIGHT = 100 / 20; //20 boxes in height
    private static final double BOX_WIDTH = 100 / 10; //10 boxes in width
    
    GridPane mainField = new GridPane();
    GridPane scoresAndMenu = new GridPane();
    GridPane root = new GridPane();
    
    Label score = new Label("0");
    
    public TetrisView(Stage primaryStage) {
        primaryStage.setResizable(false);
        

        
        
        ColumnConstraints column0 = new ColumnConstraints();
        column0.setPercentWidth(60);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40); 
        root.getColumnConstraints().addAll(column0, column1);
        
        //set the rows
        RowConstraints row0 = new RowConstraints();
        row0.setPercentHeight(100);
        row0.setFillHeight(true);
        root.getRowConstraints().addAll(row0);
       
        root.setPadding(new Insets(10, 10, 10, 10)); //top, right, bottom, left padding respectively
        root.setHgap(10);
        root.setVgap(10);
        
        root.add(mainField, 0, 0);
        
        ColumnConstraints columnBox;         
        for (int i = 0; i < 100 / BOX_WIDTH; i++) {
            columnBox = new ColumnConstraints();
            columnBox.setPercentWidth(BOX_WIDTH);
            mainField.getColumnConstraints().add(columnBox);
        }
        
        RowConstraints rowBox;         
        for (int i = 0; i < 100 / BOX_HEIGHT; i++) {
            rowBox = new RowConstraints();
            rowBox.setPercentHeight(BOX_HEIGHT);
            mainField.getRowConstraints().add(rowBox);
        }
        mainField.setPadding(new Insets(10, 10, 10, 10));
        
        RowConstraints nextTetriminoLabel = new RowConstraints();
        nextTetriminoLabel.setPercentHeight(5);
        RowConstraints nextTetrimino = new RowConstraints();
        nextTetrimino.setPercentHeight(28);
        RowConstraints scoreRegion = new RowConstraints();
        scoreRegion.setPercentHeight(15);
        RowConstraints menu = new RowConstraints();
        menu.setPercentHeight(52);
        scoresAndMenu.getRowConstraints().addAll(nextTetriminoLabel, nextTetrimino, scoreRegion, menu);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100);
        scoresAndMenu.getColumnConstraints().add(col1);
        root.add(scoresAndMenu, 1, 0);
        
        root.setGridLinesVisible(true); //debugging
        mainField.setGridLinesVisible(true);
        scoresAndMenu.setGridLinesVisible(true);
        
        Button newGame = new Button();
        newGame.setText("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                //TODO: button functionality (possibly move to controller)
                //set score to 0
                //Clear board
            }
        });
        scoresAndMenu.add(newGame, 0, 3);
        GridPane.setHalignment(newGame, HPos.CENTER);
        
        scoresAndMenu.add(score, 0, 2);
        GridPane.setHalignment(score, HPos.CENTER);
        GridPane.setValignment(score, VPos.CENTER);
        
        Label next = new Label("Next Tetrimino");
        scoresAndMenu.add(next, 0, 0);
        GridPane.setHalignment(next, HPos.CENTER);
        GridPane.setValignment(next, VPos.CENTER);
        
        Scene scene = new Scene(root, 800, 675);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public GridPane getGameGrid() {
        return mainField;
    }
    
    public Label getScoreLabel() {
        return score;
    }
}