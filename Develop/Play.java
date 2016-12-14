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
//	public static void main(String s[]) {
	/*	JFrame frame = new JFrame();
		frame.setTitle("earth 2D");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JApplet applet = new Play();
		applet.init();
		frame.getContentPane().add(applet);
		frame.pack();
		frame.setVisible(true);*/
//	}
	public Play(double[][][] BL) {
		this.BL = BL;
	}
	public void init() {
		JPanel panel = new PaintPanel(BL);
		getContentPane().add(panel);
	}
	
}

class PaintPanel extends JPanel {
	private BufferedImage image;
	double[][][]BL;
	private static double WIDTH;
	private static double HEIGHT;
	private static int INCREMENT;
	
	public PaintPanel(double[][][] BL) {
		this.BL = BL;
		setPreferredSize(new Dimension(500,500));
		setBackground(Color.white);
		URL url = getClass().getClassLoader().getResource("images/earth4.jpg");
		try {
			image = ImageIO.read(url);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		WIDTH = image.getWidth();
		HEIGHT = image.getHeight();
		INCREMENT = 20;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image,10,10,this);
		
		g2d.drawLine(INCREMENT, (int)HEIGHT/2, (int)WIDTH-INCREMENT, (int)HEIGHT/2);
		g2d.drawLine((int)WIDTH-INCREMENT, (int)HEIGHT/2, (int)WIDTH-10, (int)HEIGHT/2-5);
		g2d.drawLine((int)WIDTH-INCREMENT, (int)HEIGHT/2, (int)WIDTH-10, (int)HEIGHT/2+5);
		
		g2d.drawLine((int)WIDTH/2, 40, (int)WIDTH/2, (int)HEIGHT-50);
		g2d.drawLine((int)WIDTH/2, 40, (int)WIDTH/2-10, 50);
		g2d.drawLine((int)WIDTH/2, 40, (int)WIDTH/2+10, 50);
		
		g2d.translate((int) WIDTH / 2, (int) HEIGHT / 2);

		
		GeneralPath gp = new GeneralPath();
		gp.moveTo(0,0);
		for(int order=0;order<24;order++) {
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
		double[] X = new double[25];
		double[] Y = new double[25];
		for(int i=0;i<25;++i) {
			X[i] = BL[i][0];
			Y[i] = BL[i][1];
		//	gp.lineTo(1.2*BL[i][0],1.2*BL[i][1]);
		//    gp.quadTo(1.3*BL[i-1][0],1.5*BL[i-1][1],1.3*BL[i][0],1.5*BL[i][1]);
			}
		for(int i=0;i<X.length;i++) {
			if(X[i]>max) max = X[i];
			if(X[i]<min) min = X[i];
		}
			g2d.setColor(Color.GREEN);
			for(double x = min;x<=max;x = x+0.1) {
				g2d.drawLine((int)(1.2*x),(int)(1.5*Lagrange(X,Y,x)),(int)(1.2*x),(int)(1.5*Lagrange(X,Y,x)));
			}
		/*	if((order>=0)&&(order<8)) {
				g2d.setColor(Color.GREEN);
			}
			if((order>=8)&&(order<16)) {
				g2d.setColor(Color.RED);
			}
			if((order>=16)&&(order<24)) {
				g2d.setColor(Color.RED);
			}*/
			g2d.draw(gp);
			
			g2d.setColor(Color.RED);
			g2d.fillOval((int)(1.2*BL[0][0]),(int)(1.5*BL[0][1]),10,10);
	}
	
}