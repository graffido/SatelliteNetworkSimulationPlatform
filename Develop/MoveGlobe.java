package Develop;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.vecmath.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
public class MoveGlobe extends Applet {
	
//	public static void main(String[] args) {
//		new MainFrame(new MoveGlobe(), 480, 480);
//	}

	public void init() {
    // create canvas
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D cv = new Canvas3D(gc);
		setLayout(new BorderLayout());
		add(cv, BorderLayout.CENTER);
		BranchGroup bg = createSceneGraph();
		bg.compile();
		SimpleUniverse su = new SimpleUniverse(cv);
		su.getViewingPlatform().setNominalViewingTransform();
		su.addBranchGraph(bg);
	}
  
	private BranchGroup createSceneGraph() {
		BranchGroup root = new BranchGroup();
		// axes
		Transform3D tr = new Transform3D();
		tr.setScale(0.5);
		tr.setTranslation(new Vector3d(-0.8, -0.7, -0.5));
		TransformGroup tg = new TransformGroup(tr);
		root.addChild(tg);
		Axes axes = new Axes();
		//tg.addChild(axes);
		
		// transform
		TransformGroup spin = new TransformGroup();
		spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		spin.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		root.addChild(spin);
		// texture mapped globe
		Appearance ap = createAppearance();
		spin.addChild(new Sphere(0.4f, Primitive.GENERATE_TEXTURE_COORDS, 50, ap));			//这是创建一个球体

		//	添加轨道
	    Transform3D tr1 = new Transform3D();						//Transform3D()表示一个4X4的双精度浮点数矩阵
	    tr.setScale(0.8);
	    tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI/2));
	    TransformGroup tg1 = new TransformGroup(tr1);
	    spin.addChild(tg1);
	    
	    Shape3D torus1 = new Torus(0.003, 0.6);
	    Appearance ap1 = new Appearance();
	    ap1.setMaterial(new Material());
	    torus1.setAppearance(ap1);
	    tg1.addChild(torus1);
		
	    Shape3D torus2 = new Torus(0.003, 0.6);
	    ap1.setMaterial(new Material());
	    ap1.setTransparencyAttributes(
	    	      new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.5f));
	    torus2.setAppearance(ap1);
	    Transform3D tr2 = new Transform3D();
	    tr2.setRotation(new AxisAngle4d(1, 0, 0, Math.PI/2));		//用于旋转
	    tr2.setTranslation(new Vector3d(0,0,0));					//setTranslation设置平移变换矩阵
	    TransformGroup tg2 = new TransformGroup(tr2);
	    tg1.addChild(tg2);
	    tg2.addChild(torus2);
		
		// rotation
		MouseRotate rotator = new MouseRotate(spin);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		spin.addChild(rotator);
		// translation
		MouseTranslate translator = new MouseTranslate(spin);
		translator.setSchedulingBounds(bounds);
		spin.addChild(translator);
		// zoom
		MouseZoom zoom = new MouseZoom(spin);
		zoom.setSchedulingBounds(bounds);
		spin.addChild(zoom);
		//light
		AmbientLight light = new AmbientLight(true, new Color3f(Color.blue));
		light.setInfluencingBounds(bounds);
		root.addChild(light);
		PointLight ptlight = new PointLight(new Color3f(Color.white),
				new Point3f(0f,0f,2f), new Point3f(1f,0.3f,0f));
		ptlight.setInfluencingBounds(bounds);
		root.addChild(ptlight);
		//background
		Background background = new Background(0.1f, 0.1f, 0.1f);
		background.setApplicationBounds(bounds);
		root.addChild(background);
		return root;
	}
  
	Appearance createAppearance(){    
		Appearance appear = new Appearance();    
		String filename = "Develop\\earth1.png";
		TextureLoader loader = new TextureLoader(filename, this);
		ImageComponent2D image = loader.getImage();    
		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
				image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);    
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		appear.setTexture(texture);
		return appear;
	}
}
