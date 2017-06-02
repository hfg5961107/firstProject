package com.rczhizhi.hflibrary.myEvent;

import android.view.View;

/**
 * Created by ishishuji on 14/12/29.
 */
public class ItemEvent<T> implements BaseEvent {

    private int position;

    private T value;

    private View view;

    public int getPosition () {
        return position;
    }

    public void setPosition ( int position ) {
        this.position = position;
    }

    public View getView () {
        return view;
    }

    public void setView ( View view ) {
        this.view = view;
    }

    public T getValue () {
        return value;
    }

    public void setValue ( T value ) {
        this.value = value;
    }
}
