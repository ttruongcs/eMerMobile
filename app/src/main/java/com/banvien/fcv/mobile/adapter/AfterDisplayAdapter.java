package com.banvien.fcv.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.AfterDisplayActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/25/2016.
 */
public class AfterDisplayAdapter extends RecyclerView.Adapter<AfterDisplayAdapter.ItemHolder> {

    private AfterDisplayActivity activity;
    private List<OutletMerDTO> mData;
    private Repo repo;

    public AfterDisplayAdapter(AfterDisplayActivity activity, List<OutletMerDTO> outletMerDTOs, Repo repo) {
        this.activity = activity;
        this.mData = outletMerDTOs;
        this.repo = repo;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_display_item, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ItemHolder itemHolder = holder;
        itemHolder.bindViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvMHS)
        TextView productName;

        @Bind(R.id.checkBox)
        CheckBox checkBox;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindViews(final OutletMerDTO outletMerDTO) {
            ProductDTO productDTO = new ProductDTO();
            try {
                productDTO = repo.getProductDAO().findByProductId(Long.valueOf(outletMerDTO.getRegisterValue()));
                productName.setText(productDTO.getName());

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            Toast.makeText(activity.getApplicationContext(), outletMerDTO.getActualValue() +  " is checked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity.getApplicationContext(), outletMerDTO.getActualValue() +  " is uncheck", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (SQLException e) {
                ELog.d("Can't get product from server", e);
            }


        }
    }
}
