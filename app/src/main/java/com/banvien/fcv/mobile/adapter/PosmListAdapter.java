package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.ActionActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.fragments.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class PosmListAdapter extends RecyclerView.Adapter<PosmListAdapter.PosmHolder> {

    private List<POSMDTO> mData;

    public PosmListAdapter(List<POSMDTO> posmDTOs, Activity activity) {
        this.mData = posmDTOs;
    }

    public PosmListAdapter() {};



    @Override
    public PosmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posm_item, parent, false);
        PosmHolder outletHolder = new PosmHolder(v);

        return outletHolder;
    }

    @Override
    public void onBindViewHolder(PosmHolder holder, int position) {
        PosmHolder posmHolder = holder;
        posmHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PosmHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.posmName)
        TextView posmName;


        public PosmHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final POSMDTO posmDTO) {

            this.posmName.setText(posmDTO.getName());

//            item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(view.getContext(), ActionActivity.class);
//                    intent.putExtra("com.banvien.fcv.emer.outletId", outletDTO.getOutletId());
//                    view.getContext().startActivity(intent);
//                }
//            });
        }
    }
}
