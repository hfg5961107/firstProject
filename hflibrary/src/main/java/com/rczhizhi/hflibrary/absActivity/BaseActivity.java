package com.rczhizhi.hflibrary.absActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rczhizhi.hflibrary.R;
import com.rczhizhi.hflibrary.myEvent.BaseEvent;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by 嗨创科技 on 2017/5/16.
 */

public abstract class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {

    private Context context;

    public ActionBar actionBar;

    private boolean isOnSave = false;

    private int myProgress = 0;

    private Random random = new Random();

    private boolean isWaiting = true;

    public interface IStickyEventListener<T> {
        public void onEvent(T obj);
    }

    private List<IStickyEventListener> stickyEventListeners = new ArrayList<IStickyEventListener>();


    public <T> void addListeners(IStickyEventListener<T> listener) {
        stickyEventListeners.add(listener);
    }

    public <T> void removeListeners(IStickyEventListener<T> listener) {
        stickyEventListeners.remove(listener);
    }

    private SystemBarTintManager tintManager;


    private SwipeBackActivityHelper mHelper;

    private SwipeBackLayout mSwipeBackLayout;


    public interface IChangeStatusBar {
        public int getStatusBarColor();
    }

    private IChangeStatusBar iChangeStatusBar;

    public void setIChangeStatusBarListener(IChangeStatusBar iChangeStatusBarListener) {
        this.iChangeStatusBar = iChangeStatusBarListener;
    }

    public void removeIChanegStatusBarListener() {
        this.iChangeStatusBar = null;
    }

    protected abstract boolean isShowStatusBar();


//    public void showProgressDialog () {
//        showProgressDialog ( "加载中..." , true );
//    }
//
//
//    public void showProgressDialog ( boolean canCancel ) {
//        showProgressDialog ( "加载中..." , canCancel );
//    }
//
//    public void showProgressDialog ( String text ) {
//        showProgressDialog ( text,true );
//    }
//
////    public void showProgressDialog ( String text , boolean canCancel ) {
////        if ( mRouteCalculatorProgressDialog != null ) {
////            if ( ! mRouteCalculatorProgressDialog.isShowing () ) {
////                mRouteCalculatorProgressDialog.setCancelable ( canCancel );
////                mRouteCalculatorProgressDialog.showProgressDialog ( text );
////            }
////        }
////    }
////
////    public void closeProgressDialog () {
////        if ( mRouteCalculatorProgressDialog != null ) {
////
////            mRouteCalculatorProgressDialog.dismiss ();
////        }
////    }

    public void changeLocale(Locale locale) {
        Resources resources = getResources();//获得res资源对象

        Configuration config = resources.getConfiguration();//获得设置对象

        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。

        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }

    protected ActionBar getActionBar(Activity activity) {
        if (activity instanceof ActionBarActivity) {
            return ((ActionBarActivity) activity).getSupportActionBar();
        }
        ActionBar actionBar = getActionBarWithReflection(activity, "getSupportActionBar");
        if (actionBar == null) {
            throw new RuntimeException("Activity should derive from ActionBarActivity "
                    + "or implement a method called getSupportActionBar");
        }
        return actionBar;
    }

    protected <T> T getActionBarWithReflection(Activity activity, String methodName) {
        try {
            Method method = activity.getClass().getMethod(methodName);
            return (T) method.invoke(activity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void onThisFinish() {
        this.finish();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }


    /**
     * swipeBack start
     * *
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHelper.onPostCreate();
    }


    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mHelper.findViewById(id);
    }


    public int getMyProgress() {
        return myProgress;
    }

    public void setMyProgress(int myProgress) {
        this.myProgress = myProgress;
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            onMyClick(v);
        }
    };

    public <T> T getStickyEvent(Class<T> eventCls) {
        return EventBus.getDefault().getStickyEvent(eventCls);
    }

    public void addClickListener(View view) {
        view.setOnClickListener(clickListener);
    }

    abstract public void onMyClick(View v);


    public boolean isOnSave() {
        return isOnSave;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isOnSave = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition ( R.anim.slide_in_right, R.anim.slide_out_left );
    }


    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
            getWindow().setNavigationBarColor(color);
        } else {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//                ActivityUtils.enableTint ( this, new ColorDrawable( color ) );
                tintManager.setNavigationBarTintColor(color);
            } else {
                tintManager.setStatusBarTintColor(color);
                tintManager.setNavigationBarTintColor(color);
            }
        }
    }


    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";


    private int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

//    public void onEvent ( BaseEvent event ) {
//
//    }


    public void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }

        getSupportActionBar().hide();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isShowStatusBar()) {
            if (iChangeStatusBar != null) {
                tintManager = new SystemBarTintManager(this);
                // enable status bar tint
                tintManager.setStatusBarTintEnabled(true);
                // enable navigation bar tint
                tintManager.setNavigationBarTintEnabled(true);

                setStatusBarColor(iChangeStatusBar.getStatusBarColor());
            }
        }
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//            setMeizuStatusBarDarkIcon(this, true);
//            setMiuiStatusBarDarkMode(this, true);
//        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化布局
        setContentView(getMyLayoutResource());
        //开启注解
        ButterKnife.bind(this);

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        mSwipeBackLayout = getSwipeBackLayout();

        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        setContext(this);
        //初始化界面
        initUI();
        
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    private Toolbar toolbar;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        updateUI();
    }

    /**
     * 刷新界面
     */
    protected abstract void updateUI();

    /**
     * 初始化UI
     */
    protected abstract void initUI();

    /**
     * 初始化布局
     */
    protected abstract int getMyLayoutResource();

    protected void directTo(Class<?> activity) {
        directTo(activity, null);
    }

    protected void directTo(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(this, activity);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    protected void reDirectTo(Class<?> activity) {
        reDirectTo(activity);
    }

    protected void reDirectTo(Class<?> activity, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(this, activity);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //EventBus注册
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //EventBus解除注册
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        getSwipeBackLayout().scrollToFinishActivity();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent event){

    }
}
