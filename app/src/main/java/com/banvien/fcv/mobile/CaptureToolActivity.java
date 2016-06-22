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
import com.banvien.fcv.mobile.db.entities.CaptureToolEntity;
import com.banvien.fcv.mobile.dto.CaptureToolDTO;
import com.banvien.fcv.mobile.dto.ImageDTO;
import com.banvien.fcv.mobile.dto.routeschedule.RouteScheduleDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/21/2016.
 */
public class CaptureToolActivity extends BaseDrawerActivity {
    private static String urlImage;
    private Repo repo;
    private List<ImageDTO> imageDTOs;
    private ImageAdapter adapter;
    private RouteScheduleDTO routeScheduleDTO;

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

        routeScheduleDTO = getRouteSchedule();
        bindGallery();
    }

    private RouteScheduleDTO getRouteSchedule() {
        RouteScheduleDTO result = new RouteScheduleDTO();
        try {
            result = this.repo.getRouteScheduleDAO().findRoute();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
        return result;
    }

    private void bindGallery() {
        List<CaptureToolDTO> images = new ArrayList<>();
        try {
            images = this.repo.getCaptureToolDAO().findAll();

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
                        this.repo.getCaptureToolDAO().deleteImageFromId(imageDTOs.get(i).get_id()); //Delete from database
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

    private List<ImageDTO> loadGallery(List<CaptureToolDTO> images) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (CaptureToolDTO captureToolDTO : images) {
            File image = new File(captureToolDTO.getPathImage());
            if (image.exists()) {
                ImageDTO imageDTO = new ImageDTO();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), options);
                imageDTO.set_id(captureToolDTO.get_id());
                imageDTO.setImage(bitmap);
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
            CaptureToolEntity captureToolEntity = new CaptureToolEntity();
            captureToolEntity.setPathImage(urlImage);
            captureToolEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));

            if(routeScheduleDTO != null) {
                captureToolEntity.setRouteScheduleId(routeScheduleDTO.getRouteScheduleId());
            }

            try {
                repo.getCaptureToolDAO().create(captureToolEntity);
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
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);


        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), ScreenContants.CAPTURE_TOOL_PATH + dateString);
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
