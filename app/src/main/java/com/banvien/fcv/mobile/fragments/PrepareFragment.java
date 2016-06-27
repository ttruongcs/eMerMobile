package com.banvien.fcv.mobile.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.library.UpdateService;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PrepareFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private static final int COMPLETED = 100;

    @Bind(R.id.fabSyncTask)
    com.github.clans.fab.FloatingActionButton fabSync;

    private Repo repo;
    private static UpdatingTask updateTask = null;
    private static ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.init, container, false);
        ButterKnife.bind(this, view);
        repo = new Repo(this.getContext());
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

    private void showAlertBox(final View v) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(v.getContext());
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Select One Name:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                v.getContext(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                v.getContext());
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }

    protected void bindViews(){
        fabSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sync data from server to handheld");
                showLocationDialog(v);
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
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
                UpdateService updateService = new UpdateService(context);
//                Map<String, String> results = updateService.updateFromServer(true);
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
        updateTask = new UpdatingTask(this.getContext());
        updateTask.execute();
    }

    private void startUpdate() {
        startUpdatingTask();
    }
}
