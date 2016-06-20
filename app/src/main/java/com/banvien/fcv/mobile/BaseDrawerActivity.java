package com.banvien.fcv.mobile;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;

public class BaseDrawerActivity extends BaseActivity {
    private static boolean loggedAcc = false;
    Integer displayViewWidth_ = 0;
    Integer displayViewHeight_ = 0;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithoutInject(R.layout.activity_drawer);

    }

    @Override
    public void setContentView(int layoutResID) {

        if (layoutResID > 0) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content_frame);
            LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        }

        bindViews();
        setupHeader();
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

    @Override
    protected void setToolbarTitle(String title) {
        super.setToolbarTitle(title);
    }

    protected void setupHeader() {
//        toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
//            public void onDrawerOpened(View drawerView) {
//                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            drawerLayout.closeDrawer(GravityCompat.START);
//                        }
//                    });
//                }
//            }
//            public void onDrawerClosed(View drawerView) {
//                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        drawerLayout.openDrawer(GravityCompat.START);
//                    }
//                });
//            }
//        };
//
//        drawerLayout.setDrawerListener(toggle);
//        toggle.syncState();
    }

    protected void replaceFragmentContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

}
