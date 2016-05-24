package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.banvien.fcv.mobile.adapter.ComplainTypeAdapter;
import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletMerEntity;
import com.banvien.fcv.mobile.dto.ComplainTypeDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;

/**
 * Created by Linh Nguyen on 5/23/2016.
 */
public class ComplainTypeActivity extends BaseDrawerActivity {
    private static final String TAG = "ComplainTypeActivity";

    @Bind(R.id.rcvComplain)
    RecyclerView recyclerView;

    @Bind(R.id.btnComplain)
    Button btnSendComplaint;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ComplainTypeDTO> mData;
    private Repo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repo(this);
        setContentView(R.layout.complaintype_activity);
        setTitle(getString(R.string.complaint));
        initRecyclerView();
    }

    private void initRecyclerView() {
        mData = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ComplainTypeAdapter(mData, btnSendComplaint, this);
        recyclerView.setAdapter(adapter);

        loadComplainTypes();

    }

    private void loadComplainTypes() {
        mData.clear();
        try {
            List<ComplainTypeDTO> complainTypeDTOs = repo.getComplainTypeDAO().getAllComplainType();
            if(complainTypeDTOs.size() > 0) {
                mData.addAll(complainTypeDTOs);
            }
            mData.add(new ComplainTypeDTO()); //Create empty object for creating edittext
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            ELog.e(e.getMessage(), e);
        }
    }

    public void addToMerResult(List<OutletMerDTO> dtos) {
        try {
            for(OutletMerDTO outletMerDTO : dtos) {
                this.repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
            }
            Toast.makeText(ComplainTypeActivity.this, "Send success", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            ELog.d("Can not add complaint to mer result", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (repo != null) {
            repo.release();
        }
    }
}
