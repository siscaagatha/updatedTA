package org.cis.optur.init;

public class MinuteAtom {
    private int minute;

    public MinuteAtom(int minute) {
        this.minute = minute;
    }

    public void increase(){
        minute++;
        if(minute==60) minute = 0;
    }

    public int diff(int minute){
        if(minute==this.minute) return 0;
        return minute - this.minute;
    }
}
