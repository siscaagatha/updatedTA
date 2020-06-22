package org.cis.optur.compile.model;

import java.util.Arrays;

public class WantedShiftPattern {
    private int id;
    private String startingDay;
    private String[] pattern;

  public WantedShiftPattern(){
    }

    public WantedShiftPattern(int id, String startingDay, String[] pattern) {
        this.id = id;
        this.startingDay = startingDay;
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "WantedShiftPattern{" +
                "id=" + id +
                ", startingDay='" + startingDay + '\'' +
                ", pattern=" + Arrays.toString(pattern) +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartingDay() {
        return startingDay;
    }

    public void setStartingDay(String startingDay) {
        this.startingDay = startingDay;
    }

    public String[] getPattern() {
        return pattern;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }
}
