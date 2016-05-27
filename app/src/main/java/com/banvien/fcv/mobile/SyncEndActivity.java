package com.banvien.fcv.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import butterknife.Bind;

public class SyncEndActivity extends BaseDrawerActivity {

    private static ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    @Bind(R.id.step2_photo)
    ImageView actionStep;

    @Bind(R.id.step3_photo)
    ImageView endStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog  = new ProgressDialog(this);
        setContentView(R.layout.sync_end_day);

    }

}
