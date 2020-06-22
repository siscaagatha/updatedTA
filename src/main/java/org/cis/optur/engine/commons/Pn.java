package org.cis.optur.engine.commons;

public class Pn {
    public int day; public String[] pattern;

    public Pn(int day, String[] pattern){
        this.day = day;
        this.pattern = pattern;
    }

    public int getDay() {return day;}

    public String[] getPatern() {
        return pattern;
    }
}
