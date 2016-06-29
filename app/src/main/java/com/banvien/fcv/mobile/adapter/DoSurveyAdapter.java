package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.dto.DoSurveyAnswerDTO;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.C;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Linh Nguyen on 5/20/2016.
 */
public class DoSurveyAdapter extends RecyclerView.Adapter<DoSurveyAdapter.ViewHolder> {
    private List<QuestionEntity> data;
    private Map<Long, DoSurveyAnswerDTO> responseMap = new HashMap<>();
    private Context context;

    public DoSurveyAdapter(Context context, List<QuestionEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.posm_capture_item, parent, false);
        ViewHolder outletHolder = new ViewHolder(v);

        return outletHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = holder;
        viewHolder.bindViews(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtQuestionText)
        public TextView txtQuestionText;

        @Bind(R.id.questionImageView)
        public ImageView imageView;

        @Bind(R.id.questionAnswerPane)
        public LinearLayout questionAnswerPane;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindViews(final QuestionEntity questionEntity) {
            txtQuestionText.setText(questionEntity.getQuestionText());
            if (!TextUtils.isEmpty(questionEntity.getImagePath())) {
                Glide.with(context).load(RestClient.API_BASE_URL + questionEntity.getImagePath().substring(1)).into(imageView);
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }

            questionAnswerPane.removeAllViews();

            DoSurveyAnswerDTO surveyResponseDTO = responseMap.get(questionEntity.getQuestionId());

            List<View> views = new ArrayList<>();
            if (C.QUESTION_TYPE_MULTI_SHORT_ANSWER.equals(questionEntity.getType())) {
                EditText editText = newEditText();

                if (surveyResponseDTO != null) {
                    editText.setText(surveyResponseDTO.getAnswer());
                }
                editText.setTag(questionEntity.getQuestionId());

                views.add(editText);
            } else if (C.QUESTION_TYPE_YES_NO.equals(questionEntity.getType())) {
                CheckBox checkBox = newCheckBox();

                if (surveyResponseDTO != null) {
                    checkBox.setChecked(C.FLAG_YES.equals(surveyResponseDTO.getAnswer()));
                }
                checkBox.setTag(questionEntity.getQuestionId());

                views.add(checkBox);
            }else if (C.QUESTION_TYPE_YES_NO_MODIFIED.equals(questionEntity.getType())) {
                CheckBox checkBox = newCheckBox();

                if (surveyResponseDTO != null) {
                    checkBox.setChecked(C.FLAG_YES.equals(surveyResponseDTO.getAnswer()));
                }
                checkBox.setTag(questionEntity.getQuestionId());

                EditText editText = newEditText();

                if (surveyResponseDTO != null) {
                    editText.setText(surveyResponseDTO.getExtra());
                }
                editText.setTag(questionEntity.getQuestionId());

                views.add(editText);
                views.add(checkBox);
            }

            for (View view : views) {
                questionAnswerPane.addView(view);
            }
        }
    }

    private EditText newEditText() {
        EditText editText = new EditText(context);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setSingleLine();

        return editText;
    }

    private CheckBox newCheckBox() {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return checkBox;
    }
}
