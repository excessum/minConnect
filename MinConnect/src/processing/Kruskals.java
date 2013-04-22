package processing;

import graphics.Main;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import object.Connection;
import object.Graph;
import object.Node;

/**
 * Algorithm class containing the kruskals algorithm and the methods it has to use
 * @author 100734
 *
 */
public class Kruskals {

	public static Graph doKruskals(Graph problemGraph) {
		/*
		 * Get connections and sort them
		 */
		Graph toReturn;
		ArrayList<Connection> connectionsInGraph = problemGraph
				.getConnectionList();
		ConcurrentLinkedQueue<Connection> sortedList = sortConnectionList(connectionsInGraph);
		
		Main.getTextArea("teachingMode").append(
				"First we get all the connections from the "
						+ "problem graph, then we sort them "
						+ "into ascending order. \n \n \n");
		Main.getTextArea("debug")
				.append("First we get the ArrayList of connections, \n"
						+ "we then sort this queue into ascending order using the sortConnectionList method \n");
		/*
		 * subTrees hold all current trees in the solution
		 */
		ArrayList<Graph> subTrees = new ArrayList<Graph>();

		Main.getTextArea("teachingMode").append(
				"We now go through every connection in the sorted "
						+ "list until we have n-1 connections, "
						+ "where n is the number of nodes to \n"
						+ "determine if it forms a cycle, if it does "
						+ "not we add it to the solution graph\n\n");
		Main.getTextArea("debug").append(
				"We now go through a while loop untill"
						+ "numberOfNodes-1 is equal to number of"
						+ "connections in the solutionGraph and consider \n"
						+ "wether they create a new graph, add to a graph,"
						+ "join two existing graphs or form a cycle "
						+ "if a cycle is formed it is rejected\n\n");

		ArrayList<Graph> subTreesInvolved = null;
		String connectionLabel;
		int totalNumberOfConnections = 0;
		while ((!sortedList.isEmpty())
				&& (totalNumberOfConnections < Node.getAllNodes().size())) {
			connectionLabel = "" + sortedList.peek().getNode(1).getLabel()
					+ sortedList.peek().getNode(2).getLabel();
			try {
				subTreesInvolved = caseForConnection(sortedList.peek(),
						subTrees);
				switch (subTreesInvolved.size()) {
				/*
				 * Connection nodes were found to be in no existing trees and
				 * therefore forms a new one
				 */
				case 0:
					Graph temp = new Graph();
					temp.addConnectionToGraph(sortedList.peek());
					subTrees.add(temp);

					Main.getTextArea("debug")
							.append("The treesInvolved list came back with size 0 "
									+ "and therefore the connection "
									+ connectionLabel
									+ " is not"
									+ "in any existing tree and forms  a new one \n\n");
					Main.getTextArea("teachingMode")
							.append("The connection "
									+ connectionLabel
									+ " did not form"
									+ " a cycle and therefore is added to the solution tree\n\n"
									+ connectionLabel + " "
									+ sortedList.peek().getEdge().getWeight()
									+ "    accepted\n\n");
					break;
				/*
				 * Connection nodes were found to be in one existing tree and
				 * therefore is added to that tree
				 */
				case 1:
					subTreesInvolved.get(0).addConnectionToGraph(
							sortedList.peek());

					Main.getTextArea("debug").append(
							"The treesInvolved list came back with size 1"
									+ "and therefore the connection "
									+ connectionLabel + " is in one"
									+ " existing tree and is added to it \n\n");
					Main.getTextArea("teachingMode")
							.append("The connection "
									+ connectionLabel
									+ " did not form"
									+ " a cycle and therefore is added to the solution tree\n\n"
									+ connectionLabel + " "
									+ sortedList.peek().getEdge().getWeight()
									+ "    accepted\n\n");
					break;

				/*
				 * Connection nodes were found to be in two different trees and
				 * therefore joins them together
				 */
				case 2:
					subTreesInvolved
							.get(0)
							.getConnectionList()
							.addAll(subTreesInvolved.get(1).getConnectionList());
					subTreesInvolved.get(0).addConnectionToGraph(
							sortedList.peek());
					Graph.getGraphList().remove(subTreesInvolved.get(1));

					Main.getTextArea("debug")
							.append("The treesInvolved list came back with size 2 "
									+ "and therefore the connection nodes\n "
									+ connectionLabel
									+ "\nare in two different existing "
									+ "trees and therefore the trees"
									+ " are merged and the connection added \n\n");
					Main.getTextArea("teachingMode")
							.append("The connection "
									+ connectionLabel
									+ " did not form"
									+ " a cycle and therefore is added to the solution tree\n\n"
									+ connectionLabel + " "
									+ sortedList.peek().getEdge().getWeight()
									+ "    accepted\n\n");
					break;

				}
				totalNumberOfConnections++;
			} catch (Exception e) {
				if (e.getMessage().equals("Cycle Found")) {
					Main.getTextArea("debug")
							.append("The connection "
									+ connectionLabel
									+ " was found"
									+ " to join two nodes in an existing tree and therefore threw "
									+ "an error and is rejected\n\n");
					Main.getTextArea("teachingMode")
							.append("The connection "
									+ connectionLabel
									+ " was found "
									+ "to join two nodes in the solution tree and therefore forms a cycle and is rejected\n\n");
				} else {
					Main.getTextArea("debug").append(e.getMessage());
				}
			}
			sortedList.remove();
		}
		toReturn = new Graph();
		for(Graph x : subTrees){
			for(Connection c : x.getConnectionList()){
				toReturn.addConnectionToGraph(c);
			}
		}
		return toReturn;

	}

	/**
	 * Sorts the connection list passed in into a queue with the lowest weighted
	 * connections at the front.
	 * 
	 * @param listIn
	 * @return
	 */
	public static ConcurrentLinkedQueue<Connection> sortConnectionList(
			ArrayList<Connection> listIn) {
		ConcurrentLinkedQueue<Connection> srtd = new ConcurrentLinkedQueue<Connection>();
		ArrayList<Connection> editableList = new ArrayList<Connection>();
		editableList.addAll(listIn);
		for (int i = 0; i < listIn.size(); i++) {
			Connection c = null;
			for (Connection o : editableList) {
				if ((c == null)
						|| (o.getEdge().getWeight() < c.getEdge().getWeight())) {
					c = o;
				}
			}
			editableList.remove(c);
			srtd.add(c);
		}
		return srtd;
	}

	/**
	 * Method to return whether a connection creates a new tree, adds to an
	 * existing tree, connects two trees together or forms a cycle when being
	 * added to an existing set of trees. Will throw an exception if a cycle is
	 * found.
	 * 
	 * @param consideredConnection
	 * @param subTrees
	 * @return
	 * @throws Exception
	 */
	private static ArrayList<Graph> caseForConnection( 
		    Connection consideredConnection, ArrayList<Graph> subTrees) 
		{ 
		  final ArrayList<Graph> ret = new ArrayList<Graph>(); 
		  for (Graph g : subTrees) { 
			  int count = 0;
		    for (Connection c : g.getConnectionList()) { 	    	
		      if (c.matches(consideredConnection)) { 
		    	  count++;
		        if (count == 2) throw new RuntimeException("Cycle Found"); 
		        ret.add(g); 
		      } 
		    } 
		  } 
		  return ret; 
		} 

}
