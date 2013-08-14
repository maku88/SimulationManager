package SymulationManager.stats;

import SymulationManager.manager.Simulation;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 14.08.13
 * Time: 01:03
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsHandler {

    public void createCharts(List<Simulation> simulations) {

        ListMultimap<Integer, Simulation.SimulatorResults> simulationCases = ArrayListMultimap.create();



        for(Simulation s : simulations) {
            Map<String,Simulation.SimulatorResults> resultsMap = s.getSimulatorResultsMap();
            simulationCases.putAll(s.getSimulationCaseID(), resultsMap.values());
        }

        System.out.println(simulationCases.size());



//        LineChart chart = new LineChart("/");
//        chart.createLineChart(,"wykres");



    }





}
