package com.banvien.fcv.mobile.adapter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.ActionActivity;
import com.banvien.fcv.mobile.InOutletHomeActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.fragments.BaseFragment;
import com.banvien.fcv.mobile.utils.ColorGenerator;
import com.banvien.fcv.mobile.utils.TextDrawable;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.OutletHolder> {
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private List<OutletDTO> mData;
    private Fragment fragment;
    private String flag;

    public OutletListAdapter(List<OutletDTO> outletDTOs, Fragment fragment, String flag) {
        this.mData = outletDTOs;
        this.fragment = fragment;
        this.flag = flag;
    }

    public OutletListAdapter() {};



    @Override
    public OutletHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        OutletHolder outletHolder = new OutletHolder(v);
        mDrawableBuilder = TextDrawable.builder()
                .round();
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
        @Bind(R.id.item)
        RelativeLayout item;

        @Bind(R.id.name_distributor_label)
        TextView outletName;

        @Bind(R.id.address_distributor_label)
        TextView outletCode;

        @Bind(R.id.gps_distributor_label)
        TextView outletAddress;

        @Bind(R.id.imageOutlet)
        ImageView imageOutlet;

        public OutletHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final OutletDTO outletDTO) {
            this.outletName.setText(outletDTO.getName());
            this.outletCode.setText(buildOutletCode(outletDTO.getCode(), outletDTO.getdCode()));
            this.outletAddress.setText(buildOutletAdress(outletDTO.getLocationNo(), outletDTO.getStreet()
                    , outletDTO.getWard(), outletDTO.getCityName()));

            Random r = new Random();
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(outletDTO.getName()
                    .charAt(0)).toUpperCase(), mColorGenerator.getColor(outletDTO.getName() + r.nextInt()));
            imageOutlet.setImageDrawable(drawable);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flag.equals(ScreenContants.UNFINISH)) {
                        Intent intent = new Intent(view.getContext(), InOutletHomeActivity.class);
                        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletDTO.getOutletId());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }

        private String buildOutletCode(String outletCode, String distributorCode){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(outletCode);
            stringBuffer.append(distributorCode);
            return stringBuffer.toString();
        }

        private String buildOutletAdress(String locationNum, String street, String ward, String cityName){
            StringBuffer stringBuffer = new StringBuffer();
            if(null != locationNum) {
                stringBuffer.append(locationNum).append(",  ");
            }
            if(null != street) {
                stringBuffer.append(street).append("   ");
            }
            if(null != ward) {
                stringBuffer.append(ward).append("   ");
            }
            if(null != cityName) {
                stringBuffer.append(cityName);
            }
            return stringBuffer.toString();
        }
    }
}
