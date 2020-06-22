package org.cis.optur.init;

import org.cis.optur.compile.model.Shift;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private static Shift[] shifts;
    public static void pause(){
        Scanner n = new Scanner(System.in);
        String a = n.nextLine();
    }
    public static void printMPPM(int[][] mPNM){
        for (int i = 0; i<mPNM.length; i++){
            for (int j = 0; j<mPNM[0].length; j++){
                System.out.print(mPNM[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static int getMaxIndex( double[] array )
    {
        if ( array == null || array.length == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest; // position of the first largest found
    }

    public static int getMinIndex(double[] array){
        // add this
        if (array.length == 0)
            return -1;

        int index = 0;
        double min = array[index];

        for (int i = 1; i < array.length; i++){
            if (array[i] <= min){
                min = array[i];
                index = i;
            }
        }
        return index;
    }

    public static double getWorkHour(Shift[] shifts, String shiftName, int dayInWeek) {
        if(shiftName.equals(Constants.FREE_SHIFT_NAME)) return 0.0;
        Shift shift = null;
        for (int i = 0; i<shifts.length; i++){
            if(shifts[i].getName().equals(shiftName)){
                shift = shifts[i];
                break;
            }
        }
        if(shift==null){
            throw new Error("Shift null");
        }
        switch (dayInWeek){
            case 0: return shift.getMonday();
            case 1: return shift.getTuesday();
            case 2: return shift.getWednesday();
            case 3: return shift.getThursday();
            case 4: return shift.getFriday();
            case 5: return shift.getSaturday();
            case 6: return shift.getSunday();
            default: throw new Error("Day In week index out of bound!");
        }
    }

    public static MyHour getHourGapBetWeen(Shift shift1, Shift shift2) {
        MyHour start = new MyHour(shift1.getEndTimeHours(), shift1.getEndTimeMinutes());
        MyHour end = new MyHour(shift2.getStartTimeHours(), shift2.getStartTimeMinutes());
        return start.diffTo(end);
    }

    public static Shift getShiftByName(Shift[] shifts, String shiftName){

        for (int i = 0; i<shifts.length; i++){
            if(shifts[i].getName().equals(shiftName)){
                return shifts[i];
            }
        }
        throw new Error("Shift not found with name " + shiftName);
    }

    public static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static int getRandomElement(HashSet<Integer> myHashSet) {
        int size = myHashSet.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(Integer obj : myHashSet)
        {
            if (i == item)
                return obj;
            i++;
        }
        throw new Error("Utils getRandomElement");
    }

    public static int getRandomIndex(double[] arr){
        return new Random().nextInt(arr.length);
    }

    public static int getMenitSetelah(String s){
        Shift shift = getShift(s);
        return 60-shift.getEndTimeMinutes();
    }

    private static Shift getShift(String s) {
        for (Shift shift : shifts) {
            if(shift.getName().equals(s))
                return shift;
        }
        throw new Error("Not found shift with name" + s);
    }

    public static int getJamSetelah(String s) {
        Shift shift = getShift(s);
        return 24-shift.getEndTimeHours();
    }

    public static int getMenitSebelum(String s){
        Shift shift = getShift(s);
        return shift.getStartTimeMinutes();
    }

    public static int getJamSebelum(String s)  {
        Shift shift = getShift(s);
        return shift.getStartTimeHours();
    }

    public static void setShifts(Shift[] shifts) {
        Utils.shifts = shifts;
    }
}