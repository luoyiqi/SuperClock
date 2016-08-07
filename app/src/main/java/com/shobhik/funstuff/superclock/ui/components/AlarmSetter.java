package com.shobhik.funstuff.superclock.ui.components;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.shobhik.funstuff.superclock.AlarmPreferences;
import com.shobhik.funstuff.superclock.receivers.AlarmReceiver;
import com.shobhik.funstuff.superclock.utils.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shobhik Ghosh on 8/5/2016.
 */
public class AlarmSetter {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public void setProposedAlarm(Context mContext, Date date) {
        //Init
        alarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        int snoozeBase = AlarmPreferences.getSnoozeBaseTime(mContext);
        boolean snoozeFade = AlarmPreferences.isSnoozeIsFading(mContext);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        long val = date.getTime();
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(),
                1000 * snoozeBase, alarmIntent);
        Log.v("SuperClock Alarm Setter", "Set Alarm: " + date.getTime() + ", " + snoozeBase + "second snooze " + snoozeFade);
        Toaster.pop(mContext, "Set Alarm for " + Utils.readableDate(date));
    }


    public void setProposedAlarm(Context mContext, Date date, int alarmid) {
        //Init
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;
        alarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("alarm_id", alarmid);
        alarmIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        int snoozeBase = AlarmPreferences.getSnoozeBaseTime(mContext);
        boolean snoozeFade = AlarmPreferences.isSnoozeIsFading(mContext);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);


        long val = date.getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.v("SuperClock Alarm Setter", "Picked API 19 strategy");
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), alarmIntent);
        } else {
            Log.v("SuperClock Alarm Setter", "Picked pre-API 19 strategy");
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(),
                    1000 * snoozeBase, alarmIntent);
        }

        Log.v("SuperClock Alarm Setter", "Set Alarm: " + date.getTime() + ", " + snoozeBase + " second snooze " + snoozeFade);
        Toaster.pop(mContext, "Set Alarm for " + Utils.readableDate(date));

    }
}
