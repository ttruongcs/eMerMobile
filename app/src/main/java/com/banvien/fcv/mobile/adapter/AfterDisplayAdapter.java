package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.banvien.fcv.mobile.AfterDisplayActivity;
import com.banvien.fcv.mobile.dto.OutletMerDTO;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/25/2016.
 */
public class AfterDisplayAdapter extends RecyclerView.Adapter<AfterDisplayAdapter.ItemHolder> {

    public AfterDisplayAdapter(AfterDisplayActivity activity, List<OutletMerDTO> outletMerDTOs) {

    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
