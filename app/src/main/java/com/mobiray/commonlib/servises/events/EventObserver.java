package com.mobiray.commonlib.servises.events;

/**
 * Created by Alexey on 19.01.2016.
 */
public interface EventObserver {

    void onEventMainThread(EventMessage eventMessage);
}
