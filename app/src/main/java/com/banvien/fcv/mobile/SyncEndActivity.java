package com.banvien.fcv.mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.banvien.fcv.mobile.library.SyncOutletMerResultService;
import com.banvien.fcv.mobile.library.SyncService;

import java.io.IOException;
import java.sql.SQLException;

import butterknife.Bind;

public class SyncEndActivity extends BaseDrawerActivity {

    private static ProgressDialog progressDialog;

    @Bind(R.id.fabSyncTask)
    com.github.clans.fab.FloatingActionButton fabSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog  = new ProgressDialog(this);
        setContentView(R.layout.sync_end_day);
        fabSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressDialog  = new ProgressDialog(v.getContext());
                    progressDialog.setMessage(v.getContext().getText(R.string.updating));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    SyncOutletMerResultService syncService = new SyncOutletMerResultService(v.getContext(), 1l);
                    syncService.syncOutletMerImage(progressDialog);
                    progressDialog.dismiss();
//                    syncService.syncOutletMerImageImfomation(progressDialog);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

}
