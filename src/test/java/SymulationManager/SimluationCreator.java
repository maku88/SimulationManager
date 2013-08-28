package SymulationManager;

import ProxyServer.stats.RequestStats;
import ProxyServer.stats.ResponseType;
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



        simList.add(getSimulation(1,1,10,100));
        simList.add(getSimulation(1,2,10,100));
        simList.add(getSimulation(2,3,10,1000));
        simList.add(getSimulation(3,4,10,2000));

        simList.add(getSimulation(4,5,20,103));
        simList.add(getSimulation(5,6,30,104));
        simList.add(getSimulation(6,7,40,105));
        simList.add(getSimulation(7,8,50,1066));




        return simList;
    }

    private static Simulation getSimulation(int simulationCase, int simulationID, int ttl, int size) {

        Simulation simulation = new Simulation(simulationCase,simulationID,1,1,"FAKE",ttl,size);

        Map<Integer,Long> results = new HashMap<Integer, Long>();

        for(int i = 1 ; i < 30 ; i ++) {
            results.put(i,700L + simulationCase - i);
        }
        results.put(31,1700L);

        simulation.addResults("sim1",results,21);

        for(RequestStats stats : getProxyStats(1)) {
            simulation.addProxyStats(stats);
        }

        return simulation;
    }

    private static List<RequestStats> getProxyStats(int ratio) {

        List<RequestStats> retList = new ArrayList<RequestStats>();


        for(int i = 0; i<40; i++ ) {
            RequestStats req = new RequestStats();
            req.setResponseType(ResponseType.SERVER);
            retList.add(req);
        }

        for(int i = 0; i<60; i++ ) {
            RequestStats req = new RequestStats();
            req.setResponseType(ResponseType.CACHE);
            retList.add(req);
        }


        return retList;

    }





}
