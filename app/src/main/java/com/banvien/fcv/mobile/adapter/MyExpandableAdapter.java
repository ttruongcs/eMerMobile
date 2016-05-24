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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.dto.ProductDTO;
import com.banvien.fcv.mobile.dto.ProductgroupDTO;

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
    private LayoutInflater inflater;

    public MyExpandableAdapter(Context context, List<ProductgroupDTO> sections, Map<String, List<ProductDTO>> products) {
        this.context = context;
        this.sections = sections;
        this.products = products;
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
        EditText editText = (EditText)convertView.findViewById(R.id.edNumber);
        textViewChild.setText(childText.getName());

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    Toast.makeText(context, childText.getProductId() + ": " + v.getText().toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText numberInput;

                if(!hasFocus) {
                    numberInput = (EditText) v;
                    if(!numberInput.getText().toString().equals("")) {
                        Toast.makeText(context,childText.getProductId() + ": " + numberInput.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
