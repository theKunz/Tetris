/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import Tetriminos.Tetrimino;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;



/**
 *
 * @author Aaron
 */
public class TetrisView {
    private static final double BOX_HEIGHT = 100 / Tetrimino.GAME_HEIGHT; //20 boxes in height
    private static final double BOX_WIDTH = 100 / Tetrimino.GAME_WIDTH; //10 boxes in width
    
    GridPane mainField = new GridPane();
    GridPane scoresAndMenu = new GridPane();
    GridPane root = new GridPane();
    Pane[][] paneGrid = new Pane[10][20];
    Button newGame;
    
    Label score = new Label("0");
    
    Scene scene;
    
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
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        ColumnConstraints columnBox;         
        for (int i = 0; i < 100 / BOX_WIDTH; i++) {
                columnBox = new ColumnConstraints();
                columnBox.setPercentWidth(BOX_WIDTH);
                mainField.getColumnConstraints().add(columnBox);
                //mainField.add(new Pane(), i, j);
        }
        
        RowConstraints rowBox;         
        for (int i = 0; i < 100 / BOX_HEIGHT; i++) {
            rowBox = new RowConstraints();
            rowBox.setPercentHeight(BOX_HEIGHT);
            mainField.getRowConstraints().add(rowBox);
        }
        mainField.setPadding(new Insets(10, 10, 10, 10));
        mainField.setBackground(new Background(new BackgroundImage(new Image("/GameBorder.png", false), BackgroundRepeat.REPEAT,
                                    BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        for (int i = 0; i < 100 / BOX_WIDTH; i++) {
            for (int j = 0; j < 100/ BOX_HEIGHT; j++) {
                paneGrid[i][j] = new Pane();

                mainField.add(paneGrid[i][j], i, j);
            }
        }
        
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
        
        //root.setGridLinesVisible(true); //debugging
        //mainField.setGridLinesVisible(true);
        scoresAndMenu.setGridLinesVisible(true);
        
        newGame = new Button();
        newGame.setFocusTraversable(false);
        newGame.setText("New Game");

        scoresAndMenu.add(newGame, 0, 3);
        GridPane.setHalignment(newGame, HPos.CENTER);
        
        scoresAndMenu.add(score, 0, 2);
        GridPane.setHalignment(score, HPos.CENTER);
        GridPane.setValignment(score, VPos.CENTER);
        
        Label next = new Label("Next Tetrimino");
        scoresAndMenu.add(next, 0, 0);
        GridPane.setHalignment(next, HPos.CENTER);
        GridPane.setValignment(next, VPos.CENTER);
        
        scene = new Scene(root, 800, 675);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        String musicFile = "TetrisTheme10Mins.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        root.getChildren().add(mediaView);
    }
    
    public GridPane getGameGrid() {
        return mainField;
    }
    
    public Label getScoreLabel() {
        return score;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public Pane[][] getPaneGrid() {
        return paneGrid;
    }
    
    public Button getNewGameButton() {
        return newGame;
    }
}
