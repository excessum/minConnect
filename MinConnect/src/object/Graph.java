package object;

import java.util.ArrayList;

/**
 *A Graph is an array List of connects, as it is essentially a set of connections that are all joined.
 *@author excessum
 */
public class Graph 
{	
	/**
	 * A dynamic list of all the graphs in the problem.
	 */
	private static ArrayList<Graph> graphList = new ArrayList<Graph>();
	/**
	 * A dynamic list of all the connections in each graph, is not static. 
	 */
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	
	/**
	 * Empty constructor
	 */
	public Graph(){
		
	}
	/**
	 * Default constructor, used when creating a new tree
	 * @param connectionsIn
	 */
	public Graph(ArrayList<Connection> connectionsIn)
	{
		this.connections = connectionsIn;
	}
	
	/**
	 * Constructor used when creating previous graphs from a save, saved details are strings full of integers with
	 * spaces between them, the integers represent the connectionID. 
	 * @param connectionsIn
	 */
	public Graph(String[] connectionsIn)
	{
		for(int i = 0; i< connectionsIn.length ; i++)
		{
			this.connections.add(Connection.getConnectionFromID(Integer.parseInt(connectionsIn[i])));
		}
	}
	
	/**
	 * Method that gets the total weight of the graph that called it.
	 * @return
	 */
	public int getTotalWeight()
	{
		int weight = 0;
		for(Connection c : this.connections)
		{ 
			weight = weight+c.getEdge().getWeight(); 
		}
		return weight;
	}
	
	/**
	 * Returns the list of all the graphs.
	 * @return
	 */
	public static ArrayList<Graph> getGraphList(){
		return graphList;
	}
	
	/**
	 * Adds a connection to the graph
	 * @param c
	 */
	public void addConnectionToGraph(Connection c)
	{
			this.connections.add(c);
	}
	/**
	 * Returns the connectionList of the graph that called the method.
	 * @return
	 */
	public ArrayList<Connection> getConnectionList() {
		return this.connections;
	}
	
}
