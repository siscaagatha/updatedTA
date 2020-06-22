package org.cis.optur.auto;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.InitialSolutionResult;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.optimation.TSXSA;
import org.cis.optur.optimation.TSXTSAR;

import java.io.*;
import java.text.ParseException;
import java.util.Collections;

public class TSATSAR {
    static SP[] sps = {
            new SP(1000000, 1000000, 0.99998, 0.0, 0, 1),//A
            new SP(1000000, 1000000, 0.88889, 0.0, 0, 1),//B
            new SP(1000000, 1000000, 0.99998, 0.0, 0, 2),//C
            new SP(1500000, 1000000, 0.99998, 0.0, 0, 2),//C
            new SP(1000000, 500000, 0.99998, 0.0, 0, 2),//D
            new SP(1000000, 1000000, 0.99998, 0.1, 500000, 1),//E
            new SP(1000000, 1000000, 0.88889, 0.1, 500000, 1),//F
            new SP(1000000, 1000000, 0.88889, 0.5, 500000, 1),//G
            new SP(1000000, 1000000, 0.88889, 0.5, 200000, 1),//H
            new SP(1000000, 1000000, 0.99998, 0.1, 500000, 2),//I
            new SP(1000000, 500000, 0.99998, 0.1, 500000, 2)//J
    };

    static final String XLS_FILE_PATH = "E:\\FILE OPTUR\\OpTur7.xls";

    static final String INIT_SOL_FILE_PATH = "E:\\INITIAL SOLUTION\\Optur7.sol";

    static final String RESULT_FOLDER = "E:\\4. TSATSAR\\OPTUR7";

    public static void main(String[] args) throws ParseException, InvalidFormatException, IOException, ClassNotFoundException {
        Utils.initSC(new File(XLS_FILE_PATH));
        File solFile = new File(INIT_SOL_FILE_PATH);
        FileInputStream fi = new FileInputStream(solFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        InitialSolutionResult initialSol = (InitialSolutionResult) oi.readObject();
        try {
            System.out.println("Print Init Sol");
            File myObj = new File(RESULT_FOLDER + "\\init.txt");
            if (!myObj.createNewFile()) {
                System.out.println("Error");
                System.exit(-2);
            }
            FileWriter myWriter = new FileWriter(myObj);
            int[][] sol = initialSol.getInitialSolution();
            for (int i = 0; i < sol.length; i++) {
                for (int i1 = 0; i1 < sol[0].length; i1++) {
                    System.out.print(sol[i][i1] + "\t");
                    myWriter.write(sol[i][i1] + "\t");
                }
                System.out.println();
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Print Init Sol Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int s = 0; s < sps.length; s++) {
            System.out.println("Start Scenario " + (s+1));
            for (int i = 0; i < 10; i++) {
                int[][] temp = new int[initialSol.getInitialSolution().length][initialSol.getInitialSolution()[0].length];
                Utils.copySolutionMatrix(initialSol.getInitialSolution(), temp);

                System.out.println("Start Percobaan " + (i+1));
                OptimationResult optimationResult;
                if(sps[s].b==0){
                    TSXSA tsxsa = new TSXSA(temp);
                    optimationResult = tsxsa.getOptimationResult(sps[s].TO, sps[s].a, sps[s].iterasi,sps[s].TL,5000);
                }else {
                    TSXTSAR tsxtsar = new TSXTSAR(temp);
                    optimationResult = tsxtsar.getOptimationResult(sps[s].TO, sps[s].a, sps[s].iterasi,sps[s].TL,5000, sps[s].b, sps[s].Nb);
                }
                try {
                    File dir = new File(RESULT_FOLDER + "\\S"+(s+1));
                    if (!dir.exists()){
                        dir.mkdir();
                    }
                    File myObj = new File(dir.getAbsolutePath()+"\\P"+(i+1)+".txt");
                    if (!myObj.createNewFile()) {
                        System.out.println("Error");
                        System.exit(-2);
                    }
                    System.out.println("File created: " + myObj.getName());
                    FileWriter myWriter = new FileWriter(myObj);
                    Collections.reverse(optimationResult.getPenalties());
                    optimationResult.getPenalties().forEach(aDouble -> {
                        try {
                            myWriter.write(aDouble + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    System.out.print("Best Penalty:" + optimationResult.getBestPenalties() + "\n");
                    System.out.print("Time:" + optimationResult.getTime() + "\n");
                    myWriter.write("Best Penalty:" + optimationResult.getBestPenalties() + "\n");
                    myWriter.write("Time:" + optimationResult.getTime() + "\n");
                    int[][] sol = optimationResult.getSolution();
                    for (int j = 0; j < sol.length; j++) {
                        for (int j1 = 0; j1 < sol[0].length; j1++) {
                            System.out.print(sol[j][j1] + "\t");
                            myWriter.write(sol[j][j1] + "\t");
                        }
                        System.out.println();
                        myWriter.write("\n");
                    }
                    myWriter.close();
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}