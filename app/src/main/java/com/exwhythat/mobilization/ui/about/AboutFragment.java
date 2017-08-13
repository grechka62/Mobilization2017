package com.exwhythat.mobilization.ui.about;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import javax.inject.Inject;


public class AboutFragment extends BaseFragment implements AboutView {

    public static final String TAG = AboutFragment.class.getCanonicalName();

    @Inject
    AboutPresenterImpl presenter;

    public AboutFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
    }

    @NonNull
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
