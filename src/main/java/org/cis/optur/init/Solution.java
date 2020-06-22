package org.cis.optur.init;

import org.cis.optur.engine.commons.Utils;

import java.io.IOException;
import java.util.LinkedList;

class Solution {
    int [][] solution;

    public Solution(int [][] solution) {
        this.solution = solution;
    }

    public double countPenalty() {
        return penalty1() + penalty2() + penalty3() + penalty4() + penalty5() +
                penalty6() + penalty7() + penalty8() + penalty9();
    }

    double penalty1() {
        double penalty1 = 0;
        for (int i = 0; i < 3; i++) {
            int day = 0;
            int softConstraint = -1;
            if (i == 0) {
                softConstraint = Utils.softConstraint.getSc1a();
                if (Utils.softConstraint.getSc1a() != 0) {
                    day = (Utils.manpowerPlan[(Utils.file - 1)] * 7) - (Utils.softConstraint.getSc1a());
                } else
                    continue;;
            }
            if (i == 1) {
                softConstraint = Utils.softConstraint.getSc1b();
                if (Utils.softConstraint.getSc1b() != 0) {
                    day = (Utils.manpowerPlan[(Utils.file - 1)] * 7) - (Utils.softConstraint.getSc1b());
                } else
                    continue;;
            }
            if (i == 2) {
                softConstraint = Utils.softConstraint.getSc1c();
                if (Utils.softConstraint.getSc1c() != 0) {
                    day = (Utils.manpowerPlan[(Utils.file - 1)] * 7) - (Utils.softConstraint.getSc1c());
                } else
                    continue;;
            }
            for (int j = 0; j < Utils.employees.length; j++) {
                for (int k = 0; k < day; k++) {
                    int count = (softConstraint+1);
                    for (int l = k; l <= k+softConstraint ; l++) {
                        if (solution[j][l] != 0) {
                            if (Utils.shifts[solution[j][l]-1].getCategory() == (i+1)) {
                                count--;
                            }
                        }
                    }
                    if (count == 0)
                        penalty1 = penalty1 + 1;
                }
            }
        }
        return penalty1;
    }

    double penalty2() {
        double penalty2 = 0;
        int softConstraint = Utils.softConstraint.getSc2();
        if (softConstraint != 0) {
            int day = (Utils.manpowerPlan[Utils.file - 1] * 7) - softConstraint;
            for (int i = 0; i < Utils.employees.length; i++) {
                for (int j = 0; j < day; j++) {
                    int count = (softConstraint + 1);
                    for (int k = j; k <= j + softConstraint; k++) {
                        if (solution[i][k] > 0)
                            count--;
                    }
                    if (count == 0)
                        penalty2 = penalty2 + 1;
                }
            }
        }
        return penalty2;
    }

    double penalty3() {
        double penalty3 = 0;
        for (int i = 0; i < 3; i++) {
            int day = 0;
            int softConstraint = -1;
            if (i == 0) {
                softConstraint = Utils.softConstraint.getSc3a();
                if (Utils.softConstraint.getSc3a() != 0) {
                    day = (Utils.manpowerPlan[(Utils.file - 1)] * 7) - (Utils.softConstraint.getSc3a());
                } else
                    continue;;
            }
            if (i == 1) {
                softConstraint = Utils.softConstraint.getSc3b();
                if (Utils.softConstraint.getSc3b() != 0) {
                    day = (Utils.manpowerPlan[(Utils.file - 1)] * 7) - (Utils.softConstraint.getSc3b());
                } else
                    continue;;
            }
            if (i == 2) {
                softConstraint = Utils.softConstraint.getSc3c();
                if (Utils.softConstraint.getSc3c() != 0) {
                    day = (Utils.manpowerPlan[(Utils.file - 1)] * 7) - (Utils.softConstraint.getSc3c());
                } else
                    continue;;
            }
            for (int j = 0; j < Utils.employees.length; j++) {
                for (int k = 0; k < day; k++) {
                    if (solution[j][k+1] != 0) {
                        if (Utils.shifts[solution[j][k+1]-1].getCategory() == (i+1)) {
                            if (solution[j][k] == 0 || Utils.shifts[solution[j][k] - 1].getCategory() != (i + 1)) {
                                double calculate = 0;
                                for (int l = k + 1; l <= k + softConstraint; l++) {
                                    int count = 1;
                                    for (int m = k + 1; m <= k + softConstraint; m++) {
                                        if (solution[j][m] == 0 || Utils.shifts[solution[j][m] - 1].getCategory() != (i + 1))
                                            count = 0;
                                    }
                                    if (count == 1)
                                        calculate = calculate + 1;
                                }
                                double penalti = softConstraint - calculate;
                                penalty3 = penalty3 + penalti;
                            }
                        }
                    }
                }
            }
        }
        return penalty3;
    }

    double penalty4() {
        double penalty4 = 0;
        int softConstraint = Utils.softConstraint.getSc4();
        if (softConstraint != 0) {
            int day = (Utils.manpowerPlan[(Utils.file-1)] * 7) - softConstraint;
            for (int i = 0; i < Utils.employees.length; i++) {
                for (int j = 0; j < day; j++) {
                    if (solution[i][j+1] != 0 && solution[i][j] == 0) {
                        double calculate = 0;
                        for (int k = j+1; k <= j+softConstraint; k++) {
                            int count = 1;
                            for (int l = j+1; l <= j+softConstraint; l++) {
                                if (solution[i][l] == 0)
                                    count = 0;
                            }
                            if (count == 1)
                                calculate = calculate + 1;
                        }
                        double penalti = softConstraint - calculate;
                        penalty4 = penalty4 - penalti;
                    }
                }
            }
        }
        return penalty4;
    }

    double penalty5() {
        double penalty5 = 0;
        for (int i = 0; i < 3; i++) {
            int min = 0;
            int max = 0;
            if (i == 0) {
                min = Utils.softConstraint.getSc5aMin();
                max = Utils.softConstraint.getSc5aMax();
            }
            if (i == 1) {
                min = Utils.softConstraint.getSc5bMin();
                max = Utils.softConstraint.getSc5aMax();
            }
            if (i == 2) {
                min = Utils.softConstraint.getSc5cMin();
                max = Utils.softConstraint.getSc5cMax();
            }
            if (min == 0 && max == 0)
                continue;

            for (int j = 0; j < Utils.employees.length; j++) {
                int count = 0;
                for (int k = 0; k < Utils.manpowerPlan[(Utils.file - 1)] * 7; k++) {
                    if (solution[j][k] != 0)
                        if (Utils.shifts[solution[j][k]-1].getCategory() == (i+1))
                            count++;
                }
                min = min - count;
                max = count - max;
                if (min>max && min>0)
                    penalty5 = penalty5 + (min*min);
                if (max>min && max>0)
                    penalty5 = penalty5 + (max*max);
            }
        }
        penalty5 = Math.sqrt(penalty5);
        return penalty5;
    }

    double penalty6 () {
        double penalty6 = 0;
        if (Utils.softConstraint.getSc6()) {
            for (int i = 0; i < Utils.employees.length; i++) {
                double hour = (Utils.hourLimit[i][1]) * (Utils.manpowerPlan[(Utils.file - 1)]);
                double workingHour = 0;
                for (int j = 0; j < Utils.manpowerPlan[(Utils.file - 1)] * 7; j++) {
                    if (solution[i][j] != 0)
                        workingHour = workingHour + Utils.shifts[solution[i][j]-1].getDuration(j % 7);
                }
                hour = hour - workingHour;
                penalty6 = penalty6 + (hour*hour);
            }
            penalty6 = Math.sqrt(penalty6);
        }
        return penalty6;
    }

    double penalty7 () {
        double penalty7 = 0;
        if (Utils.softConstraint.getSc7()) {
            for (int i = 0; i < Utils.employees.length; i++) {
                int count = 0;
                for (int j = 0; j < (Utils.manpowerPlan[(Utils.file - 1)] * 7)-1; j++) {
                    if (solution[i][j] != 0 && solution[i][j+1] == 0)
                        count++;
                }
                penalty7 = penalty7 + (count*count);
            }
            penalty7 = Math.sqrt(penalty7);
        }
        return penalty7;
    }

    double penalty8 () {
        double penalty8 = 0;
        int day = (Utils.manpowerPlan[(Utils.file-1)] * 7);
        int emp = Utils.employees.length;
        if (Utils.softConstraint.getSc8() != 0) {
            penalty8 = (double) emp * day;
            for (int i = 0; i < emp; i++) {
                for (int j = 0; j < day; j++) {
                    for (int k = 0; k < Utils.want.length; k++) {
                        if (day % 7 == Utils.want[k].day) {
                            if (solution[i][j] != 0) {
                                if (Utils.shifts[solution[i][j]-1].getName().equals(Utils.want[k].pattern[0])) {
                                    if (j <= day - (Utils.want[k].pattern.length)) {
                                        int count = Utils.want[k].pattern.length;
                                        for (int l = 0; l < Utils.want[k].pattern.length; l++) {
                                            if (solution[i][j+l] != 0) {
                                                if (Utils.shifts[solution[i][j + l] - 1].getName().equals(Utils.want[k].pattern[l])) {
                                                    count--;
                                                }
                                            }
                                            else {
                                                if (Utils.want[k].pattern[l].equals("<Free>")) {
                                                    count--;
                                                }
                                            }
                                        }
                                        if (count == 0)
                                            penalty8 = penalty8 - 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return penalty8;
    }

    double penalty9 () {
        double penalty9 = 0;
        int day = (Utils.manpowerPlan[(Utils.file-1)] * 7);
        int emp = Utils.employees.length;
        if (Utils.softConstraint.getSc9() != 0) {
            for (int i = 0; i < emp; i++) {
                for (int j = 0; j < day; j++) {
                    for (int k = 0; k < Utils.unwant.length; k++) {
                        if (day % 7 == Utils.unwant[k].day) {
                            if (solution[i][j] != 0) {
                                if (Utils.shifts[solution[i][j]-1].getName().equals(Utils.unwant[k].pattern[0])) {
                                    if (j <= day - (Utils.unwant[k].pattern.length)) {
                                        int count = Utils.unwant[k].pattern.length;
                                        for (int l = 0; l < Utils.unwant[k].pattern.length; l++) {
                                            if (solution[i][j+l] != 0) {
                                                if (Utils.shifts[solution[i][j + l] - 1].getName().equals(Utils.unwant[k].pattern[l])) {
                                                    count--;
                                                }
                                            }
                                            else {
                                                if (Utils.unwant[k].pattern[l].equals("<Free>")) {
                                                    count--;
                                                }
                                            }
                                        }
                                        if (count == 0)
                                            penalty9 = penalty9 - 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return penalty9;
    }

    public void tryToFeasible() {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [solution.length][day];
        Utils.copySolutionMatrix(solution, newSolution);
        double solutionHour = Utils.diffHour(solution);
        int count = Utils.countHC6(solution);
        for(int i =0; i<50000; i++)
        {
            int llh = (int) (Math.random()*3);
            if(llh == 0)
                Utils.twoExchange(newSolution);
            if(llh == 1)
                Utils.threeExchange(newSolution);
            if(llh == 2)
                Utils.doubleTwoExchange(newSolution);
            if(Utils.isFeasibleHC4(newSolution))
            {
                if(Utils.isFeasibleHC5(newSolution))
                {
                    if(Utils.isFeasibleHC7(newSolution))
                    {
                        if(Utils.diffHour(newSolution)<=solutionHour)
                        {
                            if (Utils.countHC6(newSolution) <= count) {
                                Utils.copySolutionMatrix(newSolution, solution);
                                count = Utils.countHC6(newSolution);
                                solutionHour = Utils.diffHour(newSolution);
                            }
                            else {
                                Utils.copySolutionMatrix(solution, newSolution);
                            }
                        }
                        else {
                            Utils.copySolutionMatrix(solution, newSolution);
                        }
                    }
                    else {
                        Utils.copySolutionMatrix(solution, newSolution);
                    }
                }
                else {
                    Utils.copySolutionMatrix(solution, newSolution);
                }
            }
            else {
                Utils.copySolutionMatrix(solution, newSolution);
            }
//            System.out.println("Iterasi ke " + (i+1) + " " + solutionHour +  " hc6 " + Schedule.countHC6(solution));
        }
    }

    public void hillClimbing () throws IOException {
        for (int e = 0; e < 9; e++) {
            int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
            int[][] newSolution = new int[solution.length][day];
            int [][] baseSolution = new int [solution.length][day];
            double penalty = countPenalty();
            Utils.copySolutionMatrix(solution, newSolution);
            Utils.copySolutionMatrix(solution, baseSolution);
            double[][] plot = new double[101][2];
            int p = 0;
            long startTime = System.nanoTime();
            for (int i = 0; i < 10000; i++) {
                int llh = (int) (Math.random() * 3);
                switch (llh) {
                    case 0:
                        Utils.twoExchange(solution);
                    case 1:
                        Utils.twoExchange(solution);
                    case 2:
                        Utils.doubleTwoExchange(solution);
                }
                if (Utils.isFeasibleAllHC(solution) == 0) {
                    if (countPenalty() <= penalty) {
                        penalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                } else {
                    Utils.copySolutionMatrix(newSolution, solution);
                }
//                System.out.println("iterasi ke " + (i + 1) + " penalti : " + countPenalty());
                if ((i + 1) % 10000 == 0) {
                    plot[p][0] = i + 1;
                    plot[p][1] = penalty;
                    p = p + 1;
                }
            }

            long endTime = System.nanoTime();
            long time = (endTime-startTime) / 1000000000;
            plot [100][0] = time;
            Utils.saveOptimation(plot, e);
            System.out.println(penalty);
            Utils.copySolutionMatrix(baseSolution, solution);
//            for (int j = 0; j < plot.length; j++) {
//                for (int k = 1; k < plot[j].length; k++) {
//                    System.out.print(plot[j][k] + " ");
//                }
//                System.out.println();
//            }
        }
    }

    public int countRF (int [] rf) {
        int index = -1000000;
        int llh = -1;
        LinkedList<Integer> rl = new LinkedList<Integer>();
        for (int i = 0; i < rf.length; i++) {
            if (rf[i] > index) {
                llh = i;
                index = rf[i];
                for (int j = 0; j < rf.length; j++) {
                    if (rf[j] == index)
                        rl.add(j);
                }
                if (!rl.isEmpty()) {
                    int x = -1;
                    x = (int) (Math.random() * 3);
                    while (!rl.contains(x)) {
                        x = (int) (Math.random() * 3);
                    }
                    rl.clear();
                }
            }
        }
        return llh;
    }

    public void reinforcementLearning1 () {
        int p = 0;
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [solution.length][day];
        double S = countPenalty();
        double s = S;
        double currPenalty; double bestPenalty;
        currPenalty = bestPenalty = countPenalty();
        Utils.copySolutionMatrix(solution, newSolution);
        double [] fitness = new double[200];
        for (int i = 0; i < fitness.length; i++) {
            fitness[i] = S;
        }
        int llh = -1;
        int [] rf = {0, 0, 0};
        double diff = 0;
        double d = 0;
        double prob = 0;
        double TAwal = 10000000;
        double coolingrate = 0.99995;
        for (int i = 0; i < 1000000; i++) {
//            llh = -1;
            llh = countRF(rf);
            if (llh == 0)
                Utils.twoExchange(solution);
            if (llh == 1)
                Utils.threeExchange(solution);
            if (llh == 2)
                Utils.doubleTwoExchange(solution);
            if (Utils.isFeasibleAllHC(solution) == 0) {
                diff = countPenalty() - currPenalty;
                d = Math.abs(diff)/TAwal;
                prob = Math.exp(-d);
//                System.out.println("feasible");
                if (countPenalty() <= currPenalty) {
                    currPenalty = countPenalty();
                    Utils.copySolutionMatrix(solution, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solution, newSolution);
                        rf[llh] = rf[llh]+1;
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                } else {
                    if (prob >= Math.random()) {
//                        System.out.println("solusi jelek diterima");
                        currPenalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                        rf[llh] = rf[llh] - 1;
                    }
                }
            } else {
                Utils.copySolutionMatrix(newSolution, solution);
                rf[llh] = rf[llh] - 1;
            }
            TAwal = TAwal * coolingrate;
//            System.out.println(rf[llh]);
//            System.out.println("Iterasi ke " + (i+1) + " s " + s);
//            System.out.println("Iterasi : " + (i+1) +" suhu : " + TAwal + " diff : " + diff + " d : " + d + " prob : " + prob + " penalti : " + countPenalty());
        }
//        System.out.println(bestPenalty);
    }

    public void reinforcementLearning () throws IOException {
        for (int e = 0; e < 3; e++) {
            int[] score = {500, 500, 500};
            int score2Exchange = score[0];
            int score3Exchange = score[1];
            int scoredoubleExchange = score[2];
            int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
            int[][] newSolution = new int[Utils.employees.length][day];
            int[][] baseSolution = new int[Utils.employees.length][day];
            double penalty = countPenalty();
            double[][] plot = new double[101][1];
            int p = 0;
            long startTime = System.nanoTime();
            Utils.copySolutionMatrix(solution, newSolution);
            Utils.copySolutionMatrix(solution, baseSolution);
            for (int i = 0; i < 1000000; i++) {
                double epsilon = 1 / (Math.sqrt(i));
                if (Math.random() < epsilon) {
                    int llh = (int) (Math.random() * 3);
                    switch (llh) {
                        case 0:
                            Utils.twoExchange(solution);
                        case 1:
                            Utils.threeExchange(solution);
                        case 2:
                            Utils.doubleTwoExchange(solution);
                    }
                } else {
                    if (score2Exchange > score3Exchange && score2Exchange > scoredoubleExchange) {
                        Utils.twoExchange(solution);
                        if (countPenalty() <= penalty) {
                            if (score2Exchange < 1000) {
                                score2Exchange = score2Exchange + 10;
                            } else {
                                score2Exchange = 1000;
                            }
                        } else {
                            if (score2Exchange > 0) {
                                score2Exchange = score2Exchange - 10;
                            } else {
                                score2Exchange = 0;
                            }
                        }
                    }
                    if (score3Exchange > score2Exchange && score3Exchange > scoredoubleExchange) {
                        Utils.threeExchange(solution);
                        if (countPenalty() <= penalty) {
                            if (score3Exchange < 1000) {
                                score3Exchange = score3Exchange + 10;
                            } else {
                                score3Exchange = 1000;
                            }
                        } else {
                            if (score3Exchange > 0) {
                                score3Exchange = score3Exchange - 10;
                            } else {
                                score3Exchange = 0;
                            }
                        }
                    }
                    if (scoredoubleExchange > score2Exchange && scoredoubleExchange > score3Exchange) {
                        Utils.doubleTwoExchange(solution);
                        if (countPenalty() <= penalty) {
                            if (scoredoubleExchange < 1000) {
                                scoredoubleExchange = scoredoubleExchange + 10;
                            } else {
                                scoredoubleExchange = 1000;
                            }
                        } else {
                            if (scoredoubleExchange > 0) {
                                scoredoubleExchange = scoredoubleExchange - 10;
                            } else {
                                scoredoubleExchange = 0;
                            }
                        }
                    }
                    if (score2Exchange == score3Exchange && score2Exchange == scoredoubleExchange) {
                        if (Math.random() < 0.3) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (score2Exchange < 1000) {
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.3 && Math.random() < 0.6) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (score3Exchange < 1000) {
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.6) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (scoredoubleExchange < 1000) {
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                    if (score2Exchange == scoredoubleExchange && score2Exchange > score3Exchange) {
                        if (Math.random() < 0.5) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (score2Exchange < 1000) {
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (scoredoubleExchange < 1000) {
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                    if (score2Exchange == score3Exchange && score2Exchange > scoredoubleExchange) {
                        if (Math.random() < 0.5) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (score2Exchange < 1000) {
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (score3Exchange < 1000) {
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                    }
                    if (score3Exchange == scoredoubleExchange && score3Exchange > score2Exchange) {
                        if (Math.random() < 0.5) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (score3Exchange < 1000) {
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= penalty) {
                                if (scoredoubleExchange < 1000) {
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                }
//            System.out.println("2Exchange : " +score2Exchange+ "\t 3Exchange : " +score3Exchange+ "\t double : " +scoredoubleExchange);
                if (Utils.isFeasibleAllHC(solution) == 0) {
                    if (countPenalty() <= penalty) {
                        penalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                } else {
                    Utils.copySolutionMatrix(newSolution, solution);
                }

//                System.out.println("iterasi ke " + (i + 1) + " penalti : " + countPenalty());
//            System.out.println("score2Exchange : " + score2Exchange + " score3Exchange : " + score3Exchange + " double : " + scoredoubleExchange);
                if ((i + 1) % 10000 == 0) {
                    plot[p][0] = penalty;
                    p = p + 1;
                }
            }

            long endTime = System.nanoTime();
            long time = (endTime-startTime) / 1000000000;
            plot [100][0] = time;
            Utils.saveOptimation(plot, e);
            System.out.println(penalty);
            Utils.copySolutionMatrix(baseSolution, solution);

//            for (int j = 0; j < plot.length; j++) {
//                for (int k = 0; k < plot[j].length; k++) {
//                    System.out.print(plot[j][k] + " ");
//                }
//                System.out.println();
//            }
        }
    }

    public void simulatedAnnealing () {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [Utils.employees.length][day];
        Utils.copySolutionMatrix(solution, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = countPenalty();
        double TAwal = 10000000;
        double coolingrate = 0.99995;
        double diff = 0;
        double d = 0;
        double prob = 0;
        int p = 0;
        double [][] plot = new double [100][4];
//
        for (int i = 0; i < 1000000; i++) {
            int llh = (int) (Math.random() * 3);
            if (llh == 0)
                Utils.twoExchange(solution);
            if (llh == 1)
                Utils.threeExchange(solution);
            if (llh == 2)
                Utils.doubleTwoExchange(solution);
//            currPenalty = countPenalty();
            if (Utils.isFeasibleAllHC(solution) == 0) {
                diff = countPenalty() - currPenalty;
                d = Math.abs(diff)/TAwal;
                prob = Math.exp(-d);
//                System.out.println("feasible");
                if (countPenalty() <= currPenalty) {
                    currPenalty = countPenalty();
                    Utils.copySolutionMatrix(solution, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solution, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                } else {
                    if (prob >= Math.random()) {
//                        System.out.println("solusi jelek diterima");
                        currPenalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                }
            } else {
                Utils.copySolutionMatrix(newSolution, solution);
            }
            TAwal = TAwal * coolingrate;
//            System.out.println("Iterasi : " + (i+1) + /**" suhu : " + TAwal + " diff : " + diff + " d : " + d + " prob : " + prob + **/ " penalti : " + countPenalty());
            if ((i+1)%10000 == 0){
                plot[p][0] = i+1;
                plot[p][1] = TAwal;
                plot[p][2] = currPenalty;
                plot[p][3] = bestPenalty;
                p = p+1;
            }
        }
//        System.out.println(bestPenalty);
        for (int j = 0; j < plot.length; j++) {
            for (int k = 0; k < plot[j].length; k++) {
//                System.out.print(plot[j][k] + " ");
            }
            System.out.println();
        }
    }

    public void RL_SA () throws IOException {
        for (int e = 0; e < 3; e++) {
            int[] score = {500, 500, 500};
            int score2Exchange = score[0];
            int score3Exchange = score[1];
            int scoredoubleExchange = score[2];
            int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
            int[][] newSolution = new int[Utils.employees.length][day];
            int[][] baseSolution = new int[Utils.employees.length][day];
            double currPenalty;
            double bestPenalty;
            currPenalty = bestPenalty = countPenalty();
            double TAwal = 10000000;
            double coolingrate = 0.99995;
            double diff = 0;
            double prob = 0;
            double penalty = countPenalty();
            double[][] plot = new double[101][3];
            int p = 0;
            long startTime = System.nanoTime();
            Utils.copySolutionMatrix(solution, newSolution);
            Utils.copySolutionMatrix(solution, baseSolution);
            for (int i = 0; i < 1000000; i++) {
                double epsilon = 1 / (Math.sqrt(i));
                if (Math.random() < epsilon) {
                    int llh = (int) (Math.random() * 3);
                    switch (llh) {
                        case 0:
                            Utils.twoExchange(solution);
                        case 1:
                            Utils.threeExchange(solution);
                        case 2:
                            Utils.doubleTwoExchange(solution);
                    }
                } else {
                    if (score2Exchange > score3Exchange && score2Exchange > scoredoubleExchange) {
                        Utils.twoExchange(solution);
                        if (countPenalty() <= bestPenalty) {
                            if (score2Exchange < 1000) {
                                score2Exchange = score2Exchange + 10;
                            } else {
                                score2Exchange = 1000;
                            }
                        } else {
                            if (score2Exchange > 0) {
                                score2Exchange = score2Exchange - 10;
                            } else {
                                score2Exchange = 0;
                            }
                        }
                    }
                    if (score3Exchange > score2Exchange && score3Exchange > scoredoubleExchange) {
                        Utils.threeExchange(solution);
                        if (countPenalty() <= bestPenalty) {
                            if (score3Exchange < 1000) {
                                score3Exchange = score3Exchange + 10;
                            } else {
                                score3Exchange = 1000;
                            }
                        } else {
                            if (score3Exchange > 0) {
                                score3Exchange = score3Exchange - 10;
                            } else {
                                score3Exchange = 0;
                            }
                        }
                    }
                    if (scoredoubleExchange > score2Exchange && scoredoubleExchange > score3Exchange) {
                        Utils.doubleTwoExchange(solution);
                        if (countPenalty() <= bestPenalty) {
                            if (scoredoubleExchange < 1000) {
                                scoredoubleExchange = scoredoubleExchange + 10;
                            } else {
                                scoredoubleExchange = 1000;
                            }
                        } else {
                            if (scoredoubleExchange > 0) {
                                scoredoubleExchange = scoredoubleExchange - 10;
                            } else {
                                scoredoubleExchange = 0;
                            }
                        }
                    }
                    if (score2Exchange == score3Exchange && score2Exchange == scoredoubleExchange) {
                        if (Math.random() < 0.3) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score2Exchange < 1000) {
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.3 && Math.random() < 0.6) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score3Exchange < 1000) {
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.6) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (scoredoubleExchange < 1000) {
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                    if (score2Exchange == scoredoubleExchange && score2Exchange > score3Exchange) {
                        if (Math.random() < 0.5) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score2Exchange < 1000) {
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (scoredoubleExchange < 1000) {
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                    if (score2Exchange == score3Exchange && score2Exchange > scoredoubleExchange) {
                        if (Math.random() < 0.5) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score2Exchange < 1000) {
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score3Exchange < 1000) {
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                    }
                    if (score3Exchange == scoredoubleExchange && score3Exchange > score2Exchange) {
                        if (Math.random() < 0.5) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score3Exchange < 1000) {
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (scoredoubleExchange < 1000) {
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                }
//
//            System.out.println("2Exchange : " +score2Exchange+ "\t 3Exchange : " +score3Exchange+ "\t double : " +scoredoubleExchange);
//            System.out.println(currPenalty);
//            System.out.println(countPenalty());

//            System.out.println(prob);
                if (Utils.isFeasibleAllHC(solution) == 0) {
                    diff = countPenalty() - currPenalty;
                    prob = Math.exp(-(Math.abs(diff) / TAwal));
//                System.out.println("feasible");
                    if (countPenalty() <= currPenalty) {
                        currPenalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                        if (currPenalty <= bestPenalty) {
                            bestPenalty = currPenalty;
                            Utils.copySolutionMatrix(solution, newSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solution);
                        }
                    } else {
                        if (prob >= Math.random()) {
                            currPenalty = countPenalty();
                            Utils.copySolutionMatrix(solution, newSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solution);
                        }
                    }
                } else {
                    Utils.copySolutionMatrix(newSolution, solution);
                }
                TAwal = TAwal * coolingrate;
//                System.out.println("Iterasi : " + (i + 1) + /**" suhu : " + TAwal + " diff : " + diff +  " prob : " + prob + **/" penalti : " + countPenalty());
                if ((i + 1) % 10000 == 0) {
                    plot[p][0] = TAwal;
                    plot[p][1] = currPenalty;
                    plot[p][2] = bestPenalty;
                    p = p + 1;
                }
            }

            long endTime = System.nanoTime();
            long time = (endTime-startTime) / 1000000000;
            plot [100][0] = time;
            Utils.saveOptimation(plot, e);
//            System.out.println(penalty);
            Utils.copySolutionMatrix(baseSolution, solution);

//            System.out.println(bestPenalty);
//            for (int j = 0; j < plot.length; j++) {
//                for (int k = 1; k < plot[j].length; k++) {
//                    System.out.print(plot[j][k] + " ");
//                }
//                System.out.println();
//            }
        }
    }

    public static boolean stuck(double currPenalty, double previousCost, double currentStagnatCount) {
        double diff = currPenalty - previousCost;
        if (diff < 0.01) {
            currentStagnatCount = currentStagnatCount + 1;
        } else {
            currentStagnatCount = 0;
        }
        if (currentStagnatCount > 5) {
            return true;
        } else {
            return false;
        }
    }

    public void SAR() {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [Utils.employees.length][day];
        int [][] bestSolution = new int [Utils.employees.length][day];
        Utils.copySolutionMatrix(solution, newSolution);
        Utils.copySolutionMatrix(solution, bestSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = countPenalty();
        double TAwal = 10000000; //countPenalty() * 0.01;
        double coolingrate = 0.99995;
        double diff = 0;
        double d = 0;
        double prob = 0;
        double currentStagnantCount = 0;
        double previousCost = countPenalty();
        double stuckedBestCost = countPenalty();
        double stuckedCurrentCost = countPenalty();
        double [] prevCost = new double[1000000];

        double heat = 0;
        double[][] plot = new double[100][4];
        int p = 0;
//
        for (int i = 0; i < 1000000; i++) {
            int llh = (int) (Math.random() * 3);
            if (llh == 0)
                Utils.twoExchange(solution);
            if (llh == 1)
                Utils.threeExchange(solution);
            if (llh == 2)
                Utils.doubleTwoExchange(solution);
//            currPenalty = countPenalty();
            if (Utils.isFeasibleAllHC(solution) == 0) {
                diff = countPenalty() - currPenalty;
                d = Math.abs(diff)/TAwal;
                prob = Math.exp(-d);
//                System.out.println("feasible");
                if (countPenalty() <= currPenalty) {
                    currPenalty = countPenalty();
                    Utils.copySolutionMatrix(solution, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solution, newSolution);
                        Utils.copySolutionMatrix(solution, bestSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                } else {
                    if (prob >= Math.random()) {
//                        System.out.println("solusi jelek diterima");
                        currPenalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solution);
                    }
                }
                if (i > 0) {
                    if (Math.abs(currPenalty - prevCost[i-1]) <= 0.01) {
//                    System.out.println("stuck");
                        currentStagnantCount = currentStagnantCount + 1;
//                    System.out.println("stagnant " + currentStagnantCount);
                        if (currentStagnantCount >= 10000) {
                            Utils.copySolutionMatrix(bestSolution, solution);
                            if (bestPenalty == stuckedBestCost) {
                                if (currPenalty - stuckedCurrentCost < 0.02) {
                                    heat = heat + 1;
                                } else {
                                    heat = 0;
                                }
                            } else {
                                heat = 0;
                            }
                            currentStagnantCount = 0;
//                            System.out.println("heat " + heat);
                            TAwal = (heat * 0.2 * currPenalty + currPenalty) * 0.01;
                            stuckedBestCost = bestPenalty;
                            stuckedCurrentCost = currPenalty;
                        }
                        heat = 0;
                    }
                }
            } else {
                Utils.copySolutionMatrix(newSolution, solution);
            }
            TAwal = TAwal * coolingrate;
//            System.out.println("Iterasi : " + (i+1) + /**" suhu : " + TAwal + " diff : " + diff + " d : " + d + " prob : " + prob + **/ " penalti : " + countPenalty());
            if ((i+1)%10000 == 0) {
                plot[p][0] = i+1;
                plot[p][1] = TAwal;
                plot[p][2] = currPenalty;
                plot[p][3] = bestPenalty;
                p = p+1;
            }
            prevCost[i] = currPenalty;
        }
//        System.out.println(bestPenalty);
        for (int j = 0; j < plot.length; j++) {
            for (int k = 0; k < plot[j].length; k++) {
//                System.out.print(plot[j][k] + " ");
            }
//            System.out.println();
        }
    }

    public void RL_SAR() throws IOException {
        for (int e = 0; e < 3; e++) {
            int[] score = {500, 500, 500};
            int score2Exchange = score[0];
            int score3Exchange = score[1];
            int scoredoubleExchange = score[2];
            int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
            int[][] newSolution = new int[Utils.employees.length][day];
            int[][] baseSolution = new int[Utils.employees.length][day];
            double currPenalty;
            double bestPenalty;
            currPenalty = bestPenalty = countPenalty();
            double TAwal = 10000000;
            double coolingrate = 0.99995;
            double diff = 0;
            double prob = 0;
            double d = 0;
            double penalty = countPenalty();
            Utils.copySolutionMatrix(solution, newSolution);
            Utils.copySolutionMatrix(solution, baseSolution);
            double stuckedBestCost = 0;
            double stuckedCurrentCost = 0;
            double currentStagnantCount = 0;
            double heat = 0;
            int reheating = 0;
            double discountFactor = 0.9;
//        double epsilon = 0.1;
            double[] prevCost = new double[1000000];
            double[][] plot = new double[101][3];
            int p = 0;
            long startTime = System.nanoTime();
            for (int i = 0; i < 1000000; i++) {
                double epsilon = 1 / (Math.sqrt(i));
                if (Math.random() < epsilon) {
                    int llh = (int) (Math.random() * 3);
                    switch (llh) {
                        case 0:
                            Utils.twoExchange(solution);
                        case 1:
                            Utils.threeExchange(solution);
                        case 2:
                            Utils.doubleTwoExchange(solution);
                    }
                } else {
                    if (score2Exchange > score3Exchange && score2Exchange > scoredoubleExchange) {
                        Utils.twoExchange(solution);
                        if (countPenalty() <= bestPenalty) {
                            if (score2Exchange < 1000) {
//                            score2Exchange = (score2Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                            score2Exchange = (i * score2Exchange + 10) / i;
                                score2Exchange = score2Exchange + 10;
                            } else {
                                score2Exchange = 1000;
                            }
                        } else {
                            if (score2Exchange > 0) {
//                            score2Exchange = (score2Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                            score2Exchange = (i * score2Exchange - 10) / i;
                                score2Exchange = score2Exchange - 10;
                            } else {
                                score2Exchange = 0;
                            }
                        }
                    }
                    if (score3Exchange > score2Exchange && score3Exchange > scoredoubleExchange) {
                        Utils.threeExchange(solution);
                        if (countPenalty() <= bestPenalty) {
                            if (score3Exchange < 1000) {
//                            score3Exchange = (score3Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                            score3Exchange = (i * score3Exchange + 10) / i;
                                score3Exchange = score3Exchange + 10;
                            } else {
                                score3Exchange = 1000;
                            }
                        } else {
                            if (score3Exchange > 0) {
//                            score3Exchange = (score3Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                            score3Exchange = (i * score3Exchange - 10) / i;
                                score3Exchange = score3Exchange - 10;
                            } else {
                                score3Exchange = 0;
                            }
                        }
                    }
                    if (scoredoubleExchange > score2Exchange && scoredoubleExchange > score3Exchange) {
                        Utils.doubleTwoExchange(solution);
                        if (countPenalty() <= bestPenalty) {
                            if (scoredoubleExchange < 1000) {
//                            scoredoubleExchange = (scoredoubleExchange + 10) * (int) (Math.pow(discountFactor, i));
//                            scoredoubleExchange = (i * scoredoubleExchange + 10) / i;
                                scoredoubleExchange = scoredoubleExchange + 10;
                            } else {
                                scoredoubleExchange = 1000;
                            }
                        } else {
                            if (scoredoubleExchange > 0) {
//                            scoredoubleExchange = (scoredoubleExchange - 10) * (int) (Math.pow(discountFactor, i));
//                            scoredoubleExchange = (i * scoredoubleExchange - 10) / i;
                                scoredoubleExchange = scoredoubleExchange - 10;
                            } else {
                                scoredoubleExchange = 0;
                            }
                        }
                    }
                    if (score2Exchange == score3Exchange && score2Exchange == scoredoubleExchange) {
                        if (Math.random() < 0.3) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score2Exchange < 1000) {
//                                score2Exchange = (score2Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                                score2Exchange = (i * score2Exchange + 10) / i;
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
//                                score2Exchange = (score2Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                                score2Exchange = (i * score2Exchange - 10) / i;
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.3 && Math.random() < 0.6) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score3Exchange < 1000) {
//                                score3Exchange = (score3Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                                score3Exchange = (i * score3Exchange + 10) / i;
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
//                                score3Exchange = (score3Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                                score3Exchange = (i * score3Exchange - 10) / i;
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.6) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (scoredoubleExchange < 1000) {
//                                scoredoubleExchange = (scoredoubleExchange + 10) * (int) (Math.pow(discountFactor, i));
//                                scoredoubleExchange = (i * scoredoubleExchange + 10) / i;
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
//                                scoredoubleExchange = (scoredoubleExchange - 10) * (int) (Math.pow(discountFactor, i));
//                                scoredoubleExchange = (i * scoredoubleExchange - 10) / i;
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                    if (score2Exchange == scoredoubleExchange && score2Exchange > score3Exchange) {
                        if (Math.random() < 0.5) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score2Exchange < 1000) {
//                                score2Exchange = (score2Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                                score2Exchange = (i * score2Exchange + 10) / i;
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
//                                score2Exchange = (score2Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                                score2Exchange = (i * score2Exchange - 10) / i;
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (scoredoubleExchange < 1000) {
//                                scoredoubleExchange = (scoredoubleExchange + 10) * (int) (Math.pow(discountFactor, i));
//                                scoredoubleExchange = (i * scoredoubleExchange + 10) / i;
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
//                                scoredoubleExchange = (scoredoubleExchange -10) * (int) (Math.pow(discountFactor, i));
//                                scoredoubleExchange = (i * scoredoubleExchange - 10) / i;
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                    if (score2Exchange == score3Exchange && score2Exchange > scoredoubleExchange) {
                        if (Math.random() < 0.5) {
                            Utils.twoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score2Exchange < 1000) {
//                                score2Exchange = (score2Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                                score2Exchange = (i * score2Exchange + 10) / i;
                                    score2Exchange = score2Exchange + 10;
                                } else {
                                    score2Exchange = 1000;
                                }
                            } else {
                                if (score2Exchange > 0) {
//                                score2Exchange = (score2Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                                score2Exchange = (i * score2Exchange - 10) / i;
                                    score2Exchange = score2Exchange - 10;
                                } else {
                                    score2Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score3Exchange < 1000) {
//                                score3Exchange = (score3Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                                score3Exchange = (i * score3Exchange + 10) / i;
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
//                                score3Exchange = (score3Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                                score3Exchange = (i * score3Exchange - 10) / i;
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                    }
                    if (score3Exchange == scoredoubleExchange && score3Exchange > score2Exchange) {
                        if (Math.random() < 0.5) {
                            Utils.threeExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (score3Exchange < 1000) {
//                                score3Exchange = (score3Exchange + 10) * (int) (Math.pow(discountFactor, i));
//                                score3Exchange = (i * score3Exchange + 10) / i;
                                    score3Exchange = score3Exchange + 10;
                                } else {
                                    score3Exchange = 1000;
                                }
                            } else {
                                if (score3Exchange > 0) {
//                                score3Exchange = (score3Exchange - 10) * (int) (Math.pow(discountFactor, i));
//                                score3Exchange = (i * score3Exchange - 10) / i;
                                    score3Exchange = score3Exchange - 10;
                                } else {
                                    score3Exchange = 0;
                                }
                            }
                        }
                        if (Math.random() > 0.5) {
                            Utils.doubleTwoExchange(solution);
                            if (countPenalty() <= bestPenalty) {
                                if (scoredoubleExchange < 1000) {
//                                scoredoubleExchange = (scoredoubleExchange + 10) * (int) (Math.pow(discountFactor, i));
//                                scoredoubleExchange = (i * scoredoubleExchange + 10) / i;
                                    scoredoubleExchange = scoredoubleExchange + 10;
                                } else {
                                    scoredoubleExchange = 1000;
                                }
                            } else {
                                if (scoredoubleExchange > 0) {
//                                scoredoubleExchange = (scoredoubleExchange - 10) * (int) (Math.pow(discountFactor, i));
//                                scoredoubleExchange = (i * scoredoubleExchange - 10) / i;
                                    scoredoubleExchange = scoredoubleExchange - 10;
                                } else {
                                    scoredoubleExchange = 0;
                                }
                            }
                        }
                    }
                }
//            System.out.println("2Exchange : " +score2Exchange+ "\t 3Exchange : " +score3Exchange+ "\t double : " +scoredoubleExchange);
//            System.out.println(currPenalty);
//            System.out.println(countPenalty());

//            System.out.println(prob);
                if (Utils.isFeasibleAllHC(solution) == 0) {
                    diff = countPenalty() - currPenalty;
                    d = Math.abs(diff) / TAwal;
                    prob = Math.exp(-d);
//                System.out.println("feasible");
                    if (countPenalty() <= currPenalty) {
                        currPenalty = countPenalty();
                        Utils.copySolutionMatrix(solution, newSolution);
                        if (currPenalty <= bestPenalty) {
                            bestPenalty = currPenalty;
                            Utils.copySolutionMatrix(solution, newSolution);
//                        Schedule.copyArray(solution, bestSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solution);
                        }
                    } else {
                        if (prob >= Math.random()) {
//                        System.out.println("solusi jelek diterima");
                            currPenalty = countPenalty();
                            Utils.copySolutionMatrix(solution, newSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solution);
                        }
                    }
                    if (i > 0 && reheating <= 5) {
                        if (Math.abs(currPenalty - prevCost[i - 1]) <= 0.01) {
//                    System.out.println("stuck");
                            currentStagnantCount = currentStagnantCount + 1;
//                    System.out.println("stagnant " + currentStagnantCount);
                            if (currentStagnantCount >= 50000) {
//                            Schedule.copyArray(bestSolution, solution);
                                if (bestPenalty == stuckedBestCost) {
                                    if (currPenalty - stuckedCurrentCost < 0.02) {
                                        heat = heat + 1;
                                    } else {
                                        heat = 0;
                                    }
                                } else {
                                    heat = 0;
                                }
                                currentStagnantCount = 0;
//                            System.out.println("heat " + heat);
                                TAwal = (heat * 0.2 * currPenalty + currPenalty) * 0.01;
                                stuckedBestCost = bestPenalty;
                                stuckedCurrentCost = currPenalty;
                                reheating++;
                            }
                            heat = 0;
                        }
                    }
                } else {
                    Utils.copySolutionMatrix(newSolution, solution);
                }
                TAwal = TAwal * coolingrate;
//                System.out.println("Iterasi : " + (i + 1) + /**" suhu : " + TAwal + " diff : " + diff +  " prob : " + prob + **/" penalti : " + countPenalty());
                if ((i + 1) % 10000 == 0) {
                    plot[p][0] = TAwal;
                    plot[p][1] = currPenalty;
                    plot[p][2] = bestPenalty;
                    p = p + 1;
                }
                prevCost[i] = currPenalty;
            }

            long endTime = System.nanoTime();
            long time = (endTime-startTime) / 1000000000;
            plot [100][0] = time;
            Utils.saveOptimation(plot, e);
//            System.out.println(penalty);
            Utils.copySolutionMatrix(baseSolution, solution);
//            System.out.println(bestPenalty);
//            for (int j = 0; j < plot.length; j++) {
//                for (int k = 1; k < plot[j].length; k++) {
//                    System.out.print(plot[j][k] + " ");
//                }
//                System.out.println();
//            }
        }
    }
}
