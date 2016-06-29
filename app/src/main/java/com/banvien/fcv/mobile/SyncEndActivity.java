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
import android.widget.TextView;

import com.banvien.fcv.mobile.library.SyncOutletMerResultService;
import com.banvien.fcv.mobile.library.SyncService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;

public class SyncEndActivity extends BaseDrawerActivity {

    private static ProgressDialog progressDialog;

    @Bind(R.id.fabSyncTask)
    com.github.clans.fab.FloatingActionButton fabSync;

    @Bind(R.id.textNumSuccess)
    TextView textNumSuccess;

    @Bind(R.id.txtViewDateEndDay)
    TextView txtViewDateEndDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog  = new ProgressDialog(this);
        setContentView(R.layout.sync_end_day);

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat( "E, dd-MM-yyyy" , new Locale("vi_VN"));
        String dateString = sdf.format(date);
        txtViewDateEndDay.setText(dateString);
        fabSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setMessage(v.getContext().getText(R.string.updating));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    SyncOutletMerResultService syncService = new SyncOutletMerResultService(v.getContext(), 1l);
                    syncService.syncImageTool();
                    syncService.syncImageUniform();
                    syncService.syncOutletMerImage();
                    syncService.syncOutletMerImageImfomation();
                    syncService.syncOutletMerResultToServer(progressDialog, textNumSuccess);
                    syncService.clearData();
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
