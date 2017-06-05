package com.rczhizhi.hflibrary.absFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rczhizhi.hflibrary.R;
import com.rczhizhi.hflibrary.myDialog.MyNullDialog;
import com.rczhizhi.hflibrary.myEvent.BaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by 嗨创科技 on 2017/6/5.
 */

public abstract class BaseFragment extends Fragment {

    private int myProgress = 0;

    private Random random = new Random ();

    private List< String > loadedUrls = new ArrayList< String >();


    private View view;

    private TextView dialogTitle;


    public void showProgressDialog () {
        showProgressDialog ( "加载中..." );
    }

    public void showProgressDialog ( String text ) {

    }

    public void closeProgressDialog () {

    }


    private MyNullDialog loadingDialog;

    public MyNullDialog getLoadingDialog () {
        return loadingDialog;
    }

    public void setLoadingDialog ( MyNullDialog loadingDialog ) {
        this.loadingDialog = loadingDialog;
    }


    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

    }

    public void changeLocale ( Locale locale ) {
        Resources resources = getResources ();//获得res资源对象

        Configuration config = resources.getConfiguration ();//获得设置对象

        DisplayMetrics dm = resources.getDisplayMetrics ();//获得屏幕参数：主要是分辨率，像素等。

        config.locale = locale;
        resources.updateConfiguration ( config, dm );
    }


    public View getMyView () {
        return view;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        if ( view == null ) {
            view = inflater.inflate ( getLayoutId (), container, false );
        }

        ButterKnife.bind ( this, view );
        initView ( view );
        ViewGroup parent = ( ViewGroup ) view.getParent ();
        if ( parent != null ) {
            parent.removeView ( view );
        }

        return view;
    }

    abstract public int getLayoutId ();

    abstract public void initView ( View view );

    @Override
    public void onPause () {
        super.onPause ();
        if ( loadingDialog != null ) {
            loadingDialog.dismiss ();
        }
    }

    @Override
    public void onResume () {
        super.onResume ();
        if ( ! EventBus.getDefault ().isRegistered ( this ) ) {
            EventBus.getDefault ().register ( this );
        }
    }

    public void addLoadedUrl ( String url ) {
        if ( ! loadedUrls.contains ( url ) ) {
            loadedUrls.add ( url );
        }
    }

    public boolean isUrlExists ( String url ) {
        return loadedUrls.contains ( url );
    }

    private Runnable webLoading = new Runnable () {
        @Override
        public void run () {
            while ( isWaiting ) {
                myProgress += 10;
//                webConWaitting ( myProgress );
                try {
                    Thread.sleep ( random.nextInt ( 1000 ) );
                } catch ( InterruptedException e ) {
                    e.printStackTrace ();
                }
            }
        }
    };

    private boolean isWaiting = true;



    @Override
    public void onDestroy () {
        super.onDestroy ();
        if ( loadingDialog != null ) {
            loadingDialog.dismiss ();
        }
    }

    public void showLoading () {
        if ( loadingDialog != null ) {
            if ( ! loadingDialog.isShowing () ) {
                loadingDialog.show ();
            }
        }
    }

    public void dismissLoading () {
        if ( loadingDialog != null ) {
            loadingDialog.dismiss ();
        }
    }


    protected void directTo ( Class< ? > activity ) {
        directTo ( activity, null );
    }

    protected void directTo ( Class< ? > activity, Bundle bundle ) {
        Intent intent = new Intent ();
        if ( bundle != null ) {
            intent.putExtras ( bundle );
        }
        intent.setClass ( getActivity (), activity );
        startActivity ( intent );

    }

    @Override
    public void onDestroyView () {
        super.onDestroyView ();
        closeProgressDialog ();
    }

    @Override
    public void onAttach ( Activity activity ) {
        super.onAttach ( activity );

        dialogTitle = (TextView) activity.getLayoutInflater ().inflate ( R.layout.dialog_title, null );
        if ( ! EventBus.getDefault ().isRegistered ( this ) ) {
            EventBus.getDefault ().register ( this );
        }
    }

    @Override
    public void onDetach () {
        super.onDetach ();
        if ( EventBus.getDefault ().isRegistered ( this ) ) {
            EventBus.getDefault ().unregister ( this );
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread ( BaseEvent baseEvent ) {

    }

}
