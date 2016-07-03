package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.DeclineEntity;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.MySpeedScrollManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Linh Nguyen on 7/2/2016.
 */
public class StatusOutletActivity extends BaseDrawerActivity {
    private final String TAG = "StatusOutletActivity";
    private static final long LAST_POSITION = 9;
    private static final String CODE_OTHER = "5";
    private static long outletId;
    private static long routeScheduleDetailId;

    @BindView(R.id.lvOutletStatus)
    ListView listView;

    @BindView(R.id.edStatus)
    EditText edStatus;

    @BindView(R.id.btnDone)
    Button btnDone;

    List<DeclineEntity> mData;
    private ArrayAdapter arrayAdapter;
    private Repo repo;
    Map<String, Object> keys = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_outlet);
        getSupportActionBar().setTitle(R.string.tinhtrangcuahang);
        repo = new Repo(this);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        routeScheduleDetailId = getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        mData = new ArrayList<>();

        initListView();
        bindEvents();
    }

    private void bindEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keys.put("idSelected",mData.get(position).getDeclineMetadataId());
                keys.put("codeSelected", mData.get(position).getCode());
                edStatus.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String detailDecline = edStatus.getText().toString().trim();
                    Integer idSelected = Integer.valueOf(keys.get("idSelected").toString());
                    long rowUpdate = repo.getOutletDAO().updateDeclineStatus(routeScheduleDetailId, detailDecline, idSelected);
                    if(rowUpdate > 0) {
                        if(keys.get("codeSelected").toString().equals(CODE_OTHER)) {
                           //Nothing here
                        } else {
                            ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(getBaseContext(), routeScheduleDetailId);
                            String[] next = {ScreenContants.BEFORE_DISPLAY_COLUMN, ScreenContants.AFTER_DISPLAY_COLUMN
                                    , ScreenContants.SHORTAGE_PRODUCT_COLUMN, ScreenContants.SURVEY_COLUMN, ScreenContants.SYNC_OUTLET_COLUMN};
                            changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                                    , ScreenContants.CHECK_OUTLET_COLUMN, next, ScreenContants.END_DATE_COLUMN, false, true);
                        }
                        onBackPressed();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initListView() {
        try {
            mData = repo.getDeclineDAO().findAll();
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mData);
            listView.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }


    }

    private void loadData() {
        try {
            mData = repo.getDeclineDAO().findAll();
            arrayAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }
    }


}
