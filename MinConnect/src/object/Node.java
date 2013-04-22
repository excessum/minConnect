package object;

import java.awt.Point;
import java.util.ArrayList;

/**
 *Node objects consist of a label as well as co-ordinates and are used as part of connection objects to both get input and 
 *output of graphs and/or trees. Whenever a node is created it should be added to the array list of nodes.
 *@author Curtis Stokes
 */
public class Node {
	/**
	 * This array list is a dynamic data structure that will hold all nodes that are in the solution and problem. 
	 */
	private static ArrayList<Node> nodeList = new ArrayList<Node>();
	/**
	 * This is the name the user has given to the node, which must be unique as methods are called to get nodes
	 * based on their label. 
	 */
	private final String NODE_LABEL;
	/**
	 * This is a point object, which contains an x and y co-ordinate and is used to draw nodes to the screen.
	 */
	private Point coordinates; 
	
	/**
	 * Default constructor, used to create a new node when taking in a label and co-ordinates.
	 * @param label The label for the node
	 * @param coords The point on the screen as a Point object(Contains x, y)
	 */
	public Node(String label, Point coords){
		this.NODE_LABEL = label;
		this.coordinates = coords;
	}
	
	/**
	 * Constructor used when opening a saved file, it will get a line of text from the nodes.txt and pass
	 * it as a string array with each token of the line in a separate place in the format
	 * Label	x	y
	 * @param details When opening  a old problem it is passed as a string[]
	 */
	public Node(String[] details){
		this.NODE_LABEL = details[0];
		this.coordinates = new Point(Integer.parseInt(details[1]), Integer.parseInt(details[2]));
	}
	
	/**
	 * Method used to get the label of the node.
	 */
	public String getLabel(){
		return this.NODE_LABEL;
	}
	
	/**
	 * Method used to get the co-ordinates of the node.
	 * @return The co-ordinates of the node as a point object
	 */
	public Point getPoint(){
		return this.coordinates;
	}
	

	/**
	 * Method used to set the co-ordinates of a node after moving nodes, or after creating with the default constructor.
	 * @param coords The co-ordinates to set the node as as a point object.
	 */
	public void setPoint(Point coords){
		this.coordinates = coords;
	}
	
	/**
	 * Method to get a node object based off its label
	 * @param labelIn The label of the node you wish to get
	 * @return The node who's label equals the parameter
	 */
	public static Node getNodeFromLabel(String labelIn){
		for(Node x : nodeList){
			if(x.getLabel().equalsIgnoreCase(labelIn))
			{
				return x;
			}
		}
		return null;
	}
	
	/**
	 * Method used to grab the list of all the nodes.
	 * @return The whole list of nodes in the program
	 */
	public static ArrayList<Node> getAllNodes(){
		return nodeList;
	}
	
	/**
	 * Used in saving, gets all the details that are saved in the correct order as a string[], in the format
	 * Node_Label	x 	y
	 * @return A string[] used for saving details in the correct format.
	 */
	public String[] getSaveDetails(){
		return new String[]{this.NODE_LABEL, ""+this.coordinates.x, ""+this.coordinates.y};
	}
	
	/**
	 * Normalises the label, returning it as a UPPERCASE string
	 * @return Capitalised node label
	 */
	public String normLabel() { 
		return getLabel().toUpperCase(); 
		}

	/**
	 * Make sure label is not a duplicate
	 * @param userInput The data the user has entered
	 * @return whether or not the data is valid
	 */
	public static boolean checkValidLabel(String userInput) {
		for(Node n : nodeList)
		{
			if(n.getLabel().equalsIgnoreCase(userInput)){
				return false;
			}
		}
		return true;
	}

}
