package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.OrderActivity;
import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.ScreenContants;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;
import com.banvien.fcv.mobile.dto.ShortageProductDTO;
import com.banvien.fcv.mobile.dto.getfromserver.MProductDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.util.List;
import java.util.Map;

/**
 * Created by Linh Nguyen on 5/24/2016.
 */
public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ProductgroupDTO> sections;
    private Map<String, List<MProductDTO>> products;
    private Map<String, String> orderInfos;
    private LayoutInflater inflater;
    private Long outletId;
    private Long routeScheduleDetailId;

    public MyExpandableAdapter(Context context, List<ProductgroupDTO> sections,
                               Map<String, List<MProductDTO>> products,
                               Map<String, String> orderInfos, Long outletId, Long routeScheduleDetailId) {
        this.context = context;
        this.sections = sections;
        this.products = products;
        this.outletId = outletId;
        this.orderInfos = orderInfos;
        this.routeScheduleDetailId = routeScheduleDetailId;
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
        final MProductDTO childText = (MProductDTO) getChild(groupPosition, childPosition);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.order_list_item, null);
        }

        TextView textViewChild = (TextView) convertView.findViewById(R.id.tvProductName);
        textViewChild.setText(childText.getName());

        final TextView tvShortage = (TextView) convertView.findViewById(R.id.shortageProductId);

        CheckBox cbShortage = (CheckBox) convertView.findViewById(R.id.cbShortage);

        if(orderInfos.get(childText.getCode()) != null) {
            cbShortage.setChecked(true);
            tvShortage.setText(orderInfos.get(childText.getCode()));
        }

        cbShortage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( ((CheckBox)v).isChecked() ) {
                    insertOrRemoveData(childText, ScreenContants.INSERT, tvShortage);
                } else if(!((CheckBox)v).isChecked()) {
                    insertOrRemoveData(childText, ScreenContants.REMOVE, tvShortage);
                }
            }
        });

        return convertView;
    }

    /*If insert return id of shortage product, else: return null*/
    private void insertOrRemoveData(MProductDTO productDTO, String type, TextView tvShortage) {
        ShortageProductDTO shortageProductDTO = new ShortageProductDTO();
        shortageProductDTO.setProductCode(productDTO.getCode());
        shortageProductDTO.setRouteSCheduleDetailId(routeScheduleDetailId);
        shortageProductDTO.setProductName(productDTO.getName());
        if(type == ScreenContants.INSERT) {
            shortageProductDTO = ((OrderActivity)context).addOrder(shortageProductDTO);
            tvShortage.setText(String.valueOf(shortageProductDTO.get_id()));
        } else if(type == ScreenContants.REMOVE) {
            String idRemoved = tvShortage.getText().toString();
            ((OrderActivity)context).removeOrder(idRemoved);
            tvShortage.setText("");
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
