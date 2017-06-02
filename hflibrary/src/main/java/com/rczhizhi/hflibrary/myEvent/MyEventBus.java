package com.rczhizhi.hflibrary.myEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ishishuji on 14/12/7.
 */
public class MyEventBus {

    private static final EventBus bus = EventBus.getDefault ();
    private static final Set< Object > objects = new HashSet< Object > ();
    private static boolean active = false;

    public static void register ( Object obj ) {
        objects.add ( obj );
        if ( active ) {
            bus.register ( obj );
        }
    }
//
//    public static void registerSticky ( Object obj ) {
//        bus.registerSticky ( obj );
//    }
//
//
//    public static void registerSticky ( Class< ? > cls ) {
//        bus.registerSticky ( cls );
//    }

    public static void unregisterSticky ( Object obj ) {
        bus.removeStickyEvent ( obj );
    }

    public static void unregisterSticky ( Class< ? > cls ) {
        bus.removeStickyEvent ( cls );
    }


    public static void unregisterAllSticky () {
        bus.removeAllStickyEvents ();
    }

    public static void unregister ( Object obj ) {
        objects.remove ( obj );
        if ( active ) {
            bus.unregister ( obj );
        }
    }

    public static void post ( BaseEvent event ) {
        bus.post ( event );
    }

    public static void postSticky ( BaseEvent event ) {
        bus.postSticky ( event );
    }

    public static void paused () {
        active = false;
        for ( Object obj : objects ) {
            bus.unregister ( obj );
        }
    }

    public static void resumed () {
        active = true;
        for ( Object obj : objects ) {
            bus.register ( obj );
        }
    }

}
