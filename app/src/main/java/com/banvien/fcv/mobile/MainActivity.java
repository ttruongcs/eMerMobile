package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banvien.fcv.mobile.fragments.HomeFragment;
import com.banvien.fcv.mobile.fragments.PrepareFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


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
