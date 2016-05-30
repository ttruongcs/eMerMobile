package com.banvien.fcv.mobile;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.fragments.CustomMapFragment;
import com.banvien.fcv.mobile.utils.ELog;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity  {

    private CustomMapFragment mapFragment;
    private Repo repo;
    private static Long outletId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_map);
        repo = new Repo(this);
        outletId = getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (CustomMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
            OutletMerDTO outletMerDTO = repo.getOutletMerDAO().findByProperties(properties);

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
