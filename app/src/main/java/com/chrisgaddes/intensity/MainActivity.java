package com.chrisgaddes.intensity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn_start_stop;
    private ChronometerView rc;
    private boolean stopped;
    private TinyDB tinydb;
    private int workout_num;

    private TextView tv_workout_1;
    private TextView tv_workout_2;
    private TextView tv_workout_3;
    private TextView tv_workout_4;
    private TextView tv_workout_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinydb = new TinyDB(MainActivity.this);

        stopped = true;
        workout_num = 1;

        tv_workout_1 = (TextView) findViewById(R.id.tv_workout_1);
        tv_workout_2 = (TextView) findViewById(R.id.tv_workout_2);
        tv_workout_3 = (TextView) findViewById(R.id.tv_workout_3);
        tv_workout_4 = (TextView) findViewById(R.id.tv_workout_4);
        tv_workout_5 = (TextView) findViewById(R.id.tv_workout_5);

        btn_start_stop = (Button) findViewById(R.id.btn_start_stop);

        btn_start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stopped) {
                    startTimer();
                    btn_start_stop.setText("Stop");
                    stopped = !stopped;
                } else {
                    stopTimer();
                    btn_start_stop.setText("Start");
                    stopped = !stopped;
                }
            }
        });
    }

    private void startTimer() {
        rc = (ChronometerView) findViewById(R.id.tv_stopwatch);
//        rc.setPauseTimeOffset(tinydb.getLong("TotalForegroundTime", 0));
        rc.setOverallDuration(2 * 600);
        rc.setWarningDuration(90);
//        rc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        rc.reset();
        rc.run();
    }

    private void stopTimer() {
        addTimeToLog(workout_num, rc.getCurrentTime());
        if (rc != null) {
            rc.stop();
        }
        workout_num = workout_num+1;

    }

    private void addTimeToLog(int mworkout_num,long time){

        String str_name = "Time "+ String.valueOf(mworkout_num);

        tinydb.putLong(str_name, time);

        String str_textview_time = "Workout " + mworkout_num + ": Time = " + String.valueOf(time) + " seconds";

        switch(mworkout_num) {
            case 1:
                tv_workout_1.setText(str_textview_time);
                break;
            case 2:
                tv_workout_2.setText(str_textview_time);
                break;
            case 3:
                tv_workout_3.setText(str_textview_time);
                break;
            case 4:
                tv_workout_4.setText(str_textview_time);
                break;
            case 5:
                tv_workout_5.setText(str_textview_time);
                break;
        }
    }
}
