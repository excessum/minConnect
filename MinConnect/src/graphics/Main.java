package graphics;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Acts as the controller and main, creating and initialising all components and then adding
 * action listeners.
 * @author Curtis Stokes
 *
 */
public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Value holding dimension of main frame.
	 */
	static Dimension frameDimension;
	
	/**
	 * Area for outputting debug text to user.
	 */
	private static JTextArea debugText;
	/**
	 * Area for outputting teaching notes to user.
	 */
	private static JTextArea teachingModeText;
	/**
	 * The problem Graph panel
	 */
	ProblemGraphPanel problemGraphPanel = new ProblemGraphPanel();
	/**
	 * The solution Graph Panel
	 */
	SolutionGraphPanel solutionGraphPanel = new SolutionGraphPanel();
	/**
	 * The problemTablePanel
	 */
	TablePanel problemTablePanel = new TablePanel();
	/**
	 * The problem Graphics Component
	 */
	ProblemGraphGraphicsComponent pGComp = new ProblemGraphGraphicsComponent();
	/**
	 * The solution Graphics Component
	 */
	SolutionGraphGraphicsComponent sGComp = new SolutionGraphGraphicsComponent();
	/**
	 * The connection table
	 */
	ConnectionTable connectionTable = new ConnectionTable();
	/**
	 * CustomActionListener
	 */
	private static ActionListener action;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try
				{
					Main frame = new Main();					
					frame.setVisible(true);
					frame.setExtendedState(MAXIMIZED_BOTH);
					
					
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() 
	{
		
		/*
		 * Get the screen dimensions and set how the screen behaves
		 */
		Toolkit x = Toolkit.getDefaultToolkit();
		frameDimension = x.getScreenSize();
		setTitle("MinConnect ");
		setSize(frameDimension.width, (frameDimension.height*9)/10);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*
		 * Add panels together and create actionListener	 
		 */
		problemGraphPanel.add(pGComp);
		solutionGraphPanel.add(sGComp);
		action = new CustomActionListener(pGComp, sGComp, problemTablePanel, connectionTable);
		pGComp.addMouseListener((MouseListener) action);
		pGComp.addMouseMotionListener((MouseMotionListener) action);
		problemGraphPanel.addAllListeners(action);
		problemTablePanel.addAllListeners(action);
		
		/*
		 * Create the JMenu and its components
		 */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);	
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);	
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mntmNew.setActionCommand("new");	
		mntmNew.addActionListener(action);
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmOpen.setActionCommand("open");	
		mntmOpen.addActionListener(action);
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.setActionCommand("save");
		mntmSave.addActionListener(action);
		
		/*
		 * Create the tabbed text output panes and configure them
		 */	
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(frameDimension.width/4*3, 0, frameDimension.width/4, (frameDimension.height*9)/10);
		getContentPane().add(tabbedPane);		
		teachingModeText = new JTextArea(10,10);
		teachingModeText.setEditable(false);
		debugText = new JTextArea(10,10);
		debugText.setEditable(false);
		JScrollPane debugScroll = new JScrollPane(debugText);
		JScrollPane teachingModeScroll = new JScrollPane(teachingModeText);
		debugScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);	
		teachingModeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tabbedPane.add("Teaching Mode", teachingModeScroll);
		tabbedPane.add("Debug", debugScroll);
		this.setLayout(null);
	
		/*
		 * Create the split panes and configure them
		 */
		JSplitPane baseSplitPane = new JSplitPane();
		baseSplitPane.setBounds(0, 0, (frameDimension.width/4)*3, (frameDimension.height*9)/10);
		baseSplitPane.setDividerLocation(0.50);
		getContentPane().add(baseSplitPane);
		
		JSplitPane leftSplitPane = new JSplitPane();
		leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		leftSplitPane.setDividerLocation((frameDimension.height/10)*9/2);
		leftSplitPane.setTopComponent(problemGraphPanel);
		leftSplitPane.setBottomComponent(problemTablePanel);
			
		JSplitPane rightSplitPane = new JSplitPane();
		rightSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		rightSplitPane.setDividerLocation((frameDimension.height/10)*9/2);
		leftSplitPane.setDividerSize(3);
		rightSplitPane.setDividerSize(3);
		rightSplitPane.setTopComponent(solutionGraphPanel);
		rightSplitPane.setBottomComponent(connectionTable);
		baseSplitPane.setDividerSize(5);
		baseSplitPane.setLeftComponent(leftSplitPane);
		baseSplitPane.setRightComponent(rightSplitPane);
	
		
	}
	
	/**
	 * Method that takes in either debug or teaching mode and returns the appropriate text area.
	 * @param areaWanted String requesting either debug or teachingMode
	 * @return
	 */
	public static JTextArea getTextArea(String areaWanted)
	{
		if(areaWanted.equalsIgnoreCase("debug"))
		{
			return debugText;
		}else{
			return teachingModeText;
		}
	}
}
