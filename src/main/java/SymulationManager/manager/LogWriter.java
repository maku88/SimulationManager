package SymulationManager.manager;

import ProxyServer.stats.RequestStats;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 18.08.13
 * Time: 21:22
 * To change this template use File | Settings | File Templates.
 */
public class LogWriter {



    public void writeSimulatorStats(List<Simulation> resultList, String fileName) {

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for(Simulation result : resultList) {

                for(Simulation.SimulatorResults  simResults : result.getSimulatorResultsMap().values()) {

                    for(Integer i : simResults.getAvgReadTime().keySet()) {
                        String line = result.shortString();
                        line += simResults.getSimulatorID() + ";" + simResults.getNumberOfMistakes() + ";";
                        line += i + ";" + simResults.getAvgReadTime().get(i) + ";";
                        writer.println(line);

                    }
                }
            }
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


    public void writeProxyStats(List<Simulation> resultList, String fileName) {

        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for(Simulation result : resultList) {

                for(RequestStats stats : result.getProxyStats() ) {
                    String line = stats.getSimulationID() + ";" + stats.getCacheType() + ";" + stats.getTagID() + ";" +stats.getResponseType() + ";" + stats.getStartTimestamp()  + ";" + stats.getEndTimestamp() + ";" + stats.getDuration();
                    writer.println(line);
                }
            }
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
