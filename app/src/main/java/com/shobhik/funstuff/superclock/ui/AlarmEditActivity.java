package com.shobhik.funstuff.superclock.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.DialogFragment;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shobhik.funstuff.superclock.R;
import com.shobhik.funstuff.superclock.models.Alarm;
import com.shobhik.funstuff.superclock.utils.Utils;

import java.util.Date;

public class AlarmEditActivity extends AppCompatActivity implements DialogInterface.OnCancelListener{

    private Context mContext;
    private int currentId = 0;
    private Alarm mAlarm;

    //Fields
    private Date mDate;
    private boolean mActive;
    private int mWeekMask;
    private int mGroup;
    private String mLabel;
    private String mRingtone;

    boolean[] weekValues;
    //View classes
    private static TextView timeField;
    private TextView groupField;
    private TextView ringtoneField;
    private EditText labelField;
    private Switch activeField;
    private ToggleButton dowSun;
    private ToggleButton dowMon;
    private ToggleButton dowTue;
    private ToggleButton dowWed;
    private ToggleButton dowThu;
    private ToggleButton dowFri;
    private ToggleButton dowSat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);
        mContext = this;

        Intent intent = getIntent();
        currentId = intent.getIntExtra("alarm_id", 0);
        mAlarm = Alarm.findById(Alarm.class, currentId);
        mDate = mAlarm.getDate();

        setFieldsInScope();
        setViews();
        setListeners();
        setFieldValuesToViews();

    }

    private void setFieldsInScope() {
        mDate = mAlarm.getDate();
        mWeekMask = mAlarm.getDaysOfWeekMask();
        mLabel = mAlarm.getLabel();
        mGroup = mAlarm.getAlarmGroup();
        mRingtone = mAlarm.getRingtone();
        mActive = mAlarm.isActive();
    }

    private void setViews() {
        timeField = (TextView) findViewById(R.id.time_field);
        groupField = (TextView) findViewById(R.id.group_field);
        ringtoneField = (TextView) findViewById(R.id.ring_field);
        labelField = (EditText) findViewById(R.id.label_field);
        activeField = (Switch) findViewById(R.id.active_field);
        dowSun = (ToggleButton) findViewById(R.id.dow_sun);
        dowMon = (ToggleButton) findViewById(R.id.dow_mon);
        dowTue = (ToggleButton) findViewById(R.id.dow_tue);
        dowWed = (ToggleButton) findViewById(R.id.dow_wed);
        dowThu = (ToggleButton) findViewById(R.id.dow_thu);
        dowFri = (ToggleButton) findViewById(R.id.dow_fri);
        dowSat = (ToggleButton) findViewById(R.id.dow_sat);
    }


    public void setFieldValuesToViews() {
        timeField.setText(Utils.readableTime(mAlarm.getDate()));
        groupField.setText(""+mAlarm.getAlarmGroup());
        ringtoneField.setText(mAlarm.getRingtone());
        labelField.setText(mAlarm.getLabel());
        activeField.setChecked(mAlarm.isActive());
        dowSun.setChecked(true);
        dowMon.setChecked(true);
        dowTue.setChecked(true);
        dowWed.setChecked(true);
        dowThu.setChecked(true);
        dowFri.setChecked(true);
        dowSat.setChecked(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            Log.v("DEBUG CHECK", "Got successful result");
            if(requestCode == 999) {
                Log.v("DEBUG CHECK", "Got successful request match");
                Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                if (uri != null) {
                    String ringTonePath = uri.toString();
                    ringtoneField.setText(ringTonePath);
                }

            }
        }
    }


    private void setListeners() {
        timeField.setOnClickListener(timeListener);
        labelField.setOnEditorActionListener(labelListener);
        ringtoneField.setOnClickListener(ringtoneListener);
        LinearLayout ringtoneContainer = (LinearLayout) findViewById(R.id.ringtone_container);
        ringtoneContainer.setOnClickListener(ringtoneListener);
    }

    private View.OnClickListener timeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new com.shobhik.funstuff.superclock.ui.components.AlarmTimeDialog();
            int timeFieldId = timeField.getId();
            Bundle info = new Bundle();
            info.putInt("alarm", currentId);
            info.putInt("time_field_id", timeFieldId);
            newFragment.setArguments(info);
            newFragment.show(getFragmentManager(), "timePicker");

        }
    };

    private View.OnClickListener ringtoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select ringtone for notifications:");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_NOTIFICATION);
            startActivityForResult(intent,999);
        }
    };

    private TextView.OnEditorActionListener labelListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String value = v.getText().toString();
            mAlarm.setLabel(value);
            mAlarm.save();
            return false;
        }
    };

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.v("DEBUG CHECK", "Canceled Dialog");
        setFieldValuesToViews();
    }


}
