package org.cis.optur.optimation;

import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class TabuSearch extends Sn {

    public TabuSearch(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int iteration, int tabuListLength, int penaltyRecordRange) {
        int dayLength = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolutionMatrix = new int [Utils.employees.length][dayLength];
        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
        double bestPenalty; double currentPenalty;
        bestPenalty = currentPenalty = getCandidatePenalty();
        int[][] bestSolutionMatrix = new int[newSolutionMatrix.length][newSolutionMatrix[0].length];
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
//        int[][] tabuAudit = new int[3][3];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int random;
            do {
                random = (int) (Math.random() * 3);
            }while (tabuList.contains(random));
            int llh = random;
            //tabuAudit[0] => Terpanggil
//            tabuAudit[0][llh]++;
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
                //tabuAudit[1] => memenuhi HC
//                tabuAudit[1][llh]++;
                if (getCandidatePenalty() <= currentPenalty) {
                    //tabuAudit[2] => memproduksi solusi lebih baik
//                    tabuAudit[2][llh]++;
                    currentPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    if (currentPenalty <= bestPenalty) {
                        bestPenalty = currentPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSolutionMatrix);
                        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    } else {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currentPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    }
                } else {
                    if(tabuList.size()==tabuListLength){
                        tabuList.pollLast();
                        tabuList.offerFirst(llh);
                    }else {
                        tabuList.offerFirst(llh);
                    }
                    currentPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                }
            } else {
                if(tabuList.size()==tabuListLength){
                    tabuList.pollLast();
                    tabuList.offerFirst(llh);
                }else {
                    tabuList.addFirst(llh);
                }
                Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("[Frekuensi LLH terpanggil] | LLH0: " + tabuAudit[0][0] + " | LLH1: " + tabuAudit[0][1] + " | LLH2: " + tabuAudit[0][2]);
//        System.out.println("[Frekuensi LLH terpanggil dan memenuhi HC] | LLH0: " + tabuAudit[1][0] + " | LLH1: " + tabuAudit[1][1] + " | LLH2: " + tabuAudit[1][2]);
//        System.out.println("[Frekuensi LLH terpanggil dan memenuhi HC dan memproduksi solusi lebih baik] | LLH0: " + tabuAudit[2][0] + " | LLH1: " + tabuAudit[2][1] + " | LLH2: " + tabuAudit[2][2]);
        return new OptimationResult(penalties, endTime-startTime, bestSolutionMatrix, bestPenalty, Utils.file);
    }

}
