package com.rczhizhi.hflibrary.myEvent;

import java.util.ArrayList;

/**
 * Created by ishishuji on 15/6/13.
 */
public class ImageEvent implements BaseEvent {

    private ArrayList<String> data;

    private int tag;

    public ArrayList< String > getData () {
        return data;
    }

    public void setData ( ArrayList< String > data ) {
        this.data = data;
    }

    public int getTag () {
        return tag;
    }

    public void setTag ( int tag ) {
        this.tag = tag;
    }
}
