package org.cis.optur.init;

public class HourAtom {
    private int hour;

    public HourAtom(int hour) {
        this.hour = hour;
    }

    public int diff(int hour){
        int h1 = 24-this.hour;
        int h2 = hour;
        return h1+h2;
    }
}
