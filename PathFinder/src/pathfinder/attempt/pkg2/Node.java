/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder.attempt.pkg2;

/**
 *
 * @author Simon Dao
 */
public class Node 
{
    
    public int MOVEMENT_COST = 10;
    
    private int x;
    private int y;
    
    private boolean walkable;
    public boolean diagonal = false;
    
    private Node parent;
    
    private int g;
    private int h;
    
    public Node(int x, int y, boolean walkable)
    {
        this.x = x;
        this.y = y;
        
        this.walkable = walkable;
    }
    
    /**
     * 
     * @param parent 
     */
    public void setG(Node parent)
    {
        if (diagonal == true)
        {
            MOVEMENT_COST = 14;
        }
        else
        {
            MOVEMENT_COST = 10;
        }
        g = parent.getG() + MOVEMENT_COST;
        
    }
    /**
     * 
     * 
     * @param parent
     * @return 
     */
    public int calculateG(Node parent
    ){
        return (parent.getG() + MOVEMENT_COST);
    }
    /**
     * 
     * @param goal 
     * 
     */
    public void setH(Node goal)
    {
        h = (Math.abs(getX() - goal.getX()) + Math.abs(getY() - goal.getY())) * MOVEMENT_COST;
    }
    /**
     * 
     *  getters and setters to provide encapsulation 
     * for all of our variables
     * 
     */ 
   
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public boolean isWalkable()
	{
		return walkable;
	}
    public void setWalkable(boolean walkable)
	{
		this.walkable = walkable;
	}
    	public Node getParent()
	{
		return parent;
	}
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	public int getF()
	{
		return g + h;
	}
	public int getG()
	{
		return g;
        }
	public int getH()
	{
		return h;
	}
        
        /**
         * @param o 
         * @return  
         */
    @Override
        public boolean equals(Object o)
        {
            if (o == null)
		return false;
	    if (!(o instanceof Node))
                return false;
	    if (o == this)
	        return true;
            
            Node n = (Node) o;
            if (n.getX() == x && n.getY() == y && n.isWalkable() == walkable ) 
                return true;
            
            return false;
        }
}


