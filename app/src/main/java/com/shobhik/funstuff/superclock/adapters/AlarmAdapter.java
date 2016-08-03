package com.shobhik.funstuff.superclock.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.models.Alarm;
import com.shobhik.funstuff.superclock.utils.Utils;

import java.util.List;

/**
 * Created by Shobhik Ghosh on 6/21/2016.
 */
public class AlarmAdapter<A> extends ArrayAdapter<Alarm> {

    private Context context;
    List<Alarm> allAlarms;

    public AlarmAdapter(Context context, int resource) {
        super(context, resource);
    }


    public AlarmAdapter(Context context, int resource, List<Alarm> objects) {
        super(context, resource, objects);
        this.context = context;
        this.allAlarms = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("SuperClock", "Adapter GetView Start");
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_list_item, null);
        LinearLayout countbox=(LinearLayout)convertView.findViewById(R.id.field_count);
        TextView countnum=(TextView)convertView.findViewById(R.id.field_count_num);
        TextView fieldname=(TextView)convertView.findViewById(R.id.field_name);
        TextView fieldsubname=(TextView)convertView.findViewById(R.id.field_sub_name);
        Log.v("SuperClock", "Adapter GetView assigned views");
        Alarm alarm = allAlarms.get(position);
        fieldname.setText("#" + alarm.getId() + ":" + alarm.getLabel());
        String readableDate = Utils.readableDate(alarm.getDate());
        fieldsubname.setText(readableDate);
        Log.v("SuperClock", "Adapter GetView assigned values");

        return convertView;
    }
}
