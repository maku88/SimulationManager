package SymulationManager;

import ProxyServer.stats.RequestStats;
import SymulationManager.manager.ProxyStatsReader;
import SymulationManager.manager.Simulation;
import SymulationManager.manager.SimulationPlanReader;
import SymulationManager.stats.GroupMode;
import SymulationManager.stats.StatisticsHandler;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 25.08.13
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public class CreateHitRatioCharts {


    public static void main(String[] args) throws IOException {

        String dir = "D:\\magisterka\\ProxyServer\\SymulationManager\\simulations\\LRU\\23.08\\";


        SimulationPlanReader simulationPlanReader = new SimulationPlanReader(dir+"simulationPlan.txt");
        List<Simulation> simulationList = simulationPlanReader.read();

        ProxyStatsReader reader = new ProxyStatsReader();
        List<RequestStats> stats = reader.read(dir+"proxy.txt");

        for(RequestStats stat : stats) {
            for(Simulation sim : simulationList) {
                if(stat.getSimulationID() == sim.getSimulationID()) {
                    sim.addProxyStats(stat);
                }
            }
        }

        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.createHitRatioCharts(simulationList,dir,1, GroupMode.TTL);
        statisticsHandler.createHitRatioCharts(simulationList,dir,2, GroupMode.SIZE);

    }


}
