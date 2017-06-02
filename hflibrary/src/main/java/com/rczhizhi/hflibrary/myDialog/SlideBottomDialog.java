package com.rczhizhi.hflibrary.myDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.rczhizhi.hflibrary.R;

import butterknife.ButterKnife;


/**
 * Created by hufeng on 2017/2/23.
 */

public abstract class SlideBottomDialog extends Dialog {

    private int srceenWidth;

    private WindowManager.LayoutParams wlp;

    private Window window;

    private Context mcontext;
    /**
     * 构造函数，添加动画效果
     * @param context
     */
    public SlideBottomDialog(Context context) {
        super(context, R.style.DialogSlideAnim);
        this.mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMyLayoutResource());
        ButterKnife.bind(this);
    }

    /**
     * 初始化布局
     */
    protected abstract int getMyLayoutResource();

    /**
     * 复写启动函数，添加动画属性
     */
    @Override
    public void show() {
        super.show();

        window = getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);

        wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
//        wlp.flags &= WindowManager.LayoutParams.FLAG_BLUR_BEHIND;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO){
            srceenWidth = window.getWindowManager().getDefaultDisplay().getWidth();
//            wlp.height = window.getWindowManager().getDefaultDisplay().getHeight();
        }else {
            Point point = new Point();
            window.getWindowManager().getDefaultDisplay().getSize(point);
            srceenWidth = point.x;
//            wlp.height = point.y;
        }

        wlp.width = srceenWidth;
        window.setAttributes(wlp);
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
