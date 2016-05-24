package com.banvien.fcv.mobile;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.banvien.fcv.mobile.adapter.PosmListAdapter;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;

import java.sql.SQLException;
import java.util.List;

import butterknife.Bind;


public class PosmActivity extends BaseDrawerActivity {
    private static final String TAG = "PosmActivity";
    private Repo repo;
    private RecyclerView.Adapter adapter;
    private List<OutletMerDTO> mData;

    @Bind(R.id.posmList)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repo(this);
        setContentView(R.layout.posm_activity);
        bindViews();
        try {
            mData = findPOSMRegistered();
            recyclerView.setHasFixedSize( true );
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PosmListAdapter(mData, repo);
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

    private List<OutletMerDTO> findPOSMRegistered() throws SQLException {
        return repo.getOutletMerDAO().findByDataType(ScreenContants.POSM_DATATYPE);
    }
}
