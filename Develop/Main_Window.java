/*
 * The design of the interface  of simulator by	YongqGui
 */
package Develop;


import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import chartAnalysisWindow.src.chartWindow.AddChartFrame;
import chartAnalysisWindow.src.chartWindow.AnalysisWindow;
import core.DTNHost;
import core.SimClock;

import java.awt.event.*;
import java.io.File;
import java.util.List;

public class Main_Window extends JFrame implements ActionListener, ChangeListener{
	private static final String PATH_GRAPHICS = "buttonGraphics/";	
	private static final String ICON_PLAY = "Play16.gif";
	private static final String ICON_PAUSE = "Pause16.gif";
	private static final String ICON_FastForward = "FastForward16.gif";
	private static final String ICON_REPORT = "Report.gif";
	private static final String ICON_Stop = "Stop.gif";
	private static final String ICON_Parameter = "Parameter.gif";	
	private static final String ICON_Bigger = "Bigger.gif";
	private static final String ICON_Smaller = "Smaller.gif";
	
	/** Default width for the GUI window */
	public static final int WIN_DEFAULT_WIDTH = 1000;
	/** Default height for the GUI window */
	public static final int WIN_DEFAULT_HEIGHT = 750;

	public JPanel ButtonMenus;
	public JButton playButton;
	public JButton end;
	public JButton report;
	public JButton FastForward;
	public JButton parameter;
	public JButton Smaller;
	public JButton Bigger;
	private static JSplitPane JSP1;
	private static JSplitPane JSP2;
	private static JSplitPane JSP3;
	protected boolean simPaused = true;
    private JFileChooser chooser;
	private JTabbedPane tabs = new JTabbedPane();
	private JDesktopPane desktopPane;
	
	public ActionListener e;


	
	public Main_Window(EventLog elp, List<DTNHost> hosts) {
		super("卫星仿真系统");
		
		final String liquid =  "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	  	try {
			UIManager.setLookAndFeel(liquid);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			//	设置皮肤
	  	this.getContentPane().setBackground(Color.lightGray);			// 设置背景颜色
	  	
	  	
		setSize(WIN_DEFAULT_WIDTH,WIN_DEFAULT_HEIGHT);
	    JPanel desktop = new JPanel();
	    getContentPane().add(desktop);
	    
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
	    final JMenu[] menus = {
	    		new JMenu("File   "), new JMenu("Edit   "),
				new JMenu("Properties   "),new JMenu("Tools   "),
				new JMenu("Windows   "),new JMenu("Help   "),
	    };
	    final JMenuItem[] items = {
			  	new JMenuItem("open"), new JMenuItem("exit"),
				new JMenuItem("Fo"),  new JMenuItem("Zip"),
				new JMenuItem("3D Window"), new JMenuItem("Zot"),
				new JMenuItem("exit"), new JMenuItem("Oxen"),
				new JMenuItem("Free"), new JMenuItem("Zot"),
				new JMenuItem("2D Window"), new JMenuItem("Oxen"),
				new JMenuItem("Olly"), new JMenuItem("Oxen"),
				new JMenuItem("Free"),
	    };
	    
	    for (int i=0;i<items.length; i++){
			menus[i%6].add(items[i]);
	    };
	    JMenuBar mb = new JMenuBar();
	    for (JMenu jm:menus){
	    	mb.add(jm);
	    };
	    JPanel fileMenus = new JPanel();
	    fileMenus.setLayout(new GridLayout(2,1));
	    fileMenus.add(mb);
	    
	    //设置用来放置一排按钮,这里和第一排肯定要用两个面板
	    ButtonMenus = new JPanel();
	    ButtonMenus.setLayout(new BoxLayout(ButtonMenus, BoxLayout.X_AXIS));
	    playButton = addButton(simPaused ? ICON_PLAY : ICON_PAUSE);
	    
	    end = new JButton();
	    end.addActionListener(this);
	    end.setIcon(createImageIcon(ICON_Stop));
	    //end.setContentAreaFilled(false);
	    
	    report = new JButton();
	    report.setIcon(createImageIcon(ICON_REPORT));
	    //report.setContentAreaFilled(false);
	    //report.addActionListener(this);
	    report.addActionListener(new OpenActionListener());
	    
	    FastForward = new JButton();
	    FastForward.setIcon(createImageIcon(ICON_FastForward));
	    FastForward.addActionListener(this);
	    
	    Smaller = new JButton();
	    Smaller.setIcon(createImageIcon(ICON_Smaller));
	    Smaller.addActionListener(this);
	    
	    Bigger = new JButton();
	    Bigger.setIcon(createImageIcon(ICON_Bigger));
	    Bigger.addActionListener(this);

	    parameter = new JButton();
	    parameter.setIcon(createImageIcon(ICON_Parameter));
	    //parameter.setContentAreaFilled(false);
        parameter.addActionListener(new ActionListener() {	//按钮出来之后要弹出参数配置界面
            public void actionPerformed(ActionEvent e) {
                new RouterInfo();
            }
        });
        
	    ButtonMenus.add(end);
	    ButtonMenus.add(FastForward);
	    ButtonMenus.add(Smaller);
	    ButtonMenus.add(Bigger);
	    ButtonMenus.add(report);
	    ButtonMenus.add(parameter);
	    fileMenus.add(ButtonMenus);
	    
	  
	    //---------------------------设置节点列表----------------------------//	  	
	    JPanel NodeList = new JPanel();
	    NodeList.setLayout(new GridLayout(30,1));
	    NodeList.setBorder(new TitledBorder("Nodes"));
	    for (int i = 0; i<30; i++)
	    	NodeList.add(new JButton("N" + i));
	  

		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		//System.out.println(desktopPane.getBackground());

	    
	    //---------------------------设置三维界面----------------------------//	  	
		JInternalFrame internalFrame = new JInternalFrame("三维场景", true, true, true, true);
		internalFrame.setLocation(20, 20);
		internalFrame.setSize(500, 300);
		internalFrame.setVisible(true);
		
		moveEarth applet = new moveEarth();
	    applet.init(hosts);
	    internalFrame.getContentPane().add(applet);
	    desktopPane.add("三维场景",internalFrame);
	    
	    //---------------------------设置二维界面----------------------------//	  		    
	    JInternalFrame internalFrame1 = new JInternalFrame("二维场景", true, true, true, true);
		internalFrame1.setLocation(80, 80);
		internalFrame1.setSize(500, 300);
		internalFrame1.setVisible(true);

		Play func = new Play(applet.BL);
		func.init();
	    //internalFrame1.add(func);
	    internalFrame1.getContentPane().add(func);
	    desktopPane.add("二维场景",internalFrame1);


	    
	    //---------------------------设置事件窗口----------------------------//
	    JPanel Event = new JPanel();
        Event.setLayout(new BoxLayout(Event,BoxLayout.Y_AXIS));						//	沿着Y轴进行布局
		
	    JSP1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,false,desktopPane,new JScrollPane(elp));
	    JSP1.setResizeWeight(0.8);													//	设置splitPane1的分隔线位置，0.1是相对于splitPane1的大小而定。
	    
	    JScrollPane Jscrollp = new JScrollPane(NodeList);		
	    Jscrollp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	//	不用水平滚动轴
	    JSP2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,false,JSP1,Jscrollp);
	  	JSP2.setResizeWeight(0.99);
	  	JSP3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,false,fileMenus,JSP2);	
	  	JSP3.setResizeWeight(0.01);

	  	add(JSP3);


	}
	  
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.playButton) {
			setPaused(simPaused);
		}
		else if (e.getSource() == this.end){
			System.exit(0);
		}
		else if (e.getSource() == this.report){
			//new AnalysisWindow();
		}

	}
	
    class OpenActionListener implements ActionListener{
        public void  actionPerformed(ActionEvent e){
            JFileChooser fileChooser = new JFileChooser("analysis//");
            fileChooser.setDialogTitle("选择分析文件");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            fileChooser.setFileFilter(filter);
            JLabel label = new JLabel();
            int n = fileChooser.showOpenDialog(fileChooser);
            if (n == fileChooser.APPROVE_OPTION){
                String input =fileChooser.getSelectedFile().getPath();
                new AddChartFrame(input);
            }
//            else
//                label.setText("未选择");
        }
    }
    
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getPaused(){
		return this.simPaused;
	}
	
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(PATH_GRAPHICS+path);
		return new ImageIcon(imgURL);
	}
	
	private JButton addButton(String iconPath) {
		JButton button = new JButton(createImageIcon(iconPath));
		button.addActionListener(this);
		//button.setContentAreaFilled(false);
		ButtonMenus.add(button);
		return button;
	}
	
	/**
	 * Sets simulation to pause or play.
	 * @param paused If true, simulation is put to pause
	 */
	public void setPaused(boolean paused) {
		if (!paused) {
			this.playButton.setIcon(createImageIcon(ICON_PLAY));
			this.simPaused = true;
		}
		else {
			this.playButton.setIcon(createImageIcon(ICON_PAUSE));
			this.simPaused = false;
		}
	}
}






