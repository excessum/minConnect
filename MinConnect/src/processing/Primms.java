package processing;

import graphics.Main;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import object.Connection;
import object.Graph;
import object.Node;

/**
 * The processing method that preforms the primms algorithm to get the minimum
 * connection and guiding users through the process
 * 
 * @author excessum
 * 
 */
public class Primms {

	/**
	 * The method to actually get a solutionGraph using the primms algorithm.
	 * 
	 * @param problemGraph
	 * @return
	 */
	public static Graph getMinConnection(Graph problemGraph) {
		Graph solutionGraph = new Graph();
		String label = null;
		boolean valid = false;
		while (!valid) {
			label = JOptionPane.showInputDialog(null,
					"While node would you like to start on? ");
			if (Node.getNodeFromLabel(label) == null) {
				valid = false;
				JOptionPane.showMessageDialog(null,
						"Invalid node entered, please try again.",
						"Invalid node", JOptionPane.ERROR_MESSAGE);
			} else {
				valid = true;
			}
		}
		Main.getTextArea("teachingMode")
				.append("First we decide where to start, this is usually given in exams but you \n"
						+ "can start anywhere and get a valid result. The node "
						+ label
						+ " is where we are starting, so we get \n "
						+ "all the connections that contain this node. \n\n ");
		Main.getTextArea("debug").append(
				"User chose " + label
						+ " grabbing all connections containing this node \n");

		ArrayList<Node> nodesInSolutionGraph = new ArrayList<Node>();
		nodesInSolutionGraph.add(Node.getNodeFromLabel(label));
		Main.getTextArea("teachingMode")
				.append("We then go through this list and choose the lowest weight edge that is not \n"
						+ "already in the solution graph and add the edge and connection to the solution graph , so it now contains \n "
						+ "the starting node as well as the lowest connections joint to the starting node. \n\n");
		boolean firstPass = true;
		while (solutionGraph.getConnectionList().size() < Node.getAllNodes()
				.size() -1) {
			Connection lowest = findLowestCompliantConnections(nodesInSolutionGraph);
			Main.getTextArea("teachingMode")
					.append("The connection "
							+ lowest.getNode(1).getLabel()
							+ lowest.getNode(2).getLabel()
							+ " is the lowest weight connection that joins\n"
							+ "one new node to the solution graph so it is added.\n\n");
			solutionGraph.addConnectionToGraph(lowest);
			if (nodesInSolutionGraph.contains(lowest.getNode(1))) {
				nodesInSolutionGraph.add(lowest.getNode(2));
			} else {
				nodesInSolutionGraph.add(lowest.getNode(1));
			}

			if (firstPass) {
				Main.getTextArea("teachingMode")
						.append("We now get all the connections that contain any of the nodes in the starting graph \n"
								+ "and repeat the above process untill there is n-1 connections where n is the number of nodes. You must remember to only \n"
								+ "add a node not already in the solution graph. If two edges are equal in weight, you may choose at random. \n\n");
				firstPass = false;
			}
			
		}
		Main.getTextArea("teachingMode")
				.append("We now have n-1 connections and the algorithm is complete, now we just add up the weight of the connections to get the minimum weight.");
		return solutionGraph;

	}

	/**
	 * Method to find the lowest weight connection which does no form a cycle
	 * and has ONE node in the current solution.
	 * 
	 * @param nodesInSolutionGraph
	 * @param solution
	 * @return
	 */
	private static Connection findLowestCompliantConnections(
			ArrayList<Node> nodesInSolutionGraph) {
		ArrayList<Connection> shortList = new ArrayList<Connection>();
		for (Connection c : Connection
				.getConnectionsContaining(nodesInSolutionGraph)) {
			boolean removed = false;
			for (Node n : nodesInSolutionGraph) {
				/*
				 * If the connection contains the node, check if it is already
				 * on the shortlist, if it is, remove it, if it isn't add it
				 */

				if (!removed) {
					if ((c.getNode(1).getLabel().equalsIgnoreCase(n.getLabel()))
							|| (c.getNode(2).getLabel().equalsIgnoreCase(n
									.getLabel()))) {
						if (shortList.contains(c)) {
							shortList.remove(c);
							removed = true;
						} else {
							shortList.add(c);
						}
					}
				}
			}
		}
		return getLowestConnection(shortList);
	}

	/**
	 * Returns the lowest weight connection from the list passed in
	 * @param shortList
	 * @return
	 */
	private static Connection getLowestConnection(
			ArrayList<Connection> shortList) {
		Connection lowest = null;
		for (Connection x : shortList) {
			if ((lowest == null)
					|| (lowest.getEdge().getWeight() > x.getEdge().getWeight())) {
				lowest = x;
			}
		}
		return lowest;
	}
}
