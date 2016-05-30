package com.banvien.fcv.mobile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;

import butterknife.Bind;


public class ActionActivity extends BaseDrawerActivity {
    private static final String TAG = "ActionActivity";
    private static Long outletId;
    private Repo repo;
    @Bind(R.id.outletName)
    TextView outletName;

    @Bind(R.id.outletCode)
    TextView outletCode;

    @Bind(R.id.btnPOSM)
    Button btnPOSM;

    @Bind(R.id.outletAddress)
    TextView outletAddress;

    @Bind(R.id.btnOrder)
    Button orderStep;

    @Bind(R.id.btnCapture)
    Button btnCapture;

    @Bind(R.id.btnAfter)
    Button btnAfter;

    @Bind(R.id.btnComplaint)
    Button btnComplain;

    @Bind(R.id.btnBefore)
    Button btnBefore;

    @Bind(R.id.btnGPS)
    Button btnGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repo(this);
        setContentView(R.layout.activity_action);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
        bindViews();
        try {
            fillOutletInfo(2l);
        } catch (SQLException e) {
            Log.e(TAG, "Error when get Outlet Information");
        }
        setInitialConfiguration();
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        orderStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnPOSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PosmActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AfterDisplayActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BeforeDisplayActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AfterDisplayActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ComplainTypeActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });
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
