package SymulationManager.manager;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 09.08.13
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public interface SimulationManager {

    public void registerSimulator(String simulatorID) ;

    public void reportFinish(String simulatorID);

    public List<String> getSimulators();

}
