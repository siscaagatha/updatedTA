package org.cis.optur.compile.repository;

import org.cis.optur.lib.Repository;
import org.cis.optur.compile.model.ManpowerPlan;

public class ManpowerPlanRepository extends Repository<ManpowerPlan> {
    private int weekCount;

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    //    private int getManCount
    public ManpowerPlan findOneByShitId(int shiftId){
        for (int i = 0; i<super.getAll().size(); i++){
            ManpowerPlan manpowerPlan = super.getAtIndex(i);
            if(manpowerPlan.getId()==shiftId){
                return manpowerPlan;
            }
        }
        throw new Error("Not found manpowerplan");
    }

    public int getManNeededByDayIndexAndShiftName(int dayIndex, String shiftName){
        for (int i = 0; i<super.getAll().size(); i++){
            ManpowerPlan manpowerPlan = super.getAtIndex(i);
            if(manpowerPlan.getShift()==shiftName){
                switch (dayIndex){
                    case 0:  return manpowerPlan.getMonday();
                    case 1:  return manpowerPlan.getTuesday();
                    case 2:  return manpowerPlan.getWednesday();
                    case 3:  return manpowerPlan.getThurdsay();
                    case 4:  return manpowerPlan.getFriday();
                    case 5:  return manpowerPlan.getSaturday();
                    case 6:  return manpowerPlan.getSunday();
                    default: throw new Error("Day found ! , this is man powerplan > getManNeededByDayIndexAndShiftName");
                }
            }
        }
        throw new Error("Not found manpowerplan for dayIndex = " + dayIndex + " | shiftName = " + shiftName);
    }
}
