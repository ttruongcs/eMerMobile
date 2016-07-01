package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.RegisterHistoryActivity;
import com.banvien.fcv.mobile.dto.RegisterInfoDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/25/2016.
 */
public class RegisterInfoAdapter extends RecyclerView.Adapter {
    private RegisterHistoryActivity activity;
    private List<RegisterInfoDTO> mData;

    public RegisterInfoAdapter(RegisterHistoryActivity activity, List<RegisterInfoDTO> data) {
        this.activity = activity;
        this.mData = data;
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
        itemHolder.edTest.setText(mData.get(position).getName());
        if(position == 2) {
            itemHolder.cbCheck.setChecked(true);

        }
        itemHolder.cbCheck.setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cbAddOutlet)
        CheckBox cbCheck;

        @BindView(R.id.tvAddOutlet)
        TextView edTest;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
