package SymulationManager.manager;

import ProxyServer.stats.RequestStats;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 11.08.13
 * Time: 22:40
 * To change this template use File | Settings | File Templates.
 */
public class Simulation {

    @Getter private int simulationID;
    @Getter private int numberOfTags;
    @Getter private int numberOfRequest;
    @Getter private String cacheType;
    @Getter private int ttl;
    @Getter private int cacheSize;
    @Getter private Map<String, SimulatorResults> simulatorResultsMap = new HashMap<String, SimulatorResults>();
    @Getter private List<RequestStats> proxyStats = new ArrayList<RequestStats>();

    public Simulation(int simulationID, int numberOfTags, int numberOfRequest, String cacheType, int ttl, int cacheSize) {
        this.simulationID = simulationID;
        this.numberOfRequest = numberOfRequest;
        this.numberOfTags = numberOfTags;
        this.cacheType = cacheType;
        this.ttl = ttl;
        this.cacheSize = cacheSize;
    }


    public void addResults(String simulatorID, Map<Integer,Long> results, int numberOfMistakes)  {
        simulatorResultsMap.put(simulatorID, new SimulatorResults(simulatorID, results, numberOfMistakes));
    }

    public void addProxyStats(RequestStats stats) {
        proxyStats.add(stats);
    }

    @Override
    public String toString() {
        return "Simulation{" +
                "simulationID=" + simulationID +
                ", numberOfTags=" + numberOfTags +
                ", numberOfRequest=" + numberOfRequest +
                ", cacheType='" + cacheType + '\'' +
                ", ttl=" + ttl +
                ", cacheSize=" + cacheSize +
                '}';
    }

    public String fullString() {
        return "Simulation{" +
                "simulationID=" + simulationID +
                ", numberOfTags=" + numberOfTags +
                ", numberOfRequest=" + numberOfRequest +
                ", cacheType='" + cacheType + '\'' +
                ", ttl=" + ttl +
                ", cacheSize=" + cacheSize +
                ", simulatorResultsMap=" + simulatorResultsMap.toString() +
                ", proxyStats=" + proxyStats +
                '}';
    }

    @Data
    class SimulatorResults {

        private String simulatorID;
        private  Map<Integer,Long> avgReadTime = new HashMap<Integer, Long>();
        private int numberOfMistakes;

        public SimulatorResults(String simulatorID,  Map<Integer,Long> avgReadTime, int numberOfMistakes) {
            this.simulatorID = simulatorID;
            this.avgReadTime.putAll(avgReadTime);
            this.numberOfMistakes = numberOfMistakes;
        }
    }


}
