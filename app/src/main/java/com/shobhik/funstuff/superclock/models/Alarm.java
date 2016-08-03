package com.shobhik.funstuff.superclock.models;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Shobhik Ghosh on 6/20/2016.
 */
public class Alarm extends SugarRecord{
    private String label;
    private Date date;
    private int alarmGroup;
    private boolean isActive;
    private int daysOfWeekMask; // 7 digit mask Sun-Sat, 0-1, failover to odd-even defaults
    private String ringtone; //path
    public Alarm() {
    }

    public Alarm(String label, Date date, int alarmGroup, boolean isActive) {
        this.label = label;
        this.date = date;
        this.alarmGroup = alarmGroup;
        this.isActive = isActive;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getAlarmGroup() {
        return alarmGroup;
    }

    public void setAlarmGroup(int alarmGroup) {
        this.alarmGroup = alarmGroup;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDaysOfWeekMask() {
        return daysOfWeekMask;
    }

    public void setDaysOfWeekMask(int daysOfWeekMask) {
        this.daysOfWeekMask = daysOfWeekMask;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }
}
