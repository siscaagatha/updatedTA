//package org.cis.optur.engine;
//
//import org.apache.commons.Commons;
//import org.apache.commons.Sn;
//
//import java.util.LinkedList;
//import java.util.Random;
//
//public class SimulatedAnnealingXTabuSearch extends Optimation implements OptimationInterface {
//
//    private Random random = new Random();
//
//    //Tabu Properties Declaration
//    private LinkedList<Integer> tabuList;
//    private int tabuListLength;
//
//    //SA Properties Initiation
//    private double coolingRate;
//
//    private double currentTemp;
//
//    public SimulatedAnnealingXTabuSearch(Sn initialSolution, int numberOfIterations, int penaltyRecordRange,
//                                         //SA Properties
//                                         int initialTemp, double coolingRate,
//                                         //Tabu Properties
//                                         int tabuListLength) {
//        super(initialSolution, numberOfIterations, penaltyRecordRange);
//
//        //Tabu Properties Initiation
//        this.tabuListLength = tabuListLength;
//        this.tabuList = new LinkedList<>();
//
//
//        //SA Properties Initiation
//        this.currentTemp = Double.valueOf(initialTemp);
//        this.coolingRate = coolingRate;
//
//    }
//
////    @Override
////    public void run() throws Exception {
////        int day = Commons.planningHorizon[(Commons.file - 1)] * 7;
////        int [][] solution = getInitialSolution().getSolution();
////        int [][] newSolution = new int [Commons.emp.length][day];
////        Commons.copyArray(getInitialSolution().getSolution(), newSolution);
////        for (int i = 0; i < getNumberOfIterations(); i++) {
////            int llh = (int) (Math.random() * 3);
////            if (llh == 0)
////                Commons.twoExchange(solution);
////            if (llh == 1)
////                Commons.threeExchange(solution);
////            if (llh == 2)
////                Commons.doubleTwoExchange(solution);
////            currPenalty = countPenalty();
////            int notValidHc = Commons.validAll(solution);
////            if (Commons.validAll(solution) == 0) {
////                int a = notValidHc *8;
////            } else {
////                Commons.copyArray(newSolution, solution);
////            }
////        }
////    }
//
//        @Override
//    public void run() throws Exception {
//        Sn best = new Sn(Utils.getCopyOf(getBestSolution().getSolution()));
//        for (int i = 0; i < getNumberOfIterations(); i++) {
//            int[][] curerntSolution = Utils.getCopyOf(getCurrentSolution().getSolution());
//            int[][] curerntSolutionBackup = Utils.getCopyOf(getCurrentSolution().getSolution());
//            int randomLLH = getRandomLLHExcludeTabuList();
//
//            if (randomLLH == 0)
//                Commons.twoExchange(curerntSolution);
//            else if (randomLLH == 1)
//                Commons.threeExchange(curerntSolution);
//            else
//                Commons.doubleTwoExchange(curerntSolution);
//
//            if(Commons.validAll(curerntSolution)==0){
//                setCurrentSolution(new Sn(Utils.getCopyOf(curerntSolution)));
//                double deltaPenalty = getBestSolution().getPenalty() - getCurrentSolution().getPenalty();
//                if(deltaPenalty>0){
//                    setBestSolution(new Sn(Utils.getCopyOf(getCurrentSolution().getSolution())));
////                    best = Utils.getCopyOf(curerntSolution);
//                }else{
//                    double prob = Math.pow(Math.E, (-(Math.abs(deltaPenalty)/currentTemp)));
//                    if(prob>random.nextDouble()){
//                            insertTabuList(randomLLH);
//                    }else {
//                        setBestSolution(new Sn(Utils.getCopyOf(getCurrentSolution().getSolution())));
////                        best = Utils.getCopyOf(curerntSolution);
//                        insertTabuList(randomLLH);
//                    }
//                }
//            }else {
//                getCurrentSolution().setSolution(Utils.getCopyOf(curerntSolutionBackup));
//            }
//            currentTemp = currentTemp * coolingRate;
//            if((i+1)%getPenaltyRecordRange()==0){
//                getPenalties().push(getCurrentSolution().getPenalty());
//                System.out.println("Penalty iteration "+i+" : " + getBestSolution().getPenalty());
//                if(best.getPenalty()>getBestSolution().getPenalty()){
//                    best = new Sn(Utils.getCopyOf(getBestSolution().getSolution()));
//                }
//            }
//        }
//    }
//
//    private void insertTabuList(int llh){
//        if(tabuList.size()==tabuListLength){
//            tabuList.pop();
//            tabuList.push(llh);
//        }else {
//            tabuList.push(llh);
//        }
//    }
//
//    private int getRandomLLHExcludeTabuList(){
//        int randomInt;
//        do {
//            randomInt = random.nextInt(3);
//        }while (tabuList.contains(randomInt));
//        return randomInt;
//    }
//}
