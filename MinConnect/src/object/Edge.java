package object;

import java.util.ArrayList;

/**
 *An edge object consists of the edge weight, as well as an edge ID. This is what joins two nodes together and is 
 *the value the algorithm tries to minimize.
 * @author Curtis Stokes
 */
public class Edge {
	/**
	 * A dynamic array list of edges that will hold all edges currently in the problem and solution.
	 */
	private static ArrayList<Edge> edgeList = new ArrayList<Edge>();
	/**
	 * This is a counter in order to keep providing unique ID's, it is increased every time a Edge is made and will equal
	 * the highest id+1 when a problem is opened.
	 */
	private static int IDToGive = 1;
	
	/**
	 * This is a uniquely identifying feature of an edge which is assigned on creation.
	 */
	private final int EDGE_ID;
	
	/**
	 * This is the weight of the edge signifies the magnitude (e.g distance) between two nodes and is what is minimized
	 * in the Primm's and Kruskals algorithm.
	 */
	private int edgeWeight;
	
	/**
	 * Default constructor, used when creating a new edge with a weight.
	 * @param weight
	 */
	public Edge(int weight){
		this.EDGE_ID = IDToGive;
		this.edgeWeight = weight;
		IDToGive++;
	}
	
	/**
	 * Constructor used when creating an existing object after opening a problem.
	 * @param weight
	 * @param id
	 */
	public Edge(String[] details){
		this.EDGE_ID = Integer.parseInt(details[0]);
		this.edgeWeight = Integer.parseInt(details[1]);
	}
	
	/**
	 * Method used to get the weight of the edge that called it.
	 * @return
	 */
	public int getWeight(){
		return this.edgeWeight;
	}
	
	/**
	 * Method used to get the ID of the edge that called it.
	 */
	public int getID(){
		return this.EDGE_ID;
	}
	
	/**
	 * Sets the weight of an edge, used for updating existing edges.
	 * @param weightIn
	 */
	public void setWeight(int weightIn){
		this.edgeWeight = weightIn;
	}
	
	/**
	 * Get the list of all the edges.
	 * @return
	 */
	public static ArrayList<Edge> getAllEdges(){
		return edgeList;
	}
	
	/**
	 * Used to set the correct value for the edge ID to give new edges.
	 * @param highestID
	 */
	public static void setIDToGive(int highestID){
		IDToGive = highestID+1;
	}
	
	/**
	 * Get the details of the edge in the save format used by the program
	 * @return
	 */
	public String[] getSaveDetails(){
		return new String[]{""+this.getID(), ""+this.getWeight()};
		
	}
	/**
	 * Gets an edge object based on its ID
	 * @param id
	 * @return
	 */
	public static Edge getEdgeFromID(int id){
		for(Edge x : edgeList)
		{
			if(x.getID() == id)
			{
				return x;
			}
		}
		return null;
	}
	
}
