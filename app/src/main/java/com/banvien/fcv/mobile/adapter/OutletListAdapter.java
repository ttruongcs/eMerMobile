package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.fragments.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.OutletHolder> {

    private List<OutletDTO> mData;
    private BaseFragment fragment;

    public OutletListAdapter(List<OutletDTO> outletDTOs, BaseFragment fragment) {
        this.mData = outletDTOs;
        this.fragment = fragment;
    }

    public OutletListAdapter() {};



    @Override
    public OutletHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        OutletHolder outletHolder = new OutletHolder(v);

        return outletHolder;
    }

    @Override
    public void onBindViewHolder(OutletHolder holder, int position) {
        OutletHolder outletHolder = holder;
        outletHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OutletHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name_distributor_label)
        TextView outletName;

        @Bind(R.id.address_distributor_label)
        TextView outletCode;

        @Bind(R.id.gps_distributor_label)
        TextView outletAddress;

        public OutletHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(OutletDTO outletDTO) {
            StringBuffer code = new StringBuffer(outletDTO.getCode() != null ? outletDTO.getCode() : "")
                    .append(outletDTO.getdCode() != null ? outletDTO.getdCode() : "");

            this.outletName.setText(outletDTO.getName());
            this.outletCode.setText(code);
            outletAddress.setText("ten duong");
        }
    }
}
