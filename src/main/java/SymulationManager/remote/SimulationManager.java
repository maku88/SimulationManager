package SymulationManager.remote;

import pl.mobiid.server.tester.ProxySimulator.RemoteSimulator;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 09.08.13
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public interface SimulationManager {


    public void registerSimulator(RemoteSimulator simulator) ;

    public void reportFinish(String simulatorID, Map<Integer, Long> readStats, int numberOfMistakes, int simulationID);

    public List<RemoteSimulator> getRegisteredSimulators();

    public List<String> getFinishedSimulators();

}
