//package org.cis.optur.engine;
//
//import org.apache.commons.Commons;
//import org.apache.commons.Sn;
//
//import java.util.LinkedList;
//import java.util.Random;
//
//public class SATOptimation {
//    private Random random = new Random();
//
//    private int[][] initSol;
//    private int[][] bestSol;
//    private long time;
//    private LinkedList<Double> penalties = new LinkedList<>();
//    private int numberOfIterations;
//    private int[][] currentSol;
//    private int penaltyRecordRange;
//
//    //Tabu Properties Declaration
//    private LinkedList<Integer> tabuList = new LinkedList<>();
//    private int tabuListLength;
//
//    //SA Properties Initiation
//    private double coolingRate;
//
//    private double currentTemp;
//
//    private long startTime = System.nanoTime();
//
//
//    public SATOptimation(int[][] initSol, int numberOfIterations, int penaltyRecordRange, int initialTemp, double coolingRate, int tabuListLength){
//
//        this.tabuListLength = tabuListLength;
//        this.currentSol = Utils.getCopyOf(initSol);
//        this.bestSol = Utils.getCopyOf(initSol);
//        this.numberOfIterations = numberOfIterations;
//        this.penaltyRecordRange = penaltyRecordRange;
//        this.currentTemp = initialTemp;
//        this.coolingRate = coolingRate;
//
//        run();
//
//        long endTime = System.nanoTime();
//        time = endTime-startTime;
//    }
//
//    private void run(){
//        for (int i = 0; i < numberOfIterations; i++) {
//            int[][] iterSol = Utils.getCopyOf(currentSol);
//
//            int randomLLH = getRandomLLHExcludeTabuList();
//
//            if (randomLLH == 0)
//                Commons.twoExchange(iterSol);
//            else if (randomLLH == 1)
//                Commons.threeExchange(iterSol);
//            else
//                Commons.doubleTwoExchange(iterSol);
//
//            int notFeasibleHC = Commons.validAll(iterSol);
//            if(notFeasibleHC==0){
//                double deltaPenalty =  new Sn(currentSol).getPenalty() - new Sn(iterSol).getPenalty();
//                if(deltaPenalty>0){
//                    currentSol = Utils.getCopyOf(iterSol);
//                    if(new Sn(bestSol).getPenalty() - new Sn(currentSol).getPenalty()>0){
//                        bestSol = Utils.getCopyOf(currentSol);
//                    }
//                }else {
//                    double prob = Math.pow(Math.E, (-(Math.abs(deltaPenalty)/currentTemp)));
//                    double randDob = random.nextDouble();
////                    System.out.println("P => " + prob + " | " + randDob);
//                    if(prob>randDob){
//                        insertTabuList(randomLLH);
//                    }else {
//                        bestSol = Utils.getCopyOf(currentSol);
//                        insertTabuList(randomLLH);
//                    }
//                }
//            }
//            currentTemp = currentTemp * coolingRate;
//            if((i+1)%penaltyRecordRange==0){
//                double currentPenalty = new Sn(currentSol).getPenalty();
//                penalties.push(currentPenalty);
//                System.out.println("Iterasi ke " + (i+1) + ": penalty => " + currentPenalty);
//            }
//        }
//        System.out.println("Best sol penalty => " + new Sn(bestSol).getPenalty() );
//    }
//
//    private void insertTabuList(int randomLLH) {
//        if(tabuList.size()==tabuListLength){
//            tabuList.pop();
//            tabuList.push(randomLLH);
//        }else {
//            tabuList.push(randomLLH);
//        }
//    }
//
//    private int getRandomLLHExcludeTabuList(){
//        int randomInt;
//        do {
//            randomInt = random.nextInt(3);
//        }while (tabuList
//                .contains(randomInt));
//        return randomInt;
//    }
//
//}
