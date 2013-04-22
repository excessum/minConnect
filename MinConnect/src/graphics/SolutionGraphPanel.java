package graphics;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Empty JPanel with borderLayout to host the solutionGraph component when it is initialised 
 * @author excessum
 *
 */
public class SolutionGraphPanel extends JPanel {
	
	/**
	 * Default ID
	 */
	private static final long serialVersionUID = 1L;
	public SolutionGraphPanel()
	{
		this.setLayout(new BorderLayout());
	}
}
