package SymulationManager.stats;

import SymulationManager.SimluationCreator;
import org.junit.Test;

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
}
