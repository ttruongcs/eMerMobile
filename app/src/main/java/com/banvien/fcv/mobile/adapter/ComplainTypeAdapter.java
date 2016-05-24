package com.banvien.fcv.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.banvien.fcv.mobile.ComplainTypeActivity;
import com.banvien.fcv.mobile.MainActivity;
import com.banvien.fcv.mobile.PrepareActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.dto.ComplainTypeDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/23/2016.
 */
public class ComplainTypeAdapter extends RecyclerView.Adapter {
    private static final int TYPE_CHECKBOX = 1;
    private static final int TYPE_EDIT = 2;

    private ComplainTypeActivity activity;
    private List<ComplainTypeDTO> mData;
    private Button btnSendComplaint;
    private Set<Long> complaintTypes = new HashSet<>();
    private String complainContent = "";

    public ComplainTypeAdapter(List<ComplainTypeDTO> mData, Button button, ComplainTypeActivity activity) {
        this.mData = mData;
        this.btnSendComplaint = button;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_checkbox_item, parent, false);
            ComplainTypeHolder complainTypeHolder = new ComplainTypeHolder(v);
            return complainTypeHolder;
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_item, parent, false);
            ComplainHolder complainHolder = new ComplainHolder(v);
            return complainHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TYPE_CHECKBOX:
                ComplainTypeHolder complainTypeHolder = (ComplainTypeHolder) holder;
                String description = mData.get(position).getDescription();

                complainTypeHolder.checkBox.setText(description);

                complainTypeHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            complaintTypes.add(mData.get(position).get_id());
                        } else {
                            complaintTypes.remove(mData.get(position).get_id());
                        }
                    }
                });
                break;
            case TYPE_EDIT:
                ComplainHolder complainHolder = (ComplainHolder) holder;

                complainHolder.editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        complainContent = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                break;
        }

        btnSendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OutletMerDTO> outletMerDTOs = new ArrayList<OutletMerDTO>();
                for(Long complainTypeId : complaintTypes) {
                    OutletMerDTO outletMerDTO = new OutletMerDTO();
                    outletMerDTO.setDataType(ScreenContants.COMPLAINTYPE);
                    outletMerDTO.setActualValue(String.valueOf(complainTypeId));

                    outletMerDTO.setOutletId(1l);   // Todo: Hard code
                    outletMerDTOs.add(outletMerDTO);
                }

                if(complainContent != null && !complainContent.equals("")) {
                    OutletMerDTO outletMerDTO = new OutletMerDTO();
                    outletMerDTO.setDataType(ScreenContants.COMPLAIN);
                    outletMerDTO.setActualValue(complainContent);
                    outletMerDTO.setOutletId(1l); // Todo: Hard code
                    outletMerDTOs.add(outletMerDTO);
                }

                activity.addToMerResult(outletMerDTOs);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).get_id() > 0 ? TYPE_CHECKBOX : TYPE_EDIT;
    }

    public Map<String, Object> getFormData() {
        Map<String, Object> result = new HashMap<>();

        return result;
    }

    public class ComplainTypeHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cbComplain)
        CheckBox checkBox;

        public ComplainTypeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ComplainHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.edComplain)
        EditText editText;

        public ComplainHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
