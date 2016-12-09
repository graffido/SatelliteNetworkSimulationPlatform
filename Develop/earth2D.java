package Develop;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.font.*;
import java.awt.geom.*;

public class earth2D extends JApplet {
  public  void  init() {
    JFrame frame = new JFrame();
//    frame.setTitle("¶þÎ¬³¡¾°");
    JLabel label = new JLabel();
    String name = "C:\\Users\\YongqGui\\Workspaces\\MyEclipse 2015 CI\\JAVA3D\\images\\earth.png";
    label.setIcon(new ImageIcon(name));
    frame.add(label);
//    frame.setSize(480, 300);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setVisible(true);
  }
}
