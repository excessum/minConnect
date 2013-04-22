package object;

import java.util.ArrayList;

/**
 *A connection object consists of two nodes and an Connection and is used as the basis of this program to solve the algorithms,
 *it also contains a unique ID to identify if needed.
 * @author Curtis Stokes
 */
public class Connection 
{
	/**
	 * A dynamic array list of connections that will hold all connections currently in the problem and solution
	 */
	private static ArrayList<Connection> connectionList = new ArrayList<Connection>();
	/**
	 * This is a counter in order to keep providing unique ID's, it is increased every time a Connection is made and 
	 * will equal the highest id+1 when a problem is opened.
	 */
	private static int IDToGive = 1;
	
	/**
	 * This is a uniquely identifying feature of an Connection which is assigned on creation.
	 */
	private final int CONNECTION_ID;
	/**
	 * A node array of size two that contains both the nodes of the connection.
	 */
	private Node[] nodes = new Node[2];
	/**
	 * The edge object of the connection.
	 */
	private Edge edge = null;
	
	/**
	 * Empty constructor
	 */
	public Connection()
	{
		this.CONNECTION_ID = IDToGive;
		IDToGive++;
	}
	
	/**
	 * Default constructor used when creating a new connection.
	 * @param nodesIn
	 * @param edgeIn
	 */
	public Connection(Node[] nodesIn, Edge edgeIn)
	{
		this.CONNECTION_ID = IDToGive;
		this.nodes = nodesIn;
		this.edge = edgeIn;
		IDToGive++;
	}
	
	/**
	 * Constructor used when creating existing nodes after opening a problem.
	 * @param nodesIn
	 * @param edgeIn
	 * @param id
	 */
	public Connection(String[] details)
	{
		this.CONNECTION_ID = Integer.parseInt(details[0]);
		this.nodes[0] = Node.getNodeFromLabel(details[1]);
		this.nodes[1] = Node.getNodeFromLabel(details[2]);
		this.edge = Edge.getEdgeFromID(Integer.parseInt(details[3]));
	}
	
	/**
	 * Method to get the ID of the connection that called it.
	 * @return
	 */
	public int getID(){
		return this.CONNECTION_ID;
	}
	/**
	 * Method to get the edge of the connection
	 * @return
	 */
	public Edge getEdge()
	{
		return this.edge;
	}
	/**
	 * Method to get either node one or node two, works by return the node at index 0 if 1 is passed, and the node at 
	 * index 1 if 2 is passed.
	 * @param node test
	 * @return
	 */
	public Node getNode(int nodeToGet)
	{
		switch(nodeToGet)
		{
		case 1:
			return this.nodes[0];
		case 2:
			return this.nodes[1];
		}
		return null;
	}
	
	/**
	 * Used in saving, gets all the details that are saved in the correct order as a string[], in the format
	 * CONNECTION_ID	Node1Label		Node2Label		EdgeID	
	 * @return
	 */
	public String[] getSaveDetails()
	{
		return new String[]{""+this.CONNECTION_ID, this.nodes[0].getLabel(), this.nodes[1].getLabel(), ""+this.edge.getID()};
	}
	
	/**
	 * Method to get the list of all connections.
	 * @return
	 */
	public static ArrayList<Connection> getAllConnections(){
		return connectionList;
	}
	
	/**
	 * Method used to get a  list of connections that are joint to a list of nodes, this is used in Primm's to
	 * see all connections that are able to be considered.
	 * @param nodes
	 * @return
	 */
	public static ArrayList<Connection> getConnectionsContaining(ArrayList<Node> nodes)
	{
		ArrayList<Connection> toReturn = new ArrayList<Connection>();
		for(Node n : nodes) //For every node in the parameter
		{
			for(Connection c : connectionList) //Go through every connection
			{ 
				if((c.getNode(0) == n) || (c.getNode(1) == n)) //If the connection contains node 1 or 2
				{
					toReturn.add(c); //Add to return list
				}
			}
		}
		
		return toReturn;	
	}
	
	/**
	 * Returns the connection which matches the input id.
	 * @param id
	 * @return
	 */
	public static Connection getConnectionFromID(int id)
	{
		for(Connection c: connectionList)
		{
			if(c.getID() == id)
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Method to check whether a connection has node that matches to another connections node, used in primms
	 * @param other
	 * @return
	 */
	public boolean matches(Connection other) {
		  final String this1 = getNode(1).normLabel(), this2 = getNode(2).normLabel(),
		               other1 = other.getNode(1).normLabel(), 
		               other2 = other.getNode(2).normLabel();
		  /*return   this1.equals(other1)   || this2.equals(other1) || 
		           this1.equals(other2) || this2.equals(other2); 
	*/
		  if(this1.equals(other1) || this1.equals(other2)){
			  return true;
		  }else if(this2.equals(other1) || this2.equals(other2)){
			  return true;
		  }else{
			  return false;	  
		  }
	}
	
	/**
	 * Returns the connection that contains the node n
	 * @param n
	 * @return
	 */
	public static ArrayList<Connection> getConnectionsContaining(Node n){
		ArrayList<Connection> toReturn = new ArrayList<Connection>();
		for(Connection x : connectionList)
		{
			if(x.getNode(1).getLabel().equalsIgnoreCase(n.getLabel()))
			{
				toReturn.add(x);
			}else if (x.getNode(2).getLabel().equalsIgnoreCase(n.getLabel()))
			{
				toReturn.add(x);
			}
		}
		return toReturn;
	}
	
}
