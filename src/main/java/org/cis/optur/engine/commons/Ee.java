package org.cis.optur.engine.commons;

public class Ee {

    int id;
    double hours;
    int[] week;
    String competence;

    public Ee(String id, String hours, int[] week, String competence){
        this.id = Integer.parseInt(id);
        this.hours = Double.parseDouble(hours);
        this.week = week;
        this.competence = competence;
    }

    public int getId() { return id; }

    public double getHours(){
        return hours;
    }

    public int[] getWeek() {
        return week;
    }

    public String getCompetence(){
        return competence;
    }
}