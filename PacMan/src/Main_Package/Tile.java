/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main_Package;

/**
 *
 * @author Simon Dao
 */
public class Tile {
    
    private int x;
    private int y;
    private boolean walkable = true;
    private boolean hasFruit = true;
    private int color;
    Player player = new Player();
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean isWalkable()
    {
        return walkable;
    }
    
    public boolean setWalkable(boolean walkable)
    {
        this.walkable = walkable;
        return walkable;
    }
    
    public boolean hasFruit()
    {
        return hasFruit;
    }
    
    public boolean changeFruit(boolean hasFruit)
    {
        this.hasFruit = hasFruit;
        return hasFruit;
    }
    
    public int getColor()
    {
        return color;
    }
    
    public boolean checkCollision()
    {
               
            if (player.getX() == x && player.getY() == y)
            {    
                hasFruit = false;
            }
        
        return hasFruit;
    }
    
    public Tile(int x, int y, boolean walkable, int color)
    {
        this.x = x;
        this.y = y;
        this.walkable = walkable;
        this.color = color;
        
       // checkCollision();
    }
    public Tile(){};
}
