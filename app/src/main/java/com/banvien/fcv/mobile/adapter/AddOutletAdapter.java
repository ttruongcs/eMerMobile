package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.banvien.fcv.mobile.FindOutletSimpleActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.db.entities.OutletEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/24/2016.
 */
public class AddOutletAdapter extends RecyclerView.Adapter {
    private List<OutletEntity> mData;
    private FindOutletSimpleActivity activity;

    public AddOutletAdapter(FindOutletSimpleActivity activity, List<OutletEntity> entities) {
        this.activity = activity;
        this.mData = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_outlet_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cbAddOutlet)
        CheckBox cbAddOutlet;

        @Bind(R.id.tvAddOutlet)
        TextView tvAddOutlet;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(OutletEntity outletEntity) {
            tvAddOutlet.setText(outletEntity.getName());
            cbAddOutlet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        showDialog();
                    }
                }
            });
        }

        private void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle(activity.getString(R.string.dialog_delete_outlet_title));
            builder.setMessage(activity.getString(R.string.dialog_delete_outlet));

            String positiveText = activity.getString(R.string.accept);
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            String negativeText = activity.getString(R.string.cancel);
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}
