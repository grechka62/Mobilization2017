package com.exwhythat.mobilization.ui.main;

/**
 * Created by exwhythat on 07.07.17.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onSomeActionFromActivity() {
        //TODO some logic goes here
    }
}
