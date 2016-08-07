package com.shobhik.funstuff.superclock;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.orm.SugarContext;
import com.shobhik.funstuff.superclock.receivers.AlarmReceiver;

/**
 * Created by Shobhik Ghosh on 6/21/2016.
 */
public class SuperClock extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        IntentFilter ifx = new IntentFilter("com.simplebutneeded.android.commons.DELETE_USER_DATA");
        ifx.addCategory("visserone");
//        LocalBroadcastManager.getInstance(this).registerReceiver(new AlarmReceiver(), ifx);
//        Context.registerReceiver(new AlarmReceiver(), ifx);

    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
