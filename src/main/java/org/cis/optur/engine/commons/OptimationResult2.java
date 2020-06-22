package org.cis.optur.engine.commons;

import java.io.Serializable;
import java.util.LinkedList;

public class OptimationResult2 implements Serializable {
    private long time;
    private int[][] solution;
    private double bestPenalties;

    public OptimationResult2(long time, int[][] solution, double bestPenalties) {
        this.time = time;
        this.solution = solution;
        this.bestPenalties = bestPenalties;
    }

    public double getBestPenalties() {
        return bestPenalties;
    }

    public void setBestPenalties(double bestPenalties) {
        this.bestPenalties = bestPenalties;
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
