package com.exwhythat.mobilization.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.di.component.DaggerActivityComponent;
import com.exwhythat.mobilization.di.module.ActivityModule;

import butterknife.Unbinder;

/**
 * Created by exwhythat on 09.07.17.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ActivityComponent activityComponent;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    @NonNull
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }
}
