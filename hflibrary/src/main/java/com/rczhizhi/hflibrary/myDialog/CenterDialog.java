package com.rczhizhi.hflibrary.myDialog;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.rczhizhi.hflibrary.R;

import java.util.Locale;

import butterknife.ButterKnife;

/**
 * Created by hufeng on 2017/2/27.
 */

public abstract class CenterDialog extends MyDialog {

    public IDialogClick iDialogClick;

    private Context mcontext;

    public CenterDialog(Context context ) {
        super(context, R.style.dialog);
        this.mcontext = context;
    }


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

        Configuration config = getContext ().getResources ().getConfiguration();//获得设置对象

        DisplayMetrics dm = getContext ().getResources ().getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。

        config.locale = Locale.SIMPLIFIED_CHINESE; //简体中文

        getContext ().getResources ().updateConfiguration(config, dm);

        setContentView(getMyLayoutResource());

        ButterKnife.bind(this);

    }


    /**
     * 初始化布局
     */
    protected abstract int getMyLayoutResource();

    public interface IDialogClick {
        public void onSure(View v, String value);

        public void onCancle(View v, String value);
    }

    public void setiDialogClick ( IDialogClick iDialogClick ) {
        this.iDialogClick = iDialogClick;
    }




    public void changeLocale ( Locale locale ) {

        Configuration config = getContext ().getResources ().getConfiguration();//获得设置对象

        DisplayMetrics dm = getContext ().getResources () .getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。

        config.locale = locale;
        getContext ().getResources ().updateConfiguration ( config,dm );
    }

    protected void directTo( Class<?> activity ){
        directTo(activity,null);
    }

    protected void directTo( Class<?> activity ,Bundle bundle){
        Intent intent = new Intent();
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setClass(mcontext,activity);
        mcontext.startActivity(intent);
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    protected void reDirectTo(Class<?> activity){
        reDirectTo(activity);
    }

    protected void reDirectTo(Class<?> activity,Bundle bundle){
        Intent intent = new Intent();
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(mcontext,activity);
//        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

