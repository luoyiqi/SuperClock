package com.shobhik.funstuff.superclock.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.github.nisrulz.sensey.PinchScaleDetector;
import com.github.nisrulz.sensey.Sensey;
import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.ui.components.AlarmPulsar;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class AlarmTriggerActivity extends AppCompatActivity {

    private Context mContext;
    private boolean pinchDirectionIn = false;
    private PulsatorLayout pulsar;
    private ImageView doneView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Sensey.getInstance().init(mContext);
        setContentView(R.layout.activity_alarm_trigger);
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
