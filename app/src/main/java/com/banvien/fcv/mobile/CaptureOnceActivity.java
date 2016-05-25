package com.banvien.fcv.mobile;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.io.File;
import java.sql.SQLException;
import java.util.Random;

import butterknife.Bind;

public class CaptureOnceActivity extends BaseDrawerActivity {
    private static Long outletId;
    private static String captureType;
    private static String urlImage;
    private static OutletEntity outlet;
    private Repo repo;
    File CS185Pics;

    @Bind(R.id.btnTake)
    FloatingActionButton btnTake;

    @Bind(R.id.gridListImage)
    GridView gridListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capturelist);
        setInitialConfiguration();
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        captureType = this.getIntent().getStringExtra(ScreenContants.CAPTURE_TYPE);
        try {
            outlet = repo.getOutletDAO().findById(outletId);
        } catch (SQLException e) {
            ELog.d("Error when findById Outlet");
        }
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(v);
            }
        });
    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    // intiating camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri() );
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            OutletMerEntity outletMerEntity = new OutletMerEntity();
            outletMerEntity.setOutletId(outletId);
            outletMerEntity.setDataType(captureType);
            outletMerEntity.setActualValue(urlImage);

            try {
                repo.getOutletMerDAO().addOutletMerEntity(outletMerEntity);
            } catch (SQLException e) {
                ELog.d("Error when capture image");
            }
        }

    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(){
        return Uri.fromFile( getOutputMediaFile());
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES), outlet.getCode());
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                ELog.d("CS185Pics", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        Random r = new Random();
        urlImage = mediaStorageDir.getPath() + File.separator
                + (outlet.getCode() + r.nextInt()).toString();
        File mediaFile = new File(urlImage);
        return mediaFile;
    }

}
