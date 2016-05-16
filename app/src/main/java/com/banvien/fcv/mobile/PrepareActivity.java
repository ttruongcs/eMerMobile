package com.banvien.fcv.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;



import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;

public class PrepareActivity extends ActionBarActivity {

    private static ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private Activity parentActivity;

    @Bind(R.id.step1_photo)
    ImageView prepareStep;

    @Bind(R.id.step2_photo)
    ImageView actionStep;

    @Bind(R.id.step3_photo)
    ImageView endStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = this;
        progressDialog  = new ProgressDialog(this);

        setContentView(R.layout.init);
        setInitialConfiguration();

    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Button sync_newdays_btn = (Button) findViewById(R.id.sync_newdays_btn);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(R.string.init_step1);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//        sync_newdays_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        }

    }

}
