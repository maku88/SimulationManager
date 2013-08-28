package SymulationManager.stats;

import ProxyServer.stats.RequestStats;
import ProxyServer.stats.ResponseType;
import SymulationManager.manager.Simulation;
import SymulationManager.manager.TagReadStats;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 14.08.13
 * Time: 01:03
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsHandler {

    public void createCharts(List<Simulation> simulations, String chartDirectory, long simulationTimestamp) throws IOException {

        ListMultimap<Integer, Simulation.SimulatorResults> simulationCases = ArrayListMultimap.create();

        for(Simulation s : simulations) {
            Map<String,Simulation.SimulatorResults> resultsMap = s.getSimulatorResultsMap();
            simulationCases.putAll(s.getSimulationCase(), resultsMap.values());
        }

        LineChart chart = new LineChart("/");

        for(Integer simulationCase : simulationCases.keySet())  {

            List<Simulation.SimulatorResults> results = simulationCases.get(simulationCase);

            ListMultimap<Integer, Long> readTimeMap = ArrayListMultimap.create();

            for(Simulation.SimulatorResults singleSimulationResult : results) {

                for(Integer readNumber : singleSimulationResult.getAvgReadTime().keySet()) {

                    readTimeMap.put(readNumber,singleSimulationResult.getAvgReadTime().get(readNumber));

                }
            }

            Map<Integer,Long> avgReadTimeMap = new TreeMap<Integer, Long>();

            for(Integer readNumber : readTimeMap.keySet()) {

                long avgReadTime = 0;

                for(long readTime : readTimeMap.get(readNumber)) {
                    avgReadTime+=readTime;
                }

                avgReadTime = avgReadTime/readTimeMap.get(readNumber).size();

                avgReadTimeMap.put(readNumber,avgReadTime);

            }


            String simulationDefinistion = getSimulationCaseInfo(simulations,simulationCase);

            String chartName = getSimulationDescription(simulations, simulationCase);

            chart.createIntegerLongChart(avgReadTimeMap, chartDirectory + "\\wykres" + simulationDefinistion + "_" + simulationTimestamp, chartName);

        }


    }


    public void createHitRatioCharts(List<Simulation> simulations, String chartDirectory, long simulationTimestamp, GroupMode mode) throws IOException {

        ListMultimap<Integer, Simulation> groupedSimulations = ArrayListMultimap.create();

        for(Simulation s : simulations) {

            if(mode == GroupMode.SIZE) {
                groupedSimulations.put(s.getCacheSize(), s);
            }else if(mode == GroupMode.TTL ) {
                groupedSimulations.put(s.getTtl(), s);
            }
        }


        Map<Integer,Long> hitRatioMap = new TreeMap<Integer, Long>();


        for(Integer x : groupedSimulations.keySet()) {


            double cacheHits = 0;
            double serverHits = 0;

            for(Simulation s : groupedSimulations.get(x)) {

                for(RequestStats proxyStat : s.getProxyStats()) {

                    if(proxyStat.getResponseType() == ResponseType.CACHE ) {
                        cacheHits++;
                    }else if(proxyStat.getResponseType() == ResponseType.SERVER ) {
                        serverHits++;
                    }

                }
            }

            double sum = cacheHits+serverHits;
            double hitRatio = ( cacheHits  / sum  ) *100;

            hitRatioMap.put(x,(long)hitRatio);
        }



        BarChart chart = new BarChart("/");
//        String simulationDefinistion = getSimulationCaseInfo(simulations,simulationCase);

//        String chartName = getSimulationDescription(simulations, simulationCase);

        String xLabel = "";
        if(mode == GroupMode.SIZE) {
            xLabel = "Rozmiar cache [liczba znaczników] ";
        }
        if(mode == GroupMode.TTL)  {
            xLabel = "Time To Live [ms]";
        }

        chart.createChart(hitRatioMap, chartDirectory + "\\hitRatio_"+ mode + "_" + simulationTimestamp, "nazwa",xLabel);


    }


    public void createTagDistributionChart(List<TagReadStats> tags, String chartDirectory, long simulationTimestamp) throws IOException {

        ListMultimap<Double, String> tagDist = ArrayListMultimap.create();

        Map<Double, Integer> tagDistMap = new TreeMap<Double, Integer>();

        for(TagReadStats tag : tags) {

            if(! tagDist.containsValue(tag.getTagID())) {
                tagDist.put(tag.getProbability(),tag.getTagID());
            }

        }

        for(Double d : tagDist.keySet()) {

            tagDistMap.put(d,tagDist.get(d).size());
        }

        LineChart chart = new LineChart("/");
        chart.createDoubleIntegerChart(tagDistMap,chartDirectory+"\\wykres","aaa");
    }

    public void createAvgReadTimeFromProxy(List<Simulation> simulations,String chartDirectory, long simulationTimestamp, GroupMode mode) throws IOException {

        ListMultimap<Integer, Simulation> groupedSimulations = ArrayListMultimap.create();

        for(Simulation s : simulations) {

            if(mode == GroupMode.SIZE) {
                groupedSimulations.put(s.getCacheSize(), s);
            }else if(mode == GroupMode.TTL ) {
                groupedSimulations.put(s.getTtl(), s);
            }
        }
        Map<Integer,Long> cacheAvgTime = new TreeMap<Integer, Long>();
        Map<Integer,Long> serverAvgTime = new TreeMap<Integer, Long>();
        Map<Integer,Long> totalAvgTime = new TreeMap<Integer, Long>();


        for(Integer x : groupedSimulations.keySet()) {

            long cacheTotalReadTime = 0;
            long serverTotalReadTime = 0;
            long cacheReadCount =0;
            long serverReadCount=0;

            for(Simulation s : groupedSimulations.get(x)) {

                for(RequestStats proxyStat : s.getProxyStats()) {

                    if(proxyStat.getResponseType() == ResponseType.CACHE ) {
                        cacheReadCount++;
                        cacheTotalReadTime+=proxyStat.getDuration();
                    }else if(proxyStat.getResponseType() == ResponseType.SERVER ) {
                        serverReadCount++;
                        serverTotalReadTime+=proxyStat.getDuration();
                    }

                }
            }

            long avgCacheReadTime = cacheTotalReadTime/cacheReadCount;
            long avgServerReadTime = serverTotalReadTime/serverReadCount;
            long avgTotalReadTime = (cacheTotalReadTime+serverTotalReadTime)/(cacheReadCount+serverReadCount);

            cacheAvgTime.put(x,avgCacheReadTime);
            serverAvgTime.put(x,avgServerReadTime);
            totalAvgTime.put(x,avgTotalReadTime);
        }


        LineChart chart = new LineChart("/");

        String xLabel = "";
        if(mode == GroupMode.SIZE) {
            xLabel = "Rozmiar cache [liczba znaczników] ";
        }
        if(mode == GroupMode.TTL)  {
            xLabel = "Time To Live [ms]";
        }
        String yLabel = "Czas odczytu [ms]" ;

        chart.createMultiSetIntLongChart(cacheAvgTime,serverAvgTime,totalAvgTime, chartDirectory + "\\avgReadTime_"+ mode + "_" + simulationTimestamp, "nazwa",xLabel,yLabel);

    }


    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }


    private String getSimulationCaseInfo(List<Simulation> simulations, int simulationCase) {
        for(Simulation s : simulations ) {
            if(s.getSimulationCase() == simulationCase ) {
                return s.shortString();
            }

        }
        return null;
    }

    private String getSimulationDescription(List<Simulation> simulations, int simulationCase) {
        for(Simulation s : simulations ) {
            if(s.getSimulationCase() == simulationCase ) {
                return "Cache : " + s.getCacheType() + " TimeToLive : " + s.getTtl() + "ms Size : " + s.getCacheSize();
            }

        }
        return null;
    }




}
