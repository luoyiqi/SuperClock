package com.shobhik.funstuff.superclock.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.shobhik.funstuff.superclock.adapters.AlarmAdapter;
import com.shobhik.funstuff.superclock.models.Alarm;

import com.shobhik.funstuff.superclock.R;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Alarm> allAlarms;
    AlarmAdapter<Alarm> mAdapter;

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        long currentTime = System.currentTimeMillis();
        long nextHour = currentTime+ (3600*1000);
        long nextHourAfterThat = nextHour+ (3600*1000);
        Date date = new Date(currentTime);
        Date date2 = new Date(nextHour);
        Date date3 = new Date(nextHourAfterThat);
        Alarm a = new Alarm("First", date, 0, true);
        Alarm b = new Alarm("Second", date2, 0, true);
        Alarm c = new Alarm("Third", date3, 0, true);
        a.save();
        b.save();
        c.save();
        setAlarmList();

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long nextHour = System.currentTimeMillis()+ (3600*1000);
                Date date = new Date(nextHour);
                Alarm a = new Alarm("First", date, 0, true);
                a.save();
//                allAlarms = Alarm.listAll(Alarm.class);
                allAlarms.add(a);
                mAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alarm current = mAdapter.getItem(position);
                int currentId = current.getId().intValue();
                Intent intent = new Intent(mContext, AlarmEditActivity.class);
                intent.putExtra("alarm_id", currentId);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, AlarmTriggerActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }


    private void setAlarmList() {
        allAlarms = Alarm.listAll(Alarm.class);
        Log.v("SuperClock", "Pre-init adapter");
        mAdapter = new AlarmAdapter<Alarm>(mContext, R.layout.alarm_list_item, allAlarms);
        Log.v("SuperClock", "Post-init adapter");
        listView.setAdapter(mAdapter);
        Log.v("SuperClock", "Post-assign adapter");
    }


}
