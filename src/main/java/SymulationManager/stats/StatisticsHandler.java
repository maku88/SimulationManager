package SymulationManager.stats;

import SymulationManager.manager.Simulation;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.IOException;
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

            chart.createLineChart(avgReadTimeMap,chartDirectory + "\\wykres" +simulationDefinistion +"_"+simulationTimestamp , "");

        }


    }


    private String getSimulationCaseInfo(List<Simulation> simulations, int simulationCase) {
        for(Simulation s : simulations ) {
            if(s.getSimulationCase() == simulationCase ) {
                return s.shortString();
            }

        }
        return null;
    }




}
