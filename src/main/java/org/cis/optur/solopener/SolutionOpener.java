package org.cis.optur.solopener;

import org.cis.optur.engine.commons.InitialSolutionResult;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.OptimationResult2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class SolutionOpener {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String[] shiftName4 = new String[]{"FE", "D12", "D11", "A", "D", "N1", "E1E", "D3A", "N"};//8
        String[] shiftName5 = new String[]{"FE", "E", "N", "D8", "D", "D3", "D1", "D2", "E2", "D9"};//9
        String[] shiftName7 = new String[]{"FE", "D3", "D", "N1", "A", "D1", "A1"};//6
        String[] shiftName;
        String[][] shiftNames = {null, null, null, null, shiftName4, shiftName5, null, shiftName7};
        JFileChooser j = new JFileChooser("e:");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Sol", "sol", "sol");
        j.setFileFilter(filter);
        int result = j.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File solFile = j.getSelectedFile();
            FileInputStream fi = new FileInputStream(solFile);
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Read objects
            InitialSolutionResult initialSol;
            OptimationResult optimationResult;
            try {
                initialSol = (InitialSolutionResult) oi.readObject();
                fi.close();
                oi.close();
                System.out.println(initialSol.getInitialSolMatrix());
            }catch (ClassCastException e){
                while(true) {
                    try {
                        OptimationResult2 hai = (OptimationResult2)  oi.readObject();
                        System.out.println(hai);
//                        int opturNumber;
//                        int max = 0;
//                        for (int i = 0; i < optimationResult.getSolution().length; i++) {
//                            for (int i1 = 0; i1 < optimationResult.getSolution()[0].length; i1++) {
//                                int p = optimationResult.getSolution()[i][i1];
//                                if(max<p) max = p;
//                            }
//                        }
//                        if(max==9) opturNumber = 5;
//                        else if(max==8) opturNumber = 4;
//                        else opturNumber = 7;
//                        InitialSolutionResult initialSolutionResult = new InitialSolutionResult();
//                        initialSolutionResult.setInitialSolution(optimationResult.getSolution());
//                        initialSolutionResult.setOpturNumber(opturNumber);
//                        System.out.println(initialSolutionResult.getInitialSolMatrix());
                    } catch (EOFException f) {
                        System.out.println("End of file reached");
                        break;
                    } catch (IOException f) {

                    }
                }
            }

        }
    }
}
