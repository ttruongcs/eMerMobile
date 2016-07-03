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
import com.banvien.fcv.mobile.db.entities.OutletEndDayImagesEntity;
import com.banvien.fcv.mobile.db.entities.RouteScheduleEntity;
import com.banvien.fcv.mobile.dto.ImageDTO;
import com.banvien.fcv.mobile.dto.OutletEndDayImagesDTO;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.ELog;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class CaptureEndDayActivity extends BaseDrawerActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static Long outletId;
    private static Long routeScheduleDetailId;
    private static String urlImage;
    private Repo repo;
    private List<ImageDTO> imageDTOs;
    private ImageAdapter adapter;

    @BindView(R.id.btnTake)
    FloatingActionButton btnTake;

    @BindView(R.id.gridListImage)
    GridView gridListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capturelist);
        repo = new Repo(this);
        imageDTOs = new ArrayList<>();
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        try {
            RouteScheduleEntity routeSchedule = repo.getRouteScheduleDAO().findRoute();
            if(routeSchedule != null){
                routeScheduleDetailId = routeSchedule.getRouteScheduleId();
            }
        } catch (SQLException e) {
            ELog.d("Error when findById Outlet");
        }

        boolean takePicAction = getIntent().getBooleanExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.FALSE);
        if (takePicAction) {
            dispatchTakePictureIntent();
            return;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    private void bindGallery() {
        List<OutletEndDayImagesDTO> images = new ArrayList<>();
        try {
            images = this.repo.getOutletEndDayImagesDAO().findByRouteScheduleDetailId(routeScheduleDetailId);

        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        this.imageDTOs = loadGallery(images);
        adapter = new ImageAdapter(this, imageDTOs);
        this.gridListImage.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        this.gridListImage.setMultiChoiceModeListener(new GridView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("Select Items");
                mode.setSubtitle("One item selected");
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
                        mode.setSubtitle("One item selected");
                        break;
                    default:
                        mode.setSubtitle("" + selectCount + " items selected");
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

    private List<ImageDTO> loadGallery(List<OutletEndDayImagesDTO> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        if (images != null) {
            for (OutletEndDayImagesDTO outletEndDayImages : images) {
                ImageDTO imageDTO = new ImageDTO();
                imageDTO.set_id(outletEndDayImages.get_id());
                imageDTO.setImagePath(outletEndDayImages.getImagePath());
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
                OutletEndDayImagesEntity outletEndDayImagesEntity = new OutletEndDayImagesEntity();
                outletEndDayImagesEntity.setRouteScheduleDetailId(routeScheduleDetailId);
                outletEndDayImagesEntity.setPathImage(urlImage);
                try {
                    repo.getOutletEndDayImagesDAO().addOutletEndDayImagesEntity(outletEndDayImagesEntity);

                    bindGallery();
                } catch (SQLException e) {
                    ELog.d("Error when capture image");
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

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), ScreenContants.CAPTURE_ENDDAY + routeScheduleDetailId.toString());
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                ELog.d("failed to create directory");
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
    protected void onPause() {
        super.onPause();
        if(imageDTOs.size() > 0) {
            ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(repo);
            String[] next = {ScreenContants.CONFIRM_END_COLUMN};
            String parent = null;
            changeStatusTimeline.changeStatusToDone(ScreenContants.END_DATE_COLUMN
                    , ScreenContants.CAPTURE_END_DAY, next, parent, false);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindGallery();
    }
}
