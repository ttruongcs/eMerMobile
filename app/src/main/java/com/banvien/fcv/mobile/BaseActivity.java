package com.banvien.fcv.mobile;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vincent on 3/20/2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    protected Handler mHandler = new Handler();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
