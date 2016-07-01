package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.RegisterHistoryActivity;
import com.banvien.fcv.mobile.dto.HistoryDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 6/30/2016.
 */
public class HistoryInfoAdapter extends RecyclerView.Adapter {
    private RegisterHistoryActivity activity;
    private List<HistoryDTO> mData;

    public HistoryInfoAdapter(RegisterHistoryActivity activity, List<HistoryDTO> data) {
        this.activity = activity;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_reward_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;

        itemHolder.tvMonth.setText("T" + mData.get(position).getMonth());
        if(mData.get(position).isStatus() == true) {
            itemHolder.tvStatus.setText(activity.getString(R.string.pass));
        } else {
            itemHolder.tvStatus.setText(activity.getString(R.string.fail));
        }
        itemHolder.tvEIENumber.setText(String.valueOf(mData.get(position).getEie()));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMonth)
        TextView tvMonth;

        @BindView(R.id.tvStatus)
        TextView tvStatus;

        @BindView(R.id.tvEIENumber)
        TextView tvEIENumber;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
