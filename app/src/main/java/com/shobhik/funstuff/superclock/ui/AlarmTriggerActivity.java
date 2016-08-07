package com.shobhik.funstuff.superclock.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nisrulz.sensey.PinchScaleDetector;
import com.github.nisrulz.sensey.Sensey;
import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.models.Alarm;
import com.shobhik.funstuff.superclock.receivers.AlarmReceiver;
import com.shobhik.funstuff.superclock.ui.components.AlarmPulsar;
import com.shobhik.funstuff.superclock.utils.Utils;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class AlarmTriggerActivity extends AppCompatActivity {

    private Context mContext;
    private boolean pinchDirectionIn = false;
    private PulsatorLayout pulsar;
    private ImageView doneView;
    private Alarm xAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Sensey.getInstance().init(mContext);
        setContentView(R.layout.activity_alarm_trigger);
        Intent incoming = getIntent();
        int id = incoming.getIntExtra("alarm_id", 1);
        xAlarm = Alarm.findById(Alarm.class, id);
        String date = Utils.readableTime(xAlarm.getDate());
        TextView tv = (TextView) findViewById(R.id.time_view);
        tv.setText(date);

        doneView = (ImageView) findViewById(R.id.pulse_done_view);
        pulsar = (PulsatorLayout) findViewById(R.id.pulsator);
//        final AlarmPulsar pulsar = (AlarmPulsar) findViewById(R.id.pulsator);
        pulsar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Sensey.getInstance().stopPinchScaleDetection();
//                finish();
            }
        });
        pulsar.start();
        Sensey.getInstance().startPinchScaleDetection(pinchScaleListener);


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    PinchScaleDetector.PinchScaleListener pinchScaleListener=new PinchScaleDetector.PinchScaleListener() {
        @Override public void onScale(ScaleGestureDetector scaleGestureDetector, boolean isScalingOut) {
            if (isScalingOut) {
                // Scaling Out;
                Log.v("SuperClock Scaler", "Detected Scaling Out!");
                pinchDirectionIn = false;
            } else {
                // Scaling In
                Log.v("SuperClock Scaler", "Detected Scaling In!");
                pinchDirectionIn = true;
            }
        }

        @Override public void onScaleStart(ScaleGestureDetector scaleGestureDetector) {
            // Scaling Started
            Log.v("SuperClock Scaler", "Detected Scaling Started!");
            pulsar.start();
            pulsar.setVisibility(View.VISIBLE);
            doneView.setVisibility(View.GONE);
        }

        @Override public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            // Scaling Stopped
            Log.v("SuperClock Scaler", "Detected Scaling Stopped!");
            if(pinchDirectionIn) {
                pulsar.stop();
                pulsar.setVisibility(View.GONE);
                doneView.setVisibility(View.VISIBLE);
                int alarmid = xAlarm.getId().intValue();
                AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(mContext, AlarmReceiver.class);
                intent.putExtra("alarm_id", alarmid);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext, alarmid, intent, 0);
//                PendingIntent alarmIntent2 = PendingIntent.getBroadcast(mContext, 0, intent, 0); //Just to get rid ofa pesky default
                alarmManager.cancel(alarmIntent);
//                alarmManager.cancel(alarmIntent2);
                Log.v("SuperClock Scaler", "Stopped alarm with ID " + alarmid);

            } else {
                pulsar.setCount(4);
                pulsar.setDuration(3000);
                pulsar.start();
                pulsar.setVisibility(View.VISIBLE);
                doneView.setVisibility(View.GONE);
            }
        }
    };


}
