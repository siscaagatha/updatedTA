package org.cis.optur.engine;


import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.InitialSolutionResult;
import org.cis.optur.engine.commons.Sn;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class Mock {

    static JTextArea logsArea;

    static JScrollBar vertical;

    static File fileOptur;

    static JFileChooser fileChooserOpenOptur;

    static JFileChooser fileChooserSaveSol;

    static JFrame jFrame;
//public static Logger logger= Logger.getLogger("global");
    public static void main(String[] args) throws Exception {
        //Initial
        String[] options = {"Buat Initial Solution"};

        int option = JOptionPane.showOptionDialog(null, "Run",
                "NRP",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if(option==0){
            AtomicBoolean repeat = new AtomicBoolean(false);
            doIt: do {
                repeat.set(false);
                JFileChooser j = new JFileChooser("e:");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "excel");
                j.setFileFilter(filter);
                int result = j.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    Utils.exceptionListener = e -> {
                        JOptionPane.showMessageDialog(null, "Program Error");
                    };
                    Utils.feasibilityListener = hc -> {
                        JOptionPane.showMessageDialog(null, "Not Feasible " + hc);
                        repeat.set(true);
                    };

                    Utils.iterationListener = count -> {
                        System.out.println("Iteration => " + count);
                    };

                    if(repeat.get()) continue ;
                    File opturFile = j.getSelectedFile();
                    InitialSolutionResult initialSol = Utils.getInitialSolutionResult(opturFile);
                    String[] options2 = {"Save Solusi", "Run Lagi"};
                    Sn sn = new Sn(initialSol.getInitialSolution());
                    int option2 = JOptionPane.showOptionDialog(null, "Penalty = "+sn.getCandidatePenalty()+" | How?",
                            "NRP",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);
                    if(option2==0){
                        JFileChooser fileChooserSaveSol = new JFileChooser();
                        fileChooserSaveSol.setDialogTitle("Specify a file to save");
                        int userSelection = fileChooserSaveSol.showSaveDialog(null);

                        File fileToSave = null;

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            fileToSave = fileChooserSaveSol.getSelectedFile();
                        }
                        FileOutputStream f = new FileOutputStream(fileToSave);
                        ObjectOutputStream o = new ObjectOutputStream(f);
                        o.writeObject(initialSol);
                        o.close();
                        f.close();
                        String[] options4 = {"Run Lagi", "Stop"};
                        int option4 = JOptionPane.showOptionDialog(null, "Saved!",
                                "NRP",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);
                        if (option4==0) repeat.set(true);
                        else System.exit(0);
                    }else {
                        repeat.set(true);
                    }
                }
            }while (repeat.get());
        }
//        else {
//            AtomicBoolean repeat = new AtomicBoolean(false);
//            do {
//                JOptionPane.showMessageDialog(null, "Pilih optur dulu ya, buat inisisasi SC :)");
//                JFileChooser j2 = new JFileChooser("e:");
//                FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Excel", "xls", "excel");
//                j2.setFileFilter(filter2);
//                int result2 = j2.showOpenDialog(null);
//                if (result2 == JFileChooser.APPROVE_OPTION) {
//                    Commons.initSC(j2.getSelectedFile());
//                }
//                JOptionPane.showMessageDialog(null, "Oke sekarang pilih initial solusi dari optur tadi, yang .sol");
//                JFileChooser j = new JFileChooser("e:");
//                FileNameExtensionFilter filter = new FileNameExtensionFilter("Sol", "sol", "sol");
//                j.setFileFilter(filter);
//                int result = j.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File solFile = j.getSelectedFile();
//                    FileInputStream fi = new FileInputStream(solFile);
//                    ObjectInputStream oi = new ObjectInputStream(fi);
//                    // Read objects
//                    InitialSolutionResult initialSol = (InitialSolutionResult) oi.readObject();
//
//                    Sn sn = new Sn(initialSol.getInitialSolution());
//
//
//                    JTextField initialTemp = new JTextField("1000000");
//                    JTextField coolingRate = new JTextField("0.99998");
//                    JTextField iteration = new JTextField("1000000");
//                    JTextField tabuListLength = new JTextField("1");
//                    JTextField penaltyRecordRange = new JTextField("5000");
//                    JTextField reHeatRange = new JTextField("500000");
//                    JTextField reHeatRate = new JTextField("0.1");
//                    JPanel panel = new JPanel(new GridLayout(0, 1));
//                    panel.add(new JLabel("Initial Temp:"));
//                    panel.add(initialTemp);
//                    panel.add(new JLabel("Cooling Rate:"));
//                    panel.add(coolingRate);
//                    panel.add(new JLabel("Itreation:"));
//                    panel.add(iteration);
//                    panel.add(new JLabel("Tabu List length:"));
//                    panel.add(tabuListLength);
//                    panel.add(new JLabel("Penalty Record Range:"));
//                    panel.add(penaltyRecordRange);
//                    panel.add(new JLabel("Re-Heat Range:"));
//                    panel.add(reHeatRange);
//                    panel.add(new JLabel("Re-Heat Rate:"));
//                    panel.add(reHeatRate);
//                    int resul = JOptionPane.showConfirmDialog(null, panel, "Oke ya?",
//                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//                    if (resul == JOptionPane.OK_OPTION) {
//                        int vInitialtemp = Integer.parseInt(initialTemp.getText());
//                        double vCoolingRate = Double.parseDouble(coolingRate.getText());
//                        int vIteration = Integer.parseInt(iteration.getText());
//                        int vTabuListLength = Integer.parseInt(tabuListLength.getText());
//                        int vPenaltyRecordRange = Integer.parseInt(penaltyRecordRange.getText());
//                        int vReHeatRange = Integer.parseInt(reHeatRange.getText());
//                        double vReHeatRate = Double.parseDouble(reHeatRate.getText());
////                        OptimationResult optimationResult = sn.TSAR2(vInitialtemp, vCoolingRate, vIteration,vTabuListLength,vCoolingRate, vPenaltyRecordRange);
//                        OptimationResult optimationResult = sn.TSAR2(initialTemp, coolingRate, iteration, );
//                        System.out.println("Best penalty: " + optimationResult.getPenalties());
//                        System.out.println("Time : " + optimationResult.getTime());
//                        JFileChooser fileChooserSaveSol = new JFileChooser();
//                        fileChooserSaveSol.setDialogTitle("Specify a file to save optimation result");
//                        int userSelection = fileChooserSaveSol.showSaveDialog(null);
//
//                        File fileToSave = null;
//
//                        if (userSelection == JFileChooser.APPROVE_OPTION) {
//                            fileToSave = fileChooserSaveSol.getSelectedFile();
//                        }
//                        FileOutputStream f = new FileOutputStream(fileToSave);
//                        ObjectOutputStream o = new ObjectOutputStream(f);
//                        o.writeObject(optimationResult);
//                        o.close();
//                        f.close();
//
//                        String[] options4 = {"Run Lagi", "Udah"};
//                        int option4 = JOptionPane.showOptionDialog(null, "Best Penalty = " + optimationResult.getBestPenalties(),
//                                "NCIS'S TA",
//                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);
//                        if (option4==0) repeat.set(true);
//                        else System.exit(0);
//                    } else {
//                        System.out.println("Cancelled");
//                    }
////                    sn.TSAR(1000000, 0.99998D, 1000000, 1, 5000, 500000, 0.1D);
//                }
//            }while (repeat.get());
        }



//        Commons.exceptionListener = e -> {
//
//        };
//
//        Commons.feasibilityListener = hc -> {
//            System.out.println("Not feasibel: " + hc);
//        };
//
//        Commons.iterationListener = count -> {
//            System.out.println("Iteration count -> " + count);
//        };
//
//        InitialSolutionResult initialSol = Commons.getInitialSolutionResult(new File("C:\\Users\\5216100056\\Desktop\\OpTur (1)\\OpTur7" +
//                ".xls"));
//
//        if(initialSol.getNotFeasibleHc()==null){
//            System.out.println(initialSol.getInitialSolMatrix());
//            System.out.println("Time: " + initialSol.getProcessingTime()/1000.0 + " s");
//            System.out.println("Optur: " + initialSol.getOpturNumber());
//            System.out.println("Penalty: " + new Sn(initialSol.getInitialSolution()).getPenalty());
//            Sn sn = new Sn(initialSol.getInitialSolution());
//            sn.simulatedAnnealingXTab(1000000, 0.99998D, 1000000, 1, 5000);
////            sn.simulatedAnnealingXTabuSearch(1000000, 0.95D, 1000000, 5000);
//            int[][] initSol = initialSol.getInitialSolution();
////            SATABU(initialSol.getInitialSolution());
//        }else{
//            System.out.println("HC " + initialSol.getNotFeasibleHc() + " Not Feasible");
//        }


//        Commons.doneListener = initSol -> {
//            fileChooserSaveSol.setDialogTitle("Specify a file to save");
//            int userSelection = fileChooserSaveSol.showSaveDialog(jFrame);
//
//            File fileToSave = null;
//
//            if (userSelection == JFileChooser.APPROVE_OPTION) {
//                fileToSave = fileChooserSaveSol.getSelectedFile();
//            }
//
//            FileOutputStream f = new FileOutputStream(fileToSave);
//            ObjectOutputStream o = new ObjectOutputStream(f);
//            o.writeObject(initSol);
//            o.close();
//            f.close();
//
//            FileInputStream fi = new FileInputStream(fileToSave);
//            ObjectInputStream oi = new ObjectInputStream(fi);
//
//            // Read objects
//            int[][] sol = (int[][]) oi.readObject();
//
//            String[] shiftName = new String[]{"FE", "D3", "D", "N1", "A", "D1", "A1"};
//            for (int e = -1; e<sol.length; e++){
//                for (int d = -1; d <sol[0].length; d++){
//                    if(e==-1&&d==-1) logsArea.append("E\\D\t");
//                    else if (e==-1) logsArea.append(d + "\t");
//                    else if (d==-1) logsArea.append(e + "\t");
//                    else logsArea.append(shiftName[sol[e][d]] + "\t");
//                }
//                logsArea.append("\n");
//            }
//            vertical.setValue(vertical.getMaximum());
//
//            oi.close();
//            fi.close();
//        };
//
//        jFrame = new JFrame("Nurse Rostering");
//
//        fileChooserOpenOptur = new JFileChooser();
//
//        fileChooserSaveSol = new JFileChooser();
//
//        JPanel jPanel = new JPanel();
//
//        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
//
//        JPanel left = new JPanel();
//
//        left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
//
////        JButton btnPilihFileOptur = new JButton("Pilih File Optur");
//
//        JLabel lblOpturName = new JLabel();
//
////        btnPilihFileOptur.addActionListener(e -> {
////
////        });
//
////        left.add(btnPilihFileOptur);
//
//        left.add(lblOpturName);
//
//        //right
//
//        JPanel right = new JPanel();
//
//        left.setBorder(BorderFactory.createBevelBorder(0));
//        right.setBorder(BorderFactory.createBevelBorder(0));
//
//        logsArea = new JTextArea();
//
//        logsArea.setEditable(false);
//        logsArea.setRows(40);
//        logsArea.setColumns(100);
//
//        JScrollPane scroll = new JScrollPane (logsArea,
//                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//
//        vertical = scroll.getVerticalScrollBar();
//
//        right.add(scroll);
//
//        jPanel.add(left);
//        jPanel.add(right);
//
//        jFrame.add(jPanel);
//        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jFrame.setVisible(true);
//
//        fileChooserOpenOptur.setDialogTitle("Chose Optur File");
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "excel");
//        fileChooserOpenOptur.setFileFilter(filter);
//        int result = fileChooserOpenOptur.showOpenDialog(jFrame);
//        if (result == JFileChooser.APPROVE_OPTION) {
//            fileOptur = fileChooserOpenOptur.getSelectedFile();
//            lblOpturName.setText(fileOptur.getName());
//            try {
//                Commons.exe();
//            } catch (ParseException parseException) {
//                parseException.printStackTrace();
//            } catch (InvalidFormatException invalidFormatException) {
//                invalidFormatException.printStackTrace();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
//    }

    private static void opt() {
    }

    private static void SATABU(int[][] initialSol) throws Exception {
//        SATOptimation satOptimation = new SATOptimation(
//                initialSol,
//                1000000,
//                5000,
//                1000000,
//                0.95D,
//                1
//        );
//        SimulatedAnnealingXTabuSearch simulatedAnnealingXTabuSearch = new SimulatedAnnealingXTabuSearch(
//                new Sn(initialSol.getInitialSolution()),
//                1000000,
//                5000,
//                10000000,
//                0.95D,
//                1
//        );
//        simulatedAnnealingXTabuSearch.run();
//        System.out.println("Best Penalty: " + simulatedAnnealingXTabuSearch.getBestSolution().getPenalty());
    }

}
