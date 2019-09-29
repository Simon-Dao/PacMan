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
    Map m;
    
    private int x = 11;
    private int y = 14;
    private int px = pacman.getX();
    private int py = pacman.getY();
    
    private boolean vulnerable = false;
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void moveLeft()
    {
        
    }
    public void moveRight()
    {
        
    }
    public void moveUp()
    {
        
    }
    public void moveDown()
    {
        
    }
    public void checkCollision()
    {
        //checks surrounding nodes if pac man is there
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
    public void draw(GraphicsContext gc)
    {
       gc.setFill(Color.RED);
       gc.fillRect(x*m.getScale()+3, y*m.getScale()+3, 13, m.getScale()-2);
    }
}
