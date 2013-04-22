package graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

/**
 * JPanel that hosts a 2D array of JTextAreas in order to get table input from the user.
 * @author 100734
 *
 */
public class TablePanel extends JPanel {
	
	/**
	 * All the JTextFields for table panels
	 */
	JTextField[][] textBoxArray;
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TablePanel() {
		setLayout(new GridLayout(10, 10));
		JLabel[] jLabels = new JLabel[18];
		String[] nodeLabels = new String[] { "A", "B", "C", "D", "E", "F", "G",
				"H", "I" };
		/*
		 * Assign labels their correct letter
		 */
		for (int i = 0; i < 9; i++) {
			jLabels[i] = new JLabel(nodeLabels[i]);
			jLabels[i+9] = new JLabel(nodeLabels[i]);
		}
		/*
		 * Format the JPanel correctly and disabling any impossible connections (When they are mirrored such as AA)
		 */
		textBoxArray = new JTextField[9][9];
		for (int r = -1; r < 9; r++) 
		{
			if (r == -1) 
			{
				for(int c = -1; c<9; c++)
				{
					if(c != -1)
					{
						this.add(jLabels[c]);
					}else{
						this.add(new JLabel());
					}
				}
			} else {
				for (int c = -1; c < 9; c++) {
					if(c == -1){
						this.add(jLabels[r+9]);
					}else{
						textBoxArray[r][c] = new JTextField();
						this.add(textBoxArray[r][c]);
						if(r == c)
						{
							textBoxArray[r][c].setEditable(false);
						}
					}
				}
			}
		}

	}
	/**
	 * Method to get the 2D array of text fields
	 * @return
	 */
	public JTextField[][] getTextFields(){
		return this.textBoxArray;
	}
	/**
	 * Method used to add focus and key listeners to all the text boxes
	 * @param action
	 */
	public void addAllListeners(ActionListener action) {
		for(int i = 0; i < 9 ; i ++){
			for(int n = 0; n < 9 ; n ++){
				if(n != i){
					this.textBoxArray[i][n].addFocusListener((FocusListener) action);
					this.textBoxArray[i][n].addKeyListener((KeyListener) action);
				}
			}
		}
		
	}
}
