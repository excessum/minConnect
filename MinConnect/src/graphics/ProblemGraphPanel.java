package graphics;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
/**
 * Problem graph panel is the JPanel at the top left of the GUI
 * @author Curtis Stokes
 *
 */
public class ProblemGraphPanel extends JPanel {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/**
		 * Toolbar is the bar that hosts the buttons on the panel.
		 */
		JToolBar toolBar = new JToolBar();
		/**
		 * These JButtons are the buttons at the top of the JPanel and all have action commands equal
		 * to their name
		 */
		JToggleButton nodeMode = new JToggleButton("Node");
		JToggleButton edgeMode = new JToggleButton("Edge");
		JToggleButton delete = new JToggleButton("Delete");
		JButton clear = new JButton("Clear");
		JButton step = new JButton("Step");
		JButton solve = new JButton("Solve");
		/**
		 * Buttongroup allows only one button of the group to be toggled at a time.
		 */
		ButtonGroup bg = new ButtonGroup();
			
		/**
		 * Default constructor, adds the buttons to the toolbar and then the panel and configures them correctly
		 */
		public ProblemGraphPanel(){		
			this.setLayout(new BorderLayout());
			bg.add(nodeMode);
			bg.add(edgeMode);
			bg.add(delete);
			toolBar.setLayout(new GridLayout(1,8));
			toolBar.add(nodeMode);
			toolBar.add(edgeMode);
			toolBar.add(delete);
			toolBar.add(clear);
			toolBar.add(new JLabel(""));
			toolBar.add(step);
			toolBar.add(new JLabel(""));
			toolBar.add(solve);
			
			this.add(toolBar, BorderLayout.NORTH);

			
			
		}
		
		/**
		 * Currently unused, will use when implementing stepped problem solving.
		 * @return
		 */
		public JButton getStepButton(){
			return this.step;
		}
		
		/**
		 * Adds the action listener to all components, is called after the actionListener is made in the main class
		 * @param al
		 */
		public void addAllListeners(ActionListener al){
			nodeMode.addActionListener(al);
			edgeMode.addActionListener(al);
			delete.addActionListener(al);
			clear.addActionListener(al);
			solve.addActionListener(al);
			step.addActionListener(al);
		}
		
}
