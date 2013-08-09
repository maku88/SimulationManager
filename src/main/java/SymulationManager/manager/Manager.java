package SymulationManager.manager;

import ProxyServer.RemoteProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 09.08.13
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Manager implements SimulationManager {

    private List<String> simulators = new ArrayList<String>();

    @Autowired
    RemoteProxyServer proxyServer;


    public List<String> getSimulators() {
        return simulators;
    }

    @Override
    public void registerSimulator(String simulatorID) {
        System.out.println("Rejestruje : " + simulatorID);
        simulators.add(simulatorID);
    }

    @Override
    public void reportFinish(String simulatorID) {
        System.out.println("Koncze : "  + simulatorID);
        simulators.remove(simulatorID);

        if(simulators.isEmpty()) {
            sendClearMessageToProxy();
        }

    }

    private void sendClearMessageToProxy() {

        System.out.println("CLEAR MESSAGE TO PROXY");
        proxyServer.clearCache();
    }

    private void sendStartMessageToSimulators() {

        System.out.println("START MESSAGE TO SIMULATORS");

    }

}
