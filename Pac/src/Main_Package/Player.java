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
 * @author Simon Dao
 */
public class Player {
 
    private int x = 1;
    private int y = 1;
    
    Map m;
    
    /**
     * 
     * @return 
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * 
     * @param x 
     */
    public int setX(int x)
    {
        this.x = x;
        return x;
    }
    
    /**
     * 
     * @return 
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * 
     * @param y 
     */
    public int setY(int y)
    {
        this.y = y;
        return y;
    }
    
    /**
     * 
     * @param gc 
     */
    public void spawn(GraphicsContext gc)
    {
        gc.setFill(Color.YELLOW);
        gc.fillOval(x * m.getScale(), y * m.getScale(), m.getScale(), m.getScale());
    }
    public void die()
    {
        x = 1;
        y = 1;
    }
}
