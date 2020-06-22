package org.cis.optur.compile.model;

public class Shift {
    private int id;
    private double monday;
    private double tuesday;
    private double wednesday;
    private double thursday;
    private double friday;
    private double saturday;
    private double sunday;
    private int shiftCategory;
    private String name;
    private int startTimeHours;
    private int startTimeMinutes;
    private int endTimeHours;
    private int endTimeMinutes;
    private String competenceNeeded;

    public Shift(){
    }

    public Shift(int id, double monday, double tuesday, double wednesday, double thursday, double friday,
                 double saturday, double sunday, int shiftCategory, String name, int startTimeHours, int startTimeMinutes,
                 int endTimeHours, int endTimeMinutes, String competenceNeeded) {
        this.id = id;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.shiftCategory = shiftCategory;
        this.name = name;
        this.startTimeHours = startTimeHours;
        this.startTimeMinutes = startTimeMinutes;
        this.endTimeHours = endTimeHours;
        this.endTimeMinutes = endTimeMinutes;
        this.competenceNeeded = competenceNeeded;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                ", shiftCategory=" + shiftCategory +
                ", name='" + name + '\'' +
                ", startTimeHours=" + startTimeHours +
                ", startTimeMinutes=" + startTimeMinutes +
                ", endTimeHours=" + endTimeHours +
                ", endTimeMinutes=" + endTimeMinutes +
                ", competenceNeeded='" + competenceNeeded + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonday() {
        return monday;
    }

    public void setMonday(double monday) {
        this.monday = monday;
    }

    public double getTuesday() {
        return tuesday;
    }

    public void setTuesday(double tuesday) {
        this.tuesday = tuesday;
    }

    public double getWednesday() {
        return wednesday;
    }

    public void setWednesday(double wednesday) {
        this.wednesday = wednesday;
    }

    public double getThursday() {
        return thursday;
    }

    public void setThursday(double thursday) {
        this.thursday = thursday;
    }

    public double getFriday() {
        return friday;
    }

    public void setFriday(double friday) {
        this.friday = friday;
    }

    public double getSaturday() {
        return saturday;
    }

    public void setSaturday(double saturday) {
        this.saturday = saturday;
    }

    public double getSunday() {
        return sunday;
    }

    public void setSunday(double sunday) {
        this.sunday = sunday;
    }

    public int getShiftCategory() {
        return shiftCategory;
    }

    public void setShiftCategory(int shiftCategory) {
        this.shiftCategory = shiftCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartTimeHours() {
        return startTimeHours;
    }

    public void setStartTimeHours(int startTimeHours) {
        this.startTimeHours = startTimeHours;
    }

    public int getStartTimeMinutes() {
        return startTimeMinutes;
    }

    public void setStartTimeMinutes(int startTimeMinutes) {
        this.startTimeMinutes = startTimeMinutes;
    }

    public int  getEndTimeHours() {
        return endTimeHours;
    }

    public void setEndTimeHours(int endTimeHours) {
        this.endTimeHours = endTimeHours;
    }

    public int getEndTimeMinutes() {
        return endTimeMinutes;
    }

    public void setEndTimeMinutes(int endTimeMinutes) {
        this.endTimeMinutes = endTimeMinutes;
    }

    public String getCompetenceNeeded() {
        return competenceNeeded;
    }

    public void setCompetenceNeeded(String competenceNeeded) {
        this.competenceNeeded = competenceNeeded;
    }
}
