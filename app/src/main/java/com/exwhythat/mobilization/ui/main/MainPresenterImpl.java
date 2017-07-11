package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by exwhythat on 07.07.17.
 */

public class MainPresenterImpl<V extends MainView> extends BasePresenterImpl<V>
        implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl() {
        super();
    }

    @Override
    public void onDrawerWeatherClick() {
        getMvpView().showWeatherFragment();
    }

    @Override
    public void onDrawerAboutClick() {
        getMvpView().showAboutFragment();
    }

    @Override
    public void onDrawerSettingsClick() {
        getMvpView().showSettingsFragment();
    }
}
