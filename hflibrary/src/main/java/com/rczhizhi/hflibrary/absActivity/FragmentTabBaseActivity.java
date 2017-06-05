package com.rczhizhi.hflibrary.absActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.rczhizhi.hflibrary.R;
import com.rczhizhi.hflibrary.absFragment.NullFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 嗨创科技 on 2017/6/5.
 */

public abstract class FragmentTabBaseActivity extends SubBaseActivity {

    private FragmentManager fm;
    //定义一个布局
    private LayoutInflater layoutInflater;
    //定义FragmentTabHost对象
    private FragmentTabHost mTabHost;


    private List< Class > fragmentArray = new ArrayList< Class >();

    public List< String > getTitles () {
        return titles;
    }

    private List< String > titles = new ArrayList< String > ();

    private boolean hasCheckLogin;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        resouceInit ();
        super.onCreate ( savedInstanceState );

        fm = getSupportFragmentManager ();

        initView ();
    }

    abstract public void resouceInit ();


    abstract public Bundle getBundles ( int index );

    abstract public int getCount ();

    abstract public Class< ? > getFragmentClass ( int index );

//    @Override
//    public int getMyLayoutResource () {
//        return R.layout.fragmenttabs;
//    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    private void initView () {
        //实例化布局对象
        layoutInflater = LayoutInflater.from ( this );

        //实例化TabHost对象，得到TabHost
        mTabHost = ( FragmentTabHost ) findViewById ( android.R.id.tabhost );


        mTabHost.setup ( this, getSupportFragmentManager (), R.id.realtabcontent );

        //得到fragment的个数
        int count = getCount ();

        for ( int i = 0 ; i < count ; i++ ) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec ( getTag ( i ) ).setIndicator ( getTabItemView ( i ) );

            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab ( tabSpec, getFragmentClass ( i ), getBundles ( i ) );
            //设置Tab按钮的背景
        }
        mTabHost.setOnTabChangedListener ( new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged ( String s ) {
                if ( getSupportFragmentManager ().findFragmentByTag ( s ) instanceof NullFragment) {
                    onMyTabChanged ( s );
                } else {
                    if ( getSupportFragmentManager () != null ) {
                        getSupportFragmentManager ().executePendingTransactions ();
                    }
                    onMyTabChanged ( s );
                }

            }
        } );

        if ( ! isShowDivider () ) {
            if ( Integer.parseInt ( Build.VERSION.SDK ) >= Build.VERSION_CODES.HONEYCOMB ) {
                mTabHost.getTabWidget ().setShowDividers ( LinearLayout.SHOW_DIVIDER_NONE );
            }
        }

    }

    abstract public String getTag ( int index );

    abstract public boolean isShowDivider ();

    abstract public View getTabItemView ( int position );


    public void addFragment ( Fragment fragment ) {
        fragmentArray.add ( fragment.getClass () );
    }

    public void addFragment ( Class< ? extends Fragment > cls ) {
        fragmentArray.add ( cls );
    }


    public void addFragments ( List< Class > fragments ) {
        fragmentArray.addAll ( fragments );
    }

    abstract public void onMyTabChanged ( String tabId );

    public LayoutInflater getMyLayoutInflater () {
        return layoutInflater;
    }


    public void addTitle ( String title ) {
        titles.add ( title );
    }

    public void addTitles ( List< String > titles ) {
        titles.addAll ( titles );
    }

    public void setCurrentTab ( int index ) {
        if ( mTabHost != null ) {
            mTabHost.setCurrentTab ( index );
        }
    }

    public void setCurrentTab ( String tag ) {
        if ( mTabHost != null ) {
            mTabHost.setCurrentTabByTag ( tag );
        }
    }

    public boolean isHasCheckLogin () {
        return hasCheckLogin;
    }

    public void setHasCheckLogin ( boolean hasCheckLogin ) {
        this.hasCheckLogin = hasCheckLogin;
    }
}
