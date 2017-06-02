package com.crystal.androidone;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rczhizhi.hflibrary.absActivity.SubActivity;

import butterknife.BindView;

public class MainActivity extends SubActivity {

    @BindView(R.id.top_left_bt)
    Button topLeftBt;
    @BindView(R.id.leftArea)
    LinearLayout leftArea;
    @BindView(R.id.top_right_bt)
    Button topRightBt;
    @BindView(R.id.rightArea)
    LinearLayout rightArea;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.centerArea)
    LinearLayout centerArea;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.text)
    TextView text;


    private SensorManager sensorManager;

    @Override
    protected boolean isShowStatusBar() {
        return false;
    }

    @Override
    public void onMyClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //光照传感器
//        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        text.setText("摇一摇吧！");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }


    @Override
    public String setTitle() {
        return "首页";
    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected int getMyLayoutResource() {
        return R.layout.activity_main;
    }

    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // values数组中第一个下标的值就是当前的光照强度
//            float value = event.values[0];
//            text.setText("Current light level is " + value + " lx");
//            progress.setProgress((int) value);
//            progress.setMax(1000);

            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);
            if (xValue > 15 || yValue > 15 || zValue > 15) {
                // 认为用户摇动了手机，触发摇一摇逻辑
                text.setText("x="+xValue+",y="+yValue+",z="+zValue);
                Toast.makeText(MainActivity.this, "摇一摇成功",
                        Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    };


}
