package com.shobhik.funstuff.superclock;

import android.app.Application;

import com.orm.SugarContext;
/**
 * Created by Shobhik Ghosh on 6/21/2016.
 */
public class SuperClock extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

}
