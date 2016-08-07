package com.shobhik.funstuff.superclock;

import android.content.Context;

import com.shobhik.funstuff.superclock.data.LocalData;

/**
 * Created by Shobhik Ghosh on 8/5/2016.
 */
public class AlarmPreferences {

    /** Get a snooze time in seconds
     *
     * @param mContext
     * @return
     */
    public static int getSnoozeBaseTime(Context mContext) {
        return LocalData.getInt(mContext, LocalData.DEFAULT_SNOOZE_DURATION, 0);
    }

    /** Set a snooze time in seconds
     *
     * @param mContext
     * @param snoozeBaseTime
     */
    public static void setSnoozeBaseTime(Context mContext, int snoozeBaseTime) {
        LocalData.setInt(mContext, LocalData.DEFAULT_SNOOZE_DURATION, snoozeBaseTime);
    }

    public static boolean isSnoozeIsFading(Context mContext) {
        return LocalData.getBoolean(mContext, LocalData.DEFAULT_SNOOZE_DECLINE, false);

    }

    public static void setSnoozeIsFading(Context mContext, boolean snoozeIsFading) {
        LocalData.setBoolean(mContext, LocalData.DEFAULT_SNOOZE_DECLINE, snoozeIsFading);
    }
}
