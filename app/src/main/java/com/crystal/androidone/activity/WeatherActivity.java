package com.crystal.androidone.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crystal.androidone.R;
import com.crystal.androidone.util.HttpCallbackListener;
import com.crystal.androidone.util.HttpUtil;
import com.crystal.androidone.util.Utility;
import com.rczhizhi.hflibrary.absActivity.SubActivity;

import butterknife.BindView;

/**
 * Created by 嗨创科技 on 2017/6/2.
 */

public class WeatherActivity extends SubActivity implements View.OnClickListener {


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
    @BindView(R.id.publish_text)
    TextView publishText;
    @BindView(R.id.current_date)
    TextView currentDate;
    @BindView(R.id.weather_desp)
    TextView weatherDesp;
    @BindView(R.id.temp1)
    TextView temp1;
    @BindView(R.id.temp2)
    TextView temp2;
    @BindView(R.id.weather_info_layout)
    LinearLayout weatherInfoLayout;


    @Override
    public String setTitle() {
        return "";
    }

    @Override
    protected boolean isShowStatusBar() {
        return false;
    }

    @Override
    public void onMyClick(View v) {

    }

    @Override
    protected void updateUI() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected int getMyLayoutResource() {
        return R.layout.weather_layout;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            // 有县级代号时就去查询天气
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            // 没有县级代号时就直接显示本地天气
            showWeather();
        }
        topRightBt.setText("刷新");
        topRightBt.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        topLeftBt.setOnClickListener(this);
        topRightBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_bt:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.top_right_bt:
                publishText.setText("同步中...");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = prefs.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    queryWeatherInfo(weatherCode);
                }
                directTo(HomeActivity.class);
                break;
            default:
                break;

        }
    }

    /**
     * 查询县级代号所对应的天气代号。
     */
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode +
                ".xml";
        queryFromServer(address, "countyCode");
    }

    /**
     * 查询天气代号所对应的天气。
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode +
                ".html";
        queryFromServer(address, "weatherCode");
    }

    /**
     * 根据传入的地址和类型去向服务器查询天气代号或者天气信息。
     */
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        // 从服务器返回的数据中解析出天气代号
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    // 处理服务器返回的天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });
            }
        });
    }

    /**
     * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上。
     */
    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        toolbarTitle.setText(prefs.getString("city_name", ""));
        temp1.setText(prefs.getString("temp1", ""));
        temp2.setText(prefs.getString("temp2", ""));
        weatherDesp.setText(prefs.getString("weather_desp", ""));
        publishText.setText("今天" + prefs.getString("publish_time", "") + "发布");
        currentDate.setText(prefs.getString("current_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
    }

}
