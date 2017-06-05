package com.rczhizhi.hflibrary.absActivity;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rczhizhi.hflibrary.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by 嗨创科技 on 2017/6/5.
 */

public abstract class FragmentTabsActivity extends FragmentTabBaseActivity {

    private TextView mTabTextView;

    @Override
    public String setTitle () {
        return getTitles ().get ( 0 );
    }

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
    }

    @Override
    protected void onResume () {
        super.onResume ();
    }


    abstract public int getTabTextColorStateList ();

    abstract public int getTabIcon ( int index );

    @Override
    public View getTabItemView (int position ) {
        View view = getLayoutInflater ().inflate ( R.layout.bottom_tab_item, null );
        ImageView mImageview = ( ImageView ) view.findViewById ( R.id.imageview );
        mTabTextView = (TextView) view.findViewById ( R.id.textview );

        int tabIcon = getTabIcon ( position );

        if ( tabIcon != 0 ) {
            mImageview.setImageResource ( tabIcon );
        }

        XmlResourceParser xpp= getResources ().getXml ( getTabTextColorStateList () );

        mTabTextView.setText ( getTitles ().get ( position ) );

        try {
            ColorStateList csl= ColorStateList.createFromXml ( getResources (), xpp );
            mTabTextView.setTextColor ( csl );
        } catch ( XmlPullParserException e ) {
            e.printStackTrace ();
        } catch ( IOException e ) {
            e.printStackTrace ();
        }

        return view;
    }


    public TextView getmTabTextView () {
        return mTabTextView;
    }

    public void setmTabTextView ( TextView mTabTextView ) {
        this.mTabTextView = mTabTextView;
    }

}
