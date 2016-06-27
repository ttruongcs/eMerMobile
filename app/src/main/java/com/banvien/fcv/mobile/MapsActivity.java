package com.banvien.fcv.mobile;

import android.content.Intent;
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
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
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
    private static Long routeScheduleDetailId;
    private OutletDTO outletDTO;
    private boolean isValid = false;

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
        routeScheduleDetailId = getIntent().getLongExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, 0l);
        isValid = false;

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
                if (isValid == true) {
                    addOrUpdateGPS();
                    ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(getBaseContext(), routeScheduleDetailId);
                    String[] next = {ScreenContants.CAPTURE_OVERVIEW_COLUMN};
                    changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                            , ScreenContants.CHECK_IN_COLUMN, next, ScreenContants.END_DATE_COLUMN, false);

                    Intent intent = new Intent(v.getContext(), InOutletHomeActivity.class);
                    intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletDTO.getOutletId());
                    intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, outletDTO.getRouteScheduleDetailId());
                    startActivity(intent);


                    finish();
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = mapFragment.getLocation().getLatitude();
                double log = mapFragment.getLocation().getLongitude();

                Location locationA = new Location("point A");
                locationA.setLatitude(lat);
                locationA.setLongitude(log);
                Location locationB = new Location("point B");
                locationB.setLatitude(outletDTO.getLat());
                locationB.setLongitude(outletDTO.getLg());
                float distance = locationA.distanceTo(locationB) ;
                tvGps.setText(String.valueOf(lat) + "," + String.valueOf(log));
                if(distance >= ScreenContants.GPS_DISTANCE){
                    Toast.makeText(v.getContext(), "Khoảng cách cần nhỏ hơn hoặc bằng " + ScreenContants.GPS_DISTANCE + " đơn vị", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(v.getContext(), "Khoảng cách hợp lệ", Toast.LENGTH_SHORT).show();
                    isValid = true;
                }
            }
        });
    }

    private void setInitialConfiguration() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.fcvtoolbar);
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
