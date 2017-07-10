package com.exwhythat.mobilization.ui.settings;

import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by exwhythat on 07.07.17.
 */

public class SettingsPresenterImpl<V extends SettingsView> extends BasePresenterImpl<V>
        implements SettingsPresenter<V> {

    @Inject
    public SettingsPresenterImpl() {
        super();
    }
}
