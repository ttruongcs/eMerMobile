package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banvien.fcv.mobile.adapter.DoSurveyAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.dao.DoSurveyAnswerDAO;
import com.banvien.fcv.mobile.db.dao.QuestionDAO;
import com.banvien.fcv.mobile.db.entities.DoSurveyAnswerEntity;
import com.banvien.fcv.mobile.db.entities.QuestionEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Hieu Le on 6/28/2016.
 */
public class DoSurveyActivity extends BaseDrawerActivity implements LoaderManager.LoaderCallbacks<List<QuestionEntity>> {
    private final String TAG = "DoSurveyActivity";
    private final int LOADER_ID = 1;
    private final int ANSWER_LOADER_ID = 2;
    private ProgressDialog progressDialog;
    private Long outletId, surveyId;
    private Repo repo;
    private List<QuestionEntity> questionList = new ArrayList<>();
    private Map<Long, DoSurveyAnswerEntity> answerMap = new HashMap<>();
    @Bind(R.id.dosurvey_recyclerview)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosurvey);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        surveyId = getIntent().getLongExtra(ScreenContants.KEY_SURVEY_ID, 0l);
        repo = new Repo(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DoSurveyAdapter(this, questionList, answerMap);
        recyclerView.setAdapter(adapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

    }


    @Override
    public Loader<List<QuestionEntity>> onCreateLoader(int id, Bundle args) {
        try {
            QuestionDAO questionDAO = repo.getQuestionDAO();
            QueryBuilder<QuestionEntity, Long> queryBuilder =  questionDAO.queryBuilder();
            queryBuilder.where().eq("surveyId", surveyId);
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
            }
            if (questionIds.size() > 0) {
                try {
                    DoSurveyAnswerDAO surveyAnswerDAO = repo.getDoSurveyAnswerDAO();
                    QueryBuilder<DoSurveyAnswerEntity, Long> queryBuilder = surveyAnswerDAO.queryBuilder();
                    queryBuilder.where().in("questionId", questionIds);
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
