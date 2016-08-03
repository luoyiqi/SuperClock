package com.shobhik.funstuff.superclock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.*;

import java.util.ArrayList;

/** Helper class to save Date forms in JSON, etc as SharedPrefs
 * Created by Shobhik Ghosh on 6/20/2016.
 */
public class DateStorageUtils {

    public static String getDate(Context context, String entryName,
                                   String entryValue) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(entryName, entryValue);
    }



    public static void saveDate(Context mContext, String entryName, String entryValue) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(entryName, entryValue);

    }

}
