package org.cis.optur.engine.commons;

import java.io.Serializable;

public class InitialSolutionResult implements Serializable {
    private int[][] initialSolution;
    private Long processingTime;
    private int opturNumber;
    private String notFeasibleHc;

    public InitialSolutionResult(int[][] initialSolution, Long processingTime, int opturNumber, String notFeasibleHc) {
        this.initialSolution = initialSolution;
        this.processingTime = processingTime;
        this.opturNumber = opturNumber;
        this.notFeasibleHc = notFeasibleHc;
    }

    public InitialSolutionResult() {
    }

    public int[][] getInitialSolution() {
        return initialSolution;
    }

    public void setInitialSolution(int[][] initialSolution) {
        this.initialSolution = initialSolution;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public int getOpturNumber() {
        return opturNumber;
    }

    public void setOpturNumber(int opturNumber) {
        this.opturNumber = opturNumber;
    }

    public String getNotFeasibleHc() {
        return notFeasibleHc;
    }

    public void setNotFeasibleHc(String notFeasibleHc) {
        this.notFeasibleHc = notFeasibleHc;
    }

    public String getInitialSolMatrix(){
        if(initialSolution==null) return null;
        StringBuilder stringBuilder = new StringBuilder("");
        String[] shiftName3 = new String[]{"FE", "E59", "D95", "N", "D66", "D74", "E40", "D59", "D58"};
        String[] shiftName4 = new String[]{"FE", "D12", "D11", "A", "D", "N1", "E1E", "D3A", "N"};
        String[] shiftName5 = new String[]{"FE", "E", "N", "D8", "D", "D3", "D1", "D2", "E2", "D9"};
        String[] shiftName7 = new String[]{"FE", "D3", "D", "N1", "A", "D1", "A1"};
        String[] shiftName;
        if(opturNumber==3) shiftName = shiftName3;
        else if(opturNumber==4) shiftName = shiftName4;
        else if(opturNumber==5) shiftName = shiftName5;
        else if(opturNumber==7) shiftName = shiftName7;
        else shiftName = shiftName7;
        for (int e = -1; e<initialSolution.length; e++){
            for (int d = -1; d <initialSolution[0].length; d++){
                if(e==-1&&d==-1) stringBuilder.append("E\\D\t");
                else if (e==-1) stringBuilder.append(d + "\t");
                else if (d==-1) stringBuilder.append(e + "\t");
                else stringBuilder.append(shiftName[initialSolution[e][d]] + "\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
