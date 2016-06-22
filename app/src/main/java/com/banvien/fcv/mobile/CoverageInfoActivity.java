package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Linh Nguyen on 6/22/2016.
 */
public class CoverageInfoActivity extends BaseDrawerActivity {
    private final String TAG = "CoverageInfoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage);
    }
}
