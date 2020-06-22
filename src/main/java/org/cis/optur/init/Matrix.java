package org.cis.optur.init;


public class Matrix{
    private String[][] model;
    private String[] shiftNames;
    private int dayLength;
    private String defaultShiftName;

    public Matrix(int employeeLength, int dayLength, String defaultShiftName, String[] shiftNames) {
        this.defaultShiftName = defaultShiftName;
        this.dayLength = dayLength;
        this.shiftNames = shiftNames;
        this.model = new String[employeeLength][dayLength];
        for (int i = 0; i<model.length; i++){
            for (int j = 0; j<model[i].length; j++){
                model[i][j] = defaultShiftName;
            }
        }
    }

    public String getDefaultShiftName() {
        return defaultShiftName;
    }

    public String[] getShiftNames() {
        return shiftNames;
    }

    public String[][] getModel(){
        return model;
    }

    public void assign(Assignment assignment){
        if(Constants.IS_DEBUG) printAssigment(assignment);
        model[assignment.getEmployeeNumber()][assignment.getDayNumber()] = assignment.getShiftName();
    }

    private void printAssigment(Assignment assignment){
        System.out.println("Assign " + assignment.getShiftName() + " to E:"+ assignment.getEmployeeNumber() + ", D:" + assignment.getDayNumber());
    }

    public void printMatrix(){
        for (int i = -1; i<model.length; i++){
            for (int j = -1; j<model[0].length; j++){
                if (i==-1&&j==-1){
                    System.out.print("E/D\t");
                    continue;
                }
                if(i==-1){
                    System.out.print(j + "\t");
                    continue;
                }
                if(j==-1){
                    System.out.print(i + "\t");
                    continue;
                }
                System.out.print(model[i][j] + "\t");
            }
            System.out.println();
        }
    }

    //matrix of shiftPointer x dayPointer ; value is count of shift on its day
    public int[][] getShiftCountRecap(){
        int[][] shiftCountRecap = new int[shiftNames.length][dayLength];
        for (int i = 0; i<shiftCountRecap.length; i++) {
            for (int j = 0; j<shiftCountRecap[0].length; j++) {
                //i=shiftPointer
                //j=dayPointer
                shiftCountRecap[i][j] = getShiftCountAtDay(i, j);
            }
        }
        return shiftCountRecap;
    }

    public void setModel(String[][] model) {
        this.model = model;
    }

    public String[][] cloneModel(){
        String[][] newModel = new String[model.length][model[0].length];
        for (int i = 0; i<model.length; i++){
            newModel[i] = model[i].clone();
        }
        return newModel;
    }

    private int getShiftCountAtDay(int shiftPointer, int dayPointer) {
        String[][] tranpose = new String[model[0].length][model.length];

        for (int i = 0; i<model[0].length; i++){
            for (int j = 0; j<model.length; j++){
                tranpose[i][j] = model[j][i];
            }
        }

        String[] singleDayShifts = tranpose[dayPointer];

        String shiftName = shiftNames[shiftPointer];

        return countStringInArrayString(shiftName, singleDayShifts);
    }

    private int countStringInArrayString(String str, String[] arr){
        int result = 0;
        for (int i = 0; i<arr.length; i++){
            if(arr[i].equals(str)) result++;
        }
        return result;
    }

}
