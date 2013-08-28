package SymulationManager.stats;

import ProxyServer.stats.RequestStats;
import SymulationManager.SimluationCreator;
import SymulationManager.manager.*;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 18.08.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsHandlerTest {
    @Test
    public void testCreateCharts() throws Exception {

        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.createCharts(SimluationCreator.getSimulations(),"\\charts\\",100);

    }

    @Test
    public void createBarCharts() throws IOException {

        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.createHitRatioCharts(SimluationCreator.getSimulations(), "\\charts\\", 100, GroupMode.SIZE);

    }


    @Test
    public void createAvgReadTimeCharts() throws IOException {
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
        statisticsHandler.createAvgReadTimeFromProxy(simulationList,dir,1, GroupMode.TTL);
        statisticsHandler.createAvgReadTimeFromProxy(simulationList,dir,2, GroupMode.SIZE);

    }




}
