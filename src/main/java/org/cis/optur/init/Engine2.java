package org.cis.optur.init;

import org.cis.optur.compile.model.Employee;
import org.cis.optur.compile.model.Shift;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Engine2 {
    private Employee[] employees;
    private Shift[] shifts;
    private String[] shiftNames;
    private Matrix matrix;
    private int[][] mPNM;
    private int dayLength;
    private State state;
    public static int loopIndex = 0;
    public Engine2(Employee[] employees, Shift[] shifts, int[][] mPNM, int dayLength) throws Exception {

        this.employees = employees;
        this.shifts = shifts;
        this.shiftNames = new String[shifts.length];
        for (int i = 0; i<shiftNames.length; i++){
            this.shiftNames[i] = shifts[i].getName();
        }
        this.mPNM = mPNM;
        this.dayLength = dayLength;

        int[] employeeQueue = {14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 3, 4, 2, 1, 0};
        int[] shiftQueue = {4, 0, 1, 2, 3, 5};
        int[] dayQueue = new int[dayLength];
        ArrayList<Integer> dayQueueArrList = new ArrayList<>();

        for (int i = 0; i<(dayLength/7); i++){
            int sabtu = i*7+5;
            int minggu = i*7+6;
            dayQueueArrList.add(sabtu);
            dayQueueArrList.add(minggu);
        }

        for (int i = 0; i< dayLength; i++){
            if(dayQueueArrList.contains(i))continue;
            dayQueueArrList.add(i);
        }

        for (int i = 0; i<dayQueueArrList.size(); i++){
            dayQueue[i] = dayQueueArrList.get(i);
        }
        state = new State(employeeQueue, dayQueue, shiftQueue);
        this.matrix = new Matrix(employees.length, dayLength, Constants.FREE_SHIFT_NAME, shiftNames);
    }

    public void run(){
        while (true){
            loopIndex++;
            HCError errorHc2 = getHc2Error();
            HCError errorHc4 = getHc4Error();
            HCError errorHc7 = getHc7Error();
            HCError errorHc5 = getHc5Error();

            //jika hc 2 tidak error
            if(errorHc2==null&&errorHc4==null&&errorHc7==null&&errorHc5==null){
                //assign
                matrix.assign(new Assignment(state.getEmployeePointer(), state.getDayPointer(), shiftNames[state.getShiftPointer()]));
                state.resetShiftPointer();
                state.iEmployeePointer();
            }else {
                if(state.iShiftPointer()){
                    state.iEmployeePointer();
                }
            }
            if(isMatchMPNM()&&state.iDayPointer()){
                break;
            }
        }
        done();
        hc3();
        hc6();
    }

    private void hc6() {
        while (true){
            System.out.println("Loop index = " + loopIndex);
            while (true){
                int minIndex = (int) Math.floor(Math.random()*dayLength);
                int maxIndex = (int) Math.floor(Math.random()*dayLength);
                if(tryToSwap(minIndex, maxIndex)){
                    break;
                }
            }
            if(isFeasible()){
                break;
            }
        }


        System.out.println("=================================================================================");
        matrix.printMatrix();
        System.out.println("=================================================================================");
    }

    private void hc3() {
        while (true){
            System.out.println("Loop index = " + loopIndex);
            double[] employeesWeeklyAvg = new double[employees.length];
            double[] employeesWeeklyBaseLine = new double[employees.length];
            double[] employeesWeeklyDiff = new double[employees.length];
            double[] employeesWeeklyDiffP = new double[employees.length];
            double[] employeesWeeklyBaseLineP = new double[employees.length];

            //rata2 jam kerja existing employee tiap minggu nya, jadi tiap minggu di sum dulu, hasil sum nya jadi 1 item
            for (int i = 0; i < employeesWeeklyAvg.length; i++){
                 String[] employeeShifts = matrix.getModel()[i];
                 double employeeHourTotal = 0.0;
                 for (int j = 0; j < employeeShifts.length; j++){
                     String shiftName = employeeShifts[j];
                     double employeeHour = Utils.getWorkHour(shifts, shiftName, j%7);
                     employeeHourTotal+=employeeHour;
                 }
                 employeesWeeklyAvg[i] = employeeHourTotal/(dayLength/7);
            }

            ////rata2 weekly jam kerja seharusnya
            for (int i = 0; i<employees.length; i++){
                Employee employee = employees[i];
                employeesWeeklyBaseLine[i] = employee.getWorkWeek();
            }

            //DIFF = AVG - BASE
            for (int i = 0; i<employees.length; i++){
                double diff = employeesWeeklyAvg[i] - employeesWeeklyBaseLine[i];
                employeesWeeklyDiff[i] = diff;
            }

            //presentase
            for (int i = 0; i<employees.length; i++){
                double p = employeesWeeklyDiff[i]*100/employeesWeeklyBaseLine[i];
                employeesWeeklyDiffP[i] = p;
            }

            for (int i = 0; i<employees.length; i++){
                double p = employeesWeeklyBaseLine[i]/50.0;
                employeesWeeklyBaseLineP[i] = p;
            }

            System.out.format("%15s%15s%15s%15s%15s\n", "E", "BASE_AVG", "AVG", "DIFF", "BASE_DIFF");
            for (int i = 0; i<employeesWeeklyAvg.length; i++){
                System.out.format("%15d%15f%15f%15f%15f\n", i, employeesWeeklyBaseLine[i], employeesWeeklyAvg[i], employeesWeeklyDiff[i], employeesWeeklyBaseLineP[i]);
            }
            matrix.printMatrix();

            while (true){
                int minIndex = Utils.getRandomIndex(employeesWeeklyDiff);
                int maxIndex = Utils.getRandomIndex(employeesWeeklyDiff);
                if(Math.abs(employeesWeeklyDiff[minIndex])<employeesWeeklyDiffP[minIndex])
                if(employeesWeeklyDiff[minIndex]<employeesWeeklyDiff[maxIndex]){
                    if(tryToSwap(minIndex, maxIndex)){
                        break;
                    }
                }
            }
            if(loopIndex>50000){
                break;
            }
        }
    }


    private boolean tryToSwap(int minIndex, int maxIndex) {
        if (twoExchange(minIndex, maxIndex)){
            return true;
        }else if(doubleTwoExchange(minIndex, maxIndex)){
            return true;
        }else if(thereeExchange(minIndex, maxIndex)){
            return true;
        }else {
            return false;
        }
    }

    private boolean doubleTwoExchange(int minIndex, int maxIndex) {
        int workDayIndexOfMin = -1;
        int workDayIndexOfMax = -1;
        int freeDayIndexOfMin = -1;
        int freeDayIndexOfMax = -1;
        String shiftName = null;
        for (int i = 0; i<dayLength; i++){
            if(matrix.getModel()[minIndex][i].equals(Constants.FREE_SHIFT_NAME)){
                shiftName = matrix.getModel()[maxIndex][i];
                for (int j = 0; j<dayLength; j++){
                    if(matrix.getModel()[minIndex][j].equals(shiftName)){
                        if(matrix.getModel()[maxIndex][j].equals(Constants.FREE_SHIFT_NAME)){
                           freeDayIndexOfMin = i;
                           workDayIndexOfMax = i;
                           freeDayIndexOfMax = j;
                           workDayIndexOfMin = j;
                        }
                    }
                }
            }
        }
        if(workDayIndexOfMax==-1||workDayIndexOfMin==-1||freeDayIndexOfMax==-1||freeDayIndexOfMin==-1){
            return false;
        }
        String[][] backupModel = matrix.cloneModel();
        String[][] clonedModel = matrix.cloneModel();


        String temp = clonedModel[minIndex][freeDayIndexOfMin];
        clonedModel[minIndex][freeDayIndexOfMin] = clonedModel[maxIndex][workDayIndexOfMax];
        clonedModel[maxIndex][workDayIndexOfMax] = temp;


        String temp2 = clonedModel[minIndex][workDayIndexOfMin];
        clonedModel[minIndex][workDayIndexOfMin] = clonedModel[maxIndex][freeDayIndexOfMax];
        clonedModel[maxIndex][freeDayIndexOfMax] = temp2;

        matrix.setModel(clonedModel);
        if(isFeasible()){
            return true;
        }else {
            matrix.setModel(backupModel);
            return false;
        }
    }

    private boolean thereeExchange(int minIndex, int maxIndex) {
        String[][] backupModel = matrix.cloneModel();
        String[][] clonedModel = matrix.cloneModel();
        int freeDayIndexOfMin = -1;
        int workDayIndexOfMax = -1;
        for (int i = 0; i < dayLength; i++){
            if(matrix.getModel()[minIndex][i].equals(Constants.FREE_SHIFT_NAME)&&!matrix.getModel()[maxIndex][i].equals(Constants.FREE_SHIFT_NAME)){
                freeDayIndexOfMin = i;
                workDayIndexOfMax = i;
            }
        }
        if(freeDayIndexOfMin==-1||workDayIndexOfMax==-1) return false;

        String nonFreeShiftnName = clonedModel[maxIndex][workDayIndexOfMax];
        clonedModel[minIndex][freeDayIndexOfMin] = nonFreeShiftnName;
        clonedModel[maxIndex][workDayIndexOfMax] = Constants.FREE_SHIFT_NAME;
        matrix.setModel(clonedModel);
        if(isFeasible()){
            return true;
        }else {
            matrix.setModel(backupModel);
            return false;
        }
    }

    private boolean twoExchange(int minIndex, int maxIndex) {
        String[][] backupModel = matrix.cloneModel();
        String[][] clonedModel = matrix.cloneModel();
        int freeDayIndexOfMin = -1;
        int workDayIndexOfMax = -1;
        for (int i = 0; i < dayLength; i++){
            if(matrix.getModel()[minIndex][i].equals(Constants.FREE_SHIFT_NAME)&&!matrix.getModel()[maxIndex][i].equals(Constants.FREE_SHIFT_NAME)){
                freeDayIndexOfMin = i;
                workDayIndexOfMax = i;
            }
        }
        if(freeDayIndexOfMin==-1||workDayIndexOfMax==-1) return false;

        String nonFreeShiftnName = clonedModel[maxIndex][workDayIndexOfMax];
        clonedModel[minIndex][freeDayIndexOfMin] = nonFreeShiftnName;
        clonedModel[maxIndex][workDayIndexOfMax] = Constants.FREE_SHIFT_NAME;
        matrix.setModel(clonedModel);
        if(isFeasible()){
            return true;
        }else {
            matrix.setModel(backupModel);
            return false;
        }
    }

    private int getRandomWorkDayOf(int employeeIndex) {
        String[] shiftNames = matrix.getModel()[employeeIndex];
        HashSet<Integer> integerHashSet = new HashSet<>();
        for(int i = 0; i < shiftNames.length; i++){
            if(!shiftNames[i].equals(Constants.FREE_SHIFT_NAME)){
                integerHashSet.add(i);
            }
        }
        return Utils.getRandomElement(integerHashSet);
    }

    private int getRandomFreeDayOf(int employeeIndex) {
        String[] shiftNames = matrix.getModel()[employeeIndex];
        HashSet<Integer> integerHashSet = new HashSet<>();
        for(int i = 0; i < shiftNames.length; i++){
            if(shiftNames[i].equals(Constants.FREE_SHIFT_NAME)){
                integerHashSet.add(i);
            }
        }
        return Utils.getRandomElement(integerHashSet);
    }

    private boolean isMatchMPNM() {
        int[][] shiftCountPerDay = matrix.getShiftCountRecap();
        for (int i = 0; i<shiftCountPerDay.length; i++){
            if(shiftCountPerDay[i][state.getDayPointer()]!=mPNM[i][state.getDayPointer()%7]){
                return false;
            }
        }
        return true;
    }

    private HCError getHc5Error(){
        if(state.getDayPointer()==0){
            return null;
        }

        String shift1Name = matrix.getModel()[state.getEmployeePointer()][state.getDayPointer()-1];
        String shift2Name = shiftNames[state.getShiftPointer()];



        //cek 1 sekarang dan sebelum
        if(!shift1Name.equals(Constants.FREE_SHIFT_NAME)){
            Shift shift1 = Utils.getShiftByName(shifts, shift1Name);
            Shift shift2 = Utils.getShiftByName(shifts, shift2Name);
            MyHour gap = Utils.getHourGapBetWeen(shift1, shift2);
            if((gap.getHour()>=Constants.MAX_HOUR_GAP)){
                return null;
            }
            return new HCError("HC5", "E: " + state.getEmployeePointer() + ", S: " + shift1Name + " => " + shift2Name + ", gap = " + gap, state);
        }
        //cek sekarang dan depan
        if((state.getDayPointer()+1)!=dayLength){
            String shift3Name = matrix.getModel()[state.getEmployeePointer()][state.getDayPointer()+1];
            if(!shift3Name.equals(Constants.FREE_SHIFT_NAME)){
                Shift shift1 = Utils.getShiftByName(shifts, shift2Name);
                Shift shift2 = Utils.getShiftByName(shifts, shift3Name);
                MyHour gap = Utils.getHourGapBetWeen(shift1, shift2);
                if((gap.getHour()>=Constants.MAX_HOUR_GAP)){
                    return null;
                }
                return new HCError("HC5", "E: " + state.getEmployeePointer() + ", S: " + shift1Name + " => " + shift2Name + ", gap = " + gap, state);
            }
        }
        return null;
    }

    private HCError getHc7Error(){
        Map<Integer, String> dayShiftInWeek = new HashMap<>();
        int startDay = state.getWeekPointer()*7;
        int endDay = state.getWeekPointer()*7+7;
        double weekWorkHour = 0.0;
        for (int i = startDay; i<endDay; i++){
            String shiftName = matrix.getModel()[state.getEmployeePointer()][i];
            int dayInWeek = i%7;
            weekWorkHour+= Utils.getWorkHour(shifts, shiftName, dayInWeek);
        }
        double willAssignShiftWorkHour = Utils.getWorkHour(shifts, shiftNames[state.getShiftPointer()], state.getDayPointerInWeek());
        if((weekWorkHour+willAssignShiftWorkHour)>Constants.MAX_WORK_HOUR_IN_WEEK){
            return new HCError("HC7", "Employee " + state.getEmployeePointer() + " sudah punya " + weekWorkHour + " jam , kalo ditambah " + willAssignShiftWorkHour + " bakalan lebih dari " + Constants.MAX_WORK_HOUR_IN_WEEK + " jam", state);
        }
        return null;
    }

    private HCError getHc4Error() {
        HCError competence = hc4Competence();
        HCError workWeek = hc4WorkWeekend();

        if(competence!=null) return competence;
        if(workWeek!=null)  return workWeek;
        return null;
    }

    private HCError hc4WorkWeekend() {
        if(state.getDayPointer()%7<5){
            return null;
        }
        int[] workWeekend = employees[state.getEmployeePointer()].getWorkingWeekends();
        int weekPointer = state.getDayPointer()/7;
        for (int i = 0; i<workWeekend.length; i++){
            if((workWeekend[i])==weekPointer+1){
                return null;
            }
        }
        return new HCError("HC4", "Day: " + state.getDayPointer() + ", Employee: " + state.getEmployeePointer() + ", week: " + weekPointer, state);
    }

    private HCError hc4Competence(){
        String competenceHad = employees[state.getEmployeePointer()].getCompetence();
        String competenceNeeded = shifts[state.getShiftPointer()].getCompetenceNeeded();
        if(competenceNeeded==null){
            return null;
        }
        if(!competenceNeeded.equals(competenceHad)){
            return new HCError("HC4", "need: " + competenceNeeded + " ! = " + "had: " + competenceHad, state);
        }
        return null;
    }

    private void done() {
        matrix.printMatrix();
        HcTest hcTest = new HcTest(matrix, mPNM, employees, shifts, shiftNames);
        hcTest.HC2Test();
        hcTest.HC4Test();
        hcTest.HC7Test();
        hcTest.HC5Test();
    }

    private boolean isFeasible()  {
        HcTest hcTest = new HcTest(matrix, mPNM, employees, shifts, shiftNames);
        try {
            hcTest.HC2Test();
            hcTest.HC4Test();
            hcTest.HC7Test();
            hcTest.HC5Test();
            hcTest.HC6Test();
        }catch (Error e){
            if(e.getMessage().startsWith("HC")){
                return false;
            }
            throw e;
        }
        return true;
    }

    private HCError getHc2Error() {
        int[][] shiftCountRecap = matrix
                .getShiftCountRecap();
        if(shiftCountRecap[state.getShiftPointer()][state.getDayPointer()]<mPNM[state.getShiftPointer()][state.getDayPointer()%7]){
           return null;
        }
        return new HCError("HC2", String.format("Shift %s sudah mencapai plan pada day ke %d", state.getShiftPointer(), state.getDayPointer()), state);
    }
}
