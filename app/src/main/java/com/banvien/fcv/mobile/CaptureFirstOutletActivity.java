package com.banvien.fcv.mobile;

import android.content.Intent;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.ImageAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.OutletFirstImagesEntity;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.ImageDTO;
import com.banvien.fcv.mobile.dto.OutletFirstImagesDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.routeschedule.RouteScheduleDTO;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.ELog;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

public class CaptureFirstOutletActivity extends BaseDrawerActivity {
    private static Long outletId;
    private static Long routeScheduleDetailId;
    private static String urlImage;
    private static OutletEntity outlet;
    private Repo repo;
    private List<ImageDTO> imageDTOs;
    private ImageAdapter adapter;

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
        try {
            RouteScheduleEntity routeScheduleDTO = repo.getRouteScheduleDAO().findRoute();
            if(routeScheduleDTO != null){
                routeScheduleDetailId = routeScheduleDTO.getRouteScheduleId();
            }
            outlet = repo.getOutletDAO().findById(outletId);
        } catch (SQLException e) {
            ELog.d("Error when findById Outlet");
        }

        bindGallery();
    }

    private void bindGallery() {
        List<OutletFirstImagesDTO> images = new ArrayList<>();
        try {
            images = this.repo.getOutletFirstImagesDAO().findByRouteScheduleDetailId(routeScheduleDetailId);

        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        this.imageDTOs = loadGallery(images);
        adapter = new ImageAdapter(this, imageDTOs);
        this.gridListImage.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        this.gridListImage.setMultiChoiceModeListener(new GridView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle(getString(R.string.select_items));
                mode.setSubtitle(getString(R.string.selected, 1));
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.action_menu, menu);


                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_remover:
                        Toast.makeText(getApplicationContext(), "Remove Success", Toast.LENGTH_SHORT).show();
                        removeImageFromGallery();
                        mode.finish();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                for (ImageDTO imageDTO : imageDTOs) {
                    if (imageDTO.isChecked()) {
                        imageDTO.setChecked(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                                  boolean checked) {
                int selectCount = gridListImage.getCheckedItemCount();
                imageDTOs.get(position).setChecked(checked);
                adapter.notifyDataSetChanged();
                switch (selectCount) {
                    case 1:
                        mode.setSubtitle(getString(R.string.selected, 1));
                        break;
                    default:
                        mode.setSubtitle("" + getString(R.string.selected, selectCount));
                        break;
                }
            }
        });

        this.gridListImage.setAdapter(adapter);

    }

    private void removeImageFromGallery() {
        List<ImageDTO> removedImages = new ArrayList<>();
        try {
            for (int i = 0; i < imageDTOs.size(); i++) {
                if (imageDTOs.get(i).isChecked()) {
                    String photoUri = imageDTOs.get(i).getImagePath();

                    File image = new File(photoUri);
                    if (image.exists()) {
                        removedImages.add(imageDTOs.get(i));
                        this.repo.getOutletMerDAO().deleteImageFromId(imageDTOs.get(i).get_id()); //Delete from database
                        image.delete(); //Delete from external storage
                    } else {
                        ELog.d("File is not exist");
                    }
                }
            }

            for(ImageDTO imageDTO : removedImages) {
                imageDTOs.remove(imageDTO);

            }
            adapter.notifyDataSetChanged();


        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }

    private List<ImageDTO> loadGallery(List<OutletFirstImagesDTO> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (OutletFirstImagesDTO outletFirstImages : images) {
            File image = new File(outletFirstImages.getImagePath());
            if (image.exists()) {
                ImageDTO imageDTO = new ImageDTO();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
                imageDTO.set_id(outletFirstImages.get_id());
                imageDTO.setImage(bitmap);
                imageDTO.setImagePath(outletFirstImages.getImagePath());
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.fcvtoolbar);
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
            OutletFirstImagesEntity outletFirstImagesEntity = new OutletFirstImagesEntity();
            outletFirstImagesEntity.setRouteScheduleDetailId(routeScheduleDetailId);
            outletFirstImagesEntity.setPathImage(urlImage);
            try {
                repo.getOutletFirstImagesDAO().addOutletFirstEntity(outletFirstImagesEntity);
            } catch (SQLException e) {
                ELog.d("Error when capture image");
            }
        }
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), ScreenContants.CAPTURE_FIRST_OUTLET + outlet.getCode());
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                ELog.d(outlet.getCode(), "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        Random r = new Random();
        urlImage = mediaStorageDir.getPath() + File.separator + (r.nextInt() + ".jpg").toString();
        ELog.d("imageUrl", urlImage);
        File mediaFile = new File(urlImage);
        return mediaFile;
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindGallery();
    }

    @Override
    public void onBackPressed() {
        if(imageDTOs.size() > 0) {
            ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(this);
            changeStatusTimeline.changeStatusToDone(ScreenContants.PREPARE_DATE_COLUMN
                    , ScreenContants.CONFIRM_WORKING_COLUMN, null, ScreenContants.IN_OUTLET, true);
        }
        Intent intent = new Intent(getBaseContext(), StartDayActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
