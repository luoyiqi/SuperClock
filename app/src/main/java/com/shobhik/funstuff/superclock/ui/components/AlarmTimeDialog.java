package com.shobhik.funstuff.superclock.ui.components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.models.Alarm;
import com.shobhik.funstuff.superclock.utils.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shobhik Ghosh on 8/2/2016.
 */


public class AlarmTimeDialog extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Alarm xAlarm;


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
        Alarm xAlarm = Alarm.findById(Alarm.class, id);
        xAlarm.setDate(date);
        xAlarm.save();
        TextView tv = (TextView) getActivity().findViewById(fieldid);
//            timeField.setText(Utils.readableTime(xAlarm.getDate()));
        tv.setText(Utils.readableTime(xAlarm.getDate()));

    }

    public Date resolveFutureDate(int hourOfDay, int minute) {
        final Calendar current = Calendar.getInstance();
        Calendar proposed = Calendar.getInstance();
        proposed.set(Calendar.HOUR_OF_DAY, hourOfDay);
        proposed.set(Calendar.MINUTE, minute);

        //Check to see if the proposed date is in the future
        long t0 = current.getTimeInMillis();
        long t1 = proposed.getTimeInMillis();
        long diff = t1-t0;
        Log.v("DEBUG CHECK", "Current: " + t0);
        Log.v("DEBUG CHECK", "Proposed: " + t1);
        Log.v("DEBUG CHECK", "Diff: " + diff);

        if(diff <0) {
            proposed.add(Calendar.DAY_OF_MONTH, 1);
        }
        Date currentDate = current.getTime();
        Date newDate = proposed.getTime();
        Log.v("DEBUG CHECK", "Computed Current: " + Utils.readableDate(currentDate));
        Log.v("DEBUG CHECK", "Computed Proposed: " + Utils.readableDate(newDate));
        return newDate;

    }


}
