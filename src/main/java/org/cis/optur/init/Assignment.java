package org.cis.optur.init;

public class Assignment {
    private int employeeNumber;
    private int dayNumber;
    private String shiftName;

    public Assignment(int employeeNumber, int dayNumber, String shiftName) {
        this.employeeNumber = employeeNumber;
        this.dayNumber = dayNumber;
        this.shiftName = shiftName;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public String getShiftName() {
        return shiftName;
    }
}
