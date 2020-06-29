package org.cis.optur.engine.commons;

import java.util.LinkedList;

public class Sn {
    IterationListener iterationListener;

    public int [][] solutionMatrix;
    public double penalty = 0.0D;
    public double[] ps = new double[9];

    public Sn(int [][] solutionMatrix) {
        this.solutionMatrix = solutionMatrix;
        this.ps[0] = penalty1();
        this.ps[1] = penalty2();
        this.ps[2] = penalty3();
        this.ps[3] = penalty4();
        this.ps[4] = penalty5();
        this.ps[5] = penalty6();
        this.ps[6] = penalty7();
        this.ps[7] = penalty8();
        this.ps[8] = penalty9();
        for (double p : ps) {
            this.penalty+=p;
        }
    }

    public int[][] getSolutionMatrix(){
        return solutionMatrix;
    }

    public void setSolutionMatrix(int[][] solutionMatrix) {
        this.penalty = 0.0D;
        this.solutionMatrix = solutionMatrix;
    }

    public double getCandidatePenalty() {
        return penalty1() + penalty2() + penalty3() + penalty4() + penalty5() +
                penalty6() + penalty7() + penalty8() + penalty9();
    }

    public double getPenalty() {
        return penalty;
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
                        if (solutionMatrix[j][l] != 0) {
                            if (Utils.shifts[solutionMatrix[j][l]-1].getCategory() == (i+1)) {
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
                        if (solutionMatrix[i][k] > 0)
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
                    if (solutionMatrix[j][k+1] != 0) {
                        if (Utils.shifts[solutionMatrix[j][k+1]-1].getCategory() == (i+1)) {
                            if (solutionMatrix[j][k] == 0 || Utils.shifts[solutionMatrix[j][k] - 1].getCategory() != (i + 1)) {
                                double calculate = 0;
                                for (int l = k + 1; l <= k + softConstraint; l++) {
                                    int count = 1;
                                    for (int m = k + 1; m <= k + softConstraint; m++) {
                                        if (solutionMatrix[j][m] == 0 || Utils.shifts[solutionMatrix[j][m] - 1].getCategory() != (i + 1))
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
                    if (solutionMatrix[i][j+1] != 0 && solutionMatrix[i][j] == 0) {
                        double calculate = 0;
                        for (int k = j+1; k <= j+softConstraint; k++) {
                            int count = 1;
                            for (int l = j+1; l <= j+softConstraint; l++) {
                                if (solutionMatrix[i][l] == 0)
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
                    if (solutionMatrix[j][k] != 0)
                        if (Utils.shifts[solutionMatrix[j][k]-1].getCategory() == (i+1))
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
                    if (solutionMatrix[i][j] != 0)
                        workingHour = workingHour + Utils.shifts[solutionMatrix[i][j]-1].getDuration(j % 7);
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
                    if (solutionMatrix[i][j] != 0 && solutionMatrix[i][j+1] == 0)
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
                            if (solutionMatrix[i][j] != 0) {
                                if (Utils.shifts[solutionMatrix[i][j]-1].getName().equals(Utils.want[k].pattern[0])) {
                                    if (j <= day - (Utils.want[k].pattern.length)) {
                                        int count = Utils.want[k].pattern.length;
                                        for (int l = 0; l < Utils.want[k].pattern.length; l++) {
                                            if (solutionMatrix[i][j+l] != 0) {
                                                if (Utils.shifts[solutionMatrix[i][j + l] - 1].getName().equals(Utils.want[k].pattern[l])) {
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
                            if (solutionMatrix[i][j] != 0) {
                                if (Utils.shifts[solutionMatrix[i][j]-1].getName().equals(Utils.unwant[k].pattern[0])) {
                                    if (j <= day - (Utils.unwant[k].pattern.length)) {
                                        int count = Utils.unwant[k].pattern.length;
                                        for (int l = 0; l < Utils.unwant[k].pattern.length; l++) {
                                            if (solutionMatrix[i][j+l] != 0) {
                                                if (Utils.shifts[solutionMatrix[i][j + l] - 1].getName().equals(Utils.unwant[k].pattern[l])) {
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

    public OptimationResult HC2(int iteration, int penaltyRecordRange) {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] bestSolution = new int [Utils.employees.length][day];
        double bestPenalty = getCandidatePenalty();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int[][] backupSol = new int [Utils.employees.length][day];
            Utils.copySolutionMatrix(solutionMatrix, backupSol);
            int rand = (int) (Math.random() * 3);
            int llh = rand;

            if (llh == 0) Utils.twoExchange(solutionMatrix);
            else if (llh == 1) Utils.threeExchange(solutionMatrix);
            else Utils.doubleTwoExchange(solutionMatrix);

            double currPenalty = getCandidatePenalty();
            if (Utils.isFeasibleAllHC(solutionMatrix) == 0 && bestPenalty > currPenalty ) {
                bestPenalty = currPenalty;
                Utils.copySolutionMatrix(solutionMatrix, bestSolution);
            }else {
                Utils.copySolutionMatrix(backupSol, solutionMatrix);
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSolution, bestPenalty, Utils.file);
    }

    public OptimationResult SA2(int initialTemp, double coolingRate, int iteration, int penaltyRecordRange) {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [Utils.employees.length][day];
        Utils.copySolutionMatrix(solutionMatrix, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = getCandidatePenalty();
        int[][] bestSol = new int[newSolution.length][newSolution[0].length];
        double TAwal = initialTemp;
        double delta = 0;
        double d;
        double prob = 0;
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand = (int) (Math.random() * 3);
            int llh = rand;
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
                delta = getCandidatePenalty() - currPenalty;
                d = Math.abs(delta)/TAwal;
                prob = Math.exp(-d);
                if (getCandidatePenalty() <= currPenalty) {
                    currPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSol);
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        if (prob >= Math.random()) {
                            currPenalty = getCandidatePenalty();
                            Utils.copySolutionMatrix(solutionMatrix, newSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solutionMatrix);
                        }
                    }
                } else {
                    if (prob >= Math.random()) {
                        currPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solutionMatrix);
                    }
                }
            } else {
                Utils.copySolutionMatrix(newSolution, solutionMatrix);
            }
            TAwal = TAwal * coolingRate;
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Utils.file);
    }

    public OptimationResult T2(int iteration, int tabuListLength, int penaltyRecordRange) {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [Utils.employees.length][day];
        Utils.copySolutionMatrix(solutionMatrix, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = getCandidatePenalty();
        int[][] bestSol = new int[newSolution.length][newSolution[0].length];
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

            int llh = rand;
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
                if (getCandidatePenalty() <= currPenalty) {
                    currPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSol);
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    }
                } else {
                    if(tabuList.size()==tabuListLength){
                        tabuList.pollLast();
                        tabuList.offerFirst(llh);
                    }else {
                        tabuList.offerFirst(llh);
                    }
                    currPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolution);
                }
            } else {
                if(tabuList.size()==tabuListLength){
                    tabuList.pollLast();
                    tabuList.offerFirst(llh);
                }else {
                    tabuList.addFirst(llh);
                }
                Utils.copySolutionMatrix(newSolution, solutionMatrix);
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Utils.file);
    }

    public OptimationResult TSA2(int initialTemp, double coolingRate, int iteration, int tabuListLength, int penaltyRecordRange) {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [Utils.employees.length][day];
        Utils.copySolutionMatrix(solutionMatrix, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = getCandidatePenalty();
        int[][] bestSol = new int[newSolution.length][newSolution[0].length];
        double TAwal = initialTemp;
        double delta = 0;
        double d;
        double prob = 0;
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

            int llh = rand;
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
                delta = getCandidatePenalty() - currPenalty;
                d = Math.abs(delta)/TAwal;
                prob = Math.exp(-d);
                if (getCandidatePenalty() <= currPenalty) {
                    currPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSol);
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        if (prob >= Math.random()) {
                            if(tabuList.size()==tabuListLength){
                                tabuList.pollLast();
                                tabuList.offerFirst(llh);
                            }else {
                                tabuList.offerFirst(llh);
                            }
                            currPenalty = getCandidatePenalty();
                            Utils.copySolutionMatrix(solutionMatrix, newSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solutionMatrix);
                        }
                    }
                } else {
                    if (prob >= Math.random()) {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solutionMatrix);
                    }
                }
            } else {
                if(tabuList.size()==tabuListLength){
                    tabuList.pollLast();
                    tabuList.offerFirst(llh);
                }else {
                    tabuList.addFirst(llh);
                }
                Utils.copySolutionMatrix(newSolution, solutionMatrix);
            }
            TAwal = TAwal * coolingRate;
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
//                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Utils.file);
    }

    public OptimationResult TSAR2(int initialTemp, double coolingRate, int iteration, int tabuListLength, int penaltyRecordRange, double reHeatRate, int reHeatRange) {
        int day = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolution = new int [Utils.employees.length][day];
        Utils.copySolutionMatrix(solutionMatrix, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = getCandidatePenalty();
        int[][] bestSol = new int[newSolution.length][newSolution[0].length];
        double TAwal = initialTemp;
        double delta = 0;
        double d;
        double prob = 0;
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

            int llh = rand;
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
                delta = getCandidatePenalty() - currPenalty;
                d = Math.abs(delta)/TAwal;
                prob = Math.exp(-d);
                if (getCandidatePenalty() <= currPenalty) {
                    currPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSol);
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        if (prob >= Math.random()) {
                            if(tabuList.size()==tabuListLength){
                                tabuList.pollLast();
                                tabuList.offerFirst(llh);
                            }else {
                                tabuList.offerFirst(llh);
                            }
                            currPenalty = getCandidatePenalty();
                            Utils.copySolutionMatrix(solutionMatrix, newSolution);
                        } else {
                            Utils.copySolutionMatrix(newSolution, solutionMatrix);
                        }
                    }
                } else {
                    if (prob >= Math.random()) {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolution);
                    } else {
                        Utils.copySolutionMatrix(newSolution, solutionMatrix);
                    }
                }
            } else {
                if(tabuList.size()==tabuListLength){
                    tabuList.pollLast();
                    tabuList.offerFirst(llh);
                }else {
                    tabuList.addFirst(llh);
                }
                Utils.copySolutionMatrix(newSolution, solutionMatrix);
            }
            TAwal = TAwal * coolingRate;
            if((i+1)%reHeatRange == 0){
                TAwal = TAwal + (TAwal*reHeatRate);
                System.out.println("ReHeat!");
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
//                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Utils.file);
    }
}