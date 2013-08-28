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
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class TagStatsReader {

    public List<TagReadStats> read(String filePath) {
        List<TagReadStats> statsList = new ArrayList<TagReadStats>();
        try{
            FileInputStream fstream = new FileInputStream(filePath);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;


            while ((strLine = br.readLine()) != null)   {
                String[] s = strLine.split(";");
                TagReadStats stat = new TagReadStats(s[1],Double.parseDouble(s[2]),Long.parseLong(s[6]),Boolean.parseBoolean(s[7]));
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
