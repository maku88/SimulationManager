package SymulationManager;

import SymulationManager.manager.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 18.08.13
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public class SimluationCreator {


    public static List<Simulation> getSimulations(){
        List<Simulation> simList = new ArrayList<Simulation>();



        simList.add(getSimulation(1));
        simList.add(getSimulation(1));
        simList.add(getSimulation(2));
        simList.add(getSimulation(3));


        return simList;
    }

    private static Simulation getSimulation(int simulationCase) {

        Simulation simulation = new Simulation(simulationCase,1,1,1,"FAKE",10,10);

        Map<Integer,Long> results = new HashMap<Integer, Long>();


        for(int i = 1 ; i < 30 ; i ++) {
            results.put(i,200L + simulationCase - i);
        }


        simulation.addResults("sim1",results,21);

        simulation.addProxyStats(null);

        return simulation;
    }
}
