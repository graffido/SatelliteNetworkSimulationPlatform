/* 	
 * 最后要实现关于仿真界面上的处理全部放在这里  
 **/
package Develop;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.sun.j3d.utils.applet.MainFrame;

import ui.DTNSimTextUI;
import core.DTNHost;
import core.Settings;
import core.SimClock;

import java.awt.*;

public class OneSimUI extends DTNSimTextUI{
	private long lastUpdateRt;									// real time of last ui update
	private long startTime; 									// simulation start time
	private  EventLog eventLog;
	/** namespace of scenario settings ({@value})*/
	public static final String SCENARIO_NS = "Scenario";
	/** end time -setting id ({@value})*/
	public static final String END_TIME_S = "endTime";
	/** update interval -setting id ({@value})*/
	public static final String UP_INT_S = "updateInterval";
	/** How often the UI view is updated (milliseconds) */     
	public static final long UI_UP_INTERVAL = 60000;
	/** List of hosts in this simulation */
	protected List<DTNHost> hosts;
	
	public Main_Window main;

	/**
	 * Initializes the simulator model.
	 */
	private void NewWindow() {
		/**初始化图形界面*/
		this.eventLog = new EventLog(this);
		this.hosts = this.scen.getHosts();
		main = new Main_Window(eventLog,hosts);
		scen.addMessageListener(eventLog);
		scen.addConnectionListener(eventLog);
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		main.setVisible(true);	
		
		//start3D();
		//start2D();
	}

	private void start3D(){
	    JFrame frame = new JFrame();
	    frame.setSize(560, 460);
	    frame.setTitle("三维场景");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    MoveGlobe applet = new MoveGlobe();
	    applet.init();
	    frame.getContentPane().add(applet);
	    frame.setLocation(260,160);
	    frame.setVisible(true);
	}
	private void start2D(){
		new earth2D();
	    JFrame frame = new JFrame();
	    frame.setSize(480, 300);
	    frame.setTitle("二维场景");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JLabel label = new JLabel();
	    String name = "C:\\Users\\YongqGui\\Workspaces\\MyEclipse 2015 CI\\JAVA3D\\images\\earth.png";
	    label.setIcon(new ImageIcon(name));
	    frame.add(label);
	    frame.setLocation(260,160);
	    frame.setVisible(true);
	}
	/**
	 * Starts the simulation.
	 */
	public void start() {
		super.initModel();
		runSim();
	}
	
	private void startGUI() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
			    public void run() {
					try {
						NewWindow();
					} catch (AssertionError e) {
						processAssertionError(e);
					}
			    }
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	protected void processAssertionError(AssertionError e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runSim(){
		Settings s = new Settings(SCENARIO_NS);
		
		startGUI();
		while(main.getPaused() == true){			// 界面等待确定配置参数
			try {
				 synchronized (this){
					wait(10);
				 }
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setParameter();
		double simTime = SimClock.getTime();
		double endTime = scen.getEndTime();
		
		// ----------------------- 用于测试参数 --------------------------------//
		System.out.println("仿真时间"+"  "+endTime);
		System.out.println("更新时间："+"  "+scen.getUpdateInterval());
		// ----------------------- 用于测试参数 --------------------------------//
		
		
		startTime = System.currentTimeMillis();
		lastUpdateRt = startTime;
	
		while (simTime < endTime && !simCancelled){			
			if (main.getPaused()) {
				try {
					 synchronized (this){
						wait(10);
					 }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					world.update();
				} catch (AssertionError e) {
					e.printStackTrace();
					done();
					return;
				}
				simTime = SimClock.getTime();
			}
			this.update(false);
		}
		
		double duration = (System.currentTimeMillis() - startTime)/1000.0;
		
		simDone = true;
		done();
		this.update(true); // force final UI update
		
		print("Simulation done in " + String.format("%.2f", duration) + "s");
	
	}
	/**
	 * Updates user interface if the long enough (real)time (update interval)
	 * has passed from the previous update.
	 * @param forced If true, the update is done even if the next update
	 * interval hasn't been reached.
	 */
	private void update(boolean forced) {
		long now = System.currentTimeMillis();
		long diff = now - this.lastUpdateRt;
		double dur = (now - startTime)/1000.0;
		if (forced || (diff > UI_UP_INTERVAL)) {
			// simulated seconds/second 
			double ssps = ((SimClock.getTime() - lastUpdate)*1000) / diff;
			this.lastUpdateRt = System.currentTimeMillis();
			this.lastUpdate = SimClock.getTime();
		}		
	}
	
	private void print(String txt) {
		System.out.println(txt);
	}
	
	/**
	 * 当从界面重新配置参数之后，将参数重新写入到scen中，更新相应参数，不妨碍原有程序读取。
	 */
	private void setParameter(){
		Settings s = new Settings(SCENARIO_NS);
		double interval =  s.getDouble(UP_INT_S);	//	更新时间
		scen.setUpdateInterval(interval);
		
		double endTime = s.getDouble(END_TIME_S);	//	结束时间
		scen.setEndTime(endTime);
	}
}
