package com.exwhythat.mobilization.ui.about;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import javax.inject.Inject;


public class AboutFragment extends BaseFragment implements AboutView {

    public static final String TAG = AboutFragment.class.getCanonicalName();

    @Inject
    AboutPresenterImpl<AboutView> presenter;

    public AboutFragment() {}

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.action_about);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
