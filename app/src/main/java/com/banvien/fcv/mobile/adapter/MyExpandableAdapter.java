package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.OrderActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/24/2016.
 */
public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ProductgroupDTO> sections;
    private Map<String, List<ProductDTO>> products;
    private Map<String, String> orderInfos;
    private LayoutInflater inflater;
    private Long outletId;

    public MyExpandableAdapter(Context context, List<ProductgroupDTO> sections,
                               Map<String, List<ProductDTO>> products, Map<String, String> orderInfos, Long outletId) {
        this.context = context;
        this.sections = sections;
        this.products = products;
        this.outletId = outletId;
        this.orderInfos = orderInfos;
        inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return this.sections.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.products.get(this.sections.get(groupPosition).getName()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.sections.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.products.get(this.sections.get(groupPosition).getName()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ProductgroupDTO section = (ProductgroupDTO) getGroup(groupPosition);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.order_list_group_item, null);
        }

        TextView textViewSection = (TextView) convertView.findViewById(R.id.tvProductSection);
        textViewSection.setText(section.getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ProductDTO childText = (ProductDTO) getChild(groupPosition, childPosition);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.order_list_item, null);
        }

        TextView textViewChild = (TextView) convertView.findViewById(R.id.tvProductName);
        textViewChild.setText(childText.getName());

        CheckBox cbShortage = (CheckBox) convertView.findViewById(R.id.cbShortage);

        cbShortage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ((CheckBox)v).isChecked() ) {
                    Toast.makeText(v.getContext(), childText.getName() + " checked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), childText.getName() + " unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_DONE) {
//                    OutletMerDTO outletMerDTO = new OutletMerDTO();
//                    outletMerDTO.setDataType(ScreenContants.ORDER);
//                    outletMerDTO.setActualValue(v.getText().toString());
//                    outletMerDTO.setOutletId(outletId);
//                    outletMerDTO.setReferenceValue(String.valueOf(childText.getProductId()));
//                    insertOrUpdateData(outletMerDTO);
//
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                EditText numberInput;
//
//                if(!hasFocus) {
//                    numberInput = (EditText) v;
//                    if(!numberInput.getText().toString().equals("")) {
//                        OutletMerDTO outletMerDTO = new OutletMerDTO();
//                        outletMerDTO.setDataType(ScreenContants.ORDER);
//                        outletMerDTO.setActualValue(numberInput.getText().toString());
//                        outletMerDTO.setOutletId(outletId);
//                        outletMerDTO.setReferenceValue(String.valueOf(childText.getProductId()));
//
//                        insertOrUpdateData(outletMerDTO);
//                    }
//                }
//            }
//        });

        return convertView;
    }

    private void insertOrUpdateData(OutletMerDTO outletMerDTO) {
        boolean isExist = ((OrderActivity)context).checkProductExist(outletMerDTO);
        if(!isExist) {
            ((OrderActivity)context).addOrder(outletMerDTO);
            ELog.d("insert", "Product is not exist in mer result");
        } else {
            ((OrderActivity)context).updateOrder(outletMerDTO);
            ELog.d("Update", "product is existed in mer result");
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
