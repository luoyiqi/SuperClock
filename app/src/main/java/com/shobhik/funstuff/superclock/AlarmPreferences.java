package com.shobhik.funstuff.superclock;

/**
 * Created by Shobhik Ghosh on 8/5/2016.
 */
public class AlarmPreferences {
    private static int snoozeBaseTime;
    private static boolean snoozeIsFading;

    public static int getSnoozeBaseTime() {
        return snoozeBaseTime;
    }

    public static void setSnoozeBaseTime(int snoozeBaseTime) {
        snoozeBaseTime = snoozeBaseTime;
    }

    public static boolean isSnoozeIsFading() {
        return snoozeIsFading;
    }

    public static void setSnoozeIsFading(boolean snoozeIsFading) {
        snoozeIsFading = snoozeIsFading;
    }
}
