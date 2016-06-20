package com.banvien.fcv.mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.dto.ImageDTO;

import java.util.List;

/**
 * Created by Linh Nguyen on 5/27/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<ImageDTO> imageDTOs;

    public ImageAdapter(Context context, List<ImageDTO> imageDTOs) {
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
        CheckableLayout checkableLayout;
        ImageView imageView;

        if(convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(this.imageDTOs.get(position).getImage());
        if(!this.imageDTOs.get(position).isChecked()) {
            imageView.setAlpha(1f);
        } else {
            imageView.setAlpha(0.3f);
        }


        return imageView;
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
