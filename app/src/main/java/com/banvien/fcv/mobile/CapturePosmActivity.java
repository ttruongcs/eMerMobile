package com.banvien.fcv.mobile;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.banvien.fcv.mobile.adapter.PosmCaptureListAdapter;
import com.banvien.fcv.mobile.adapter.PosmListAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;


public class CapturePosmActivity extends BaseDrawerActivity {
    private static final String TAG = "CapturePosmActivity";
    private Repo repo;
    private RecyclerView.Adapter adapter;
    private List<OutletMerDTO> mData;
    private static Long outletId;
    private static String captureType;

    @Bind(R.id.posmList)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repo(this);
        setContentView(R.layout.posm_activity);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        captureType = this.getIntent().getStringExtra(ScreenContants.CAPTURE_TYPE);
        bindViews();
        try {
            mData = findPOSMRegistered(outletId);
            recyclerView.setHasFixedSize( true );
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PosmCaptureListAdapter(mData, repo, captureType);
            recyclerView.setAdapter(adapter);
        } catch (SQLException e) {
            Log.e(TAG, "Error when get Outlet Information");
        }
        setInitialConfiguration();
    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.posm_title_merchandising);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

    }

    private List<OutletMerDTO> findPOSMRegistered(Long outletId) throws SQLException {
        return repo.getOutletMerDAO().findByDataTypeAndOutlet(ScreenContants.POSM_TYPE, outletId);
    }
}
