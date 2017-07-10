package com.exwhythat.mobilization.ui.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @Inject
    AboutPresenter<AboutView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initActivity();
    }

    private void initActivity() {
        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }
}
