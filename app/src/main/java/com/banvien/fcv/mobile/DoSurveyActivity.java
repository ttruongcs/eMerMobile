package com.banvien.fcv.mobile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.DoSurveyAdapter;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.dao.DoSurveyAnswerDAO;
import com.banvien.fcv.mobile.db.dao.QuestionDAO;
import com.banvien.fcv.mobile.db.entities.DoSurveyAnswerEntity;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.utils.C;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.DialogBuilder;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.IDGenerator;
import com.banvien.fcv.mobile.utils.StringUtils;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Hieu Le on 6/28/2016.
 */
public class DoSurveyActivity extends BaseDrawerActivity implements LoaderManager.LoaderCallbacks<List<QuestionEntity>> {
    private final String TAG = "DoSurveyActivity";
    private final static int LOADER_ID = IDGenerator.newId();
    private final static int ANSWER_LOADER_ID = IDGenerator.newId();
    private ProgressDialog progressDialog;
    private Long outletId, surveyId, routeScheduleDetailId;
    private Repo repo;
    private List<QuestionEntity> questionList = new ArrayList<>();
    private Map<Long, DoSurveyAnswerEntity> answerMap = new HashMap<>();
    private List<Long> requiredQuestionIds = new ArrayList<>();
    @BindView(R.id.dosurvey_recyclerview)
    RecyclerView recyclerView;
    private DoSurveyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dialog confirmDialog;
    private boolean isSaved;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosurvey);


        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        surveyId = getIntent().getLongExtra(ScreenContants.KEY_SURVEY_ID, 0l);
        String title = getIntent().getStringExtra(ScreenContants.KEY_SURVEY_TITLE);
        if (StringUtils.isNotBlank(title)) {
            setToolbarTitle(title);
        } else {
            setToolbarTitle(A.s(R.string.survey_title));
        }
        routeScheduleDetailId = getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);

        repo = new Repo(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DoSurveyAdapter(this, questionList, answerMap, outletId, surveyId, routeScheduleDetailId);
        recyclerView.setAdapter(adapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(A.s(R.string.loading));
        progressDialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.survey_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!isSaved) {
            if (confirmDialog == null) {
                confirmDialog = DialogBuilder.buildConfirmDlg(this, A.s(R.string.confirm), A.s(R.string.dosurvey_not_yet_saved_exit_warning_msg),
                        new Runnable() {
                            @Override
                            public void run() {
                                DoSurveyActivity.super.onBackPressed();
                            }
                        }, null, false);
            }
            confirmDialog.show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_save) {
            if (!checkRequiredAnswers()) {
                Toast.makeText(this, A.s(R.string.save_survey_warning_required_answer), Toast.LENGTH_LONG).show();
                return true;
            }

            progressDialog.show();
            new AsyncTask<Void, Void, Boolean>() {
                private boolean success = false;
                @Override
                protected Boolean doInBackground(Void... params) {
                    boolean res = false;
                    try {
                        saveAnswers();
                        res = true;
                    } catch (SQLException e) {
                        ELog.e(e.getMessage(), e);
                    }
                    success = res;
                    return res;
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    progressDialog.dismiss();
                    if(success) {
                        ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(getBaseContext(), routeScheduleDetailId);
                        String[] next = {ScreenContants.SYNC_OUTLET_COLUMN};
                        changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                                , ScreenContants.SURVEY_COLUMN, next, ScreenContants.END_DATE_COLUMN, false);
                    }
                    Toast.makeText(DoSurveyActivity.this, A.s(success ? R.string.save_survey_success_msg : R.string.save_survey_failed_msg), Toast.LENGTH_LONG).show();
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressDialog.show();
                }
            }.execute();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkRequiredAnswers() {
//        for (Long q : requiredQuestionIds) {
//            if (!answerMap.containsKey(q)) {
//                return false;
//            }
//        }
        // TODO required or not
        return true;
    }

    @Override
    public Loader<List<QuestionEntity>> onCreateLoader(int id, Bundle args) {
        try {
            QuestionDAO questionDAO = repo.getQuestionDAO();
            QueryBuilder<QuestionEntity, Long> queryBuilder =  questionDAO.queryBuilder();
            if (surveyId != null && surveyId > 0) {
                queryBuilder.where().eq("surveyId", surveyId);
            }
            queryBuilder.orderBy("questionId", true);
            return questionDAO.getResultSetLoader(this, queryBuilder.prepare());
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<QuestionEntity>> loader, List<QuestionEntity> data) {
        if (loader != null && data != null) {
            questionList.clear();
            questionList.addAll(data);

            getSupportLoaderManager().initLoader(ANSWER_LOADER_ID, null, new AnswerLoader(this));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<QuestionEntity>> loader) {
        questionList.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void saveAnswers() throws SQLException {
        Map<Long, List<View>> answerViewMap =  adapter.getAnswerViewMap();
        for (QuestionEntity questionEntity : questionList) {
            DoSurveyAnswerEntity doSurveyAnswerEntity = answerMap.get(questionEntity.getQuestionId());
            if (doSurveyAnswerEntity == null) {
                doSurveyAnswerEntity = new DoSurveyAnswerEntity();
                doSurveyAnswerEntity.setOutletId(outletId);
                doSurveyAnswerEntity.setQuestionId(questionEntity.getQuestionId());
                doSurveyAnswerEntity.setSurveyId(surveyId);
                doSurveyAnswerEntity.setRouteScheduleDetailId(routeScheduleDetailId);
            }

            List<View> views = answerViewMap.get(questionEntity.getQuestionId());
            if (views != null && views.size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (View view : views) {
                    if (C.QUESTION_TYPE_YES_NO.equals(questionEntity.getType())) {
                        if (view instanceof CheckBox) {
                            setAnswerYesOrNo4Checked(doSurveyAnswerEntity, view);
                        }
                    } else if (C.QUESTION_TYPE_YES_NO_MODIFIED.equals(questionEntity.getType())) {
                        if (view instanceof CheckBox) {
                            setAnswerYesOrNo4Checked(doSurveyAnswerEntity, view);
                        } else if (view instanceof EditText) {
                            setExtra4Input(doSurveyAnswerEntity, view);
                        }
                    }else if (C.QUESTION_TYPE_FREE_TEXT.equals(questionEntity.getType()) || C.QUESTION_TYPE_SHORT_ANSWER.equals(questionEntity.getType())) {
                        if (view instanceof EditText) {
                            setAnswer4Input(doSurveyAnswerEntity, view);
                        }
                    } else if (C.QUESTION_TYPE_MULTI_SELECT_CHOICE.equals(questionEntity.getType())) {
                        if (view instanceof CheckBox) {
                            CheckBox checkBox = (CheckBox)view;
                            if (checkBox.isChecked() && checkBox.getTag() != null) {
                                if (sb.length() > 0) {
                                    sb.append("|");
                                }
                                sb.append(checkBox.getTag().toString().substring(checkBox.getTag().toString().indexOf("_") + 1));
                            }
                        }
                    } else if (C.QUESTION_TYPE_MULTI_CHOICE.equals(questionEntity.getType())) {
                        if (view instanceof CheckBox && view.getTag() != null && ((CheckBox) view).isChecked()) {
                            doSurveyAnswerEntity.setAnswer(view.getTag().toString().substring(view.getTag().toString().indexOf("_") + 1));
                        }
                    }
                }

                if (C.QUESTION_TYPE_MULTI_SELECT_CHOICE.equals(questionEntity.getType())) {
                    doSurveyAnswerEntity.setAnswer(sb.toString());
                }
            } else {
                doSurveyAnswerEntity.setAnswer(null);
                doSurveyAnswerEntity.setExtra(null);
            }

            if (doSurveyAnswerEntity.get_id() == null) {
                repo.getDoSurveyAnswerDAO().create(doSurveyAnswerEntity);
            } else {
                repo.getDoSurveyAnswerDAO().update(doSurveyAnswerEntity);
            }
        }

        isSaved = true;

        repo.getStatusInOutletDAO().updateStatus("khaoSat", null, routeScheduleDetailId);
    }

    private void setAnswerYesOrNo4Checked(DoSurveyAnswerEntity doSurveyAnswerEntity, View view) {
        CheckBox checkBox = (CheckBox)view;
        doSurveyAnswerEntity.setAnswer(checkBox.isChecked()? C.FLAG_YES : C.FLAG_NO);
    }

    private void setAnswer4Input(DoSurveyAnswerEntity doSurveyAnswerEntity, View view) {
        EditText editText = (EditText) view;
        doSurveyAnswerEntity.setAnswer(editText.getText().toString());
    }

    private void setExtra4Input(DoSurveyAnswerEntity doSurveyAnswerEntity, View view) {
        EditText editText = (EditText) view;
        doSurveyAnswerEntity.setExtra(editText.getText().toString());
    }

    private class AnswerLoader implements LoaderManager.LoaderCallbacks<List<DoSurveyAnswerEntity>> {
        private Context context;
        public AnswerLoader(Context context) {
            this.context = context;
        }

        @Override
        public Loader<List<DoSurveyAnswerEntity>> onCreateLoader(int id, Bundle args) {
            List<Long> questionIds = new ArrayList<>(questionList.size());
            for (QuestionEntity questionEntity : questionList) {
                questionIds.add(questionEntity.getQuestionId());
                if (questionEntity.getRequireAnswer() != null && questionEntity.getRequireAnswer().booleanValue()) {
                    requiredQuestionIds.add(questionEntity.getQuestionId());
                }
            }
            if (questionIds.size() > 0) {
                try {
                    DoSurveyAnswerDAO surveyAnswerDAO = repo.getDoSurveyAnswerDAO();
                    QueryBuilder<DoSurveyAnswerEntity, Long> queryBuilder = surveyAnswerDAO.queryBuilder();
                    Where where = queryBuilder.where().eq("outletId", outletId);
                    if (surveyId != null && surveyId > 0) {
                        where.and().eq("surveyId", surveyId);
                    }
                    where.and().in("questionId", questionIds);
                    return surveyAnswerDAO.getResultSetLoader(context, queryBuilder.prepare());
                } catch (SQLException e) {
                    ELog.e(e.getMessage(), e);
                }
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<List<DoSurveyAnswerEntity>> loader, List<DoSurveyAnswerEntity> data) {
            if (loader != null && data != null) {
                for (DoSurveyAnswerEntity doSurveyAnswerEntity : data) {
                    answerMap.put(doSurveyAnswerEntity.getQuestionId(), doSurveyAnswerEntity);
                }
                adapter.notifyDataSetChanged();
            }
            progressDialog.dismiss();
        }

        @Override
        public void onLoaderReset(Loader<List<DoSurveyAnswerEntity>> loader) {
            answerMap.clear();
        }
    }
}
