package org.cis.optur.optimation;

import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class SimulatetAneling extends Sn {

    public SimulatetAneling(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int initialTemperatureInt, double coolingRate, int iteration, int penaltyRecordRange) {
        int dayLength = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolutionMatrix = new int [Utils.employees.length][dayLength];
        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
        double bestPenalty; double currentPenalty;
        bestPenalty = currentPenalty = getCandidatePenalty();
        int[][] bestSolutionMatrix = new int[newSolutionMatrix.length][newSolutionMatrix[0].length];
        double penaltyDelta = 0;
        double prob = 0;
        double initialTemperature = initialTemperatureInt;
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int random = (int) (Math.random() * 3);
            int llh = random;
            if (llh == 0)
            {
                Utils.twoExchange(solutionMatrix);
            }
            else if (llh == 1)
            {
                Utils.threeExchange(solutionMatrix);
            }
            else
            {
                Utils.doubleTwoExchange(solutionMatrix);
            }
            if (Utils.isFeasibleAllHC(solutionMatrix) == 0) {
                penaltyDelta = getCandidatePenalty() - currentPenalty;
                prob = Math.exp(-(Math.abs(penaltyDelta)/initialTemperature));
                if (getCandidatePenalty() <= currentPenalty) {
                    currentPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    if (currentPenalty <= bestPenalty) {
                        bestPenalty = currentPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSolutionMatrix);
                        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    } else {
                        if (prob >= Math.random()) {
                            currentPenalty = getCandidatePenalty();
                            Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                        } else {
                            Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
                        }
                    }
                } else {
                    if (prob >= Math.random()) {
                        currentPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    } else {
                        Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
                    }
                }
            } else {
                Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
            }
            initialTemperature = initialTemperature * coolingRate;
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSolutionMatrix, bestPenalty, Utils.file);
    }
}
