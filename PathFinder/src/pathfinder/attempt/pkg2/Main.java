/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder.attempt.pkg2;

import java.util.List;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Simon Dao
 * 
 * TODO 
 * : add randomly generated maps
 * :add start and end node
 */
public class Main extends Application
{   
    private int width = 601;
    private int height = 601;
 
    private boolean running = true;
    
    GraphicsContext gc;
    Game game = new Game();
    Stage stage;
    Scene scene;
    
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        this.stage = stage;
        //set up scene 
        StackPane gameScreen = new StackPane();
        Canvas c = new Canvas(width,height);
        gc = c.getGraphicsContext2D();
        gameScreen.getChildren().add(c);
        scene = new Scene(gameScreen,width,height);
        
        // START
        game = new Game();
        game.userInput(scene);
        tick();
        
        stage.setTitle("A star demo");
        stage.setScene(scene);
        stage.show();
        
    }
  
    public void tick() {
        new AnimationTimer()
        {
            long lastTick = 0;
    
           //TIMER     
            public void handle(long now) 
            {
                if(running == true ) 
                { 
                    stage.setScene(scene);
                    
                    if(lastTick == 0 ) 
                    {
                        lastTick = now;
                        game.update(gc);
                      
                        return;
                    }
              
                    if ( now - lastTick > 1000000000/60) 
                    {
                        lastTick = now;
                        game.update(gc);
                    }
                }
            }
        }.start(); 
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
