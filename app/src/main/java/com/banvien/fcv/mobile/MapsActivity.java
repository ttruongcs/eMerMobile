package com.banvien.fcv.mobile;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.fragments.CustomMapFragment;
import com.banvien.fcv.mobile.utils.ELog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity  {

    private CustomMapFragment mapFragment;
    private Repo repo;
    private static Long outletId;
    private OutletDTO outletDTO;

    @Bind(R.id.tvTime)
    TextView tvTime;

    @Bind(R.id.tvGps)
    TextView tvGps;

    @Bind(R.id.btnAgree)
    Button btnAgree;

    @Bind(R.id.btnCheck)
    Button btnCheck;

    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_map);
        ButterKnife.bind(this);
        repo = new Repo(this);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (CustomMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        outletDTO = getOutlet(outletId);

        bindView();
        bindEvent();
    }

    private OutletDTO getOutlet(Long outletId) {
        OutletDTO result = new OutletDTO();

        try {
            OutletEntity entity = repo.getOutletDAO().findById(outletId);

            if(entity != null) {
                result = OutletUtil.convertToDTO(entity);
            }

        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

        return result;
    }

    private void bindView() {
        mapFragment.setInfo(tvTime ,tvGps);
    }


    private void bindEvent() {
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = mapFragment.getLocation().getLatitude();
                double log = mapFragment.getLocation().getLongitude();
                tvGps.setText(String.valueOf(lat));

                Toast.makeText(v.getContext(), String.valueOf(lat), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        addOrUpdateGPS();
    }

    private void addOrUpdateGPS() {
        Map<String, Object> properties = new HashMap<>();
        Location location = mapFragment.getLocation();
        String locationValue = location.getLatitude() + "," + location.getLongitude();
        try {
            properties.put(ScreenContants.DATA_TYPE, ScreenContants.GPS);
            properties.put(ScreenContants.OUTLETID, outletId);
            OutletMerDTO outletMerDTO = repo.getOutletMerDAO().findFirstResultByProperties(properties);

            if(outletMerDTO.get_id() <= 0) {
                OutletMerDTO gpsDTO = new OutletMerDTO();
                gpsDTO.setActualValue(locationValue);
                gpsDTO.setDataType(ScreenContants.GPS);
                gpsDTO.setOutletId(outletId);
                repo.getOutletMerDAO().addOutletMerEntity(OutletMerUtil.convertToEntity(gpsDTO));
            } else {
                outletMerDTO.setActualValue(locationValue);
                repo.getOutletMerDAO().updateOutletMerEntity(OutletMerUtil.convertToEntity(outletMerDTO));
            }
            Toast.makeText(getApplicationContext(),
                    "Check in at gps("+ locationValue + ") successful",
                    Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            ELog.d(e.getMessage(), e);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(repo != null) {
            repo.release();
        }
    }
}
