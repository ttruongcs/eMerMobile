package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.banvien.fcv.mobile.fragments.HomeFragment;


public class MainActivity extends BaseDrawerActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_frame, new HomeFragment());
        tx.commit();
    }
}
