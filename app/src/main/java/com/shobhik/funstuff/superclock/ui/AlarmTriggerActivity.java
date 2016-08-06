package com.shobhik.funstuff.superclock.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.ui.components.AlarmPulsar;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class AlarmTriggerActivity extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_alarm_trigger);

        final PulsatorLayout pulsar = (PulsatorLayout) findViewById(R.id.pulsator);
//        final AlarmPulsar pulsar = (AlarmPulsar) findViewById(R.id.pulsator);
        pulsar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pulsar.start();
    }
}
