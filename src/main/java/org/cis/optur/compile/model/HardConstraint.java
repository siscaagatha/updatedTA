package org.cis.optur.compile.model;

public class HardConstraint {
    private boolean hc1;
    private boolean hc2;
    private float hc3;
    private boolean hc4;
    private Integer hc5NEWeekday;
    private Integer hc5NEWeekend;
    private Integer hc5EDWeekday;
    private Integer hc5EDWeekend;
    private Integer hc5DNWeekday;
    private Integer hc5DNWeekend;
    private Integer hc6;
    private Integer hc7;

    public HardConstraint(){
    }

    public HardConstraint(boolean hc1, boolean hc2, float hc3, boolean hc4, Integer hc5NEWeekday, Integer hc5NEWeekend, Integer hc5EDWeekday, Integer hc5EDWeekend, Integer hc5DNWeekday, Integer hc5DNWeekend, Integer hc6, Integer hc7) {
        this.hc1 = hc1;
        this.hc2 = hc2;
        this.hc3 = hc3;
        this.hc4 = hc4;
        this.hc5NEWeekday = hc5NEWeekday;
        this.hc5NEWeekend = hc5NEWeekend;
        this.hc5EDWeekday = hc5EDWeekday;
        this.hc5EDWeekend = hc5EDWeekend;
        this.hc5DNWeekday = hc5DNWeekday;
        this.hc5DNWeekend = hc5DNWeekend;
        this.hc6 = hc6;
        this.hc7 = hc7;
    }

    @Override
    public String toString() {
        return "HardConstraint{" +
                "hc1=" + hc1 +
                ", hc2=" + hc2 +
                ", hc3=" + hc3 +
                ", hc4=" + hc4 +
                ", hc5NEWeekday=" + hc5NEWeekday +
                ", hc5NEWeekend=" + hc5NEWeekend +
                ", hc5EDWeekday=" + hc5EDWeekday +
                ", hc5EDWeekend=" + hc5EDWeekend +
                ", hc5DNWeekday=" + hc5DNWeekday +
                ", hc5DNWeekend=" + hc5DNWeekend +
                ", hc6=" + hc6 +
                ", hc7=" + hc7 +
                '}';
    }

    public boolean isHc1() {
        return hc1;
    }

    public void setHc1(boolean hc1) {
        this.hc1 = hc1;
    }

    public boolean isHc2() {
        return hc2;
    }

    public void setHc2(boolean hc2) {
        this.hc2 = hc2;
    }

    public float getHc3() {
        return hc3;
    }

    public void setHc3(float hc3) {
        this.hc3 = hc3;
    }

    public boolean isHc4() {
        return hc4;
    }

    public void setHc4(boolean hc4) {
        this.hc4 = hc4;
    }

    public Integer getHc5NEWeekday() {
        return hc5NEWeekday;
    }

    public void setHc5NEWeekday(int hc5NEWeekday) {
        this.hc5NEWeekday = hc5NEWeekday;
    }

    public Integer getHc5NEWeekend() {
        return hc5NEWeekend;
    }

    public void setHc5NEWeekend(int hc5NEWeekend) {
        this.hc5NEWeekend = hc5NEWeekend;
    }

    public Integer getHc5EDWeekday() {
        return hc5EDWeekday;
    }

    public void setHc5EDWeekday(int hc5EDWeekday) {
        this.hc5EDWeekday = hc5EDWeekday;
    }

    public Integer getHc5EDWeekend() {
        return hc5EDWeekend;
    }

    public void setHc5EDWeekend(int hc5EDWeekend) {
        this.hc5EDWeekend = hc5EDWeekend;
    }

    public Integer getHc5DNWeekday() {
        return hc5DNWeekday;
    }

    public void setHc5DNWeekday(int hc5DNWeekday) {
        this.hc5DNWeekday = hc5DNWeekday;
    }

    public Integer getHc5DNWeekend() {
        return hc5DNWeekend;
    }

    public void setHc5DNWeekend(int hc5DNWeekend) {
        this.hc5DNWeekend = hc5DNWeekend;
    }

    public Integer getHc6() {
        return hc6;
    }

    public void setHc6(int hc6) {
        this.hc6 = hc6;
    }

    public Integer getHc7() {
        return hc7;
    }

    public void setHc7(int hc7) {
        this.hc7 = hc7;
    }

}
