package org.cis.optur.init;

public class MyHour {
    private int hour;
    private int minute;

    public MyHour(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }




    public MyHour diffTo(MyHour to) {
        int hourGap = new HourAtom(this.hour).diff(to.getHour());
        int minuteGap = new MinuteAtom(this.minute).diff(to.getMinute());
        if(minuteGap<0){
            minuteGap = 60 + minuteGap;
            hourGap -= 1;
        }
        return new MyHour(hourGap, minuteGap);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MyHour{");
        sb.append("hour=").append(hour);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
