package com.exwhythat.mobilization.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.di.component.DaggerActivityComponent;
import com.exwhythat.mobilization.di.module.ActivityModule;

import butterknife.Unbinder;

/**
 * Created by exwhythat on 09.07.17.
 */

public class BaseActivity extends AppCompatActivity implements BaseView {

    private ActivityComponent mActivityComponent;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }

    public void setUnbinder(Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
