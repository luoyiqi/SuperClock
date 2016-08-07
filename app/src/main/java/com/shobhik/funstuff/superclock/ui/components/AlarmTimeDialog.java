package com.shobhik.funstuff.superclock.ui.components;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shobhik.funstuff.superclock.AlarmPreferences;
import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.data.LocalData;
import com.shobhik.funstuff.superclock.models.Alarm;
import com.shobhik.funstuff.superclock.receivers.AlarmReceiver;
import com.shobhik.funstuff.superclock.utils.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shobhik Ghosh on 8/2/2016.
 */


public class AlarmTimeDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Alarm xAlarm;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Log.v("DEBUG CHECK", "Time changed: " + hourOfDay + ":" + minute);
        Date date = resolveFutureDate(hourOfDay, minute);
        Bundle info = getArguments();
        int id = info.getInt("alarm");
        int fieldid = info.getInt("time_field_id");
        xAlarm = Alarm.findById(Alarm.class, id);
        xAlarm.setDate(date);
        xAlarm.save();
        TextView tv = (TextView) getActivity().findViewById(fieldid);
//            timeField.setText(Utils.readableTime(xAlarm.getDate()));
        tv.setText(Utils.readableTime(xAlarm.getDate()));
        Context mContext = getActivity();
        int alarmid = xAlarm.getId().intValue();
        setProposedAlarm(mContext, date, alarmid);
    }

    /** Detects if a proposed time is less than 24 hours ahead and corrects the day value.
     *
     * An hour/minute value less than the current time would normally result in a timestamp that
     * exists in the past, instead of the future. This method ensures that proposed alarm times
     * are always in the future.
     *
     * @param hourOfDay
     * @param minute
     * @return
     */
    public Date resolveFutureDate(int hourOfDay, int minute) {
        final Calendar current = Calendar.getInstance();
        Calendar proposed = Calendar.getInstance();
        proposed.set(Calendar.HOUR_OF_DAY, hourOfDay);
        proposed.set(Calendar.MINUTE, minute);
        proposed.set(Calendar.SECOND, 0);
        proposed.set(Calendar.MILLISECOND, 0);

        //Check to see if the proposed date is in the future
        long t0 = current.getTimeInMillis();
        long t1 = proposed.getTimeInMillis();
        long diff = t1-t0;
        Log.v("DEBUG CHECK", "Current:  " + t0);
        Log.v("DEBUG CHECK", "Proposed: " + t1);
        Log.v("DEBUG CHECK", "Diff: " + diff);

        if(diff < 0) {
            proposed.add(Calendar.DAY_OF_MONTH, 1);
        }
        Date currentDate = current.getTime();
        Date newDate = proposed.getTime();
        Log.v("DEBUG CHECK", "Computed Current:  " + Utils.readableDate(currentDate));
        Log.v("DEBUG CHECK", "Computed Proposed: " + Utils.readableDate(newDate));
        return newDate;

    }


    public void setProposedAlarm(Context mContext, Date date, int alarmid) {
        //Init
        alarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("alarm_id", alarmid);
        alarmIntent = PendingIntent.getBroadcast(mContext, alarmid, intent, 0);
        int snoozeBase = AlarmPreferences.getSnoozeBaseTime(mContext);
        boolean snoozeFade = AlarmPreferences.isSnoozeIsFading(mContext);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);

        String label = xAlarm.getLabel();

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
