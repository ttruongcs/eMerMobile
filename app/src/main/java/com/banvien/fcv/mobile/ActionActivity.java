package com.banvien.fcv.mobile;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;

import java.sql.SQLException;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ActionActivity extends BaseDrawerActivity {
    private static final String TAG = "ActionActivity";
    private Repo repo;
    @Bind(R.id.outletName)
    TextView outletName;

    @Bind(R.id.outletCode)
    TextView outletCode;

    @Bind(R.id.posm)
    ImageView imgPosm;

    @Bind(R.id.outletAddress)
    TextView outletAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repo(this);
        setContentView(R.layout.activity_action);
        Long outletId = this.getIntent().getLongExtra("com.banvien.fcv.emer.outletId", 0l);
        bindViews();
        try {
            fillOutletInfo(2l);
        } catch (SQLException e) {
            Log.e(TAG, "Error when get Outlet Information");
        }
        setInitialConfiguration();
    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_title);
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

    private void fillOutletInfo(Long outletId) throws SQLException {
        OutletEntity entity = repo.getOutletDAO().findById(outletId);
        if(entity != null) {
            OutletDTO outletDTO = OutletUtil.convertToDTO(entity);
            outletName.setText(outletDTO.getName());
            outletCode.setText(buildOutletCode(outletDTO.getCode(), outletDTO.getdCode()));
            outletAddress.setText(buildOutletAdress(outletDTO.getLocationNo(), outletDTO.getStreet()
                    , outletDTO.getWard(), outletDTO.getCityName()));
        }
    }

    private String buildOutletCode(String outletCode, String distributorCode){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(outletCode);
        stringBuffer.append(distributorCode);
        return stringBuffer.toString();
    }

    private String buildOutletAdress(String locationNum, String street, String ward, String cityName){
        StringBuffer stringBuffer = new StringBuffer();
        if(null != locationNum) {
            stringBuffer.append(locationNum).append(",  ");
        }
        if(null != street) {
            stringBuffer.append(street).append("   ");
        }
        if(null != ward) {
            stringBuffer.append(ward).append("   ");
        }
        if(null != cityName) {
            stringBuffer.append(cityName);
        }
        return stringBuffer.toString();
    }
}
