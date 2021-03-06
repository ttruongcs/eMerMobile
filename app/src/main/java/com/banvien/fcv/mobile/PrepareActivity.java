package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.library.UpdateService;
import com.banvien.fcv.mobile.utils.CheckNetworkConnection;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

public class PrepareActivity extends BaseDrawerActivity {

    private static final String TAG = "PrepareActivity";
    private static final int COMPLETED = 100;

    @BindView(R.id.fabSyncTask)
    com.github.clans.fab.FloatingActionButton fabSync;

    @BindView(R.id.textNumSuccess)
    TextView textNumSuccess;

    @BindView(R.id.txtViewDateStartDay)
    TextView txtViewDateStartDay;

    private Repo repo;
    private static UpdatingTask updateTask = null;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
        repo = new Repo(this);
        getSupportActionBar().setTitle(R.string.dongbodulieuphancong);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat( "E, dd-MM-yyyy" , new Locale("vi_VN"));
        String dateString = sdf.format(date);
        txtViewDateStartDay.setText(dateString);


        bindViews();
    }

    private void showLocationDialog(final View v) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(v.getContext());
        builder.setTitle(getString(R.string.submit_warning));
        builder.setMessage(getString(R.string.submit_warning_contain));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!CheckNetworkConnection.isConnectionAvailable(PrepareActivity.this)){
                            Toast.makeText(PrepareActivity.this, A.s(R.string.sync_error_phone_connection), Toast.LENGTH_LONG).show();
                            return;
                        }

                        UpdateService updateService = new UpdateService(v.getContext(), repo);
                        progressDialog  = new ProgressDialog(v.getContext());
                        progressDialog.setMessage(v.getContext().getText(R.string.updating));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        updateService.updateFromServer(true, progressDialog, textNumSuccess);
//                        startUpdate();

                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        android.support.v7.app.AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    @Override
    protected void bindViews(){
        super.bindViews();
        fabSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sync data from server to handheld");
                showLocationDialog(v);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }

    private class UpdatingTask extends AsyncTask<String, Void, Map<String, String>> {
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
        protected void onPostExecute(final Map<String, String> mapResult) {
            updateTask = null;
            if (mapResult != null) {
                dismissProgressDialog();
                textNumSuccess.setText(mapResult.get("numOutletSuccess"));
                Toast.makeText(context, context.getText(R.string.update_successful), Toast.LENGTH_LONG).show();
            } else {
                if(errorMessage != null) {
//                    MyUtils.showOKAlertDialog(parentActivity, errorMessage);
                }else {
                    Toast.makeText(context, context.getText(R.string.update_failed), Toast.LENGTH_LONG).show();
                }
            }

        }

        protected Map<String, String> doInBackground(final String... args) {
            Map<String, String> results = new Hashtable<>();
            try {
                UpdateService updateService = new UpdateService(context, repo);
//                results = updateService.updateFromServer(true);
                errorMessage = results.get("errorMessage");
                if(errorMessage != null) {
                    return null;
                }
            }catch (Exception e) {
                Log.e("HomeActivity", e.getMessage(), e);
                return null;
            }
            return results;
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
