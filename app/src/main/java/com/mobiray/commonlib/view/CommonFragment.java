package com.mobiray.commonlib.view;

import android.app.Fragment;

import com.mobiray.commonlib.servises.events.EventMessage;
import com.mobiray.commonlib.servises.events.EventObserver;

import de.greenrobot.event.EventBus;

/**
 * Created by Alexey on 19.01.2016.
 */
public abstract class CommonFragment extends Fragment implements EventObserver {

    @Override
    public void onStart() {
        super.onStart();
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Override
    public void onEventMainThread(EventMessage eventMessage) {
        switch (eventMessage.code) {
            case EventMessage.CODE_AD_APPS_UPDATED:
                adAppsUpdated();
                break;
        }
    }

    protected boolean isUseEventBus() {
        return false;
    }

    protected void adAppsUpdated() {
        //
    }
}
