package com.rczhizhi.hflibrary.myEvent;

/**
 * Created by ishishuji on 14/12/12.
 */
public class MyVolleyEvent < T > implements BaseEvent {

    private int position;

    private int status;

    public boolean isHasPosition () {
        return hasPosition;
    }

    public void setHasPosition ( boolean hasPosition ) {
        this.hasPosition = hasPosition;
    }

    private boolean hasPosition = false;

    private T _success;


    public T get_success () {
        return _success;
    }

    public void set_success ( T _success ) {
        this._success = _success;
    }


    public int getPosition () {
        return position;
    }

    public void setPosition ( int position ) {
        this.position = position;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus ( int status ) {
        this.status = status;
    }
}
