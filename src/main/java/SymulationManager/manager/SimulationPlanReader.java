package SymulationManager.manager;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maciek
 * Date: 11.08.13
 * Time: 23:09
 * To change this template use File | Settings | File Templates.
 */
public class SimulationPlanReader {


    private String filePath = "";

    public SimulationPlanReader(String filePath) {
        this.filePath = filePath;
    }


    public List<Simulation> read() {
        List<Simulation> listOfSimulations = new ArrayList<Simulation>();
        try{
            FileInputStream fstream = new FileInputStream(filePath);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int simulationsCounter = 1;
            while ((strLine = br.readLine()) != null)   {
                String[] s = strLine.split(";");

                int count = Integer.parseInt(s[0]);

                for(int i = 0; i< count ; i++ ) {
                    listOfSimulations.add(new Simulation(simulationsCounter,Integer.parseInt(s[1]),Integer.parseInt(s[2]),s[3],Integer.parseInt(s[4]),Integer.parseInt(s[5])));
                    simulationsCounter++;
                }

            }
            in.close();

            return listOfSimulations;
        }catch (Exception e){//Catch exception if any
            e.printStackTrace();
            return listOfSimulations;
        }

    }
}
