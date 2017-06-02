package com.crystal.androidone.util;

/**
 * Created by 嗨创科技 on 2017/6/2.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}
