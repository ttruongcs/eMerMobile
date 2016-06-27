package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class BaseDrawerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentViewWithoutInject(R.layout.activity_drawer);
        if (layoutResID > 0) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_frame);
            LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        }

        bindViews();
        setupToolbar();
    }


    @Override
    protected void setupToolbar() {
        super.setupToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    protected void setToolbarTitle(String title) {
        super.setToolbarTitle(title);
    }

}
