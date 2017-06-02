package com.rczhizhi.hflibrary.absActivity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rczhizhi.hflibrary.R;

/**
 * Created by 嗨创科技 on 2017/5/16.
 */

public abstract class SubBaseActivity extends BaseActivity {

    private LinearLayout customBarView = null;

    private TextView titleView = null;

    private final int APP_THEME = android.R.color.background_dark;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        //扁平化ui
//        FlatUI.initDefaultValues ( this );

        setToolbar ( (Toolbar) findViewById ( R.id.toolbar ) );

        if ( getToolbar () != null ) {
            getToolbar ().setBackgroundColor ( getResources ().getColor ( android.R.color.transparent ) );
        }

        titleView = ( TextView ) getToolbar ().findViewById ( R.id.toolbar_title );

        if ( ! TextUtils.isEmpty ( setTitle () ) ) {
            titleView.setText ( setTitle () );
        }

        if ( getToolbar () != null ) {
            setSupportActionBar ( getToolbar () );
        }

    }


    public Button getRightBt (String text ) {
        if ( getToolbar () != null ) {
            Button button = ( Button ) getToolbar ().findViewById ( R.id.top_right_bt );
            button.setText ( text );
            return button;
        } else {
            return null;
        }
    }

    public abstract String setTitle ();

    public void setToolBarBackgroundColor ( int color ) {

        if ( getToolbar () != null ) {
            getToolbar ().setBackgroundColor ( color );
        }
    }

    public void setActionBarBackgroundResource ( int resource ) {
        if ( getToolbar () != null ) {
            getToolbar ().setBackgroundResource ( resource );
        }
    }

    public void setCustomSubTitle ( String subTitle ) {
        if ( getToolbar () != null ) {
            getToolbar ().setSubtitle ( subTitle );
        }
    }

    private void updateToolbar () {
        if ( getToolbar () != null ) {
            getToolbar ().postInvalidate ();
        }
    }

    public void addIconToActionBarCenter ( View... centerViews ) {
        if ( getToolbar () != null ) {
            LinearLayout linearLayout = ( LinearLayout ) getToolbar ().findViewById ( R.id.centerArea );
            linearLayout.removeAllViews ();
            for ( View view : centerViews ) {
                linearLayout.addView ( view );
            }
        }

    }


    public void addIconToActionBarLeft ( View leftView ) {
        if ( getToolbar () != null ) {
            LinearLayout linearLayout = ( LinearLayout ) getToolbar ().findViewById ( R.id.leftArea );
            linearLayout.removeAllViews ();
            linearLayout.addView ( leftView );
        }
    }


    public void addIconToActionBarRight ( View rightView ) {
        if ( getToolbar () != null ) {
            LinearLayout linearLayout = ( LinearLayout ) getToolbar ().findViewById ( R.id.rightArea );
            linearLayout.removeAllViews ();
            linearLayout.addView ( rightView );
        }
    }

    public void addIconToActionBarRight ( View... rightViews ) {
        if ( getToolbar () != null ) {
            LinearLayout linearLayout = ( LinearLayout ) getToolbar ().findViewById ( R.id.rightArea );
            linearLayout.removeAllViews ();
            for ( View view : rightViews ) {
                linearLayout.addView ( view );
            }
        }
    }

    public View getCustomBarView () {
        return customBarView;
    }

    public void setCustomBarView ( LinearLayout customBarView ) {
        this.customBarView = customBarView;
    }

    public void setCustomTitle ( String title ) {
        titleView.setText ( title );
    }

    public String getCustomTitle () {
        return titleView.getText ().toString ();
    }

    protected void setViewBackground ( View view, Drawable drawable ) {
        if ( Build.VERSION.SDK_INT >= 16 ) {
            view.setBackground ( drawable );
        } else {
            view.setBackgroundDrawable ( drawable );
        }
    }


}
