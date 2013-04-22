package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JComponent;
import object.Connection;
import object.Node;

/**
 * The graphics component responsible for getting graph input
 * @author Curtis Stokes
 *
 */
public class SolutionGraphGraphicsComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The diameter of the node.
	 */
	private static int nodeDiameter = 20;
	/**
	 * The ArrayList of nodes to draw
	 */
	protected ArrayList<Node> nodes;
	/**
	 * The ArrayList of Connections to draw
	 */
	protected ArrayList<Connection> connections;
	/**
	 * Variable holding the dimension of the component
	 */
	Dimension componentSize;
	/**
	 * Variable holding the solution answer
	 */
	String minConnection = "";
	/**
	 * Constructor called when made to grab node and connection lists
	 */
	public SolutionGraphGraphicsComponent(){
		this.nodes = Node.getAllNodes();
		this.connections = new ArrayList<Connection>();
		componentSize = this.getSize();
	}
	
	/**
	 * Method that paints all the objects on screen, using the Graphics2D package
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
		/*
		 * Create the graphics object and enable anti aliasing
		 */
		Graphics2D graphics = (Graphics2D)g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.BLACK);
		
		/*
		 *If the minimum connection is not empty,  draw it 
		 */
		if (!minConnection.equalsIgnoreCase("")){
			graphics.drawString("Minimum connection : "+minConnection, 20, 20);
		}
		/*
		 * If the connections list is not empty draw the edges from them and there weights
		 */
		if(!this.connections.isEmpty())
		{
			for(Connection c : connections)
			{
				
				graphics.drawLine(c.getNode(1).getPoint().x, c.getNode(1).getPoint().y, c.getNode(2).getPoint().x, c.getNode(2).getPoint().y);
				int xDistance = (c.getNode(1).getPoint().x + c.getNode(2).getPoint().x)/2;
				int yDistance = (c.getNode(1).getPoint().y + c.getNode(2).getPoint().y)/2;
				graphics.drawString(""+c.getEdge().getWeight(), xDistance + 5, yDistance + 5);
				/*
				 * AffineTransform at = new AffineTransform();
				 
				double angle = getAngle(c.getNode(1).getPoint(), c.getNode(2).getPoint());
				at.setToRotation(Math.toRadians(angle));
				graphics.setTransform(at);
				graphics.drawString(""+c.getEdge().getWeight(), xDistance, yDistance);
				at.setToRotation(0);*/			
				
			}
		}
		/*
		 * Draw all the nodes
		 */
		if(!this.nodes.isEmpty())
		{
			for(Node n : nodes)
			{
				 graphics.setColor(Color.BLACK);
				 graphics.fillOval((n.getPoint().x - SolutionGraphGraphicsComponent.nodeDiameter/2) ,(n.getPoint().y - SolutionGraphGraphicsComponent.nodeDiameter/2), SolutionGraphGraphicsComponent.nodeDiameter, SolutionGraphGraphicsComponent.nodeDiameter);
				 graphics.setColor(Color.RED);
				 graphics.fillOval((n.getPoint().x - ((SolutionGraphGraphicsComponent.nodeDiameter/2) -1)) ,(n.getPoint().y - ((SolutionGraphGraphicsComponent.nodeDiameter/2) -1)), SolutionGraphGraphicsComponent.nodeDiameter -2, SolutionGraphGraphicsComponent.nodeDiameter -2);
				 graphics.setColor(Color.BLACK);
				 if(n.getLabel().length() == 1)
				 {
					 graphics.drawString(n.getLabel(), n.getPoint().x-4, n.getPoint().y +3);
				 }
				 else
				 {
					 graphics.drawString(n.getLabel(), (n.getPoint().x) -10, (n.getPoint().y) +10);
				 }

			}
		}
		
	}
	

	/**
	 * Set the connection list to draw
	 * @param connections
	 */
	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}
	
	/**
	 * Method to set the number to display for the solution
	 * @param min
	 */
	public void setMinConnection(int min){
		this.minConnection = ""+min;
	}
	
	




}
