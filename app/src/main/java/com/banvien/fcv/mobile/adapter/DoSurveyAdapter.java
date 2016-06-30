package com.banvien.fcv.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banvien.fcv.mobile.R;
import com.banvien.fcv.mobile.db.entities.DoSurveyAnswerEntity;
import com.banvien.fcv.mobile.db.entities.QuestionContentEntity;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.C;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hieu Le on 5/20/2016.
 */
public class DoSurveyAdapter extends RecyclerView.Adapter<DoSurveyAdapter.ViewHolder> {
    private final int FREE_TEXT_ROWS = 3;
    private List<QuestionEntity> data;
    private Map<Long, DoSurveyAnswerEntity> answerMap;
    private Map<Long, List<View>> answerViewMap = new HashMap<>();
    private Map<Long, QuestionEntity> questionEntityMap = null;
    private Long outletId, surveyId, routeScheduleDetailId;

    private Context context;

    public DoSurveyAdapter(Context context, List<QuestionEntity> data, Map<Long, DoSurveyAnswerEntity> answerMap, Long outletId, Long surveyId, Long routeScheduleDetailId) {
        this.context = context;
        this.data = data;
        this.answerMap = answerMap;
        this.outletId = outletId;
        this.surveyId = surveyId;
        this.routeScheduleDetailId = routeScheduleDetailId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dosurvey, parent, false);
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
            txtQuestionText.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(questionEntity.getImagePath())) {
                Glide.with(context).load(RestClient.API_BASE_URL + questionEntity.getImagePath().substring(1)).into(imageView);
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.GONE);
            }

            questionAnswerPane.removeAllViews();

            DoSurveyAnswerEntity doSurveyAnswerEntity = answerMap.get(questionEntity.getQuestionId());

            List<View> views = new ArrayList<>();
            if (C.QUESTION_TYPE_SHORT_ANSWER.equals(questionEntity.getType())) {
                EditText editText = newEditText(1);

                if (doSurveyAnswerEntity != null) {
                    editText.setText(doSurveyAnswerEntity.getAnswer());
                }

                addListener(editText, false);

                editText.setTag(questionEntity.getQuestionId());

                views.add(editText);
            } else if (C.QUESTION_TYPE_YES_NO.equals(questionEntity.getType())) {
                CheckBox checkBox = newCheckBox();

                if (doSurveyAnswerEntity != null) {
                    checkBox.setChecked(C.FLAG_YES.equals(doSurveyAnswerEntity.getAnswer()));
                }

                addListener(checkBox);

                checkBox.setText(questionEntity.getQuestionText());

                checkBox.setTag(questionEntity.getQuestionId());

                txtQuestionText.setVisibility(View.GONE);

                views.add(checkBox);
            }else if (C.QUESTION_TYPE_YES_NO_MODIFIED.equals(questionEntity.getType())) {
                CheckBox checkBox = newCheckBox();

                if (doSurveyAnswerEntity != null) {
                    checkBox.setChecked(C.FLAG_YES.equals(doSurveyAnswerEntity.getAnswer()));
                }

                addListener(checkBox);

                checkBox.setText(questionEntity.getQuestionText());

                checkBox.setTag(questionEntity.getQuestionId());

                txtQuestionText.setVisibility(View.GONE);

                EditText editText = newEditText(1);

                if (doSurveyAnswerEntity != null) {
                    editText.setText(doSurveyAnswerEntity.getExtra());
                }

                addListener(editText, true);

                editText.setTag(questionEntity.getQuestionId());

                views.add(editText);
                views.add(checkBox);
            } else if (C.QUESTION_TYPE_MULTI_CHOICE.equals(questionEntity.getType()) || C.QUESTION_TYPE_MULTI_SELECT_CHOICE.equals(questionEntity.getType())) {
                Set<String> choiceSet = null;
                if (C.QUESTION_TYPE_MULTI_SELECT_CHOICE.equals(questionEntity.getType())) {
                    if (doSurveyAnswerEntity != null && !TextUtils.isEmpty(doSurveyAnswerEntity.getAnswer())) {
                        choiceSet = new HashSet<>();
                        String[] temp = doSurveyAnswerEntity.getAnswer().split("\\|");
                        for (String s : temp) {
                            choiceSet.add(s.trim());
                        }
                    }
                }
                if (questionEntity.getQuestionContents() != null) {
                    for (QuestionContentEntity questionContentEntity : questionEntity.getQuestionContents()) {
                        String value = questionContentEntity.getValue();
                        if (value == null) {
                            value = questionContentEntity.getQuestionContentId().toString();
                        }
                        CheckBox checkBox = newCheckBox();
                        checkBox.setText(questionContentEntity.getLabel());

                        if (C.QUESTION_TYPE_MULTI_SELECT_CHOICE.equals(questionEntity.getType())) {
                            if (choiceSet != null && choiceSet.contains(value)) {
                                checkBox.setChecked(true);
                            }
                        } else if(doSurveyAnswerEntity != null && value.equals(doSurveyAnswerEntity.getAnswer())){
                            checkBox.setChecked(true);
                        }

                        addListener(checkBox);

                        checkBox.setTag(questionEntity.getQuestionId() + "_" + value);

                        views.add(checkBox);
                    }
                }
            }else if (C.QUESTION_TYPE_FREE_TEXT.equals(questionEntity.getType())) {

                EditText editText = newEditText(FREE_TEXT_ROWS);

                if (doSurveyAnswerEntity != null) {
                    editText.setText(doSurveyAnswerEntity.getAnswer());
                }

                addListener(editText, false);

                editText.setTag(questionEntity.getQuestionId());

                views.add(editText);
            }

            for (View view : views) {
                questionAnswerPane.addView(view);
            }

            List<View> answerViews = answerViewMap.get(questionEntity.getQuestionId());
            if (answerViews == null) {
                answerViews = new ArrayList<>();
                answerViewMap.put(questionEntity.getQuestionId(), answerViews);
            }
            answerViews.clear();
            answerViews.addAll(views);
        }
    }

    private EditText newEditText(int rows) {
        final EditText editText = new EditText(context);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        editText.setMaxLines(rows);

        return editText;
    }

    private void addListener(final EditText editText, final boolean isExtra) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getTag() != null) {
                    Long questionId = Long.valueOf(editText.getTag().toString());
                    DoSurveyAnswerEntity doSurveyAnswerEntity = answerMap.get(questionId);
                    if (doSurveyAnswerEntity == null) {
                        doSurveyAnswerEntity = new DoSurveyAnswerEntity();
                        doSurveyAnswerEntity.setQuestionId(questionId);
                        doSurveyAnswerEntity.setOutletId(outletId);
                        doSurveyAnswerEntity.setSurveyId(surveyId);
                        doSurveyAnswerEntity.setRouteScheduleDetailId(routeScheduleDetailId);
                        answerMap.put(questionId, doSurveyAnswerEntity);
                    }
                    if (isExtra) {
                        doSurveyAnswerEntity.setExtra(s.toString());
                    } else {
                        doSurveyAnswerEntity.setAnswer(s.toString());
                    }
                }
            }
        });
    }

    private CheckBox newCheckBox() {
        final CheckBox checkBox = new CheckBox(context);
        checkBox.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        return checkBox;
    }

    private void addListener(final CheckBox checkBox) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.getTag() != null) {
                    Long questionId = null;
                    String answerValue = null;
                    String sTag = checkBox.getTag().toString();
                    int index = sTag.indexOf("_");
                    if (index > 0) {
                        questionId =  Long.valueOf(sTag.substring(0, index));
                        answerValue = sTag.substring(index + 1);
                    } else {
                        questionId = Long.valueOf(sTag);
                    }
                    String questionType = getQuestionType(questionId);

                    DoSurveyAnswerEntity doSurveyAnswerEntity = answerMap.get(questionId);
                    if (doSurveyAnswerEntity == null) {
                        doSurveyAnswerEntity = new DoSurveyAnswerEntity();
                        doSurveyAnswerEntity.setQuestionId(questionId);
                        doSurveyAnswerEntity.setOutletId(outletId);
                        doSurveyAnswerEntity.setSurveyId(surveyId);
                        doSurveyAnswerEntity.setRouteScheduleDetailId(routeScheduleDetailId);
                        answerMap.put(questionId, doSurveyAnswerEntity);
                    }

                    if (C.QUESTION_TYPE_MULTI_SELECT_CHOICE.equals(questionType)) {
                        if (!TextUtils.isEmpty(doSurveyAnswerEntity.getAnswer())) {
                            List<String> aList = Arrays.asList(doSurveyAnswerEntity.getAnswer().split("\\|"));
                            if (isChecked) {
                                if (!aList.contains(answerValue)) {
                                    aList.add(answerValue);
                                }
                            } else {
                                aList.remove(answerValue);
                            }
                            StringBuilder sb = new StringBuilder();
                            for (String s : aList) {
                                if (sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(s);
                            }
                            doSurveyAnswerEntity.setAnswer(sb.toString());
                        } else if(isChecked){
                            doSurveyAnswerEntity.setAnswer(answerValue);
                        }
                    } else if (C.QUESTION_TYPE_MULTI_CHOICE.equals(questionType)) {
                        if (isChecked) {
                            List<View> views = answerViewMap.get(questionId);
                            for (View view : views) {
                                CheckBox cb = (CheckBox) view;
                                if (cb != checkBox) {
                                    cb.setChecked(false);
                                }
                            }

                            doSurveyAnswerEntity.setAnswer(answerValue);

                        } else if(answerValue.equals(doSurveyAnswerEntity.getAnswer())){
                            doSurveyAnswerEntity.setAnswer(null);
                        }
                    } else {
                        doSurveyAnswerEntity.setAnswer(isChecked? C.FLAG_YES : C.FLAG_NO);
                    }

                }
            }
        });
    }

    private String getQuestionType(Long questionId) {
        if (questionEntityMap == null) {
            questionEntityMap = new HashMap<>();
            for (QuestionEntity questionEntity : data) {
                questionEntityMap.put(questionEntity.getQuestionId(), questionEntity);
            }
        }
        QuestionEntity questionEntity = questionEntityMap.get(questionId);

        return questionEntity.getType();
    }

    public Map<Long, List<View>> getAnswerViewMap() {
        return answerViewMap;
    }
}
