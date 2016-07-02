package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Linh Nguyen on 7/2/2016.
 */
public class StatusOutletActivity extends BaseDrawerActivity {
    private final String TAG = "StatusOutletActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_outlet);
    }
}
