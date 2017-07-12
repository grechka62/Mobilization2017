package com.exwhythat.mobilization.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.exwhythat.mobilization.di.component.ActivityComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by exwhythat on 10.07.17.
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    protected Unbinder unbinder;

    private BaseActivity activity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Nullable
    public ActivityComponent getActivityComponent() {
        if (activity != null) {
            return activity.getActivityComponent();
        }
        return null;
    }

    @Nullable
    public BaseActivity getBaseActivity() {
        return activity;
    }
}