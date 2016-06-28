package com.banvien.fcv.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Linh Nguyen on 6/28/2016.
 */
public class LoginActivity extends BaseDrawerActivity {
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
