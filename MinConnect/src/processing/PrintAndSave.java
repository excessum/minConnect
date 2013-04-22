package processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

import object.Connection;
import object.Edge;
import object.Node;

public class PrintAndSave {
	static File f = null;
	static String homeDir;
	static ArrayList<Connection> connections = new ArrayList<Connection>();

	public static void save() {
		chooseHomeDirectory();
		f = new File(homeDir + "\\nodes.txt");
		f.delete();
		PrintStream ps = null;
		try {
			ps = new PrintStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (Node node : Node.getAllNodes()) {
			String[] temp = node.getSaveDetails();
			for (int i = 0; i < temp.length; i++) {
				ps.print(temp[i] + " ");
			}
			ps.println();
		}
		ps.close();
		f = new File(homeDir + "\\edges.txt");
		f.delete();
		System.runFinalization();
		System.gc();
		try {
			ps = new PrintStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Edge edge : Edge.getAllEdges()) {
			String[] temp = edge.getSaveDetails();
			for (int i = 0; i < temp.length; i++) {
				ps.print(temp[i] + " ");
			}
			ps.println();
		}
		ps.close();
		f = new File(homeDir + "\\connections.txt");
		f.delete();
		System.runFinalization();
		System.gc();
		try {
			ps = new PrintStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Connection con : Connection.getAllConnections()) {
			String[] temp = con.getSaveDetails();
			for (int i = 0; i < temp.length; i++) {
				ps.print(temp[i] + " ");
			}
			ps.println();
		}
		ps.close();
		System.runFinalization();
		System.gc();
	}

	public static void openAllItems() {
		chooseFile();
		f = new File(homeDir + "\\nodes.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		do {
			String s = scan.nextLine();
			Scanner p = new Scanner(s);
			String[] toSave = new String[3];
			int count = 0;
			while (p.hasNext()) {
				toSave[count] = p.next();
				count++;
			}
			Node.getAllNodes().add(new Node(toSave));
			p.close();
		} while (scan.hasNextLine());

		scan.close();
		System.runFinalization();
		System.gc();

		f = new File(homeDir + "\\edges.txt");
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		do {
			String s = scan.nextLine();
			Scanner p = new Scanner(s);
			String[] toSave = new String[2];
			int count = 0;
			while (p.hasNext()) {
				toSave[count] = p.next();
				count++;
			}
			Edge.getAllEdges().add(new Edge(toSave));
			p.close();
		} while (scan.hasNextLine());

		scan.close();
		System.runFinalization();
		System.gc();

		f = new File(homeDir + "\\connections.txt");
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		do
			try {
				String s = scan.nextLine();
				Scanner p = new Scanner(s);
				String[] toSave = new String[4];
				int count = 0;
				while (p.hasNext()) {
					toSave[count] = p.next();
					count++;
				}
				Connection.getAllConnections().add(new Connection(toSave));
				p.close();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		while (scan.hasNextLine());

		scan.close();
		System.runFinalization();
		System.gc();
	}

	private static void chooseFile() {
		JFileChooser jc = new JFileChooser();
		jc.setFileSelectionMode(1);
		int returnValue = jc.showOpenDialog(null);
		if (returnValue == 0) {
			File g = jc.getSelectedFile();
			homeDir = g.getAbsolutePath();
		}

	}

	private static void chooseHomeDirectory() {
		JFileChooser jc = new JFileChooser();
		jc.setFileSelectionMode(1);
		int returnValue = jc.showOpenDialog(null);
		if (returnValue == 0) {
			File g = jc.getSelectedFile();
			homeDir = g.getAbsolutePath();
		}

	}

	public static String[] getAllConnectionsToPrint() {
		String[] toReturn = new String[Connection.getAllConnections().size()];
		int count = 0;
		for (Connection c : Connection.getAllConnections()) {
			if (connections.isEmpty()) {
				if (((int) c.getNode(1).getLabel().charAt(0)) < ((int) c
						.getNode(2).getLabel().charAt(0))) {
					toReturn[count] = "Connection " + c.getNode(1).getLabel()
							+ c.getNode(2).getLabel() + "\t\t     "
							+ c.getEdge().getWeight();
				} else {
					toReturn[count] = "Connection " + c.getNode(2).getLabel()
							+ c.getNode(1).getLabel() + "\t\t     "
							+ c.getEdge().getWeight();
				}
			} else if (!connections.contains(c)) {
				if (((int) c.getNode(1).getLabel().charAt(0)) < ((int) c
						.getNode(2).getLabel().charAt(0))) {
					toReturn[count] = "Connection " + c.getNode(1).getLabel()
							+ c.getNode(2).getLabel() + "\t\t     "
							+ c.getEdge().getWeight()
							+ "\t\t   Rejected - Cycle";
				} else {
					toReturn[count] = "Connection " + c.getNode(2).getLabel()
							+ c.getNode(1).getLabel() + "\t\t     "
							+ c.getEdge().getWeight()
							+ "\t\t   Rejected - Cycle";
				}
			} else if (connections.contains(c)) {
				if (((int) c.getNode(1).getLabel().charAt(0)) < ((int) c
						.getNode(2).getLabel().charAt(0))) {
					toReturn[count] = "Connection " + c.getNode(1).getLabel()
							+ c.getNode(2).getLabel() + "\t\t     "
							+ c.getEdge().getWeight()
							+ "\t\t   Accepted";
				} else {
					toReturn[count] = "Connection " + c.getNode(2).getLabel()
							+ c.getNode(1).getLabel() + "\t\t     "
							+ c.getEdge().getWeight()
							+ "\t\t   Accepted";
				}
			}
			count++;
		}

		return toReturn;

	}

	public static void setConnections(ArrayList<Connection> conIn) {
		connections = conIn;
	}

}
