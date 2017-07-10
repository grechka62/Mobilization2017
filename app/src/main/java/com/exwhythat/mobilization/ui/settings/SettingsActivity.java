package com.exwhythat.mobilization.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.ui.base.BaseActivity;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity {

    @Inject
    SettingsPresenter<SettingsView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initActivity();
    }

    private void initActivity() {
        getActivityComponent().inject(this);
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
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
