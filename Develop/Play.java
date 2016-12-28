package Develop;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import java.awt.font.*;
import java.awt.geom.*;
import java.io.*;
import java.net.URL;

import javax.imageio.*;

public class Play extends JApplet {
	double[][][] BL;
	PaintPanel panel;
	int number;
	
	public Play(double[][][] BL,int number) {
		this.number = number;
		this.BL = new double[number][200][2];
		this.BL = BL;
	}
	public void init() {
		panel = new PaintPanel(BL,number);
		getContentPane().add(panel);
	}
	
	public PaintPanel getJP() {
		return this.panel;
	}
	
	public void setFlag(boolean flag) {
		panel.setFlag(flag);
	}
	public void zoom(int width,int height) {
		panel.zoom(width, height);
	}
}

class PaintPanel extends JPanel implements Runnable{
	private ImageIcon image;
	double[][][]BL;
	int number;
	int step = 0;
	boolean flag;
	private static double WIDTH;
	private static double HEIGHT;
	private static int INCREMENT;
	
	public PaintPanel(double[][][] BL,int number) {
		this.number = number;
		this.BL = new double[number][200][2];
		this.BL = BL;
		this.flag = true;
		setPreferredSize(new Dimension(500,500));
		setBackground(Color.white);
		image = new ImageIcon(getClass().getClassLoader().getResource("images/earth4.jpg"));// Icon由图片文件形成
	/*	URL url = getClass().getClassLoader().getResource("images/earth4.jpg");
		try {
			image = ImageIO.read(url);
		} catch (IOException ex) {
			ex.printStackTrace();
		}*/
		WIDTH = image.getIconWidth();
		HEIGHT = image.getIconHeight();
		INCREMENT = 20;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
	//	g2d.drawImage(image,10,10,this);
		image.paintIcon(this,g2d,10,10);
		
		g2d.drawLine(INCREMENT, (int)HEIGHT/2, (int)WIDTH-INCREMENT, (int)HEIGHT/2);
		g2d.drawLine((int)WIDTH-INCREMENT, (int)HEIGHT/2, (int)WIDTH-10, (int)HEIGHT/2-5);
		g2d.drawLine((int)WIDTH-INCREMENT, (int)HEIGHT/2, (int)WIDTH-10, (int)HEIGHT/2+5);
		
		g2d.drawLine((int)WIDTH/2, 40, (int)WIDTH/2, (int)HEIGHT-50);
		g2d.drawLine((int)WIDTH/2, 40, (int)WIDTH/2-10, 50);
		g2d.drawLine((int)WIDTH/2, 40, (int)WIDTH/2+10, 50);
		
		g2d.translate((int) WIDTH / 2, (int) HEIGHT / 2);

		
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0,0);
		for(int order=0;order<number;order++) {
			drawOrbit(order,gp, g2d,BL[order]);
		}
	}
	
	public double Lagrange(double x[],double y[],double value) {
		double sum = 0;
		double L;
		for(int i=0;i<x.length;i++) {
			L = 1;
			for(int j = 0;j<x.length;j++) {
				if(j!=i) {
					L = L*(value-x[j])/(x[i]-x[j]);
				}
			}
			sum = sum+L*y[i];
		}
		return sum;
	}
	
	private void drawOrbit(int order,GeneralPath gp, Graphics2D g2d,double[][] BL) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		double[] X = new double[50];
		double[] Y = new double[50];
		for(int i=0;i<50;++i) {
			X[i] = BL[i][0];
			Y[i] = BL[i][1];
			}
		for(int i=0;i<X.length;i++) {
			if(X[i]>max) max = X[i];
			if(X[i]<min) min = X[i];
		}
			g2d.setColor(Color.GREEN);
			for(double x = min;x<=max;x = x+0.8) {
				g2d.drawLine((int)(((double)WIDTH/430.0)*x),(int)(((double)HEIGHT/358.0)*Lagrange(X,Y,x)),
				                  (int)(((double)WIDTH/430.0)*x),(int)(((double)HEIGHT/358.0)*Lagrange(X,Y,x)));
			}
			
			g2d.setColor(Color.RED);
			g2d.fillOval(((int)(((double)WIDTH/430.0)*BL[step][0]))-5,((int)(((double)HEIGHT/358.0)*BL[step][1]))-5,10,10);
	}
	
	public void run() {
		while(true) {
			if(flag == true) {
				try {
					Thread.sleep(600);
			    }
			    catch(InterruptedException e) {
				    //do nothing
			    }
			    step+=1;
				this.repaint();
				if(step>=50/*this.number*/) {
					step = 0;
				}
			}
			else{
				while(flag == false){			// 界面等待确定配置参数
					try {
						 synchronized (this){
							wait(10);
						 }
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public void zoom(int width,int height) {
		this.image.setImage(image.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
		this.WIDTH = width;
		this.HEIGHT = height;
	}
}