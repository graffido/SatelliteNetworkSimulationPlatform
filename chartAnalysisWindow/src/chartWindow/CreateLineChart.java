package chartAnalysisWindow.src.chartWindow;
/**
 * Created by ustc on 2016/12/8.
 */


/**
 * Created by ustc on 2016/12/8.
 */

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
public class CreateLineChart {

    public  Loadtxt load;

    public CreateLineChart(Loadtxt e){
        this.load = e;
        XYDataset dataset = createXYDataset(this.load);
        //步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
        JFreeChart freeChart = createChart(dataset,this.load);
        //步骤3：将JFreeChart对象输出到文件，Servlet输出流等
        saveAsFile(freeChart, "analysis\\analysisChart.jpg", 700, 400);
    }
    /**
     * 创建JFreeChart LineXY Chart（折线图）

     public static void main(String[] args) {
     //步骤1：创建XYDataset对象（准备数据）
     CreateJFreeChartXYline chart = new CreateJFreeChartXYline();
     }
     */
    // 保存为文件
    public static void saveAsFile(JFreeChart chart, String outputPath,
                                  int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);
            // 保存为PNG
            ChartUtilities.writeChartAsPNG(out, chart, weight, height);
            // 保存为JPEG
            // ChartUtilities.writeChartAsJPEG(out, chart, 500, 400);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    // 根据XYDataset创建JFreeChart对象
    public static JFreeChart createChart(XYDataset dataset,Loadtxt load) {
        // 创建JFreeChart对象：ChartFactory.createXYLineChart
        JFreeChart jfreechart = ChartFactory.createXYLineChart(load.TITLE, // 标题
                load.XLABEL, // categoryAxisLabel （category轴，横轴，X轴标签）
                load.YLABEL, // valueAxisLabel（value轴，纵轴，Y轴的标签）
                dataset, // dataset
                PlotOrientation.VERTICAL, true, // legend
                false, // tooltips
                false); // URLs

        // 使用CategoryPlot设置各种参数。以下设置可以省略。
       /* XYPlot plot = (XYPlot) jfreechart.getPlot();
        // 背景色 透明度
        plot.setBackgroundAlpha(0.5f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.5f);
        // 其它设置可以参考XYPlot类
        */

        XYPlot plot = (XYPlot) jfreechart.getPlot();
        //  XYBarRenderer renderer = (XYBarRenderer)plot.getRenderer();


        plot.setDomainGridlinePaint(Color.blue);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.blue);
        plot.setRangeGridlinesVisible(true);
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setOutlineVisible(true);
        plot.setOutlinePaint(Color.magenta);


        ValueAxis domainAxis = plot.getDomainAxis();
        NumberAxis domainAxis1 = (NumberAxis)plot.getDomainAxis();//x轴设置
        NumberAxis rAxis1 = (NumberAxis)plot.getRangeAxis();
        domainAxis1.setTickUnit(new NumberTickUnit(2));
        ValueAxis rAxis = plot.getRangeAxis();
        domainAxis.setTickLabelPaint(Color.red);//X轴的标题文字颜色
        domainAxis.setTickLabelsVisible(true);//X轴的标题文字是否显示
        domainAxis.setAxisLinePaint(Color.red);//X轴横线颜色
        domainAxis.setTickMarksVisible(true);//标记线是否显示
        domainAxis.setTickMarkOutsideLength(3);//标记线向外长度
        domainAxis.setTickMarkInsideLength(3);//标记线向内长度
        domainAxis.setTickMarkPaint(Color.red);//标记线颜色
        //domainAxis.setRange(5, 10);



        rAxis.setTickLabelPaint(Color.red);//Y轴的标题文字颜色
        rAxis.setTickLabelsVisible(true);//Y轴的标题文字是否显示
        rAxis.setAxisLinePaint(Color.red);//Y轴横线颜色
        rAxis.setTickMarksVisible(true);//标记线是否显示
        rAxis.setTickMarkOutsideLength(3);//标记线向外长度
        rAxis.setTickMarkInsideLength(3);//标记线向内长度
        rAxis.setTickMarkPaint(Color.red);//标记线颜色

        //设置距离图片左端距离
        domainAxis.setUpperMargin(0.2);
        //设置距离图片右端距离
        domainAxis.setLowerMargin(0.2);
        //数据轴精度
        NumberAxis na = (NumberAxis) plot.getRangeAxis();
        na.setAutoRangeIncludesZero(true);
        //  DecimalFormat df = new DecimalFormat("#0.000");
        //数据轴数据标签的显示格式
        //  na.setNumberFormatOverride(df);
        //设置柱的透明度
        plot.setForegroundAlpha(1.0f);

        if(load.Xmin != null && load.Xmax != null){

            domainAxis.setRange(Double.parseDouble(load.Xmin), Double.parseDouble(load.Xmax));
        }
        if(load.Ymin != null && load.Ymax != null){

            rAxis.setRange(Double.parseDouble(load.Ymin), Double.parseDouble(load.Ymax));
        }
        if(load.Xunit != null){
            domainAxis1.setTickUnit(new NumberTickUnit(Double.parseDouble(load.Xunit)));
        }
        if(load.Yunit != null){
            rAxis1.setTickUnit(new NumberTickUnit(Double.parseDouble(load.Yunit)));
        }

        return jfreechart;
    }

    /**
     * 创建XYDataset对象
     *
     */
    private static XYDataset createXYDataset(Loadtxt load) {
        XYSeries xyseries1 = new XYSeries("");
        double x =0;
        double y =0;
        for (int i=0;i<load.dataX.size();i++){
            // xyseries1.add(1987, 50);
            x =  Double.parseDouble(load.dataX.get(i));
            y =  Double.parseDouble(load.dataY.get(i));
            xyseries1.add(x, y);
        }

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();

        xySeriesCollection.addSeries(xyseries1);

        return xySeriesCollection;
    }
}
