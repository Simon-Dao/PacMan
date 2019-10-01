/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main_Package;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Simon
 */
public class Enemy 
{
    Player pacman = new Player();
    Map m = new Map();
    public boolean vulnerable = false;
    
    private int x = 11;
    private int y = 14;
    private int px = pacman.getX();
    private int py = pacman.getY();
   
    private boolean alive = true;
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    /**
     * 
     * @param x
     * @return 
     */
    public int setX(int x)
    {
        this.x = x;
        return x;
    }
    
    /**
     * 
     * @param x
     * @return 
     */
    public int setY(int y)
    {
        this.y = y;
        return y;
    }

    
    public void moveLeft()
    {
        x--;
    }
    public void moveRight()
    {
        x++;
    }
    public void moveUp()
    {
        y++;
    }
    public void moveDown()
    {
        y--;
    }
    
    public void kill()
    {
        if(pacman.getX() == x && pacman.getY() == y)
        {
            pacman.die();
        }
    }
    public void die()
    {
        x = 11;
        y = 14;
    }
    
}
