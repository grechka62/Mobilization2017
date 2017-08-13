package com.exwhythat.mobilization.ui.base;

/**
 * Created by exwhythat on 09.07.17.
 */

public interface BasePresenter<V> {
    void onAttach(V view);
    void onDetach();
}
