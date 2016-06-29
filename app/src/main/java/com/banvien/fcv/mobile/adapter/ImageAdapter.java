package com.banvien.fcv.mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.dto.ImageDTO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<ImageDTO> imageDTOs;
    private final LayoutInflater mInflater;

    public ImageAdapter(Context context, List<ImageDTO> imageDTOs) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.imageDTOs = imageDTOs;
    }

    @Override
    public int getCount() {
        return this.imageDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return imageDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ImageView imageView;

        if(v == null) {
            v = mInflater.inflate(R.layout.grid_item, parent, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
        }

        imageView = (ImageView) v.getTag(R.id.picture);

        ImageDTO imageDTO = this.imageDTOs.get(position);
        if (imageDTO.getImage() != null) {
            imageView.setImageBitmap(imageDTO.getImage());
        } else if (imageDTO.getImagePath() != null) {
            final String decoded = Uri.decode(Uri.fromFile(new File(imageDTO.getImagePath())).toString());
            ImageLoader.getInstance().displayImage(decoded, imageView);
        }

        if(!this.imageDTOs.get(position).isChecked()) {
            imageView.setAlpha(1f);
        } else {
            imageView.setAlpha(0.3f);
        }

        return v;
    }



    private class CheckableLayout extends FrameLayout implements Checkable {

        private boolean mChecked;

        public CheckableLayout(Context context) {
            super(context);
        }

        @SuppressLint("NewApi")
        @SuppressWarnings("deprecation")
        @Override
        public void setChecked(boolean checked) {
            mChecked = checked;
            int sdk = Build.VERSION.SDK_INT;
            if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
                setBackgroundDrawable(checked ? getResources().getDrawable(R.drawable.bubble_blue) : null);
            } else {
                setBackground(checked ? ContextCompat.getDrawable(context, R.drawable.bubble_blue): null);
            }

        }

        @Override
        public boolean isChecked() {
            return mChecked;
        }

        @Override
        public void toggle() {
            setChecked(!mChecked);
        }


    }
}
