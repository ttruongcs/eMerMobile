package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.banvien.fcv.mobile.adapter.SurveyArrayAdapter;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.dao.SurveyDAO;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.IDGenerator;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 6/28/2016.
 */
public class SurveyActivity extends BaseDrawerActivity implements LoaderManager.LoaderCallbacks<List<SurveyEntity>> {
    private final String TAG = "SurveyActivity";
    private final static int LOADER_ID = IDGenerator.newId();
    private ProgressDialog progressDialog;
    private Long outletId, routeScheduleDetailId;
    private Repo repo;
    private List<SurveyEntity> surveyList = new ArrayList<>();
    @BindView(R.id.survey_listview)
    ListView listView;
    private SurveyArrayAdapter surveyArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        setToolbarTitle(A.s(R.string.survey_title));

        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        repo = new Repo(this);
        surveyArrayAdapter = new SurveyArrayAdapter(this, surveyList);
        listView.setAdapter(surveyArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SurveyEntity surveyEntity = surveyList.get(position);
                Intent intent = new Intent(SurveyActivity.this, DoSurveyActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                intent.putExtra(ScreenContants.KEY_SURVEY_ID, surveyEntity.getSurveyId());
                intent.putExtra(ScreenContants.KEY_SURVEY_TITLE, surveyEntity.getName());
                intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, routeScheduleDetailId);
                startActivity(intent);
            }
        });

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(A.s(R.string.loading));
        progressDialog.show();

    }


    @Override
    public Loader<List<SurveyEntity>> onCreateLoader(int id, Bundle args) {
        Loader<List<SurveyEntity>> loader = null;
        try {
            SurveyDAO surveyDAO = repo.getSurveyDAO();
            long count = surveyDAO.count(outletId);
            QueryBuilder<SurveyEntity, Long> queryBuilder =  surveyDAO.queryBuilder();
            if (count > 0) {
                queryBuilder.where().eq("outletId", outletId);
            } else {
                queryBuilder.where().isNull("outletId");
            }
            queryBuilder.orderBy("startDate", true);
            loader = surveyDAO.getResultSetLoader(this, queryBuilder.prepare());
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<SurveyEntity>> loader, List<SurveyEntity> data) {
        if (loader != null && data != null) {
            surveyList.clear();
            surveyList.addAll(data);
            surveyArrayAdapter.notifyDataSetChanged();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<List<SurveyEntity>> loader) {
        surveyList.clear();
    }
}
