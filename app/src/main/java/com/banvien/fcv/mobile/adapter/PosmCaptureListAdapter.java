package com.banvien.fcv.mobile.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.CaptureOnceActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.utils.ColorGenerator;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.TextDrawable;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class PosmCaptureListAdapter extends RecyclerView.Adapter<PosmCaptureListAdapter.PosmHolder> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private List<OutletMerDTO> mData;
    private Repo repo;
    private POSMDTO posmDTO;
    private String captureType;

    public PosmCaptureListAdapter(List<OutletMerDTO> posmDTOs, Repo repo, String captureType) {
        this.mData = posmDTOs;
        this.repo = repo;
        this.captureType = captureType;
        mDrawableBuilder = TextDrawable.builder()
                .round();
    }

    @Override
    public PosmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posm_capture_item, parent, false);
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
        @BindView(R.id.posmName)
        TextView posmName;

        @BindView(R.id.imagePosm)
        ImageView imagePosm;

        @BindView(R.id.lnClickToCapture)
        LinearLayout lnClickToCapture;

        public PosmHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final OutletMerDTO outletMerDTO) {
            try {
                posmDTO = repo.getPosmDAO().findByCode(outletMerDTO.getRegisterValue());
                posmName.setText(posmDTO.getName());
                Random r = new Random();
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(posmDTO.getName()
                        .charAt(0)).toUpperCase(), mColorGenerator.getColor(posmDTO.getName() + r.nextInt()));
                imagePosm.setImageDrawable(drawable);

                lnClickToCapture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), CaptureOnceActivity.class);
                        intent.putExtra(ScreenContants.KEY_POSM_ID, posmDTO.getPosmId());
                        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletMerDTO.getOutletId());
                        intent.putExtra(ScreenContants.CAPTURE_TYPE, captureType);
                        intent.putExtra(ScreenContants.KEY_TAKE_PICTURE_ACTION, Boolean.TRUE);
                        ELog.d("Error when capture POSM", String.valueOf(posmDTO.getPosmId()));
                        v.getContext().startActivity(intent);
                    }
                });

            } catch (SQLException e) {
                ELog.d("Error when biding OutletMer", e);
            }
            this.posmName.setText(posmDTO.getName());
        }
    }
}
