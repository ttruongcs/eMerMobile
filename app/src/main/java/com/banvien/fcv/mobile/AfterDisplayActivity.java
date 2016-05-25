package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.banvien.fcv.mobile.db.Repo;

/**
 * Created by Linh Nguyen on 5/25/2016.
 */
public class AfterDisplayActivity extends BaseDrawerActivity {
    private static final String TAG = "TAG";

    private Repo repo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_display);
        repo = new Repo(this);
    }
}
