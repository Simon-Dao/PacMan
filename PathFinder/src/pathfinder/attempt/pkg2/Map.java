 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinder.attempt.pkg2;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Gamer
 */
public class Map 
{
    /**
     * width of the map in columns
     */
    private int width;
    /**
     * height of the map in rows
     */
    private int height;
    
    // stores the coordinates of each node in the web
    private Node[][] nodes;
    
    private int scale;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;
    
    //temp 
    LinkedList<Node> path;
    
    /**
     * 
     * @param map 
     */
    public Map(int[][] map)
    {
        
        this.width = map[0].length;
        this.height = map.length;
        
        nodes = new Node[width][height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                nodes[x][y] = new Node( x, y, map[x][y] == 0);
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * 
     * @return 
     */
    public int getHeight()
    {
        return height;
    }
    
    public int getScale()
    {
        return scale;
    }
    
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    /**
     * 
     * @param gc
     * @param path 
     */
    public void drawMap(GraphicsContext gc, List<Node> path)
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (!nodes[x][y].isWalkable())
                {
                    gc.setFill(Color.RED);
               
                }
                else if (nodes[x][y].getX() == startX && nodes[x][y].getY() == startY )
                {
                    gc.setFill(Color.BLUE);
                }
                else if (nodes[x][y].getX() == goalX && nodes[x][y].getY() == goalY )
                {
                    gc.setFill(Color.PURPLE);
                }
                else if (path != null && path.contains(new Node(x,y,true)))
                {
                    gc.setFill(Color.LIMEGREEN);
                }
                else 
                {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect((x * scale)+1, (y * scale)+1, scale-1, scale-1);
            }
        }
    }
    
    /**
     * 
     * @param map 
     * print a view of all the nodes on the command line
     */
    public void printMap(int[][] map)
    {
        System.out.print("   ");
        // x coordinate system
        for (int x = 0; x < width; x++)
        {
             if (x > 9)
            {
            System.out.print(" "+x);
            } 
            else 
            {
            System.out.print(" 0"+x);
            }
        }
        
        for (int i = 0; i< height; i++)
        {   
            //create a new line for each row
            System.out.println();
            
            // coordinate system
            if (i > 9)
            {
            System.out.print(i);
            } 
            else 
            {
            System.out.print(i+" ");
            }
           
            for (int j = 0; j< width; j++)
            {
                if (nodes[j][i].isWalkable())
                {
                  
                    System.out.print("  w");
                } 
                else
                {   
                  
                    System.out.print("  .");
                }
                
            }    
        }    
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return node 
     * returns a node matching the coordinates
     */
    public Node getNode(int x,int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < width)
        {
            return nodes[x][y];
        } 
        else
        {
            return null;
        }
    }
    
    /**
     * 
     * 
     * @param startX
     * @param startY
     * @param goalX
     * @param goalY
     * @return 
     */
    public final List<Node> findPath(int startX, int startY, int goalX, int goalY)
    {
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
	// If our start position is the same as our goal position ...
	if (startX == goalX && startY == goalY)
	{
	// Return an empty path, because we don't need to move at all.
            return new LinkedList<Node>();
	}

        // The set of nodes already visited.
	List<Node> openList = new LinkedList<Node>();
	// The set of currently discovered nodes still to be visited.
	List<Node> closedList = new LinkedList<Node>();

	// Add starting node to open list.
	openList.add(nodes[startX][startY]);

	// This loop will be broken as soon as the current node position is
	// equal to the goal position.
	while (true)
	{
            // Gets node with the lowest F score from open list.
	    Node current = lowestFInList(openList);
	    // Remove current node from open list.
            openList.remove(current);
            // Add current node to closed list.
            closedList.add(current);

            // If the current node position is equal to the goal position ...
            if ((current.getX() == goalX) && (current.getY() == goalY))
            {
      
		// Return a LinkedList containing all of the visited nodes.
		return calcPath(nodes[startX][startY], current);
            }

            List<Node> adjacentNodes = getAdjacent(current, closedList);
            
            for (Node adjacent : adjacentNodes)
            {
                // If node is not in the open list ...
                if (!openList.contains(adjacent))
                {
                    // Set current node as parent for this node.
                    adjacent.setParent(current);
                    // Set H costs of this node (estimated costs to goal).
                    adjacent.setH(nodes[goalX][goalY]);
                    // Set G costs of this node (costs from start to this node).
                    adjacent.setG(current);
                    // Add node to openList.
                    openList.add(adjacent);
                }
                    // Else if the node is in the open list and the G score from
                    // current node is cheaper than previous costs ...
            else if (adjacent.getG() > adjacent.calculateG(current))
            {
                // Set current node as parent for this node.
                adjacent.setParent(current);
                // Set G costs of this node (costs from start to this node).
                adjacent.setG(current);
            }
        }

            // If no path exists ...
            if (openList.isEmpty())
            {
                // Return an empty list.
                return new LinkedList<Node>();
            }
            // But if it does, continue the loop.
        }
    }

	/**
	 * @param start
	 *            The first node on the path.
	 * @param goal
	 *            The last node on the path.
	 * @return a list containing all of the visited nodes, from the goal to the
	 *         start.
	 */
    private List<Node> calcPath(Node start, Node goal)
    {
        LinkedList<Node> path = new LinkedList<Node>();

        Node node = goal;
        boolean done = false;
        while (!done)
        {
            path.addFirst(node);
            node = node.getParent();
            if (node.equals(start))
            {
                done = true;
            }
        }
            return path;
    }

	/**
	 * @param list
	 *            The list to be checked.
        * @return The node with the lowest F score in the list.
        */
    private Node lowestFInList(List<Node> list)
    {
        
        
	Node cheapest = list.get(0);
	for (int i = 0; i < list.size(); i++)
	{
	if (list.get(i).getF() < cheapest.getF())
	{
            cheapest = list.get(i);
	}
	}
        return cheapest;
    }

	/**
	 * @param node
	 *            The node to be checked for adjacent nodes.
	 * @param closedList
	 *            A list containing all of the nodes already visited.
	 * @return A LinkedList with nodes adjacent to the given node if those
	 *         exist, are walkable and are not already in the closed list.
	 */
    private List<Node> getAdjacent(Node node, List<Node> closedList)
    {
        
	List<Node> adjacentNodes = new LinkedList<Node>();
	int x = node.getX();
	int y = node.getY();

	Node adjacent;

	// Check left node
	if (x > 0)
	{
            node.diagonal = false;
                    
            adjacent = getNode(x - 1, y);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
            adjacentNodes.add(adjacent);
            }
        }

	// Check right node
	if (x < width)
	{
            
            node.diagonal = false;
                    
            adjacent = getNode(x + 1, y);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
                adjacentNodes.add(adjacent);
            }
	}

	// Check top node
	if (y > 0)
	{
            node.diagonal = false;
                    
            adjacent = this.getNode(x, y - 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
                adjacentNodes.add(adjacent);
            }
        }

	// Check bottom node
	if (y < height)
	{
            node.diagonal = false;
                    
            adjacent = this.getNode(x, y + 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
		adjacentNodes.add(adjacent);
            }
	}
        
        // top left node
        if (x > 1 && y > 1)
        {
            node.diagonal = true;
                    
            adjacent = this.getNode(x - 1, y - 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
                    
                adjacentNodes.add(adjacent);
            }
        }
        
        // top right node
        if (x < width -1 && y > height - 1)
        {
            node.diagonal = true;
                    
            adjacent = this.getNode(x + 1, y - 1);        
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {   
                            
                adjacentNodes.add(adjacent);
            }
        }
        
        // bottom left node
        if (x > 1 && y < height-1)
        {
            node.diagonal = true;
                    
            adjacent = this.getNode(x - 1, y + 1);        
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
                   
                adjacentNodes.add(adjacent);
            }
        }
        
        // bottom right node 
        if (x < width-1 && y < height-1)
        {
            node.diagonal = true;
                    
            adjacent = this.getNode(x + 1, y + 1);        
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent))
            {
                        
		adjacentNodes.add(adjacent);
            }
        }
            return adjacentNodes;
    }
}