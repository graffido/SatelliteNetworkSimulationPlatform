package Develop;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.applet.*;

//java3D
import javax.vecmath.*;
import javax.media.j3d.*;

import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.vp.*;

import core.DTNHost;

//3d data
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//import jat.core.plot.plot.Plot3DPanel;
import satellite_orbit.Printable;
import satellite_orbit.TwoBody;
//3d data

public class moveEarth extends Applet {
	List<DTNHost> hosts;
	
/*	static Point3f[] Point = new Point3f[200];
	Point3f[] Point1 = new Point3f[200];
	Point3f[] Point2 = new Point3f[200];*/
	
//	public static void main(String[] args) {
//	//	new MainFrame(new moveEarth(),640,360);
//	//	new earth2D(Point);
//	}
	/**
	*Create 3D interface
	*/
	public void init(List<DTNHost> hosts) {
		GraphicsConfiguration gc =
				SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv,BorderLayout.CENTER);
		BranchGroup root = new BranchGroup();
		//创建坐标轴
		Transform3D tr = new Transform3D();
		tr.setScale(0.5);
		tr.setTranslation(new Vector3d(0,0,0));
		TransformGroup tg = new TransformGroup(tr);
		root.addChild(tg);
	/*	Axes axes = new Axes();
		tg.addChild(axes);*/
		//创建坐标轴
		
		//创建纹理
		Appearance ap = createAppearance();
		root.addChild(new Sphere(0.4f,
		Primitive.GENERATE_TEXTURE_COORDS,50,ap));
		BoundingSphere bounds = new BoundingSphere();
		//创建纹理
		
		// 创建灯光
		AmbientLight light = new AmbientLight(true,
		new Color3f(Color.blue));
		light.setInfluencingBounds(bounds);
		root.addChild(light);
		PointLight ptlight = new PointLight(new Color3f(Color.white),
		new Point3f(0f,0f,2f),new Point3f(1f,0.3f,0f));
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);
		// 创建灯光
		
		//测试Walker星座
		this.hosts = new ArrayList<DTNHost>(hosts);
		for(int order = 0; order < this.hosts.size(); order++) {
			double[] orbitParameters = this.hosts.get(order).getParameters();
			drawLine drawline = new drawLine(orbitParameters[0],orbitParameters[1],
					orbitParameters[2],orbitParameters[3],orbitParameters[4],orbitParameters[5]);
			Point3f point = drawline.getPoint(0);
		    Shape3D drawpoint = new drawPoint(point);
		    tg.addChild(drawline);
		    tg.addChild(drawpoint);
		 //   Point = drawline.getPoints();
		}
		//测试Walker星座
			
		//添加单个轨道和卫星节点
	/*	drawLine drawline = new drawLine(8000,0.1,15,0,0,0);
		Point3f point = drawline.getPoint(0);
		System.out.println("test"+" "+point.x+" "+point.y+" "+point.z);
		Shape3D drawpoint = new drawPoint(point);
		tg.addChild(drawline);
		tg.addChild(drawpoint);
		Point = drawline.getPoints();
		
		drawLine drawline1 = new drawLine(8000,0.2,20,0,0,0);
		Point3f point1 = drawline1.getPoint(60);
		System.out.println("test1"+" "+point1.x+" "+point1.y+" "+point1.z);
		Shape3D drawpoint1 = new drawPoint(point1);
		tg.addChild(drawline1);
		tg.addChild(drawpoint1);
		Point1 = drawline1.getPoints();
		
		drawLine drawline2 = new drawLine(8000,0.1,40,0,0,0);
		Point3f point2 = drawline2.getPoint(25);
		System.out.println("test2"+" "+point2.x+" "+point2.y+" "+point2.z);
		Shape3D drawpoint2 = new drawPoint(point2);
		tg.addChild(drawline2);
		tg.addChild(drawpoint2);
		Point2 = drawline2.getPoints();*/
		//添加单个轨道和卫星节点
		
		//添加旋转
	/*	Alpha alpha = new Alpha(-1,6000);
		RotationInterpolator rotator = 
		new RotationInterpolator(alpha,tg);
		rotator.setSchedulingBounds(bounds);
		tg.addChild(rotator);*/
		//添加旋转
		
		//设置背景
		Color3f bgColor = new Color3f(0.0f,0.0f,0.0f);
		Background background =/* createBackground();*/new Background(bgColor);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		root.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		//设置背景
		
		//创建并且使用OrbitBehavior
		OrbitBehavior orbit = new OrbitBehavior(cv);
		orbit.setSchedulingBounds(new BoundingSphere());
		su.getViewingPlatform().setViewPlatformBehavior(orbit);
		su.addBranchGraph(root);
		//创建并且使用OrbitBehavior
	}
	// 创建外观
	Appearance createAppearance() {
		Appearance appear = new Appearance();
		URL filename =
				getClass().getClassLoader().getResource("images/earth3.jpg");
		TextureLoader loader = new TextureLoader(filename,this);
		Texture texture = loader.getTexture();
		appear.setTexture(texture);
		return appear;
	}
	// 创建外观
	
	//创建背景
	Background createBackground() {
		Background background = new Background();
		BranchGroup bg = new BranchGroup();
		Sphere sphere = new Sphere(1.0f,Sphere.GENERATE_NORMALS |
		Sphere.GENERATE_NORMALS_INWARD |
		Sphere.GENERATE_TEXTURE_COORDS,60);
		Appearance ap = sphere.getAppearance();
		bg.addChild(sphere);
		background.setGeometry(bg);
		
		URL filename =
				getClass().getClassLoader().getResource("images/background2.jpg");
		TextureLoader loader = new TextureLoader(filename,this);
		Texture texture = loader.getTexture();
		ap.setTexture(texture);
		return background;	
	}
	//创建背景
}

/**
 * Copyright(C),2016-2020,USTC.
 * ClassName:drawPoint
 * draw satellite's orbit
 * 
 * @author XiJianXu
 * @Date 2016-12-05
 * @version 1.0
 */
class drawPoint extends Shape3D {
	/**
	@param point get satellte's initial coordinate
	*/
	public drawPoint(Point3f point) {
		Point3f[] vertexes0=new Point3f[1];
        Color3f[] pointcolors0=new Color3f[1];
		vertexes0[0]=point;
        pointcolors0[0]=new Color3f(0.1f,0.2f,0.4f);
		int vCount=1;
        PointArray points=new PointArray(vCount,PointArray.COORDINATES|IndexedPointArray.COLOR_3);
        points.setCoordinates(0,vertexes0);
        points.setColors(0,pointcolors0);
        PointAttributes pointsattributes=new PointAttributes();
        pointsattributes.setPointSize(15.0f);
        pointsattributes.setPointAntialiasingEnable(true);
        Appearance app=new Appearance();
        app.setPointAttributes(pointsattributes);
        this.setGeometry(points);
        this.setAppearance(app);
	}
}

class drawLine extends Shape3D implements Printable{
	
	/**卫星轨道参数*/
	public double a; 
	double e;
	double i;
	double raan;
	double w;
	double ta;
	/**卫星轨道参数*/
	double max;
	int step;
	//int order;
	
	/**卫星轨道坐标*/
	int steps = 200;
	double[][] XYZ = new double[steps][3];
    double[][] points = new double[1][3];
	
	Point3f[] vertexes = new Point3f[200];
	/**卫星轨道坐标*/
	
	/**
	* @param a semi-major axis
	* @param e eccentricity
	* @param i inclination in degrees
	* @param raan right ascension of ascending node in degrees
	* @param w argument of perigee in degrees
	* @param ta true anomaly in degrees
	*/
	public drawLine(double a,double e,double i,
	             double raan,double w,double ta/*int order*/) {
					 this.a = a;
					 this.e = e;
					 this.i = i;
					 this.raan = raan;
					 this.w = w;
					 this.ta = ta;
					 this.max = 0;
					 this.step = 0;
				//	 this.order = order;
					 TwoBody sat = new TwoBody(a, e, i, raan, w, ta);
					 double period = sat.period();
		             double tf = period;
					 double t0 = 0.0;
		             sat.setSteps(steps);
	                 sat.propagate(t0, tf, this, true);
					 points=new double[1][3];
					 points[0][0]=sat.rv.x[0];
					 points[0][1]=sat.rv.x[1];
					 points[0][2]=sat.rv.x[2];
					 
					 for(int m=0;m<200;m++) vertexes[m]=new Point3f();
					 for(int m=0;m<200;m++) {
						 vertexes[m].x = (float)XYZ[m][0]/34000/*16000*3*/;
						 vertexes[m].y = (float)XYZ[m][1]/34000/*8000/3*/;
						 vertexes[m].z = (float)XYZ[m][2]/34000/*8000*4*/;
					 }
	
	Random rm = new Random();
	Color3f[] colors=new Color3f[200];
    for(int m=0;m<200;m++) colors[m]=new Color3f((float)rm.nextDouble(),(float)rm.nextDouble(),(float)rm.nextDouble()/*0.1f,0.4f,0.6f*/);
    LineArray lines=new LineArray(200,LineArray.COORDINATES|LineArray.COLOR_3);
    lines.setCoordinates (0,vertexes);
    lines.setColors(0,colors);

    LineAttributes lineattributes=new LineAttributes();
    lineattributes.setLineWidth(1.0f);
	lineattributes.setLineAntialiasingEnable(true);
    lineattributes.setLinePattern(0);

    Appearance app=new Appearance();
    app.setLineAttributes(lineattributes);
    this.setGeometry(lines);
    this.setAppearance(app);
	}
	/**
	*@param k the order of satellite
	*/
	public Point3f getPoint(int k) {
		return this.vertexes[k];
	}
	
	public Point3f[] getPoints() {
		return this.vertexes;
	}
	
	/**得到运动模型产生的200的三维坐标函数，实现了Printable*/
	public void print(double t, double[] y) {
		if (step < XYZ.length) {
			XYZ[step][0] = y[0];
			XYZ[step][1] = y[1];
			XYZ[step][2] = y[2];
			if (y[0] > max)
				max = y[0];
			if (y[1] > max)
				max = y[1];
			if (y[2] > max)
				max = y[2];
			step++;
		}
	}
	/**得到运动模型产生的200的三维坐标函数，实现了Printable*/
	
	public void print1(double t, double[] y) {
		
	}
	
	public void print2(double t, double[] y) {
		
	}
}