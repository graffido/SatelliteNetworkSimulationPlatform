package chartAnalysisWindow.src.chartWindow;

/**
 * Created by ustc on 2016/12/8.
 */
public class SelectChart {
    public Loadtxt load;

    public SelectChart(String FilePath, int i) {

        this.load = new Loadtxt(FilePath);
        String type = this.load.ChartType;
        switch (i) {
            case 0: {
                if (type == null || type == "line") {

                    CreateLineChart chart = new CreateLineChart(this.load);
                } else {
                    CreateBarChart chart = new CreateBarChart(this.load);
                }
                break;
            }

            case 1:
                CreateLineChart chart = new CreateLineChart(this.load);
                break;
            case 2:
                CreateBarChart chart2 = new CreateBarChart(this.load);
                break;

        }

    }

}
