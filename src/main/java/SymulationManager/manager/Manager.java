package SymulationManager.manager;

import ProxyServer.RemoteProxyServer;
import ProxyServer.stats.RequestStats;
import SymulationManager.remote.SimulationManager;
import SymulationManager.stats.GroupMode;
import SymulationManager.stats.StatisticsHandler;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mobiid.server.tester.ProxySimulator.RemoteSimulator;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 09.08.13
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Manager implements SimulationManager {

    @Getter private List<RemoteSimulator> registeredSimulators = new ArrayList<RemoteSimulator>();
    @Getter private List<String> finishedSimulators = new ArrayList<String>();

    private List<Simulation> simulations = new ArrayList<Simulation>();
    private Simulation currentSimulation;
    private SimulationPlanReader reader;
    private int simulationIndex = 0;
    private static Logger log = Logger.getLogger(Manager.class);
    private int numberOfSimulators;
    private StatisticsHandler statisticsHandler = new StatisticsHandler();

    @Autowired RemoteProxyServer proxyServer;

    private String dir="";


    public void setup(String simulationPlanFilePath, int numberOfSimulators, String simulationsDir) {
        this.numberOfSimulators = numberOfSimulators;
        this.dir = simulationsDir;
        reader  = new SimulationPlanReader(simulationPlanFilePath);
        simulations = reader.read();
        currentSimulation = simulations.get(0);
        sendClearMessageToProxy(currentSimulation);
    }


    @Override
    public void registerSimulator(RemoteSimulator simulator) {
        try {
            log.info("REGISTER simulatorID : " + simulator.getSimulatorID());
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        registeredSimulators.add(simulator);

        if(registeredSimulators.size() == numberOfSimulators) {
            sendStartMessageToSimulators(registeredSimulators,currentSimulation);
        }

    }

    @Override
    public void reportFinish(String simulatorID, Map<Integer,Long> readStats, int numberOfMistakes, int simulationID) {
        log.info("FINISH simulatorID : " + simulatorID + " simulationID : " + simulationID);
        finishedSimulators.add(simulatorID);
        currentSimulation.addResults(simulatorID,readStats,numberOfMistakes);

        if(finishedSimulators.size() == registeredSimulators.size()) {
            finishedSimulators.clear();
            log.info("ALL SIMULATORS FINISHED");

            simulationIndex ++;

            if(simulationIndex < simulations.size()) {
                currentSimulation = simulations.get(simulationIndex);
                log.info("NEW SIMULATION : " + currentSimulation.toString());
                sendClearMessageToProxy(currentSimulation);

                try {
                    Thread.sleep(10000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                sendStartMessageToSimulators(registeredSimulators,currentSimulation);
            }else {

                collectStatsFromProxy();
            }
        }
    }

    private void sendClearMessageToProxy(Simulation simulation) {
        log.info(simulationIndex + " SENDING SETUP MESSAGE TO PROXY  " + simulation.toString());
        proxyServer.reloadCache(simulation.getCacheType(), simulation.getTtl(), simulation.getCacheSize(), simulation.getSimulationID());
    }

    private void sendStartMessageToSimulators(List<RemoteSimulator> simulators, Simulation simulation) {
        System.out.println(registeredSimulators.size());
        for(RemoteSimulator simulator : simulators) {
            try {
                log.info("SENDING START SIMULATION MESSAGE TO SIMULATOR : " + simulator.getSimulatorID());
                simulator.startSimulation(simulation.getNumberOfTags(), simulation.getNumberOfRequest(), simulation.getSimulationID());
            } catch (RemoteException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void collectStatsFromProxy() {
        log.info("COLLECTIONG STATS FROM PROXY  " );
        List<RequestStats> stats = proxyServer.getStats();

        log.info("STATS SIZE  " + stats.size());
        for(RequestStats stat : stats) {
            for(Simulation sim : simulations) {
                if(stat.getSimulationID() == sim.getSimulationID()) {
                    sim.addProxyStats(stat);
                }
            }
        }

        LogWriter writer = new LogWriter();

        long timestamp = new Date().getTime();

        writer.writeProxyStats(simulations, dir+"\\proxy"+timestamp+ ".txt");
        writer.writeSimulatorStats(simulations, dir+"\\symulator" + timestamp + ".txt");

        try {
            statisticsHandler.createCharts(simulations,dir,timestamp);
            statisticsHandler.createHitRatioCharts(simulations,dir,timestamp, GroupMode.SIZE);
            statisticsHandler.createHitRatioCharts(simulations,dir,timestamp, GroupMode.TTL);
            statisticsHandler.createAvgReadTimeFromProxy(simulations,dir,timestamp, GroupMode.TTL);
            statisticsHandler.createAvgReadTimeFromProxy(simulations,dir,timestamp, GroupMode.SIZE);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void setProxyServer(RemoteProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }
}
