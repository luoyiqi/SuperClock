package com.shobhik.funstuff.superclock.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Shobhik Ghosh on 6/20/2016.
 */
public class Utils {

    /**
     * Checks if a given string is null, blank or empty and returns true if so.
     * Mostly useful for LocalData strings because they can be unpredictable when
     * not explicitly assigned a value.
     * @param  s - String value to test
     * @return true if null/empty, false if real string
     */
    public static boolean isBlankOrNullString(String s){
        return s == null || s.isEmpty() || s.equalsIgnoreCase("");
    }

    public static ArrayList<String> generateStringArrayRepresentation(String jsonArrayInString) {
        ArrayList<String> output = new ArrayList<String>();
        try {
            JSONArray jArr = new JSONArray(jsonArrayInString);
            if(jArr != null) {
                for(int i = 0; i < jArr.length(); i ++ ) {
                    output.add(jArr.get(i).toString());
                }
            }
            return output;
        } catch (JSONException e) {
            e.printStackTrace();
            return output;
        }

    }

    public static String readableDate(Date date) {
        DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        DateFormat midForm = new SimpleDateFormat("MMM dd, yy h:mm a");
        return midForm.format(date);
    }

    public static String readableTime(Date date) {
        DateFormat midForm = new SimpleDateFormat("h:mm a");
        return midForm.format(date);
    }

}
