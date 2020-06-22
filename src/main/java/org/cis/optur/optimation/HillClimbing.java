package org.cis.optur.optimation;
import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class HillClimbing extends Sn{

    public HillClimbing(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int iteration, int penaltyRecordRange) {
        int dayLength = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] bestSolutionMatrix = new int [Utils.employees.length][dayLength];
        double bestPenalty = getCandidatePenalty();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int[][] backupSolutionMatrix = new int [Utils.employees.length][dayLength];
            Utils.copySolutionMatrix(solutionMatrix, backupSolutionMatrix);
            int randomInt = (int) (Math.random() * 3);
            int llh = randomInt;

            if (llh == 0) Utils.twoExchange(solutionMatrix);
            else if (llh == 1) Utils.threeExchange(solutionMatrix);
            else Utils.doubleTwoExchange(solutionMatrix);

            double currentPenalty = getCandidatePenalty();
            if (Utils.isFeasibleAllHC(solutionMatrix) == 0 && bestPenalty > currentPenalty ) {
                bestPenalty = currentPenalty;
                Utils.copySolutionMatrix(solutionMatrix, bestSolutionMatrix);
            }else {
                Utils.copySolutionMatrix(backupSolutionMatrix, solutionMatrix);
            }
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
