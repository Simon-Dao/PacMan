package Main_Package;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * TODO add smoother movement
 * add sound
 * add enemies
 * add a pause screen
 * add a main menu
 * add a map maker *OPTIONAL*
 * add special fruit
 * 
 * 
 * @author Simon Dao
 */
public class Main extends Application 
{
    //variables
    private final int width = 700;
    private final int height = 700;
    private final int velocity = 1;
    
    private final String title = "Pac Man!!!";
    
    private boolean running = false;
    private GraphicsContext gc;
    private Scene scene;
    
    private Player pacMan = new Player();
    private Control control = new Control();
    private Audio soundPlayer = new Audio();
    
    // red green yellow color for the map
    private int r = 2;
    private int g = 3;
    private int y = 4;
    private int b = 5;
    
    private int count = 0;
            
    private enum Direction {up,down,left,right}
    private Direction oldDir;
    private Direction dir = Direction.right;

    private int[][] GOOGLE_MAP = 
    {               //12
                   {1,1,1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                   {1,0,0,0,0,0,0,0,0,0,0,0, 1,b,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                   {1,0,1,1,1,1,0,1,1,1,1,0, 1,b,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                   {1,0,1,b,b,1,0,1,b,b,1,0, 1,b,1,0,1,0,0,0,0,0,0,0,0,1,0,1},
                   {1,0,1,1,1,1,0,1,1,1,1,0, 1,1,1,0,1,0,1,1,0,1,1,1,0,1,0,1},
                   {1,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1},
                   {1,0,1,1,1,1,0,1,1,1,1,0, 1,1,1,0,0,0,0,1,0,1,0,1,0,0,0,1},
                   {1,0,0,0,0,1,0,0,0,0,0,0, 0,0,0,1,1,1,0,1,0,1,0,1,1,1,0,1},
                   {1,0,1,1,0,1,0,1,1,1,1,0, 1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                   {1,0,0,1,0,1,0,0,0,0,0,0, 1,1,0,1,1,1,1,1,0,1,1,1,1,1,0,1},
                   {1,1,0,1,0,1,0,1,1,1,1,0, b,b,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                   {0,0,0,0,0,0,0,0,0,b,b,b, y,b,1,1,1,1,0,g,g,g,0,r,r,r,r,0},
                   //5
                   {0,1,1,1,1,0,r,r,r,b,y,y, y,b,1,0,0,1,0,g,0,0,0,r,0,0,0,0},
                   {0,1,0,0,0,0,r,b,r,b,y,b, 6,b,1,0,1,1,0,g,0,1,0,r,r,r,0,0},
                   {0,1,0,1,1,0,r,b,r,b,y,b, y,b,1,0,0,0,0,g,0,1,0,r,0,0,0,0},
                   {0,1,0,0,1,0,r,b,r,b,y,b, y,b,1,1,1,1,0,g,0,1,0,r,r,r,r,0},
                   {0,1,1,1,1,0,r,r,r,b,y,y, b,b,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                   //11   
                   {0,0,0,0,0,0,0,0,0,b,b,b, 1,0,1,1,1,1,0,1,0,1,1,0,1,0,1,1},
                   {1,1,0,1,0,1,0,1,0,1,1,1, 1,0,0,0,0,0,0,1,0,1,1,0,1,0,0,1},
                   {1,0,0,0,0,1,0,1,0,1,1,1, 1,0,1,1,1,1,1,1,0,1,1,0,1,1,0,1},
                   {1,0,1,0,1,1,0,1,0,0,0,0, 1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                   {1,0,1,0,1,1,0,1,1,1,1,0, 1,0,1,1,1,1,1,1,0,1,1,1,1,1,0,1},
                   {1,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                   {1,0,1,1,1,1,0,1,1,1,1,0, 1,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                   {1,0,1,b,b,1,0,1,b,b,1,0, 1,b,1,0,1,b,b,b,1,0,1,b,b,1,0,1},
                   {1,0,1,1,1,1,0,1,1,1,1,0, 1,b,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                   {1,0,0,0,0,0,0,0,0,0,0,0, 1,b,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                   {1,1,1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
              //    1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5 1 2 3 4 5         
    };
    
    private Map m;
    /**
     * 
     * @param primaryStage 
     */
    @Override
    public void start(Stage primaryStage) 
    {  
        //setup scene, graphics and canvas
        Pane layout = new Pane();
        Canvas c = new Canvas(width,height);
        GraphicsContext gc = c.getGraphicsContext2D();
        layout.getChildren().add(c);
        scene = new Scene(layout, width, height);
        
////////////////////////////////////////////////////////////////////////////////
        //START OF GAME
////////////////////////////////////////////////////////////////////////////////
        // sets up escape button
        control.setSceneControls(scene, primaryStage);
        setPauseScreen(scene,gc);
        setControls(scene,primaryStage);
        rotateMap(GOOGLE_MAP);
        reflectMap(GOOGLE_MAP);
        
        //draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 700, 700);   
        
        //draw map
        m = new Map(GOOGLE_MAP);
        m.drawMap(gc);
        
        //draw pac man
        gc.setFill(Color.YELLOW);
        gc.fillOval(pacMan.getX() * m.getScale(), pacMan.getY() * m.getScale(), m.getScale(), m.getScale());
        
        soundPlayer.beginning();
        
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run() 
            {
                running = true;
            }
    };
        
    Timer timer = new Timer("Timer");
     
    long delay = 4000L;
    timer.schedule(task, delay);
        
    new AnimationTimer()
    {
        long lastTick = 0;        
    /**
     * 
     * @param now 
     */     
    public void handle(long now)
    {
        if(running == true )
        {            
            primaryStage.setScene(scene);
                    
            if(lastTick == 0 )
            {
            lastTick = now;
            tick(gc);
            return;
            }
            
            if ( now - lastTick > 1000000000/8)
            {
                lastTick = now;
                tick(gc);          
            }
        }
    }
    }.start(); 
        
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Draws background and pacMan
     * sets movement
     * checks which fruits have been in the path of the player and removes it
     * 
     * @param gc 
     */
    public void tick(GraphicsContext gc)
    {
        setMovement();
        setCollision();
        
        m.drawBackground(gc); 
        m.drawMap(gc);
        
        cherrySound();
        
        gc.setFill(Color.YELLOW);
        gc.fillOval(pacMan.getX() * m.getScale(), pacMan.getY() * m.getScale(), m.getScale(), m.getScale());
    
        // turns any node in pacMan's path to set hasFruit as false
        m.getNode(pacMan.getX(), pacMan.getY()).changeFruit(false);
        m.SubtractCherries();
    }
    
    /**
     *  *DISCLAIMER*
     * 
     * the reason why this method is here is because 
     * the integer[] map shows up rotated 90 degrees clockwise and inverted
     * on screen so this method counteracts that transformation
     * 
     * //////////////////////////////////////////////////
     * @param mapy
     * @return 
     */
    public int[][] rotateMap(int[][] mapy)
    {
       int N = mapy.length;
        
        //turn counter clockwise 90 degrees
        for (int i =0; i < N/2 ;i++)
        {
            for (int j = i; j < N - i - 1; j++ )
            {
                int temp = mapy[i][j];
                mapy[i][j] = mapy[N - 1 - j][i]; 
                mapy[N - 1 - j][i] = mapy[N - 1 - i][N - 1 - j]; 
                mapy[N - 1 - i][N - 1 - j] = mapy[j][N - 1 - i]; 
                mapy[j][N - 1 - i] = temp; 
            
            }
        }
        return mapy;
    }     
    
    /**
     * 
     * @param mapy
     * @return 
     */
    public int[][] reflectMap(int[][] mapy)
    {
       
        for (int j = 0;j< 12 ; j++)
        {
            for(int i = 0; i < (mapy.length / 2); i++) 
            {
                int temp = mapy[j][i];
                mapy[j][i] = mapy[j][mapy.length - i - 1];
                mapy[j][mapy.length - i - 1] = temp;
            }
        }
        return mapy;
    }
    
    //controls 
    /**
     * sets up the controls for the game
     * 
     * @param scene
     * @param primaryStage 
     */
     public void setControls(Scene scene, Stage primaryStage)
    {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keys ->
        {
            //up
            if(keys.getCode() == KeyCode.W)
            {
                // if node to above is not walkable
                if(!m.getNode(pacMan.getX(), pacMan.getY()-1).isWalkable())
                {
                    dir = oldDir;
                }
                else
                {
                    dir = Direction.up;
                }
            }
            
            //left
            else if(keys.getCode() == KeyCode.A)
            {
                // if node to the left is not walkable
                if(!m.getNode(pacMan.getX()-1, pacMan.getY()).isWalkable())
                {
                    dir = oldDir;
                }
                else
                {
                    dir = Direction.left;
                }
            }
            
            //down
            else if(keys.getCode() == KeyCode.S)
            {
                // if node below is not walkable
                if(!m.getNode(pacMan.getX(), pacMan.getY()+1).isWalkable())
                {
                    dir = oldDir;
                }
                else
                {
                    dir = Direction.down;
                }
            }
            
            //right
            else if(keys.getCode() == KeyCode.D)
            {
                // if node the right is not walkable
                if(!m.getNode(pacMan.getX()+1, pacMan.getY()).isWalkable())
                {
                    dir = oldDir;
                }
                else
                {
                    dir = Direction.right;
                }
            }
            
        }); 
    }    
    
    // sets up what action pacman will take when a certain button is pressed
    // sets up collision 
    public void setMovement()
    {
        //movement
        
        oldDir = Direction.right;
        
        switch(dir)
        {
            case right:
                oldDir = Direction.right;
                pacMan.setX(pacMan.getX() + velocity);
                break;
            case left:
                oldDir = Direction.left;
                pacMan.setX(pacMan.getX() - velocity);
                break;
            case up:
                oldDir = Direction.up;
                pacMan.setY(pacMan.getY() - velocity);
                break;
            case down:
                oldDir = Direction.down;
                pacMan.setY(pacMan.getY() + velocity);
                break;
        }
        
        //checking collision
        // if pacman is going right
        if(dir == Direction.right)
        {
            // if the node to the right of pacman is not walkable 
            if(!m.getNode(pacMan.getX(), pacMan.getY()).isWalkable())
            {
                pacMan.setX(pacMan.getX()-1);
            }
        }
        // if pacman is going left
        if(dir == Direction.left)
        {   
            // if the node to the left of pacman is not walkable 
            if(!m.getNode(pacMan.getX(), pacMan.getY()).isWalkable())
            {
                pacMan.setX(pacMan.getX()+1);
            }
        }
        //if pacman is going up
        if(dir == Direction.up)
        {
            // if the node above pacman is not walkable 
            if(!m.getNode(pacMan.getX(), pacMan.getY()).isWalkable())
            {
                pacMan.setY(pacMan.getY()+1);
            }
        }
        // if pacman is going down
        if(dir == Direction.down)
        {
            // if the node below pacman is not walkable 
            if(!m.getNode(pacMan.getX(), pacMan.getY()).isWalkable())
            {
                pacMan.setY(pacMan.getY()-1);
            }
        }      
    }
    
    public void setCollision()
    {   
        // go through the left portal
        if(pacMan.getX() == -1 && dir == dir.left)
        {
            pacMan.setX(27);
            pacMan.setY(pacMan.getY());
            
        }
        
        // go through the right portal
        if(pacMan.getX() == 28 && dir == dir.right)
        {
            pacMan.setX(0);
            pacMan.setY(pacMan.getY());
            
        }
    }

    //sees if the current node has cherries and plays a cherry eating sound
    public void cherrySound()
    {
        if(m.getNode(pacMan.getX(), pacMan.getY()).hasFruit())
        {
            soundPlayer.play_eat();
        } 
    }
    
    public void print(String thing)
    {
        System.out.println(thing);
    }
    
    public void setPauseScreen(Scene scene, GraphicsContext gc)
    {
    scene.addEventFilter(KeyEvent.KEY_PRESSED, keys ->
        {
            if(keys.getCode() == KeyCode.P)
            {
                count++;
                running = false;
                
                if(count % 2 == 0)
                {
                    running = true;
                } 
                gc.setFill(Color.ORANGE);
                gc.fillRoundRect(160, 260,390, 60,30,30);
                
                gc.setFill(Color.RED);
                gc.setFont(new Font("Courier New",50));
                gc.fillText("GAME PAUSED", 190, 305);
                
                System.out.println(running);
                System.out.println(count);
            }  
        });
            }
     /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}

