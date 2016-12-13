/*
 * The design of the interface  of simulator by	YongqGui
 */
package Develop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.Settings;


public class RouterInfo extends JFrame implements ActionListener, ChangeListener{
	/** Default width for the GUI window */
	public static final int WIN_DEFAULT_WIDTH = 700;
	/** Default height for the GUI window */
	public static final int WIN_DEFAULT_HEIGHT = 500;
	public JButton Confirm;
	public JButton Reset;
	public JButton Concel;
	public JComboBox RouterC;
	public JTextField simTime;
	public JTextField interval;
	public JTextField warmUp;
	public JTextField totalNodes;
	public JTextField totalPlane;
	public JTextField phaseFactor;
	/**轨道半径**/
	public JTextField radius;
	/**轨道离心率**/
	public JTextField eccentricity;
	public JTextField bufferSize;
	public JTextField transmissionRadius;
	public JTextField transmissionRate;
	public JTextField messageSize;
	public JTextField messageTTL;
	public JTextField worldSizeX;
	public JTextField worldSizeY;
	public JTextField worldSizeZ;
	public JComboBox gridLayer;
	
	public RouterInfo(){
		super("参数配置");										//   copyright by USTC");
		setSize(WIN_DEFAULT_WIDTH,WIN_DEFAULT_HEIGHT);
		
		//---------------------------设置tab页面----------------------------//		
		final JTabbedPane tabs = new JTabbedPane();
		this.setLayout(null);
		JPanel jp1 = RouteInfoSetting();	
		JPanel jp2 = CacheSetting();
		JPanel jp3 = MovementSetting();		
		JPanel jp4 = LinkSetting();
		JPanel jp5 = MainSetting();

		tabs.add("路由模块",jp1);		
		tabs.add("缓存模块",jp2);
		tabs.add("运动模块",jp3);		
		tabs.add("链路模块",jp4);
		tabs.add("总体参数",jp5);
		
		//---------------------------设置几个按钮----------------------------//	
		JPanel ButtonMenu = new JPanel();
		ButtonMenu.setLayout(new BoxLayout(ButtonMenu, BoxLayout.X_AXIS));
	    Reset = new JButton("默认");									// 为按钮加入监听器
	    Reset.addActionListener(this);
	    Concel = new JButton("取消");
	    Concel.addActionListener(this);
	    Confirm = new JButton("确认");
	    Confirm.addActionListener(this);
	    
		ButtonMenu.add(Box.createHorizontalStrut(250));
		ButtonMenu.add(Reset);
		ButtonMenu.add(Box.createHorizontalStrut(30));
		ButtonMenu.add(Concel);
		ButtonMenu.add(Box.createHorizontalStrut(30));
		ButtonMenu.add(Confirm);
		
		tabs.setBounds(0, 0, 666, 380);
		ButtonMenu.setBounds(150, 405, 500, 30);
		add(tabs);
		add(ButtonMenu);
		this.setLocationRelativeTo(null);
		this.setResizable(false);							//	设置界面不可最大化
		setVisible(true);
	}
	
	public JPanel RouteInfoSetting(){
		JPanel jp1 = new JPanel();
		//JPanel jp = new JPanel();
		//---------------------------设置路由参数页面----------------------------//	
		JPanel RouteFirst = new JPanel();
		RouteFirst.setBorder(new TitledBorder("一级配置界面"));
		//RouteFirst.setLayout(new GridLayout(8,1,10,10));
		RouteFirst.setLayout(null);
		//第一行
	    JLabel label1=new JLabel("路由协议选择：",JLabel.LEFT);
		RouterC = new JComboBox();
		String[] description = {
				"EASRRouter", "GridRouter", "ClusterRouter",
			    "FirstContactRouter", "DirectDeliveryRouter", "EpidemicRouter",
		};
		
	    for(int i = 0; i < 6; i++)
	    	RouterC.addItem(description[i]);
		
		label1.setBounds(10, 25, 100, 30);
		RouterC.setBounds(130, 25, 160, 30);
		RouteFirst.add(label1);
		RouteFirst.add(RouterC);

		//		第二行：路由模式选择，指定路径还是逐跳路由？
		JLabel rlabel1 = new JLabel("路由模式选择：",JLabel.LEFT);
		JLabel rlabel2 = new JLabel("逐跳确认",JLabel.LEFT);
		JLabel rlabel3 = new JLabel("指定路径",JLabel.LEFT);
		ButtonGroup g = new ButtonGroup();
	    JRadioButton	    rb1 = new JRadioButton("", false),
	    					rb2 = new JRadioButton("", false);
	    rb1.setSelected(true);
	    g.add(rb1);
	    g.add(rb2);
	    
		rlabel1.setBounds(10, 65, 100, 30);
		rlabel2.setBounds(130,65, 55, 30);
		rb1.setBounds(189,65, 20, 30);
		rlabel3.setBounds(215,65, 55, 30);
		rb2.setBounds(272,65,20, 30);
		RouteFirst.add(rlabel1);
		RouteFirst.add(rlabel2);
		RouteFirst.add(rlabel3);
		RouteFirst.add(rb1);
		RouteFirst.add(rb2);
		//第三行 		
		JLabel qlabel1 = new JLabel("发送队列模式：",JLabel.LEFT);
		JLabel qlabel2 = new JLabel("Random",JLabel.LEFT);
		JLabel qlabel3 = new JLabel("FIFO",JLabel.LEFT);
		ButtonGroup q = new ButtonGroup();
	    JRadioButton	    qb1 = new JRadioButton("", false),
	    					qb2 = new JRadioButton("", false);
	    qb1.setSelected(true);
	    q.add(qb1);
	    q.add(qb2);
	    qlabel1.setBounds(10, 105, 100, 30);
	    qlabel2.setBounds(131,105, 55, 30);
	    qb1.setBounds(189,105, 20, 30);
	    qlabel3.setBounds(215,105, 55, 30);
	    qb2.setBounds(272,105,20, 30);
	    
		RouteFirst.add(qlabel1);
		RouteFirst.add(qlabel2);
		RouteFirst.add(qlabel3);
		RouteFirst.add(qb1);
		RouteFirst.add(qb2);
		
		//第四行
	    JLabel label2=new JLabel("消息大小：",JLabel.LEFT);
		final JTextField txt1 = new JTextField("1");
		final JTextField txt2 = new JTextField("10");
		JLabel label21 = new JLabel("~",JLabel.CENTER);
		JLabel label22 = new JLabel("M",JLabel.CENTER);
		
		label2.setBounds(10, 145, 100, 30);
		txt1.setBounds(130, 145, 60, 30);
		label21.setBounds(190, 145, 10, 30);
		txt2.setBounds(200, 145, 60, 30);
		label22.setBounds(265, 145, 10, 30);
		RouteFirst.add(label2);
		RouteFirst.add(txt1);
		RouteFirst.add(label21);
		RouteFirst.add(txt2);
		RouteFirst.add(label22);
		//第五行
	    JLabel label3=new JLabel("消息生存时间：",JLabel.LEFT);
		final JTextField txt3 = new JTextField("1");
		final JTextField txt4 = new JTextField("5");
		JLabel label31 = new JLabel("~",JLabel.CENTER);
		JLabel label32 = new JLabel("Min",JLabel.CENTER);
		
		label3.setBounds(10, 185, 100, 30);
		txt3.setBounds(130, 185, 60, 30);
		label31.setBounds(190, 185, 10, 30);
		txt4.setBounds(200, 185, 60, 30);
		label32.setBounds(265, 185, 20, 30);
		RouteFirst.add(label3);
		RouteFirst.add(txt3);
		RouteFirst.add(label31);
		RouteFirst.add(txt4);
		RouteFirst.add(label32);
		
		//第六行
	    JLabel label4=new JLabel("节点缓存大小：",JLabel.LEFT);
	    JLabel label5=new JLabel("M",JLabel.LEFT);
		bufferSize = new JTextField("1000");
		label4.setBounds(10, 225, 100, 30);
		bufferSize.setBounds(130,225, 130, 30);
		label5.setBounds(265, 225, 20, 30);
		RouteFirst.add(label4);
		RouteFirst.add(bufferSize);
		RouteFirst.add(label5);

	    //第七行
	    JLabel label61=new JLabel("节点传输速率：",JLabel.LEFT);
	    JLabel label62=new JLabel("kbps",JLabel.LEFT);
		transmissionRate = new JTextField("100");
		label61.setBounds(10, 265, 100, 30);
		transmissionRate .setBounds(130,265, 130, 30);
		label62.setBounds(265, 265, 40, 30);
		RouteFirst.add(label61);
		RouteFirst.add(transmissionRate);
		RouteFirst.add(label62);
		
		//第八行
	    JLabel label71=new JLabel("节点传输半径：",JLabel.LEFT);
	    JLabel label72=new JLabel("km",JLabel.LEFT);
		transmissionRadius = new JTextField("500");
		label71.setBounds(10, 305, 100, 30);
		transmissionRadius.setBounds(130,305, 130, 30);
		label72.setBounds(265, 305, 40, 30);
		RouteFirst.add(label71);
		RouteFirst.add(transmissionRadius);
		RouteFirst.add(label72);
		
		RouteFirst.setSize(320, 200);
		//---------------------------设置二级路由参数页面----------------------------//			
		final JPanel RouteSecond = new JPanel();
		RouteSecond.setBorder(new TitledBorder("二级配置界面"));
		RouteSecond.setLayout(null);
		//	设置监听器
		RouterC.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// 首先设置默认存在的参数变量
				
				
				
				
				// 最后网格法需要添加的参量
				if(RouterC.getSelectedIndex()==1 || RouterC.getSelectedIndex()==2){
					RouteSecond.removeAll();
					
					JLabel rlabel1 = new JLabel("计算模式选择：",JLabel.LEFT);
					JLabel rlabel2 = new JLabel("实时轨道计算模式",JLabel.LEFT);
					JLabel rlabel3 = new JLabel("轨道信息存储模式",JLabel.LEFT);
					ButtonGroup g = new ButtonGroup();
				    JRadioButton	    rb1 = new JRadioButton("", false),
				    					rb2 = new JRadioButton("", false);
				    g.add(rb1);
				    g.add(rb2);
				    rlabel1.setBounds(10, 20, 200, 30);
				    rlabel2.setBounds(90, 55, 120, 30);
				    rb1.setBounds(215,55, 20, 30);
				    rlabel3.setBounds(90, 90, 120, 30);
				    rb2.setBounds(215,90, 20, 30);
				    
				    RouteSecond.add(rlabel1);
				    RouteSecond.add(rlabel2);
				    RouteSecond.add(rlabel3);
				    RouteSecond.add(rb1);
				    RouteSecond.add(rb2);
				    
				    
					JLabel rlabel21 = new JLabel("划分层数：",JLabel.LEFT);
					gridLayer = new JComboBox();
					String[] description = {
							    "1","2","3"
					};
					for(int i = 0; i < 3; i++)
						gridLayer.addItem(description[i]);
					rlabel21.setBounds(10, 130, 70, 30);
					gridLayer.setBounds(90, 130, 80, 30);
					RouteSecond.add(rlabel21);
					RouteSecond.add(gridLayer);
				    
				    RouteSecond.repaint();
				}
				else{
					RouteSecond.removeAll();
				    RouteSecond.repaint();
				}
			}
		});
		
		jp1.setLayout(null);
		RouteFirst.setBounds(10, 0, 330, 350);
		RouteSecond.setBounds(340, 0, 330, 350);
		jp1.add(RouteFirst);
		jp1.add(RouteSecond);
		
		return jp1;
	}
	
	public JPanel CacheSetting(){
		JPanel jp2 = new JPanel();
		//---------------------------设置缓存参数页面----------------------------//	
		JPanel CacheFirst = new JPanel();
		CacheFirst.setBorder(new TitledBorder("一级配置界面"));
		CacheFirst.setLayout(null);
		//	第一行
	    JLabel label1=new JLabel("逐跳确认",JLabel.LEFT);
	    JLabel label2=new JLabel("快速转发",JLabel.LEFT);
	    
	    ButtonGroup g = new ButtonGroup();
	    JRadioButton	    rb1 = new JRadioButton("", false),
	    					rb2 = new JRadioButton("", false);
	    rb1.setSelected(true);
	    g.add(rb1);
	    g.add(rb2);
	    
	    label1.setBounds(10, 20, 60, 30);
	    rb1.setBounds(70, 20, 20, 30);
	    label2.setBounds(110, 20, 60, 30);
	    rb2.setBounds(170, 20, 20, 30);
	    CacheFirst.add(label1);
	    CacheFirst.add(rb1);
	    CacheFirst.add(label2);
	    CacheFirst.add(rb2);
	    
	    // 第三行
	    JLabel label3=new JLabel("节点缓存大小：",JLabel.LEFT);
		JTextField txt3 = new JTextField("1000");
		JLabel label31 = new JLabel("M",JLabel.LEFT);		
		label3.setBounds(10, 60, 100, 30);
		txt3.setBounds(130,60, 130, 30);
		label31.setBounds(265, 60, 20, 30);
		CacheFirst.add(label3);
		CacheFirst.add(txt3);
		CacheFirst.add(label31);

	    
	    // 第四行
	    JLabel label4 = new JLabel("文件请求间隔：",JLabel.LEFT);
		JTextField txt4 = new JTextField("1");
		JLabel label41 = new JLabel("Min",JLabel.LEFT);

		label4.setBounds(10, 100, 100, 30);
		txt4.setBounds(130,100, 130, 30);
		label41.setBounds(265, 100, 20, 30);
		CacheFirst.add(label4);
		CacheFirst.add(txt4);
		CacheFirst.add(label41);
	    
	    // 第五行
	    JLabel label5=new JLabel("文件请求大小：",JLabel.LEFT);
		final JTextField txt5 = new JTextField("1");
		final JTextField txt51 = new JTextField("10");
		JLabel label51 = new JLabel("~",JLabel.LEFT);
		JLabel label52 = new JLabel("M",JLabel.LEFT);

		label5.setBounds(10, 140, 100, 30);
		txt5.setBounds(130, 140, 60, 30);
		label51.setBounds(190, 140, 10, 30);
		txt51.setBounds(200, 140, 60, 30);
		label52.setBounds(265, 140, 20, 30);
		CacheFirst.add(label5);
		CacheFirst.add(txt5);
		CacheFirst.add(label51);
		CacheFirst.add(txt51);
		CacheFirst.add(label52);
		
	    // 第六行
	    JLabel label6 = new JLabel("分片数目：",JLabel.LEFT);
		JTextField txt6 = new JTextField("10");
		JLabel label61 = new JLabel("个",JLabel.LEFT);
		label6.setBounds(10, 180, 100, 30);
		txt6.setBounds(130,180, 130, 30);
		label61.setBounds(265, 180, 20, 30);
		CacheFirst.add(label6);
		CacheFirst.add(txt6);
		CacheFirst.add(label61);

	    
		//---------------------------设置二级缓存参数页面----------------------------//			
		JPanel CacheSecond = new JPanel();
		CacheSecond.setBorder(new TitledBorder("二级配置界面"));
		
		jp2.setLayout(null);
		CacheFirst.setBounds(10, 0, 330, 350);
		CacheSecond.setBounds(340, 0, 330, 350);
		jp2.add(CacheFirst);
		jp2.add(CacheSecond);
		
		return jp2;
	}
	
	public JPanel MovementSetting(){
		JPanel jp3 = new JPanel();
		//JPanel jp = new JPanel();
		//---------------------------设置缓存参数页面----------------------------//	
		JPanel MovementFirst = new JPanel();
		MovementFirst.setBorder(new TitledBorder("一级配置界面"));
		//MovementFirst.setLayout(new GridLayout(8,1,10,10));
		MovementFirst.setLayout(null);
		
		//第一行
	    JLabel label1=new JLabel("星座配置：",JLabel.LEFT);
		JComboBox WalkerC = new JComboBox();
		String[] description = {
				    "Walker star", "Walker delta",
		};
		
	    for(int i = 0; i < 2; i++)
	    	WalkerC.addItem(description[i]);
	    
	    label1.setBounds(10, 25, 100, 30);
	    WalkerC.setBounds(130, 25, 120, 30);
	    MovementFirst.add(label1);
	    MovementFirst.add(WalkerC);

		
		//  第二行
	    JLabel label2=new JLabel("轨道模型：",JLabel.LEFT);
		JComboBox OrbitC = new JComboBox();
		String[] description1 = {
				    "TwoBody Model", "Perturbation model",
		};
		
	    for(int i = 0; i < 2; i++)
	    	OrbitC.addItem(description1[i]);
	    
	    label2.setBounds(10, 65, 100, 30);
	    OrbitC.setBounds(130, 65, 120, 30);
	    MovementFirst.add(label2);
	    MovementFirst.add(OrbitC);

		
		//  第三行
	    JLabel label3 = new JLabel("节点数目：",JLabel.LEFT);
		JTextField txt3 = new JTextField("50");
		JLabel label31 = new JLabel("个",JLabel.LEFT);
	    label3.setBounds(10, 105, 100, 30);
	    txt3.setBounds(130, 105, 120, 30);
	    label31.setBounds(255, 105, 20, 30);
	    MovementFirst.add(label3);
	    MovementFirst.add(txt3);
	    MovementFirst.add(label31);

		
		//---------------------------设置二级缓存参数页面----------------------------//			
		JPanel MovementSecond = new JPanel();
		MovementSecond.setLayout(null);
		MovementSecond.setBorder(new TitledBorder("二级配置界面"));
		
		//第一行 构型码M/N/P
		JLabel mslabel1 = new JLabel("构型码M/N/P：",JLabel.LEFT);
		JLabel mslabel11 = new JLabel("M-总的节点数：",JLabel.LEFT);
		this.totalNodes = new JTextField("30");
		JLabel mslabel12 = new JLabel("N-轨道平面数：",JLabel.LEFT);
		this.totalPlane = new JTextField("3");
		JLabel mslabel13 = new JLabel("P-相位因子：",JLabel.LEFT);
		this.phaseFactor = new JTextField("55");
		JLabel mslabel2 = new JLabel("轨道半径：",JLabel.LEFT);
		this.radius = new JTextField("9000");
		JLabel mslabel3 = new JLabel("离心率：",JLabel.LEFT);
		this.eccentricity = new JTextField("0");
		
		mslabel1.setBounds(10, 20, 100, 30);
		mslabel11.setBounds(35, 60, 100, 30);
		this.totalNodes.setBounds(145,60, 100, 30);
		mslabel12.setBounds(35, 100, 100, 30);
		this.totalPlane.setBounds(145, 100, 100, 30);
		mslabel13.setBounds(35, 140, 100, 30);
		this.phaseFactor.setBounds(145, 140, 100, 30);
		
		mslabel2.setBounds(10, 185, 100, 30);
		this.radius.setBounds(145, 185, 100, 30);
		mslabel3.setBounds(10, 225, 100, 30);
		this.eccentricity.setBounds(145, 225, 100, 30);
		
		MovementSecond.add(mslabel1);
		MovementSecond.add(mslabel11);
		MovementSecond.add(this.totalNodes);
		MovementSecond.add(mslabel12);
		MovementSecond.add(this.totalPlane);
		MovementSecond.add(mslabel13);
		MovementSecond.add(this.phaseFactor);
		MovementSecond.add(mslabel2);
		MovementSecond.add(this.radius);
		MovementSecond.add(mslabel3);
		MovementSecond.add(this.eccentricity);
		
		//jp3.setLayout(new GridLayout(1,2));
		jp3.setLayout(null);
		MovementFirst.setBounds(10, 0, 330, 350);
		MovementSecond.setBounds(340, 0, 330, 350);
		jp3.add(MovementFirst);
		jp3.add(MovementSecond);
		
		return jp3;
	}
	
	public JPanel LinkSetting(){
		JPanel jp4 = new JPanel();
		//---------------------------设置缓存参数页面----------------------------//	
		JPanel LinkFirst = new JPanel();
		LinkFirst.setBorder(new TitledBorder("一级配置界面"));
		LinkFirst.setLayout(null);
		
		
		//第一行
	    JLabel label1=new JLabel("链路模型：",JLabel.RIGHT);		
		JLabel label2 = new JLabel("激光头");
		JTextField text1 = new JTextField("1");
		JLabel label3 = new JLabel("微波天线");
		JTextField text2 = new JTextField("1");
		JLabel label4 = new JLabel("个");
		JLabel label5 = new JLabel("个");
		
		label1.setBounds(10, 20, 60, 30);
		label2.setBounds(100, 20, 60, 30);
		text1.setBounds(170, 20, 60, 30);
		label4.setBounds(240, 20, 20, 30);
		label3.setBounds(100, 60, 60, 30);
		text2.setBounds(170, 60, 60, 30);
		label5.setBounds(240, 60, 20, 30);
		
		LinkFirst.add(label1);
		LinkFirst.add(label2);
		LinkFirst.add(text1);
		LinkFirst.add(label3);
		LinkFirst.add(text2);
		LinkFirst.add(label4);
		LinkFirst.add(label5);
		
		//---------------------------设置二级缓存参数页面----------------------------//			
		JPanel LinkSecond = new JPanel();
		LinkSecond.setBorder(new TitledBorder("二级配置界面"));
		
		jp4.setLayout(null);
		LinkFirst.setBounds(10, 0, 330, 350);
		LinkSecond.setBounds(340, 0, 330, 350);
		jp4.add(LinkFirst);
		jp4.add(LinkSecond);
		
		return jp4;
	}
	
	public JPanel MainSetting(){
		JPanel jp5 = new JPanel();
		//---------------------------设置主体参数页面----------------------------//	
		JPanel LinkFirst = new JPanel();
		LinkFirst.setBorder(new TitledBorder("一级配置界面"));
		LinkFirst.setLayout(null);
		
		//第一行
	    JLabel label1 = new JLabel("三维边界：",JLabel.RIGHT);
	    worldSizeX = new JTextField("100000");
	    worldSizeY = new JTextField("100000");
	    worldSizeZ = new JTextField("100000");	    
		JLabel km = new JLabel("Km");

		label1.setBounds(0, 25, 80, 30);
		worldSizeX.setBounds(95, 25, 50, 30);
		worldSizeY.setBounds(155, 25, 50, 30);
		worldSizeZ.setBounds(215, 25, 50, 30);
		km.setBounds(270, 25, 20, 30);
		LinkFirst.add(label1);
		LinkFirst.add(worldSizeX);
		LinkFirst.add(worldSizeY);
		LinkFirst.add(worldSizeZ);
		LinkFirst.add(km);
		
		//第二行 仿真时间设置
		JLabel label2 = new JLabel("仿真时间：",JLabel.RIGHT);
		JLabel time = new JLabel("s");
		simTime = new JTextField("43200",JTextField.CENTER);
		simTime.addActionListener(this);
		label2.setBounds(0, 65, 80, 30);
		simTime.setBounds(95, 65, 170, 30);
		time.setBounds(270, 65, 20, 30);
		LinkFirst.add(label2);
		LinkFirst.add(simTime);
		LinkFirst.add(time);
		
		
		//第三行 仿真步长
		JLabel label3 = new JLabel("仿真步长：",JLabel.RIGHT);
		JLabel time2 = new JLabel("s");
		interval = new JTextField("0.1");
		label3.setBounds(0, 105, 80, 30);
		interval.setBounds(95, 105, 170, 30);
		time2.setBounds(270, 105, 20, 30);
		LinkFirst.add(label3);
		LinkFirst.add(interval);
		LinkFirst.add(time2);
		
		//第四行 预热时间
		JLabel label4 = new JLabel("预热时间：",JLabel.RIGHT);
		JLabel time3 = new JLabel("s");
		warmUp = new JTextField("1000");
		label4.setBounds(0, 145, 80, 30);
		warmUp.setBounds(95, 145, 170, 30);
		time3.setBounds(270, 145, 20, 30);
		LinkFirst.add(label4);
		LinkFirst.add(warmUp);
		LinkFirst.add(time3);
		
		
		//---------------------------设置二级缓存参数页面----------------------------//			
		JPanel LinkSecond = new JPanel();
		LinkSecond.setBorder(new TitledBorder("二级配置界面"));
		
		jp5.setLayout(null);
		LinkFirst.setBounds(10, 0, 330, 350);
		LinkSecond.setBounds(340, 0, 330, 350);
		jp5.add(LinkFirst);
		jp5.add(LinkSecond);
		
		
		return jp5;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.Confirm) {
			this.ParaSet();
			this.setVisible(false);
		}
		if (e.getSource() == this.Concel) {
			this.setVisible(false);
		}
		if (e.getSource() == this.Reset) {
			
		}
		
	}
	

	//--------------------------- 参数设置 --------------------------//
	public void ParaSet(){
		this.RouteParaSet();
		this.CacheParaSet();
		this.MoveParaSet();
		this.LinkParaSet();
	}

	private void LinkParaSet() {
		// TODO Auto-generated method stub
		
	}

	private void MoveParaSet() {
		// TODO Auto-generated method stub
		
	}

	private void CacheParaSet() {
		// TODO Auto-generated method stub
		
	}

	public void RouteParaSet(){
		Settings settings = new Settings();
		settings.setSetting("Group.router",String.valueOf(RouterC.getSelectedItem()));
		if (gridLayer != null)
			settings.setSetting("Group.layer",String.valueOf(gridLayer.getSelectedItem()));
		
		settings.setSetting("Group.bufferSize",String.valueOf(bufferSize.getText()));
		settings.setSetting("Interface.transmitSpeed",String.valueOf(transmissionRate.getText()) + "k");//kbps
		settings.setSetting("Interface.transmitRange",String.valueOf(transmissionRadius.getText()) + "k");//km
		
		settings.setSetting("Scenario.endTime",String.valueOf(simTime.getText()));
		settings.setSetting("Scenario.updateInterval",String.valueOf(interval.getText()));
		settings.setSetting("MovementModel.warmup",String.valueOf(warmUp.getText()));

		settings.setSetting("Group.nrofHosts",String.valueOf(totalNodes.getText()));
		settings.setSetting("userSetting.nrofPlane",String.valueOf(totalPlane.getText()));
		settings.setSetting("userSetting.phaseFactor",String.valueOf(phaseFactor.getText()));
		settings.setSetting("userSetting.radius",String.valueOf(radius.getText()));
		settings.setSetting("userSetting.eccentricity",String.valueOf(eccentricity.getText()));
		
		settings.setSetting("MovementModel.worldSize", String.valueOf(worldSizeX.getText()) + ", " + 
					String.valueOf(worldSizeY.getText()) + ", " + String.valueOf(worldSizeZ.getText()));
	}

}
