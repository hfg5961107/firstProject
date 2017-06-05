package com.crystal.androidone.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import com.crystal.androidone.R;
import com.rczhizhi.hflibrary.Utils.ToastUtil;
import com.rczhizhi.hflibrary.absActivity.FragmentTabsActivity;
import com.rczhizhi.hflibrary.absFragment.NullFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 嗨创科技 on 2017/6/5.
 */

public class HomeActivity extends FragmentTabsActivity {


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
    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabs)
    TabWidget tabs;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;
    @BindView(R.id.fmBg)
    LinearLayout fmBg;

    @Override
    public int getTabTextColorStateList() {
        return R.color.main_bottom_text_color;
    }

    @Override
    public int getTabIcon(int index) {
        switch (index) {
            case 0:
                return R.mipmap.ic_launcher_round;
            case 1:
                return R.mipmap.ic_launcher_round;
            case 2:
                return R.mipmap.ic_launcher_round;
            case 3:
                return R.mipmap.ic_launcher_round;
            case 4:
                return R.mipmap.ic_launcher_round;
            default:
                return R.mipmap.ic_launcher_round;
        }
    }

    @Override
    public void resouceInit() {
        addTitle("首页");
        addTitle("附近");
        addTitle("消息");
        addTitle("自诊");
        addTitle("我的");
    }

    @Override
    public Bundle getBundles(int index) {
        Bundle bundle = new Bundle();
        return bundle;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Class<?> getFragmentClass(int index) {
        switch (index) {
            case 0:
                return NullFragment.class;
            case 1:
                return NullFragment.class;
            case 2:
                return NullFragment.class;
            case 3:
                return NullFragment.class;
            case 4:
                return NullFragment.class;
            default:
                return NullFragment.class;
        }
    }

    @Override
    public String getTag(int index) {
        return getTitles().get(index);
    }

    @Override
    public boolean isShowDivider() {
        return false;
    }

    @Override
    public void onMyTabChanged(String tabId) {
        if (TextUtils.equals(tabId, "首页")) {
            setCustomTitle("APP名称");
        } else {
            setCustomTitle(tabId);
        }
        switch (tabId) {
            case "首页":
                toolbar.setVisibility(View.VISIBLE);
                topLeftBt.setText("");
                topLeftBt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                topRightBt.setText("");
                topRightBt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_launcher, 0);
                break;
            case "附近":
                ToastUtil.showShort(getContext(), "附近功能正在开发...");
                break;
            case "消息":
                topRightBt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                toolbar.setVisibility(View.VISIBLE);
                break;
            case "自诊":
                ToastUtil.showShort(getContext(), "自诊功能正在开发...");
                break;
            case "我的":
                toolbar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected boolean isShowStatusBar() {
        return true;
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
    public int getMyLayoutResource() {
        return R.layout.fragmenttabs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
