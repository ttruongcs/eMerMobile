package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.banvien.fcv.mobile.adapter.SurveyArrayAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.dao.SurveyDAO;
import com.banvien.fcv.mobile.db.entities.SurveyEntity;
import com.banvien.fcv.mobile.utils.ELog;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 6/28/2016.
 */
public class SurveyActivity extends BaseDrawerActivity implements LoaderManager.LoaderCallbacks<List<SurveyEntity>> {
    private final String TAG = "SurveyActivity";
    private final int LOADER_ID = 1;
    private ProgressDialog progressDialog;
    private Long outletId;
    private Repo repo;
    private List<SurveyEntity> surveyList = new ArrayList<>();
    @Bind(R.id.survey_listview)
    ListView listView;
    private SurveyArrayAdapter surveyArrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_list);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        repo = new Repo(this);
        surveyArrayAdapter = new SurveyArrayAdapter(this, surveyList);
        listView.setAdapter(surveyArrayAdapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

    }


    @Override
    public Loader<List<SurveyEntity>> onCreateLoader(int id, Bundle args) {
        try {
            SurveyDAO surveyDAO = repo.getSurveyDAO();
            QueryBuilder<SurveyEntity, Long> queryBuilder =  surveyDAO.queryBuilder();
            queryBuilder.where().eq("outletId", outletId);
            queryBuilder.orderBy("startDate", true);
            return surveyDAO.getResultSetLoader(this, queryBuilder.prepare());
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
        return null;
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
