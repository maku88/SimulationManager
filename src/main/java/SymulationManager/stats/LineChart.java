package SymulationManager.stats;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 14.08.13
 * Time: 01:12
 * To change this template use File | Settings | File Templates.
 */

import ProxyServer.stats.RequestStats;
import SymulationManager.manager.Simulation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.block.GridArrangement;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryStepRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Map;


public class LineChart {

    private String filePath = "";

    public LineChart(String filePath) {
        this.filePath = filePath;

    }


    public void createIntegerLongChart(Map<Integer, Long> inputMap, String chartDir, String chartName) throws IOException {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

        for(Integer key : inputMap.keySet()) {

            line_chart_dataset.addValue(inputMap.get(key),chartName,key);

        }

        createChart(line_chart_dataset,chartDir,chartName,0.0,800.0);
        saveResultToFile(inputMap,chartDir);

    }



    public void createDoubleIntegerChart(Map<Double,Integer> inputMap,String chartDir, String chartName) throws IOException {
        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();

        XYDataset dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("XYSeriesCollection", false, true); //use the allowDuplicateValues flag!

        for(Double d : inputMap.keySet()) {
//            defaultCategoryDataset.addValue(inputMap.get(d),chartName,d);
            series.add(d,inputMap.get(d));
        }

        ((XYSeriesCollection)dataset).addSeries(series);

        createXYChart(dataset, chartDir, "Prawdopodobieństwo","Liczba Znaczników", 0.0, 1.0);
        saveResultToFile(inputMap,chartDir);
    }

    public void createMultiSetIntLongChart(Map<Integer,Long> cacheSerise,Map<Integer,Long> serverSeries,Map<Integer,Long> totalSeries,String chartDir, String chartName, String xLabel, String yLabel) throws IOException {

        XYDataset dataset = new XYSeriesCollection();
//        XYSeries seriesCache = new XYSeries("średni czas odczytu cache", false, true); //use the allowDuplicateValues flag!
//
//        for(Integer i : cacheSerise.keySet()) {
//            seriesCache.add(i,cacheSerise.get(i));
//        }
//
//        ((XYSeriesCollection)dataset).addSeries(seriesCache);
//
//        XYSeries seriesServer = new XYSeries("średni czas odczytu server", false, true); //use the allowDuplicateValues flag!
//
//        for(Integer i : serverSeries.keySet()) {
//            seriesServer.add(i,serverSeries.get(i));
//        }
//
//        ((XYSeriesCollection)dataset).addSeries(seriesServer);


        XYSeries seriesTotal = new XYSeries("Średni czas odczytu znacznika", false, true); //use the allowDuplicateValues flag!

        for(Integer i : totalSeries.keySet()) {
            seriesTotal.add(i,totalSeries.get(i));
        }

        ((XYSeriesCollection)dataset).addSeries(seriesTotal);

        createAvgXYChart(dataset, chartDir, xLabel,yLabel, 0.0, 1000.0);
        saveResultToFile(totalSeries,chartDir);
    }


    public void createStringDoubleLineChart(Map<String, Double> inputMap, String chartDir, String chartName) throws IOException {
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();



        for(String key : inputMap.keySet()) {

            line_chart_dataset.addValue(inputMap.get(key),chartName,key);

        }

        createChart(line_chart_dataset,chartDir,chartName,-1.0,1.0);

    }

    private void createChart(DefaultCategoryDataset dataset, String chartDir, String chartName, double minY, double maxY) throws IOException {


    /* Step -2:Define the JFreeChart object to create line chart */
        JFreeChart lineChartObject= ChartFactory.createLineChart("",
                "Numer odczytu",
                "Czas odczytu [ms]",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategoryMargin(0.2);
//        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

//        lineChartObject.getCategoryPlot().setDomainAxis(xAxis);
        lineChartObject.getCategoryPlot().setBackgroundPaint(Color.white);

        //ustawienie lini pionowych
        lineChartObject.getCategoryPlot().setDomainGridlinesVisible(true);
        lineChartObject.getCategoryPlot().setDomainGridlinePaint(Color.BLACK);


        //ustawienie koloru lini poziomych
        lineChartObject.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);

        lineChartObject.getCategoryPlot().getRenderer().setSeriesPaint(0,Color.blue);

        lineChartObject.getCategoryPlot().getRangeAxis().setRange(minY,maxY);

        saveChartToImg(lineChartObject,chartDir);

    }


    private void createXYChart(XYDataset dataset, String chartDir, String xLabel,String yLabel, double minX, double maxX) throws IOException {

        NumberAxis domain = new NumberAxis();
        domain.setLabel(xLabel);
        domain.setRange(minX, maxX);
        domain.setTickUnit(new NumberTickUnit(0.1));
        domain.setVerticalTickLabels(false);


        ValueAxis yAxis = new NumberAxis(yLabel);
        XYItemRenderer renderer = new SamplingXYLineRenderer();

        XYPlot plot = new XYPlot(dataset, domain, yAxis, renderer);
        plot.getRenderer().setSeriesPaint(0,Color.blue);
        plot.setBackgroundPaint(Color.white);


        JFreeChart chart = new JFreeChart(plot);

        chart.setBackgroundPaint(Color.white);
        chart.getLegend().setVisible(true);

        saveChartToImg(chart,chartDir);

    }

    private void createAvgXYChart(XYDataset dataset, String chartDir, String xLabel,String yLabel, double minX, double maxX) throws IOException {

//        NumberAxis domain = new NumberAxis();
//        domain.setLabel(xLabel);

//        domain.setRange(minX, maxX);
//        domain.setTickUnit(new NumberTickUnit(0.1));
//        domain.setVerticalTickLabels(false);


//        ValueAxis yAxis = new NumberAxis(yLabel);
//        XYItemRenderer renderer = new SamplingXYLineRenderer();

//        XYPlot plot = new XYPlot(dataset, domain, yAxis, renderer);
//        plot.getRenderer().setSeriesPaint(0,Color.blue);
//        plot.getRenderer().setSeriesPaint(1,Color.red);
//        plot.getRenderer().setSeriesPaint(2,Color.green);
//        plot.setBackgroundPaint(Color.white);

//        JFreeChart chart = new JFreeChart(plot);

        JFreeChart chart = ChartFactory.createXYLineChart("",xLabel,yLabel,dataset,PlotOrientation.VERTICAL,true,false,false);
        chart.getXYPlot().setBackgroundPaint(Color.white);


        //ustawienie lini pionowych
        chart.getXYPlot().setDomainGridlinesVisible(true);
        chart.getXYPlot().setDomainGridlinePaint(Color.BLACK);


        //ustawienie koloru lini poziomych
        chart.getXYPlot().setRangeGridlinePaint(Color.BLACK);

        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.blue);
        saveChartToImg(chart, chartDir);
    }


    private void saveChartToImg(JFreeChart chart, String chartDir) throws IOException {
        int width=800; /* Width of the image */
        int height=400; /* Height of the image */
        File lineChart=new File(chartDir +".png");
        ChartUtilities.saveChartAsPNG(lineChart, chart, width, height);
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




