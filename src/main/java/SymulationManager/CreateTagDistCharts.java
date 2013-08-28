package SymulationManager;

import ProxyServer.stats.RequestStats;
import SymulationManager.manager.*;
import SymulationManager.stats.GroupMode;
import SymulationManager.stats.StatisticsHandler;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 25.08.13
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class CreateTagDistCharts {

    public static void main(String[] args) throws IOException {

        String dir = "D:\\magisterka\\ProxyServer\\SymulationManager\\simulations\\TTL\\22.08\\";


        TagStatsReader reader = new TagStatsReader();
        List<TagReadStats> list = reader.read(dir+"tags.txt");


        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.createTagDistributionChart(list,dir,1);


    }

}
