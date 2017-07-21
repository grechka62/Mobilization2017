package com.exwhythat.mobilization.ui.about;

import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by exwhythat on 07.07.17.
 */

public class AboutPresenterImpl<V extends AboutView> extends BasePresenterImpl<V>
        implements AboutPresenter<V> {

    @Inject
    public AboutPresenterImpl() {
        super();
    }
}
