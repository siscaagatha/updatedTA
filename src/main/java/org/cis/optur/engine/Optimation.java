package org.cis.optur.engine;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class Optimation {
    private Sn initialSolution;
    private Sn bestSolution;
    private long time;
    private LinkedList<Double> penalties;
    private int numberOfIterations;
    private Sn currentSolution;
    private int penaltyRecordRange;

    public Optimation(Sn initialSolution, int numberOfIterations, int penaltyRecordRange) {
        this.initialSolution = new Sn(initialSolution.getSolutionMatrix());
        this.bestSolution = new Sn(Utils.getCopyOf(initialSolution.getSolutionMatrix()));
        this.currentSolution = new Sn(Utils.getCopyOf(initialSolution.getSolutionMatrix()));
        this.penalties = new LinkedList<>();
        this.numberOfIterations = numberOfIterations;
        this.penaltyRecordRange = penaltyRecordRange;
    }

    public int getPenaltyRecordRange() {
        return penaltyRecordRange;
    }

    public void setPenaltyRecordRange(int penaltyRecordRange) {
        this.penaltyRecordRange = penaltyRecordRange;
    }

    public Sn getInitialSolution() {
        return initialSolution;
    }

    public void setInitialSolution(Sn initialSolution) {
        this.initialSolution = initialSolution;
    }

    public Sn getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(Sn bestSolution) {
        this.bestSolution = bestSolution;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public LinkedList<Double> getPenalties() {
        return penalties;
    }

    public void setPenalties(LinkedList<Double> penalties) {
        this.penalties = penalties;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public Sn getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(Sn currentSolution) {
        this.currentSolution = currentSolution;
    }
}
