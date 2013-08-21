package SymulationManager.manager;

import SymulationManager.SimluationCreator;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 18.08.13
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
public class LogWriterTest {
    @Test
    public void testWrite() throws Exception {

        LogWriter writer = new LogWriter();






//        writer.writeProxyStats(simList, "\\proxy"+new Date().getTime()+ ".txt");
        writer.writeSimulatorStats(SimluationCreator.getSimulations(), "\\symulator" + new Date().getTime() + ".txt");






    }
}
