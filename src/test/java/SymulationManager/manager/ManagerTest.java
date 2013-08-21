package SymulationManager.manager;

import ProxyServer.RemoteProxyServer;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import pl.mobiid.server.tester.ProxySimulator.RemoteSimulator;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 09.08.13
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */

public class ManagerTest {

    private Manager manager;
    private RemoteProxyServer proxy;
    private int numberOfSimulatorsToRegister = 2;

    @Before
    public void setManager() {
        manager = new Manager();
        proxy = mock(RemoteProxyServer.class);
        manager.setProxyServer(proxy);
        URL rootfile= SimulationPlanReaderTest.class.getResource("/simulationPlan");
        manager.setup(rootfile.getPath(),numberOfSimulatorsToRegister,"D:\\magisterka\\ProxyServer\\SymulationManager\\simulations");
    }


    @Test
    public void shouldRegisterOnlyOneSimulator() throws Exception {
        RemoteSimulator sim = mock(RemoteSimulator.class);
        manager.registerSimulator(sim);
        Assert.assertTrue(manager.getRegisteredSimulators().size() ==1);
    }

    @Test
    public void shouldRegisterTwoSimulatorsAndFireEvents() {
        RemoteSimulator sim1 = mock(RemoteSimulator.class);
        RemoteSimulator sim2 = mock(RemoteSimulator.class);

        manager.registerSimulator(sim1);
        manager.registerSimulator(sim2);
        Assert.assertTrue(manager.getRegisteredSimulators().size() == numberOfSimulatorsToRegister);

        try {
            verify(sim1,times(1)).startSimulation(anyInt(),anyInt(),anyInt());
            verify(sim2,times(1)).startSimulation(anyInt(),anyInt(),anyInt());
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



    }


    @Test
    public void testReportFinish() throws Exception {
        RemoteSimulator sim = mock(RemoteSimulator.class);
        manager.registerSimulator(sim);
        manager.reportFinish("1", new HashMap<Integer, Long>(),19,1);

        verify(proxy,times(2)).reloadCache(anyString(), anyInt(), anyInt(),anyInt());

        verify(sim,times(1)).startSimulation(anyInt(),anyInt(),anyInt());
    }
}
