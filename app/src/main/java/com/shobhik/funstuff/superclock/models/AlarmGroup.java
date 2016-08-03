package com.shobhik.funstuff.superclock.models;

import com.orm.SugarRecord;
import com.shobhik.funstuff.superclock.models.Alarm;

import java.util.List;

/**
 * Created by Shobhik Ghosh on 8/1/2016.
 */
public class AlarmGroup extends SugarRecord {
    private String name;
    private String isActive;
    private int weekMask;

    public AlarmGroup() {
    }

    public AlarmGroup(String name, String isActive, int weekMask) {
        this.name = name;
        this.isActive = isActive;
        this.weekMask = weekMask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public int getWeekMask() {
        return weekMask;
    }

    public void setWeekMask(int weekMask) {
        this.weekMask = weekMask;
    }



    public List<Alarm> getAlarms() {
        return Alarm.find(Alarm.class, "group = ?", ""+getId().intValue()  )  ;
    }


}
