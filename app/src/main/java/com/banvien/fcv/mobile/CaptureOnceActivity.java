package com.banvien.fcv.mobile;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.banvien.fcv.mobile.adapter.ImageAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.ImageDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MultiChoiceModeListener;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

public class CaptureOnceActivity extends BaseDrawerActivity {
    private static Long outletId;
    private static Long posmId;
    private static String captureType;
    private static String urlImage;
    private static OutletEntity outlet;
    private Repo repo;
    private List<ImageDTO> imageDTOs;
    private List<ResolveInfo> mApps;

    @Bind(R.id.btnTake)
    FloatingActionButton btnTake;

    @Bind(R.id.gridListImage)
    GridView gridListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadApps();
        setContentView(R.layout.capturelist);
        setInitialConfiguration();
        repo = new Repo(this);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        captureType = this.getIntent().getStringExtra(ScreenContants.CAPTURE_TYPE);
        if(captureType != ScreenContants.IMAGE_AFTER_POSM
                || captureType != ScreenContants.IMAGE_BEFORE_POSM ) {
            posmId = this.getIntent().getLongExtra(ScreenContants.KEY_POSM_ID, 0l);
        }
        try {
            outlet = repo.getOutletDAO().findById(outletId);
        } catch (SQLException e) {
            ELog.d("Error when findById Outlet");
        }

        bindGallery();
    }

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    private void bindGallery() {
        String posmId = null;
        List<OutletMerDTO> images = new ArrayList<>();
        try {
            images = this.repo.getOutletMerDAO().findImageByDataType(captureType, outletId, posmId);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        this.imageDTOs = loadGallery(images);
        final ImageAdapter adapter = new ImageAdapter(this, imageDTOs);
        this.gridListImage.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        this.gridListImage.setMultiChoiceModeListener(new MultiChoiceModeListener(gridListImage));

        this.gridListImage.setAdapter(adapter);

//        gridListImage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                imageDTOs.remove(position);
//                adapter.notifyDataSetChanged();
//                return false;
//            }
//        });
    }

    private List<ImageDTO> loadGallery(List<OutletMerDTO> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for(OutletMerDTO outletMerDTO : images) {
            File image = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + outletMerDTO.getActualValue());
            if(image.exists()) {
                ImageDTO imageDTO = new ImageDTO();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
                imageDTO.set_id(outletMerDTO.get_id());
                imageDTO.setImage(bitmap);
                imageDTOs.add(imageDTO);
            }
        }

        return imageDTOs;
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
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri());
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
            outletMerEntity.setRouteScheduleDetailId(outlet.getRouteScheduleId());
            if(captureType != ScreenContants.IMAGE_AFTER_POSM
                    || captureType != ScreenContants.IMAGE_BEFORE_POSM ) {
                outletMerEntity.setReferenceValue(posmId.toString());
            }
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

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), outlet.getCode());
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                ELog.d(outlet.getCode(), "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        Random r = new Random();
        urlImage = mediaStorageDir.getPath() + File.separator
                + (outlet.getCode() + r.nextInt() + ".jpg").toString();
        File mediaFile = new File(urlImage);
        return mediaFile;
    }

}
