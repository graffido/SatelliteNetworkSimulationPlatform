package chartAnalysisWindow.src.chartWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class AddChartFrame {
	/** Default width for the GUI window */
	public static final int WIN_DEFAULT_WIDTH = 800;
	/** Default height for the GUI window */
	public static final int WIN_DEFAULT_HEIGHT = 480;
	private  JPanel  ButtonMenus;
	private  static JSplitPane JSP1;
	private String txtPath;
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private	JLabel label = new JLabel("图表类型");
    private	JLabel imageLabel = new JLabel("");
    private	JButton ExitButton = new JButton("退出");
    private	JComboBox comboBox;
    
    public AddChartFrame( String input){
    	
	    ButtonMenus = new JPanel();
	    ButtonMenus.setLayout(new BoxLayout(ButtonMenus, BoxLayout.X_AXIS));
        txtPath = input;
        SelectChart t =new SelectChart(this.txtPath,0);

		frame.setSize(WIN_DEFAULT_WIDTH,WIN_DEFAULT_HEIGHT);
        ExitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        comboBox = new JComboBox();
        comboBox.setSize(50, 30);
        comboBox.addItem("默认");
        comboBox.addItem("折线图");
        comboBox.addItem("柱状图");

        ButtonMenus.add(Box.createHorizontalStrut(10));
        ButtonMenus.add(label);
        ButtonMenus.add(Box.createHorizontalStrut(10));
        ButtonMenus.add(comboBox);
        ButtonMenus.add(Box.createHorizontalStrut(800));

        panel.add(imageLabel);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(ButtonMenus);
        frame.add(Box.createVerticalStrut(10));
        frame.add(panel);

        showChart();
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() ==ItemEvent.SELECTED){
                    SelectChart t =new SelectChart(txtPath,comboBox.getSelectedIndex());
                    showChart();
                }
            }
        });
        frame.setVisible(true);
    }
    public void showChart(){
        ImageIcon icon = new ImageIcon("analysis\\analysisChart.jpg");
        icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
        imageLabel.setHorizontalAlignment(0);
        imageLabel.setIcon(icon);
        frame.setVisible(true);
    }
}
