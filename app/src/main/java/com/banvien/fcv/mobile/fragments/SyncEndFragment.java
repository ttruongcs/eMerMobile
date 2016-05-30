package com.banvien.fcv.mobile.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.library.SyncService;
import com.banvien.fcv.mobile.library.UpdateService;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SyncEndFragment extends BaseFragment {
    private static final String TAG = "SyncEndFragment";
    private static Long outletId;
    @Bind(R.id.fabSyncTask)
    com.github.clans.fab.FloatingActionButton fabSync;


    private static UpdatingTask updateTask = null;
    private static ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.init, container, false);
        ButterKnife.bind(this, view);
        bindViews();
        return view;
    }

    private void showLocationDialog(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle(getString(R.string.submit_warning));
        builder.setMessage(getString(R.string.submit_warning_contain));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog  = new ProgressDialog(v.getContext());
                        startUpdate();
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

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    protected void bindViews(){
        fabSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sync data to server from handheld");
                showLocationDialog(v);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
                SyncService syncService = new SyncService(context, outletId);
                Map<String, String> results = syncService.syncToServer(true);
                errorMessage = results.get("errorMessage");
                if(errorMessage != null) {
                    return false;
                }
            }catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
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
        updateTask = new UpdatingTask(this.getContext());
        updateTask.execute();
    }

    private void startUpdate() {
        startUpdatingTask();
    }
}
