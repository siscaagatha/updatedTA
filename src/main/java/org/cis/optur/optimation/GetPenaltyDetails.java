package org.cis.optur.optimation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.cis.optur.engine.commons.Sn;
import org.cis.optur.engine.commons.Utils;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.util.LinkedList;

public class GetPenaltyDetails {

        public static final String BASE_PATH = "E:\\4. TSATSAR\\OPTUR7";
        public static final String OPTUR_FILE_PATH = "E:\\FILE OPTUR\\OpTur7.xls";
        public static final int OPTUR_INDEX = 6; //Start from 0
        public static final int[] EMPLOYEE_COUNTS = {0,0,0,30,20,0,15};
        public static final int EMPLOYEE_COUNT = EMPLOYEE_COUNTS[OPTUR_INDEX];

        public static void main(String[] args) throws IOException, InvalidFormatException, ParseException {
            Utils.initSC(new File(OPTUR_FILE_PATH));
            JFileChooser jFileChooser = new JFileChooser(BASE_PATH);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = jFileChooser.showOpenDialog(null);
            File sFolder = null;
            if(result == JFileChooser.APPROVE_OPTION){
                sFolder = jFileChooser.getSelectedFile();
            }
            if(!sFolder.isDirectory()) System.exit(-5);

            for (File file : sFolder.listFiles()) {
                System.out.println("=====================" + file.getName() + "=====================");
                int[][] solutionMatrix = getSolutionMatrix(file);
//                printMatrix(solutionMatrix);
                Sn sn = new Sn(solutionMatrix);
                for (int i = 0; i < sn.ps.length; i++) {
                    System.out.println("P" + (i+1) + " = " + sn.ps[i]);
                }
                System.out.println("P = " + sn.penalty);
                System.out.println("================================================");
            }
        }

        private static void printMatrix(int[][] solutionMatrix) {
            for (int i = 0; i < solutionMatrix.length; i++) {
                for (int i1 = 0; i1 < solutionMatrix[i].length; i1++) {
                    System.out.print(solutionMatrix[i][i1] + "\t");
                }
                System.out.println();
            }
        }

        private static int[][] getSolutionMatrix(File file) throws IOException {
            FileReader readfile = new FileReader(file);
            BufferedReader readbuffer = new BufferedReader(readfile);
            LinkedList<String> lines = new LinkedList<>();
            String line;
            while ((line=readbuffer.readLine())!=null){
                lines.push(line);
            }
            String[] strings = lines.get(EMPLOYEE_COUNT-1).split("\t");
            int[][] matrix = new int[EMPLOYEE_COUNT][strings.length];
            int employeeI = 0;
            for (int i = EMPLOYEE_COUNT-1; i >=0; i--) {
                String[] ll = lines.get(i).split("\t");
                for (int i1 = 0; i1 < ll.length; i1++) {
                    matrix[employeeI][i1] = Integer.parseInt(ll[i1]);
                }
                employeeI++;
            }
            return matrix;
        }

        public void listFilesForFolder(final File folder) {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    System.out.println(fileEntry.getName());
                }
            }
        }
    }
