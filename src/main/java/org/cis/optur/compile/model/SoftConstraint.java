package org.cis.optur.compile.model;

public class SoftConstraint {
    private Integer sc1Day;
    private Integer sc1Evening;
    private Integer sc1Night;
    private Integer sc2;
    private Integer sc3Day;
    private Integer sc3Evening;
    private Integer sc3Night;
    private Integer sc4;
//    private Integer sc5Day;
    private MinMax sc5Day;
    private MinMax sc5Evening;
    private MinMax sc5Night;
    private boolean sc6;
    private boolean sc7;
    private Integer sc8;
    private Integer sc9;

    public SoftConstraint(){
    }

    public SoftConstraint(Integer sc1Day, Integer sc1Evening, Integer sc1Night, Integer sc2, Integer sc3Day, Integer sc3Evening, Integer sc3Night, Integer sc4, MinMax sc5Day, MinMax sc5Evening, MinMax sc5Night, boolean sc6, boolean sc7, Integer sc8, Integer sc9) {
        this.sc1Day = sc1Day;
        this.sc1Evening = sc1Evening;
        this.sc1Night = sc1Night;
        this.sc2 = sc2;
        this.sc3Day = sc3Day;
        this.sc3Evening = sc3Evening;
        this.sc3Night = sc3Night;
        this.sc4 = sc4;
        this.sc5Day = sc5Day;
        this.sc5Evening = sc5Evening;
        this.sc5Night = sc5Night;
        this.sc6 = sc6;
        this.sc7 = sc7;
        this.sc8 = sc8;
        this.sc9 = sc9;
    }

    @Override
    public String toString() {
        return "SoftConstraint{" +
                "sc1Day=" + sc1Day +
                ", sc1Evening=" + sc1Evening +
                ", sc1Night=" + sc1Night +
                ", sc2=" + sc2 +
                ", sc3Day=" + sc3Day +
                ", sc3Evening=" + sc3Evening +
                ", sc3Night=" + sc3Night +
                ", sc4=" + sc4 +
                ", sc5Day=" + sc5Day +
                ", sc5Evening=" + sc5Evening +
                ", sc5Night=" + sc5Night +
                ", sc6=" + sc6 +
                ", sc7=" + sc7 +
                ", sc8=" + sc8 +
                ", sc9=" + sc9 +
                '}';
    }

    public Integer getSc1Day() {
        return sc1Day;
    }

    public void setSc1Day(Integer sc1Day) {
        this.sc1Day = sc1Day;
    }

    public Integer getSc1Evening() {
        return sc1Evening;
    }

    public void setSc1Evening(Integer sc1Evening) {
        this.sc1Evening = sc1Evening;
    }

    public Integer getSc1Night() {
        return sc1Night;
    }

    public void setSc1Night(Integer sc1Night) {
        this.sc1Night = sc1Night;
    }

    public Integer getSc2() {
        return sc2;
    }

    public void setSc2(Integer sc2) {
        this.sc2 = sc2;
    }

    public Integer getSc3Day() {
        return sc3Day;
    }

    public void setSc3Day(Integer sc3Day) {
        this.sc3Day = sc3Day;
    }

    public Integer getSc3Evening() {
        return sc3Evening;
    }

    public void setSc3Evening(Integer sc3Evening) {
        this.sc3Evening = sc3Evening;
    }

    public Integer getSc3Night() {
        return sc3Night;
    }

    public void setSc3Night(Integer sc3Night) {
        this.sc3Night = sc3Night;
    }

    public Integer getSc4() {
        return sc4;
    }

    public void setSc4(Integer sc4) {
        this.sc4 = sc4;
    }

    public MinMax getSc5Day() {
        return sc5Day;
    }

    public void setSc5Day(MinMax sc5Day) {
        this.sc5Day = sc5Day;
    }

    public MinMax getSc5Evening() {
        return sc5Evening;
    }

    public void setSc5Evening(MinMax sc5Evening) {
        this.sc5Evening = sc5Evening;
    }

    public MinMax getSc5Night() {
        return sc5Night;
    }

    public void setSc5Night(MinMax sc5Night) {
        this.sc5Night = sc5Night;
    }

    public boolean isSc6() {
        return sc6;
    }

    public void setSc6(boolean sc6) {
        this.sc6 = sc6;
    }

    public boolean isSc7() {
        return sc7;
    }

    public void setSc7(boolean sc7) {
        this.sc7 = sc7;
    }

    public Integer getSc8() {
        return sc8;
    }

    public void setSc8(Integer sc8) {
        this.sc8 = sc8;
    }

    public Integer getSc9() {
        return sc9;
    }

    public void setSc9(Integer sc9) {
        this.sc9 = sc9;
    }
}
