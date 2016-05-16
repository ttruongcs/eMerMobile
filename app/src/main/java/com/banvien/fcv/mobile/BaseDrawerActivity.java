package com.banvien.fcv.mobile;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class BaseDrawerActivity extends BaseActivity {
    private static boolean loggedAcc = false;
    Integer displayViewWidth_ = 0;
    Integer displayViewHeight_ = 0;

    ActionBarDrawerToggle toggle;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);

        if (layoutResID > 0) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_frame);
            LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        }

        bindViews();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayViewWidth_ = size.x;
        displayViewHeight_ = size.y;
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

    protected void replaceFragmentContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

}
