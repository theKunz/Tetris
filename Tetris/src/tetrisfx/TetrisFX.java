/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetrisfx;

import java.io.File;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Aaron
 */
public class TetrisFX extends Application {
    
    TetrisView view;
    TetrisModel model;
    TetrisController controller;
    
    @Override
    public void start(Stage primaryStage) {
        view = new TetrisView(primaryStage);
        model = new TetrisModel();
        controller = new TetrisController(model, view);
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop(){
        //System.out.println("Game is closing");
        controller.stopGameLoop();
    }
}
