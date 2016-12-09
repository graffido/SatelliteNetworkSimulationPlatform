//所以这个用不到了？？


package chartAnalysisWindow.src.chartWindow;
/**
 * Created by ustc on 2016/12/8.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class AnalysisWindow {

    private  JFrame frame = new JFrame();
    private  JPanel panel  = new JPanel();
    private  Container content = frame.getContentPane();
    private  JToolBar toolbar = new JToolBar();
    private  JButton OpenButton =new JButton("文件");
    private  JButton ExitButton = new JButton("取消");
    private  JPanel  ButtonMenus;
	private  static JSplitPane JSP1;
	
    public AnalysisWindow(){
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dim.width/5,dim.height/5,dim.width/2,dim.height/2);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
	    ButtonMenus = new JPanel();
	    ButtonMenus.setLayout(new BoxLayout(ButtonMenus, BoxLayout.X_AXIS));
	    
        OpenButton.addActionListener(new OpenActionListener());
        ExitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //System.exit(0);
            	frame.setVisible(false);
            }
        });
        ButtonMenus.add(OpenButton);
        ButtonMenus.add(ExitButton);

	    JSP1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,false,ButtonMenus,panel);
	    JSP1.setResizeWeight(0.01);
        content.add(JSP1);
        frame.setVisible(true);
    }
    class OpenActionListener implements ActionListener{

        public void  actionPerformed(ActionEvent e){
           // JFileChooser fileChooser = new JFileChooser("F://娱乐//picture//");
            JFileChooser fileChooser = new JFileChooser("analysis//");
            fileChooser.setDialogTitle("选择分析文件");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            fileChooser.setFileFilter(filter);
            JLabel label = new JLabel();
            int n = fileChooser.showOpenDialog(fileChooser);
            if (n == fileChooser.APPROVE_OPTION){
                String input =fileChooser.getSelectedFile().getPath();
            //    System.out.print(input +"\n");// cancel------// ---------------------------\
                new AddChartFrame(input);
            }
            else
                label.setText("未选择");
           
             panel.removeAll();
            panel.add(label);
            content.add(panel);
            panel.updateUI();
            frame.repaint();
        }
    }

//    public static void main(String[] args) {
//        new AnalysisWindow();
//    }
}
