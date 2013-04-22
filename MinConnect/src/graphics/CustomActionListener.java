package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import object.Connection;
import object.Edge;
import object.Graph;
import object.Node;
import processing.Kruskals;
import processing.Primms;
import processing.PrintAndSave;

/**
 * Handles all the events of the program
 * 
 * @author Curtis Stokes
 * 
 */
public class CustomActionListener implements ActionListener, MouseListener,
		MouseMotionListener, FocusListener, KeyListener {
	/**
	 * Booleans to determine action to take on mouse events
	 */
	private boolean nodeMode, edgeMode, deleteMode;
	/**
	 * Node that has global scope to allow transfer between mouse clicked and
	 * released events
	 */
	private Node selectedNode;
	/**
	 * Variable used to get scope of graphic component in the action listener
	 */
	ProblemGraphGraphicsComponent pGComp;
	/**
	 * Variable used to get scope of graphic component in the action listener
	 */
	SolutionGraphGraphicsComponent sGComp;
	/**
	 * Variable used to get scope of table component in the action listener
	 */
	TablePanel pTable;
	/**
	 * Variable used to get scope of list component in the action listener
	 */
	ConnectionTable cTable;
	/**
	 * The solution to the problem
	 */
	Graph solutionGraph = new Graph();
	/**
	 * Holds which item is currently focused so we can unhighlight it before
	 * highlighting another, used in JTextAreas
	 */
	private Component currentFocused;
	/**
	 * The color object to give the yellow highlight
	 */
	static final Color highlight = Color.getHSBColor(180.0F, 256.0F, 256.0F);

	/**
	 * The constructor used by the main method in order to pass scope of various
	 * components
	 * 
	 * @param pIn
	 * @param sGComp2
	 * @param problemTablePanel
	 * @param connectionTable
	 */
	public CustomActionListener(ProblemGraphGraphicsComponent pIn,
			SolutionGraphGraphicsComponent sGComp2,
			TablePanel problemTablePanel, ConnectionTable connectionTable) {
		pGComp = pIn;
		sGComp = sGComp2;
		pTable = problemTablePanel;
		cTable = connectionTable;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	/**
	 * Method that ensures that only numbers may be entered into the JTextAreas
	 */
	public void keyTyped(KeyEvent e) {
		if (!String.valueOf(e.getKeyChar()).matches("[0-9]")) {
			e.setKeyChar('\000');
		}
	}

	@Override
	/**
	 * Method that highlights boxes once focus is gained
	 */
	public void focusGained(FocusEvent e) {
		this.currentFocused = ((Component) e.getSource());
		if (this.currentFocused.getClass() == JTextField.class) {
			this.currentFocused.setBackground(highlight);
		}
	}

	@Override
	/**
	 * Method that dehighlights boxes once focus is lose
	 */
	public void focusLost(FocusEvent e) {
		if (e.getSource().getClass() == JTextField.class)
			((JTextField) e.getSource()).setBackground(Color.WHITE);
	}

	@Override
	/**
	 * Handles moving of nodes
	 */
	public void mouseDragged(MouseEvent me) {

		if (this.nodeMode) {
			if (this.selectedNode != null) {
				this.selectedNode.setPoint(me.getPoint());
			} else {
				JOptionPane
						.showMessageDialog(null,
								"There is no node to move here.",
								"node not located", 0);
			}

		}
		repaint();

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

	@Override
	/**
	 * Method used to handle the creation and deletion of nodes
	 */
	public void mouseClicked(MouseEvent me) {
		Point p = new Point(me.getX(), me.getY());
		if (this.nodeMode) {
			boolean nodeFound = false;
			if (nodeSearch(p) != null) {
				JOptionPane.showMessageDialog(null,
						"Already a node near here. Try another position.",
						"Node exists", 0);
				nodeFound = true;
			}
			if (!nodeFound) {
				/*
				 * Create Node
				 */
				String label = JOptionPane
						.showInputDialog("What would you like to call this node?");
				if (Node.checkValidLabel(label)) {
					if (label.matches("[a-zA-Z]")) {
						Node temp = new Node(label, p);
						Node.getAllNodes().add(temp);
					} else {
						JOptionPane.showMessageDialog(null,
								"Please enter a single letter only");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Duplicate node found");
				}
			}
		}
		if (this.deleteMode) {
			if (nodeSearch(me.getPoint()) != null) {
				Connection.getAllConnections().removeAll(
						Connection.getConnectionsContaining(nodeSearch(me
								.getPoint())));
				Node.getAllNodes().remove(nodeSearch(me.getPoint()));
				cTable.updateList();
			}
		}
		repaint();

	}

	/**
	 * Repaints both graphics components when called
	 */
	private void repaint() {
		sGComp.repaint();
		pGComp.repaint();

	}

	/**
	 * Find a node based on the point on screen, used to move, check for nodes
	 * and delete nodes.
	 * 
	 * @param p
	 * @return
	 */
	private Node nodeSearch(Point p) {
		if (!Node.getAllNodes().isEmpty()) {
			for (Node n : Node.getAllNodes()) {
				if (n.getPoint().distance(p) <= ProblemGraphGraphicsComponent
						.getTolerance()) {
					return n;
				}
			}
		}
		return null;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	/**
	 * Calls the node search method to get scope of the node clicked throughout the class.
	 */
	public void mousePressed(MouseEvent me) {
		selectedNode = nodeSearch(me.getPoint());

	}

	@Override
	/**
	 * Handles creation of edges.
	 */
	public void mouseReleased(MouseEvent me) {
		if (this.edgeMode) {
			Node endNode = nodeSearch(me.getPoint());
			if ((this.selectedNode != null) && (endNode != null)
					&& (endNode != this.selectedNode)) {
				Edge newEdge = new Edge(
						Integer.parseInt(JOptionPane
								.showInputDialog("Please enter the weight of the edge")));
				Edge.getAllEdges().add(newEdge);
				Connection x = new Connection(new Node[] { endNode,
						this.selectedNode }, newEdge);
				Connection.getAllConnections().add(x);
				cTable.updateList();
			}
		}
		repaint();
	}

	@Override
	/**
	 * Handles the actions to preform based on button presses.
	 */
	public void actionPerformed(ActionEvent ae) {
		int switchNo = getCommandNumber(ae.getActionCommand());
		switch (switchNo) {
		case 1:
			deleteMode = false;
			edgeMode = false;
			nodeMode = true;
			break;
		case 2:
			deleteMode = false;
			edgeMode = true;
			nodeMode = false;
			break;
		case 3:
			deleteMode = true;
			edgeMode = false;
			nodeMode = false;
			break;
		case 4:
			clear();
			break;
		case 5:
			PrintAndSave.save();
			break;
		case 6:
			PrintAndSave.openAllItems();
			cTable.updateList();
			break;
		case 7:
			if (Connection.getAllConnections().isEmpty()) {
				checkForTableInput();
			} else {
				generateTable();
			}
			Graph problemGraph = new Graph(Connection.getAllConnections());
			int chosenAlgorithmOption = JOptionPane.showOptionDialog(null,
					"Would you like primms or kruskals?", "Algorithm", -1, 3,
					null, new String[] { "Primms", "Kruskals", "Cancel" },
					"Cancel");
			switch (chosenAlgorithmOption) {
			case 0:
				solutionGraph = Primms.getMinConnection(problemGraph);
				break;
			case 1:
				solutionGraph = Kruskals.doKruskals(problemGraph);
				break;
			}

			sGComp.setConnections(solutionGraph.getConnectionList());
			sGComp.setMinConnection(solutionGraph.getTotalWeight());
			PrintAndSave.setConnections(solutionGraph.getConnectionList());
			cTable.updateList();
			break;
		}
		repaint();

	}

	/**
	 * Turns graphical input into table input
	 */
	private void generateTable() {
		JTextField[][] array = pTable.getTextFields();
		if (tableFormat()) {
			if (Node.getAllNodes().size() <= 9) {
				for (Connection c : Connection.getAllConnections()) {
					int position1 = (int) c.getNode(1).normLabel().charAt(0) - 65;
					int position2 = (int) c.getNode(2).normLabel().charAt(0) - 65;
					array[position1][position2].setText(""
							+ c.getEdge().getWeight());
					array[position2][position1].setText(""
							+ c.getEdge().getWeight());
				}
			}

		}

	}

	/**
	 * Used to determine if a table can be generated
	 * 
	 * @return true for valid table format, false for invalid
	 */
	private boolean tableFormat() {
		if (Node.getAllNodes().size() <= 9) {
			for (Node n : Node.getAllNodes()) {
				if (n.getLabel().length() != 1) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Turns table input into graphical input
	 */
	private void checkForTableInput() {
		ConcurrentLinkedQueue<Point> points = new ConcurrentLinkedQueue<Point>();
		points.add(new Point(20, 20));
		points.add(new Point(120, 20));
		points.add(new Point(220, 20));
		points.add(new Point(20, 120));
		points.add(new Point(20, 70));
		points.add(new Point(20, 170));
		points.add(new Point(70, 20));
		points.add(new Point(170, 20));
		points.add(new Point(170, 170));
		JTextField[][] array = pTable.getTextFields();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (!array[r][c].getText().equalsIgnoreCase("")) {
					/*
					 * Create node if does not exist, get them if they do
					 */
					char nodeLabel = (char) (r + 65);
					String label = "" + nodeLabel;
					Node n1, n2;
					Edge edge;
					if (Node.getNodeFromLabel(label) == null) {
						n1 = new Node(label, points.remove());
						Node.getAllNodes().add(n1);
					} else {
						n1 = Node.getNodeFromLabel(label);
					}
					nodeLabel = (char) (c + 65);
					label = "" + nodeLabel;
					if (Node.getNodeFromLabel(label) == null) {
						n2 = new Node(label, points.remove());
						Node.getAllNodes().add(n2);
					} else {
						n2 = Node.getNodeFromLabel(label);
					}

					if (getConnection(n1, n2) != null) {
						getConnection(n1, n2).getEdge().setWeight(
								Integer.parseInt(array[r][c].getText()));
					} else {
						edge = new Edge(Integer.parseInt(array[r][c].getText()));
						Edge.getAllEdges().add(edge);
						Connection.getAllConnections().add(
								new Connection(new Node[] { n1, n2 }, edge));
						cTable.updateList();
					}

				}
			}
		}

	}

	/**
	 * Gets the connection that contains both nodes passed in.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	private Connection getConnection(Node n1, Node n2) {
		ArrayList<Connection> toCompare1 = new ArrayList<Connection>();
		ArrayList<Connection> toCompare2 = new ArrayList<Connection>();
		toCompare1.addAll(Connection.getConnectionsContaining(n1));
		toCompare2.addAll(Connection.getConnectionsContaining(n2));
		for (Connection c : toCompare1) {
			for (Connection x : toCompare2) {
				if (c.getID() == x.getID()) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * Used to make readability easier by return a number for a switch
	 * 
	 * @param actionCommand
	 * @return
	 */
	private int getCommandNumber(String actionCommand) {
		if (actionCommand.equalsIgnoreCase("Node")) {
			return 1;
		} else if (actionCommand.equalsIgnoreCase("Edge")) {
			return 2;
		} else if (actionCommand.equalsIgnoreCase("Delete")) {
			return 3;
		} else if (actionCommand.equalsIgnoreCase("Clear")
				|| actionCommand.equalsIgnoreCase("New")) {
			return 4;
		} else if (actionCommand.equalsIgnoreCase("Save")) {
			return 5;
		} else if (actionCommand.equalsIgnoreCase("Open")) {
			return 6;
		} else if (actionCommand.equalsIgnoreCase("Solve")) {
			return 7;
		}
		return -1;
	}

	/**
	 * Clear all objects and therefore any graphic components on screen
	 */
	private void clear() {
		int choice = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to clear all objects?");
		if (choice == JOptionPane.YES_OPTION) {
			Connection.getAllConnections().clear();
			Edge.getAllEdges().clear();
			Node.getAllNodes().clear();
			solutionGraph.getConnectionList().clear();
			for (JTextField[] ja : pTable.getTextFields()) {
				for (JTextField j : ja) {
					j.setText("");
				}
			}
			cTable.updateList();
			Main.getTextArea("debug").setText("");
			Main.getTextArea("teachingMode").setText("");
			repaint();
		}
	}

}
