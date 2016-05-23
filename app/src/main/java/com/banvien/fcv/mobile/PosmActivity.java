package com.banvien.fcv.mobile;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.banvien.fcv.mobile.adapter.PosmListAdapter;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.dto.POSMDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class PosmActivity extends BaseDrawerActivity {
    private static final String TAG = "PosmActivity";
    private Repo repo;
    private List<POSMDTO> mData;

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
            // find POSM regitered
            recyclerView.setAdapter(new PosmListAdapter(mData, this));
            findPOSMRegistered();
        } catch (SQLException e) {
            Log.e(TAG, "Error when get Outlet Information");
        }
        setInitialConfiguration();
    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.pref_about_category_title);
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

    private List<POSMDTO> findPOSMRegistered() throws SQLException {
        List<POSMDTO> results = new ArrayList<>();
        List<OutletMerDTO> outletMerDTOList = repo.getOutletMerDAO().findByDataType(ScreenContants.POSM_DATATYPE);
        if(outletMerDTOList != null && outletMerDTOList.size() > 0) {
            for(OutletMerDTO outletMerDTO : outletMerDTOList) {
                results.add(repo.getPosmDAO().findByCode(outletMerDTO.getRegisterValue()));
            }
        }
        return results;
    }

}
