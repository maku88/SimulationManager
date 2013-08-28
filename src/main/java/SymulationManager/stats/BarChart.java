package SymulationManager.stats;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 25.08.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class BarChart {

    private String filePath = "";

    public BarChart(String filePath) {
        this.filePath = filePath;

    }


    public void createChart(Map<Integer, Long> inputMap, String chartDir, String chartName, String xLabel) throws IOException {

        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        for(Integer key : inputMap.keySet()) {

            line_chart_dataset.addValue(inputMap.get(key),chartName,key);

        }


        saveResultToFile(inputMap,chartDir);

    /* Step -2:Define the JFreeChart object to create line chart */
        JFreeChart lineChartObject= ChartFactory.createBarChart("",
                xLabel,
                "Hit Ratio [ % ]",
                line_chart_dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);





//        CategoryAxis xAxis = new CategoryAxis();
//        xAxis.setCategoryMargin(0.2);
//        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

//        lineChartObject.getCategoryPlot().setDomainAxis(xAxis);
        lineChartObject.getCategoryPlot().setBackgroundPaint(Color.white);
//
        //ustawienie lini pionowych
        lineChartObject.getCategoryPlot().setDomainGridlinesVisible(true);
        lineChartObject.getCategoryPlot().setDomainGridlinePaint(Color.BLACK);


        //ustawienie koloru lini poziomych
        lineChartObject.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);

        lineChartObject.getCategoryPlot().getRenderer().setSeriesPaint(0,Color.blue);

        lineChartObject.getCategoryPlot().getRangeAxis().setRange(0.0,100.0);

        final CategoryPlot plot = lineChartObject.getCategoryPlot();
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
        ((BarRenderer) plot.getRenderer()).setMaximumBarWidth(.05);

        lineChartObject.getLegend().setVisible(false);




    /* Step -3 : Write line chart to a file */
        int width=600; /* Width of the image */
        int height=300; /* Height of the image */
        File lineChart=new File(chartDir +".png");
        ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, width, height);

    }

    private void saveResultToFile(Map inputMap, String dir) {

        try {
            PrintWriter writer = new PrintWriter(dir+".txt", "UTF-8");

            writer.println(dir);
            for(Object key : inputMap.keySet()) {
                String line = key.toString() + ";" + inputMap.get(key);
                writer.println(line);
            }
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
