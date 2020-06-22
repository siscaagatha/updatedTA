package org.cis.optur.auto;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.InitialSolutionResult;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.optimation.TabuSearch;

import java.io.*;
import java.text.ParseException;
import java.util.Collections;

public class TS {
    static SP sp = new SP(1000000, 0, 0.0, 0.0, 0, 1);

    static final String XLS_FILE_PATH = "E:\\FILE OPTUR\\OpTur7.xls";

    static final String INIT_SOL_FILE_PATH = "E:\\INITIAL SOLUTION\\Optur7.sol";

    static final String RESULT_FOLDER = "E:\\2. TABU\\OPTUR7";

    public static void main(String[] args) throws ParseException, InvalidFormatException, IOException, ClassNotFoundException {
        Utils.initSC(new File(XLS_FILE_PATH));
        File solFile = new File(INIT_SOL_FILE_PATH);
        FileInputStream fi = new FileInputStream(solFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        InitialSolutionResult initialSol = (InitialSolutionResult) oi.readObject();
        try {
            System.out.println("Print Init Sol");
            File dir = new File(RESULT_FOLDER);
            if (!dir.exists()){
                dir.mkdir();
            }
            File myObj = new File(dir.getAbsolutePath() + "\\init.txt");

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
        for (int i = 0; i < 10; i++) {
            int[][] temp = new int[initialSol.getInitialSolution().length][initialSol.getInitialSolution()[0].length];
            Utils.copySolutionMatrix(initialSol.getInitialSolution(), temp);
            TabuSearch tabuSearch = new TabuSearch(temp);
            System.out.println("Start Percobaan " + (i+1));
            OptimationResult optimationResult;
            optimationResult = tabuSearch.getOptimationResult(sp.iterasi,sp.TL, 5000);
            try {
                File dir = new File(RESULT_FOLDER);
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