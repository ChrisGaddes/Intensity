package com.chrisgaddes.intensity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btn_start_stop;
    private Button btn_reset;
    private ChronometerView rc;
    private boolean stopped;
    private TinyDB tinydb;
    private int workout_num;

    private EditText tv_workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinydb = new TinyDB(MainActivity.this);

        stopped = true;
        workout_num = 1;

        tv_workout = (EditText) findViewById(R.id.tv_workout);

        btn_start_stop = (Button) findViewById(R.id.btn_start_stop);
        btn_reset = (Button) findViewById(R.id.btn_reset);

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

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetLog();
            }
        });
    }

    private void startTimer() {
        rc = (ChronometerView) findViewById(R.id.chron_stopwatch);
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

    private void resetLog() {
        if (rc != null) {
            rc.reset();
        }
        workout_num = 1;

        tv_workout.setText("");
    }

    private void addTimeToLog(int mworkout_num,long time){

        String str_name = "Time "+ String.valueOf(mworkout_num);

        tinydb.putLong(str_name, time);

        String str_current_text = tv_workout.getText().toString();

        String str_textview_time = str_current_text + "Workout " + mworkout_num + ": TUL = " + String.valueOf(time) + " seconds" + "\n\n" ;

        tinydb.putString("Text",str_textview_time);

        tv_workout.setText(str_textview_time);

    }
}
