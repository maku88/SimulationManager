package SymulationManager.manager;

import junit.framework.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 11.08.13
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class SimulationPlanReaderTest {
    @Test
    public void testRead() throws Exception {
        URL rootfile= SimulationPlanReaderTest.class.getResource("/simulationPlan");
        System.out.println(rootfile.getPath());
        SimulationPlanReader reader = new SimulationPlanReader(rootfile.getPath());


        List<Simulation> simulationList = reader.read();

        for(Simulation s : simulationList) {
            System.out.println(s.toString());
        }

        Assert.assertTrue(simulationList.size() == 11);


    }
}
