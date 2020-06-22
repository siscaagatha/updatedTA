package org.cis.optur.init;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.ParseException;

public class State {
    private int employeePointer;
    private int shiftPointer;
    private int dayPointer;

        private int[] dayQueue;
        private int[] employeeQueue;
        private int[] shiftQueue;

        private int dayQueuePointer = 0;
        private int employeeQueuePointer = 0;
        private int shiftQueuePointer = 0;

    public State(int[] employeeQueue, int[] dayQueue, int[] shiftQueue) throws Exception {
            this.dayQueue = dayQueue;
            this.employeeQueue = employeeQueue;
            this.shiftQueue = shiftQueue;
            shiftPointer = shiftQueue[shiftQueuePointer];
            dayPointer = dayQueue[dayQueuePointer];
            employeePointer = employeeQueue[employeeQueuePointer];
            initState();
        }



    public int getWeekPointer(){
            return dayPointer/7;
        }

        public int getDayPointerInWeek(){
            return dayPointer%7;
        }

        public void resetShiftPointer(){
            shiftQueuePointer = 0;
//            Utils.shuffleArray(shiftQueue);
    }

    //return true if shiftPointer is reset
    public boolean iShiftPointer(){
        shiftQueuePointer++;
        if(shiftQueuePointer==shiftQueue.length){
            shiftQueuePointer = 0;
            shiftPointer = shiftQueue[shiftQueuePointer];
            return true;
        }
        shiftPointer = shiftQueue[shiftQueuePointer];
        return false;
    }

    public boolean iEmployeePointer(){
        employeeQueuePointer++;
        if(employeeQueuePointer==employeeQueue.length){
            employeeQueuePointer = 0;
            employeePointer = employeeQueue[employeeQueuePointer];
            return true;
        }
        employeePointer = employeeQueue[employeeQueuePointer];
        return false;
    }

    //return true if no day left
    public boolean iDayPointer(){
        dayQueuePointer++;
        if(dayQueuePointer==dayQueue.length) return true;
        dayPointer = dayQueue[dayQueuePointer];
        return false;
    }

    public int getEmployeePointer() {
        return employeePointer;
    }

    public int getShiftPointer() {
        return shiftPointer;
    }

    public int getDayPointer() {
        return dayPointer;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("State{");
        sb.append("employeePointer=").append(employeePointer);
        sb.append(", shiftPointer=").append(shiftPointer);
        sb.append(", dayPointer=").append(dayPointer);
        sb.append('}');
        return sb.toString();
    }
    static JTextArea logsArea;

    static JScrollBar vertical;

    static File fileOptur;

    static JFileChooser fileChooserOpenOptur;

    static JFileChooser fileChooserSaveSol;

    static JFrame jFrame;

    static Long startTime;
    static Long endTime;

    private static void initState() throws ParseException, InvalidFormatException, IOException {
//
//        Commons.exceptionListener = e -> {
//            logsArea.append(e.getMessage());
//            logsArea.append("\n");
//            vertical.setValue( vertical.getMaximum() );
//        };
//
//        Commons.chooseOpturListener = () -> fileOptur;
//
//        Commons.iterationListener = count -> {
//            logsArea.append("Iteration count => " + count);
//            logsArea.append("\n");
//            vertical.setValue( vertical.getMaximum() );
//        };
//
//        Commons.feasibilityListener = hc -> {
//            logsArea.append(hc + " Not Feasible");
//            logsArea.append("\n");
//            vertical.setValue( vertical.getMaximum() );
////            System.exit(0);
//        };
//        Commons.doneListener = initSol -> {
//            endTime = System.currentTimeMillis();
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
//            String[] shiftName3 = new String[]{"FE", "E59", "D95", "N", "D66", "D74", "E40", "D59", "D58"};
//            String[] shiftName4 = new String[]{"FE", "D12", "D11", "A", "D", "N1", "E1E", "D3A", "N"};
//            String[] shiftName5 = new String[]{"FE", "E", "N", "D8", "D", "D3", "D1", "D2", "E2", "D9"};
//            String[] shiftName7 = new String[]{"FE", "D3", "D", "N1", "A", "D1", "A1"};
//            int opturNumber = getOpturNumber(fileOptur.getName());
//            String[] shiftName;
//
//            if(opturNumber==3){
//                shiftName = shiftName3;
//            }else if(opturNumber==4){
//                shiftName = shiftName4;
//            }else if(opturNumber==5){
//                shiftName = shiftName5;
//            }else{
//                shiftName = shiftName7;
//            }
//
//            for (int e = -1; e<sol.length; e++){
//                for (int d = -1; d <sol[0].length; d++){
//                    if(e==-1&&d==-1) logsArea.append("E\\D\t");
//                    else if (e==-1) logsArea.append(d + "\t");
//                    else if (d==-1) logsArea.append(e + "\t");
//                    else logsArea.append(shiftName[sol[e][d]] + "\t");
//                }
//                logsArea.append("\n");
//            }
//
//            Solution solution = new Solution(sol);
//            logsArea.append("Penalty:\n");
//            logsArea.append(String.valueOf(solution.countPenalty()));
//            logsArea.append("\n");
//
//            logsArea.append("Time (seconds):\n");
//            logsArea.append(String.valueOf((endTime-startTime)/1000.0));
//            logsArea.append("\n");
//
//            vertical.setValue(vertical.getMaximum());
//
//            oi.close();
//            fi.close();
//        };

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

//        JButton btnPilihFileOptur = new JButton("Pilih File Optur");

//        JLabel lblOpturName = new JLabel();

//        btnPilihFileOptur.addActionListener(e -> {
//
//        });

//        left.add(btnPilihFileOptur);

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
//                startTime = System.currentTimeMillis();
//                Commons.exe();
//            } catch (ParseException parseException) {
//                parseException.printStackTrace();
//            } catch (InvalidFormatException invalidFormatException) {
//                invalidFormatException.printStackTrace();
//            } catch (IOException ioException) {
//                ioException.printStackTrace();
//            }
//        }
    }

    public static int getOpturNumber(String fileName){
        if(fileName.indexOf("1")>-1) return 1;
        else if(fileName.indexOf("2")>-1) return 2;
        else if(fileName.indexOf("3")>-1) return 3;
        else if(fileName.indexOf("4")>-1) return 4;
        else if(fileName.indexOf("5")>-1) return 5;
        else if(fileName.indexOf("6")>-1) return 6;
        else if(fileName.indexOf("7")>-1) return 7;
        else return 7;
    }
}
