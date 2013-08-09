package SymulationManager.manager;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 09.08.13
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */

public class ManagerTest {

    private SimulationManager manager;

    @org.junit.Before
    public void setManager() {
        manager = new Manager();

    }


    @Test
    public void testRegisterSimulator() throws Exception {
        manager.registerSimulator("Sim1");
        Assert.assertTrue(manager.getSimulators().size() ==1);
    }

    @Test
    public void testReportFinish() throws Exception {
        String sim = "Sim";
        manager.registerSimulator(sim);
        manager.reportFinish(sim);
        Assert.assertTrue(manager.getSimulators().size() == 0);

    }
}
