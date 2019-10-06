/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder.attempt.pkg2;

import java.util.List;
import java.util.Random;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Simon Dao
 */

public class Game 
{
    
    int[] input = new int[4];
    
    Map map;
    /**
     * 
     * 0 = walkable
     * 
     */
    private final int MAP_1_SCALE = 50;
    
    int[][] map1 = {
        
        {0,1,0,1,0,0,0,1,1,1,0,1},
        {0,0,0,0,0,0,0,0,1,1,0,0},
        {0,0,0,1,1,0,0,1,0,0,1,0},
        {1,0,0,0,1,0,1,0,1,0,0,1},
        {0,0,1,0,0,0,1,0,0,1,1,1},
        {0,0,0,1,1,0,1,0,0,1,0,0},
        {0,0,0,1,0,0,0,0,0,0,0,0},
        {0,1,1,0,0,0,0,0,0,0,1,0},
        {0,0,0,1,0,1,1,0,0,0,0,1},
        {0,1,1,1,0,1,0,0,1,1,1,0},
        {0,0,0,0,0,0,0,0,0,1,0,0},
        {0,0,0,1,1,1,0,0,0,0,0,0},
    };
    
    private final int MAP_2_SCALE = 25;
    int[][] map2 = new int[24][24];
    private final int MAP_3_SCALE = 6;
    int[][] map3 = new int[99][99];
    List<Node> path;
    
    public int startX;
    public int startY;
    public int goalX;
    public int goalY;
    
    public int c = 0;
    
    public Game()
    {   
        generateMap(map3);
        rotateMap(map3);
        reflectMap(map3);
        map = new Map(map3);
        map.setScale(this.MAP_3_SCALE);
    }
    
    // move the player 
    public void update(GraphicsContext gc)
    {      
        render(gc);
        
    }
    
    public void render(GraphicsContext gc)
    {
        //background
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,601,601);
        
        gc.setFill(Color.GREEN);
        gc.fillRect(600, 0, 3, 601);
        ///////////////////////////////////////
        // param (int startx ,int starty ,int goalx ,int goaly) 
        path = map.findPath(startX, startY, goalX, goalY);
        
        ///////////////////////////////////////
        map.drawMap(gc, path); 
    }
    
    public void print()
    {   
        map.printMap(map3); 
    }
    
     public void userInput(Scene scene)
    {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouse ->
        {
            int mouseX = (int)mouse.getX();
            int mouseY = (int)mouse.getY();
            count();
            
            if(c % 2 == 0)
            {
                goalX = findCoordinate(mouseX);
                goalY = findCoordinate(mouseY);
            }
            else
            {
                startX = findCoordinate(mouseX);
                startY = findCoordinate(mouseY);
            }
        });
    }
    public int findCoordinate(int coord)
    {
        boolean done = false;
        int count = 0; 
        int tile_size = 6;
        
            while(done == false)
            {
                // find how many tiles can fit in the coordinate
                coord = coord - tile_size;
                count++;
                if(coord < 0)
                {   
                    done = true;
                    count--;
                    coord = count;
                }
            }
        return coord;
    }
     
    public int[][] generateMap(int[][] rmap)
    {   
        for (int i = 0; i < rmap.length; i++)
        {
            for (int j = 0; j < rmap.length; j++)
            {
                int walkable;
                Random r = new Random();
                double rand = r.nextDouble();
                
                if (rand < .2)
                {
                    walkable = 1;
                } 
                else 
                {
                    walkable = 0;
                }
                rmap[i][j] = walkable;
            }
        }
        return rmap;
    }
    
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
    
    private void count()
    {
        c++;
        if(c > 2)
            c = 0;
    }
}
