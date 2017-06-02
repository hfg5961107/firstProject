package com.rczhizhi.hflibrary.absActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.rczhizhi.hflibrary.R;


/**
 * Created by 嗨创科技 on 2017/5/16.
 */

public abstract class SubActivity extends SubBaseActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

        setToolBarBackgroundColor(getResources().getColor(R.color.colorPrimary));

        Button button = ( Button ) getToolbar ().findViewById ( R.id.top_left_bt );
        button.setText ( getResources ().getString ( R.string.back ) );
        button.setCompoundDrawablesWithIntrinsicBounds ( R.drawable.back_icon, 0, 0, 0 );
        button.setGravity ( Gravity.LEFT | Gravity.CENTER_VERTICAL );
        button.setCompoundDrawablePadding ( getResources ().getDimensionPixelSize ( R.dimen.toolbar_bt_drawable_padding ) );
        button.setOnClickListener ( new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                SubActivity.this.finish ();
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        } );

    }
}
