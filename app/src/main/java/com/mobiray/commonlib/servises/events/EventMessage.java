package com.mobiray.commonlib.servises.events;

/**
 * Created by Alexey on 19.01.2016.
 */
public class EventMessage {

    public static final int CODE_AD_APPS_UPDATED = 1;

    public int code;

    public EventMessage(int code) {
        this.code = code;
    }
}
