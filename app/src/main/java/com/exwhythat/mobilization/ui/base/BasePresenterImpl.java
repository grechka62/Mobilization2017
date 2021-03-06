package com.exwhythat.mobilization.ui.base;

import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * Created by exwhythat on 09.07.17.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private V mvpView;

    @Inject
    public BasePresenterImpl() {
    }

    @Override
    public void onAttach(V view) {
        if (mvpView != null) {
            throw new IllegalStateException("MvpView is not null in onAttach! Check the view was properly detached");
        }
        mvpView = view;
    }

    @Override
    public void onDetach() {
        mvpView = null;
    }

    @Nullable
    public V getMvpView() {
        return mvpView;
    }
}
