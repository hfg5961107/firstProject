package com.rczhizhi.hflibrary.myDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by hufeng on 2017/3/1.
 */

public abstract class MyDialog extends Dialog {

    private Context mcontext;

    public MyDialog(Context context) {
        super(context);
        this.mcontext = context;
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
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
}
