package SymulationManager.stats;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 14.08.13
 * Time: 01:12
 * To change this template use File | Settings | File Templates.
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class LineChart {

    private String filePath = "";

    public LineChart(String filePath) {
        this.filePath = filePath;

    }


    public void createLineChart(Map<Integer, Long> inputMap, String chartDir, String chartName) throws IOException {

        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        for(Integer key : inputMap.keySet()) {

            line_chart_dataset.addValue(inputMap.get(key),"Średni czas odczytu",key);

        }


    /* Step -2:Define the JFreeChart object to create line chart */
        JFreeChart lineChartObject= ChartFactory.createLineChart(chartName,
                "Odczyt",
                "Czas odczytu [ms]",
                line_chart_dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);





        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategoryMargin(0.2);
//        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        lineChartObject.getCategoryPlot().setDomainAxis(xAxis);
        lineChartObject.getCategoryPlot().setBackgroundPaint(Color.white);

        //ustawienie lini pionowych
        lineChartObject.getCategoryPlot().setDomainGridlinesVisible(true);
        lineChartObject.getCategoryPlot().setDomainGridlinePaint(Color.BLACK);


        //ustawienie koloru lini poziomych
        lineChartObject.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);

        lineChartObject.getCategoryPlot().getRenderer().setSeriesPaint(0,Color.blue);




    /* Step -3 : Write line chart to a file */
        int width=1000; /* Width of the image */
        int height=400; /* Height of the image */
        File lineChart=new File(chartDir +".png");
        ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, width, height);

    }


}

