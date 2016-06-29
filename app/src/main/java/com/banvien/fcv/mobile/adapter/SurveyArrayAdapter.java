package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hau on 7/29/2015.
 */
public class SurveyArrayAdapter extends ArrayAdapter<SurveyEntity> {

    private final Context context;
    private List<SurveyEntity> surveys;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public SurveyArrayAdapter(Context context, List<SurveyEntity> list) {
        super(context, R.layout.row_survey, list);
        this.context = context;
        this.surveys = list;
    }

    private static class ViewHolder {
        public TextView txtName;
        public TextView txtDate;
    }

    @Override
    public SurveyEntity getItem(int position) {
        return surveys.get(position);
    }

    @Override
    public int getCount() {
        return surveys.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        ViewHolder holder = null;
        if (convertView == null) {
            v = A.getInflater().inflate(R.layout.row_survey, viewGroup, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) v.findViewById(R.id.txt_survey_name);
            holder.txtDate= (TextView) v.findViewById(R.id.txt_survey_date);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        final SurveyEntity dto = surveys.get(position);
        holder.txtName.setText(dto.getName());
        String startDate = dto.getStartDate() != null? (simpleDateFormat.format(dto.getStartDate())) : null;
        String endDate = dto.getEndDate() != null? (simpleDateFormat.format(dto.getEndDate())) : null;
        holder.txtDate.setText((startDate != null? startDate  + " - ": "" ) + (endDate != null? endDate : ""));

        return v;
    }

}
