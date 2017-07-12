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
        // TODO: is it good practice or there is better way?.. (Except Kotlin, ofc).
        MainView view = getMvpView();
        if (view != null) {
            view.showWeatherFragment();
        }
    }

    @Override
    public void onDrawerAboutClick() {
        MainView view = getMvpView();
        if (view != null) {
            view.showAboutFragment();
        }
    }

    @Override
    public void onDrawerSettingsClick() {
        MainView view = getMvpView();
        if (view != null) {
            view.showSettingsFragment();
        }
    }
}
