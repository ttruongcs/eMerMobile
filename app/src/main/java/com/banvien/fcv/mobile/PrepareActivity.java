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
    private static UpdatingTask updateTask = null;
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

    private class UpdatingTask extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private String errorMessage = null;

        public UpdatingTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            progressDialog.setMessage(context.getText(R.string.updating));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            updateTask = null;
            dismissProgressDialog();
            if (success) {
                Toast.makeText(context, context.getText(R.string.update_successful), Toast.LENGTH_LONG).show();
            } else {
                if(errorMessage != null) {
//                    MyUtils.showOKAlertDialog(parentActivity, errorMessage);
                }else {
                    Toast.makeText(context, context.getText(R.string.update_failed), Toast.LENGTH_LONG).show();
                }
            }

        }

        protected Boolean doInBackground(final String... args) {
            try {
//                String auditorCode = MyUtils.readAuditorCodeFromSettings(context);
                String auditorCode = "TCT0001";
//                UpdateService updateService = new UpdateService(context);
//                Map<String, String> results = updateService.updateFromServer(auditorCode, true);
//                errorMessage = results.get("errorMessage");
                if(errorMessage != null) {
                    return false;
                }
            }catch (Exception e) {
                Log.e("HomeActivity", e.getMessage(), e);
                return false;
            }
            return true;
        }
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    private void startUpdatingTask() {
        updateTask = new UpdatingTask(this);
        updateTask.execute();
    }

    private void startUpdate() {
        startUpdatingTask();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(updateTask != null &&  updateTask.getStatus().equals(AsyncTask.Status.RUNNING) && progressDialog != null) {
            progressDialog.setMessage(this.getText(R.string.updating));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

}
