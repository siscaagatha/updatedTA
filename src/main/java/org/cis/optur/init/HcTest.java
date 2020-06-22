package org.cis.optur.init;


import org.cis.optur.compile.model.Employee;
import org.cis.optur.compile.model.Shift;


public class HcTest {
    private Matrix matrix;
    private int[][] mPNM;
    private Employee[] employees;
    private Shift[] shifts;
    private String[] shiftNames;
    public HcTest(Matrix matrix, int[][] mPNM, Employee[] employees, Shift[] shifts, String[] shiftNames) {
        this.matrix = matrix;
        this.mPNM = mPNM;
        this.employees = employees;
        this.shifts = shifts;
        this.shiftNames = shiftNames;
    }

    public void HC5Test(){
        for (int e = 0; e<employees.length; e++){
            for (int d = 1; d<Constants.DAY_LENGTH; d++){
                String shiftName1 = matrix.getModel()[e][d-1];
                String shiftName2 = matrix.getModel()[e][d];
                if(shiftName1.equals(Constants.FREE_SHIFT_NAME)||shiftName2.equals(Constants.FREE_SHIFT_NAME)){
                    continue;
                }
                Shift shift1 = Utils.getShiftByName(shifts, shiftName1);
                Shift shift2 = Utils.getShiftByName(shifts, shiftName2);
                MyHour gap = Utils.getHourGapBetWeen(shift1, shift2);
                if((gap.getHour()>=Constants.MAX_HOUR_GAP))
                    continue;
                throw new Error("HC5 E: " + e + ", " + " S: " + shiftName1 + " => " + shiftName2 + ", day :"+ d +", gap = " + gap);
            }
        }
    }

    public void HC7Test(){
        for (int e = 0; e<employees.length; e++){
            for (int w = 0; w<Constants.DAY_LENGTH/7; w++){
                double weeklyWorkHour = 0.0;
                for (int d = 0; d<7; d++){
                    String shiftName = matrix.getModel()[e][(w*7)+d];
                    weeklyWorkHour += Utils.getWorkHour(shifts, shiftName, d);
                }
                if(weeklyWorkHour>Constants.MAX_WORK_HOUR_IN_WEEK){
                    throw new Error("HC7 E: " + e + ", W: " + w + " weekly work hour = " + weeklyWorkHour);
                }
            }
        }
    }

    public void HC2Test(){
        for (int i = 0; i<matrix.getModel().length; i++){
            for (int j = 0; j<matrix.getShiftNames().length; j++){
                if(matrix.getShiftNames()[j].equals(matrix.getDefaultShiftName()))continue;
                if(mPNM[j][i%7]!=matrix.getShiftCountRecap()[j][i]){
                    throw new Error("HC2 Belum terpennuhi! day ke " + i + "\t" + matrix.getShiftNames()[j] + " \t" + mPNM[j][i%7] + "!=" + matrix.getShiftCountRecap()[j][i]);
                }
            }
        }
    }

    public void HC4Test() {
        for (int i = 0; i<matrix.getModel().length; i++){
            for (int j = 0; j<matrix.getModel()[i].length; j++){
                String shiftName = matrix.getModel()[i][j];
                String competenceHad = employees[i].getCompetence();
                if(getShiftIndex(shiftName)==-1){
                    continue;
                }
                String competenceNeeded = shifts[getShiftIndex(shiftName)].getCompetenceNeeded();
                if(competenceNeeded!=null){
                    if(!competenceNeeded.equals(competenceHad)){
//                        return false;
                        throw new Error("HC4 Belum terpennuhi! Competence, employee " + i + " , day " + j);
                    }
                }
            }
        }
        for (int i = 0; i<matrix.getModel().length; i++){
            for (int j = 0; j<matrix.getModel()[i].length; j++){
                if(isWeekEnd(j)&&!matrix.getModel()[i][j].equals("FE")){
                    int[] workWeeks = employees[i].getWorkingWeekends();
                    boolean err = true;
                    int weekPointer = (j/7)+1;
                    if(isIncluded(workWeeks, weekPointer)){
                        err = false;
                    }
                    if(err){
                        throw new Error("HC4 Belum terpennuhi! Work week , employee ke " + i);
                    }
                }
            }
        }
    }

    private void printArrInt(int[] arr){
        for (int k = 0; k<arr.length; k++){
            System.out.print(arr[k] + "\t");
        }
        System.out.println();
    }

    private boolean isIncluded(int[] arr, int a){
        for (int i = 0; i< arr.length; i++){
            if(arr[i]==a){
                return true;
            }
        }
        return false;
    }

    private boolean isWeekEnd(int i) {
        return i%7>4;
    }

    private int getShiftIndex(String shiftName){
        if(shiftName.equals(Constants.FREE_SHIFT_NAME)) return -1;
        for (int i = 0; i<shiftNames.length; i++){
            if(shiftNames[i].equals(shiftName))
                return i;
        }
        return -1;
    }

    public void HC6Test(){
        ee: for (int e = 0; e<matrix.getModel().length; e++){
            ww : for (int w = 0; w<matrix.getModel()[0].length/7; w++){
                if(isFreeBerurutanLebihDari1(matrix.getModel()[e], w))
                    continue ww;
                int jamMax = 0;
                int menitMax = 0;
                dd: for (int d = 0; d<7; d++){
                    int jam = 0;
                    int menit = 0;
                    if(d==0){
                        //awal
                        jam+=24;
                        jam+=Utils.getJamSebelum(matrix.getModel()[e][w*d+1]);
                        menit+=Utils.getMenitSebelum(matrix.getModel()[e][w*d+1]);
                    }else if (d==6){
                        //akhir
                        jam+=24;
                        jam+=Utils.getJamSetelah(matrix.getModel()[e][w*d-1]);
                        menit+=Utils.getMenitSetelah(matrix.getModel()[e][w*d-1]);
                    }else {
                        jam+=24;

                        jam+=Utils.getJamSetelah(matrix.getModel()[e][w*d-1]);
                        menit+=Utils.getMenitSetelah(matrix.getModel()[e][w*d-1]);

                        jam+=Utils.getJamSebelum(matrix.getModel()[e][w*d+1]);
                        menit+=Utils.getMenitSebelum(matrix.getModel()[e][w*d+1]);
                    }
                    while (menit>60){
                        jam++;
                        menit-=60;
                    }
                    if (jamMax<jam||(jamMax==jam&&menitMax<menit)){
                        jamMax = jam;
                        menitMax = menit;
                    }
                }
                if(jamMax<Constants.CON_FREE)
                    throw new Error("HC6 Belum terpennuhi! Work week " + w + ", employee ke " + e);
            }
        }
    }



    private boolean isFreeBerurutanLebihDari1(String[] strings, int w) {
        String before = "";
        for (String shiftName : strings) {
            if (before.equals(Constants.FREE_SHIFT_NAME)&&shiftName.equals(Constants.FREE_SHIFT_NAME)){
                return true;
            }else {
                before = shiftName;
            }
        }
        return false;
    }
}
