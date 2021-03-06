package com.banvien.fcv.mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.ImageAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.CaptureBeforeEntity;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.ImageDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/23/2016.
 */
public class CaptureBeforeActivity extends BaseDrawerActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static String urlImage;
    private Repo repo;
    private List<ImageDTO> imageDTOs;
    private ImageAdapter adapter;
    private RouteScheduleEntity routeScheduleDTO;
    private static long outletId;
    private static OutletEntity outlet;

    @BindView(R.id.btnTake)
    FloatingActionButton btnTake;

    @BindView(R.id.gridListImage)
    GridView gridListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capturelist);
        repo = new Repo(this);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        imageDTOs = new ArrayList<>();
        try {
            outlet = repo.getOutletDAO().findById(outletId);
        } catch (SQLException e) {
            ELog.d("CaptureBeforeActivity", "Error when find Outlet ");
        }
        routeScheduleDTO = getRouteSchedule();

        boolean takePicAction = getIntent().getBooleanExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.FALSE);
        if (takePicAction) {
            dispatchTakePictureIntent();
            return;
        }

    }

    private RouteScheduleEntity getRouteSchedule() {
        RouteScheduleEntity result = new RouteScheduleEntity();
        try {
            result = this.repo.getRouteScheduleDAO().findRoute();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return result;
    }

    private void bindGallery() {
        List<CaptureBeforeEntity> images = new ArrayList<>();
        try {
            images = this.repo.getCaptureBeforeDAO().findByOutletId(outletId);

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
                        this.repo.getCaptureBeforeDAO().deleteImageFromId(imageDTOs.get(i).get_id()); //Delete from database
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

    private List<ImageDTO> loadGallery(List<CaptureBeforeEntity> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        if (images != null) {
            for (CaptureBeforeEntity captureToolDTO : images) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.set_id(captureToolDTO.get_id());
                imageDTO.setImagePath(captureToolDTO.getPathImage());
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
                dispatchTakePictureIntent();
            }
        });
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFileUri());
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File file = new File(urlImage);
            if(file.exists()) {
                CaptureBeforeEntity captureBeforeEntity = new CaptureBeforeEntity();
                captureBeforeEntity.setPathImage(urlImage);
                captureBeforeEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
                captureBeforeEntity.setOutletId(outletId);
                if(routeScheduleDTO != null) {
                    captureBeforeEntity.setRouteScheduleId(routeScheduleDTO.getRouteScheduleId());
                }

                try {
                    repo.getCaptureBeforeDAO().create(captureBeforeEntity);

                    bindGallery();
                } catch (SQLException e) {
                    ELog.d("Error when capture image");
                } catch (NullPointerException e) {
                    ELog.d("Outlet is not exist");
                }
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
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);


        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                , ScreenContants.CAPTURE_BEFORE_PATH + outlet.getCode() + "/" + dateString);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        Random r = new Random();
        urlImage = mediaStorageDir.getPath() + File.separator
                + (System.currentTimeMillis() + r.nextInt() + ".jpg").toString();
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
    protected void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }
}
