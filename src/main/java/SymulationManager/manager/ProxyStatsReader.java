package SymulationManager.manager;

import ProxyServer.stats.RequestStats;
import ProxyServer.stats.ResponseType;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 25.08.13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class ProxyStatsReader {


    public List<RequestStats> read(String filePath) {
        List<RequestStats> statsList = new ArrayList<RequestStats>();
        try{
            FileInputStream fstream = new FileInputStream(filePath);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;


            while ((strLine = br.readLine()) != null)   {
                String[] s = strLine.split(";");

                RequestStats stat = new RequestStats();
                stat.setSimulationID(Integer.parseInt(s[0]));
                stat.setResponseType(ResponseType.valueOf(s[3]));
                stat.setDuration(Long.parseLong(s[6]));

                statsList.add(stat);

            }
            in.close();

            return statsList;
        }catch (Exception e){//Catch exception if any
            e.printStackTrace();
            return statsList;
        }

    }


}
