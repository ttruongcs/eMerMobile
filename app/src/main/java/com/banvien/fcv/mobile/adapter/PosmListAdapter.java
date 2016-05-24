package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.ActionActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;
import com.banvien.fcv.mobile.fragments.BaseFragment;
import com.banvien.fcv.mobile.utils.ColorGenerator;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.TextDrawable;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class PosmListAdapter extends RecyclerView.Adapter<PosmListAdapter.PosmHolder> {

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private List<OutletMerDTO> mData;
    private Repo repo;

    public PosmListAdapter(List<OutletMerDTO> posmDTOs, Repo repo) {
        this.mData = posmDTOs;
        this.repo = repo;
        mDrawableBuilder = TextDrawable.builder()
                .round();
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

        @Bind(R.id.imagePosm)
        ImageView imagePosm;

        @Bind(R.id.posmValue)
        CheckBox posmValue;

        public PosmHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final OutletMerDTO outletMerDTO) {
            POSMDTO posm = new POSMDTO();
            try {
                posm = repo.getPosmDAO().findByCode(outletMerDTO.getRegisterValue());
                posmName.setText(posm.getName());
                Random r = new Random();
                TextDrawable drawable = mDrawableBuilder.build(String.valueOf(posm.getName()
                        .charAt(0)).toUpperCase(), mColorGenerator.getColor(posm.getName() + r.nextInt()));
                imagePosm.setImageDrawable(drawable);

                posmValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            try {
                                OutletMerDTO dbOutletMer = repo.getOutletMerDAO().findByOutletMerId(outletMerDTO.get_id());
                                if (dbOutletMer != null) {
                                    POSMDTO posm = repo.getPosmDAO().findByCode(outletMerDTO.getRegisterValue());
                                    dbOutletMer.setActualValue(posm.getCode());

                                    repo.getOutletMerDAO().update(OutletMerUtil.convertToEntity(dbOutletMer));
                                    ELog.e("Update OutletMer Success");
                                }
                            } catch (SQLException e) {
                                ELog.e("POSM Activity", e);
                            }
                        }
                    }
                });

            } catch (SQLException e) {
                ELog.d("Error when biding OutletMer", e);
            }
            this.posmName.setText(posm.getName());
        }
    }
}
