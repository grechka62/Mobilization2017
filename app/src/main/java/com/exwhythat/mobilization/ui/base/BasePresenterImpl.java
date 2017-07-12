package com.exwhythat.mobilization.ui.base;

import javax.inject.Inject;

/**
 * Created by exwhythat on 09.07.17.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    private V mView;

    @Inject
    public BasePresenterImpl() {
    }

    @Override
    public void onAttach(V view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    public V getMvpView() {
        return mView;
    }
}
