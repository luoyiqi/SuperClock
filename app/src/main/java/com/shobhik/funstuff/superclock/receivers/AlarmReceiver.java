package com.shobhik.funstuff.superclock.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shobhik.funstuff.superclock.ui.AlarmTriggerActivity;
import com.shobhik.funstuff.superclock.ui.components.Toaster;

/**
 * Created by Shobhik Ghosh on 8/5/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context mContext, Intent intent) {
        Log.v("SuperClock", "Received Intent");
        Intent intent2 = new Intent(mContext, AlarmTriggerActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent2);
//        Toaster.pop(mContext.getApplicationContext(), "Triggered Alarm!");

    }
}
