package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
public class ProblemGraphGraphicsComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The diameter of the node.
	 */
	private static int nodeDiameter = 20;
	/**
	 * The tolerance in selecting a node.
	 */
	private static int tolerance = nodeDiameter/2;
	/**
	 * The minimum gap between two nodes.
	 */
	private static int minGap = 30;
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
	 * Constructor called when made to grab node and connection lists
	 */
	public ProblemGraphGraphicsComponent(){
		this.nodes = Node.getAllNodes();
		this.connections = Connection.getAllConnections();
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
		 * Draw the nodes
		 */
		if(!this.nodes.isEmpty())
		{
			for(Node n : nodes)
			{
				 graphics.setColor(Color.BLACK);
				 graphics.fillOval((n.getPoint().x - ProblemGraphGraphicsComponent.nodeDiameter/2) ,(n.getPoint().y - ProblemGraphGraphicsComponent.nodeDiameter/2), ProblemGraphGraphicsComponent.nodeDiameter, ProblemGraphGraphicsComponent.nodeDiameter);
				 graphics.setColor(Color.RED);
				 graphics.fillOval((n.getPoint().x - ((ProblemGraphGraphicsComponent.nodeDiameter/2) -1)) ,(n.getPoint().y - ((ProblemGraphGraphicsComponent.nodeDiameter/2) -1)), ProblemGraphGraphicsComponent.nodeDiameter -2, ProblemGraphGraphicsComponent.nodeDiameter -2);
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
	 * Get minimum gap between nodes
	 * @return
	 */
	public static int getMinGap(){
		return minGap;
	}
	/**
	 * Get node tolerance ( the error in pixels the user can make)
	 * @return
	 */
	public static int getTolerance(){
		return tolerance;
	}
	

	 /** Fetches relative angle between two point
	 * where 3 O'Clock is 0 and 12 O'Clock is 270 degrees, currently unused untill affineTransformations is implemented
	 * @param screenPoint
	 * @return angle in degrees from 0-360.
	 */
	public double getAngle(Point one, Point two)
	{
		double dx = Math.abs(one.getX() - two.getX());
	    
	    double dy = -Math.abs(one.getX() - two.getX());

	    double inRads = Math.atan2(dy,dx);

	    // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
	    if (inRads < 0)
	  inRads = Math.abs(inRads);
	    else
	  inRads = 2*Math.PI - inRads;
	    
	    
	    
	    return Math.toDegrees(inRads);
	}







}
