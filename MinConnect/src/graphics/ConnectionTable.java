package graphics;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import processing.PrintAndSave;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * This is a List JPanel that displays all the current connections in the program
 * @author excessum
 *
 */
public class ConnectionTable extends JPanel {
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List to hold all the connection objects
	 */
	JList list;
	/**
	 * Create the panel.
	 */
	public ConnectionTable() {
		/*
		 * Set layout to allow 2/3rds of JPanel to be the list
		 */
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{198, 198, 0};
		gridBagLayout.rowHeights = new int[]{443, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		add(label, gbc_label);
		
		/*
		 * Create a JList to hold all the connection values, one per a line
		 */
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setFixedCellHeight(20);
		list.setVisibleRowCount(-1);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 0;
		add(list, gbc_list);
		
		
		

	}
	
	/**
	 * Method called to update the data of the list
	 */
	public void updateList() {
		String[] data = PrintAndSave.getAllConnectionsToPrint();
		this.list.setListData(data);
	}

}
