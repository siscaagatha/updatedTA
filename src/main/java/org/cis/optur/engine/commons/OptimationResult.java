package org.cis.optur.engine.commons;

import java.io.Serializable;
import java.util.LinkedList;

public class OptimationResult implements Serializable {
    private LinkedList<Double> penalties;
    private long time;
    private int[][] solution;
    private double bestPenalties;
    private int opturNumber;

    public OptimationResult(LinkedList<Double> penalties, long time, int[][] solution, double bestPenalties) {
        this.penalties = penalties;
        this.time = time;
        this.solution = solution;
        this.bestPenalties = bestPenalties;
    }
    public OptimationResult(LinkedList<Double> penalties, long time, int[][] solution, double bestPenalties, int opturNumber) {
        this.penalties = penalties;
        this.time = time;
        this.solution = solution;
        this.bestPenalties = bestPenalties;
        this.opturNumber = opturNumber;
    }

    public int getOpturNumber() {
        return opturNumber;
    }

    public void setOpturNumber(int opturNumber) {
        this.opturNumber = opturNumber;
    }

    public double getBestPenalties() {
        return bestPenalties;
    }

    public void setBestPenalties(double bestPenalties) {
        this.bestPenalties = bestPenalties;
    }

    public LinkedList<Double> getPenalties() {
        return penalties;
    }

    public void setPenalties(LinkedList<Double> penalties) {
        this.penalties = penalties;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void setSolution(int[][] solution) {
        this.solution = solution;
    }
}
