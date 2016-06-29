package com.banvien.fcv.mobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banvien.fcv.mobile.beanutil.OutletMerUtil;
import com.banvien.fcv.mobile.beanutil.OutletUtil;
import com.banvien.fcv.mobile.db.Repo;
import com.banvien.fcv.mobile.db.entities.OutletEntity;
import com.banvien.fcv.mobile.dto.OutletDTO;
import com.banvien.fcv.mobile.dto.OutletMerDTO;
import com.banvien.fcv.mobile.fragments.CustomMapFragment;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.ChangeStatusTimeline;
import com.banvien.fcv.mobile.utils.ELog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity  {

    private CustomMapFragment mapFragment;
    private Repo repo;
    private static Long outletId;
    private static Long routeScheduleDetailId;
    private static long updateGps; // 1: Has Update, 2: No
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
        updateGps = getIntent().getLongExtra(ScreenContants.KEY_GPS_UPDATE, 0);
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
        if(updateGps == 1) {
            btnCheck.setText(getString(R.string.update));
            btnAgree.setVisibility(View.INVISIBLE);

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)btnCheck.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        }
    }


    private void bindEvent() {
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid == true) {
                    addOrUpdateGPS();
                    changeStatus();
                } else {
                    Toast.makeText(v.getContext(), "Cần check hợp lệ trước khi đồng ý", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(updateGps == 0) {
                    double lat = mapFragment.getLocation().getLatitude();
                    double log = mapFragment.getLocation().getLongitude();

                    Location locationA = new Location("point A");
                    locationA.setLatitude(lat);
                    locationA.setLongitude(log);
                    Location locationB = new Location("point B");
                    locationB.setLatitude(outletDTO.getLat());
                    locationB.setLongitude(outletDTO.getLg());
                    float distance = locationA.distanceTo(locationB) ;
                    tvGps.setText("GPS: " + String.valueOf(lat) + "," + String.valueOf(log));
                    if(distance >= ScreenContants.GPS_DISTANCE){
                        Toast.makeText(v.getContext(), "Khoảng cách cần nhỏ hơn hoặc bằng " + ScreenContants.GPS_DISTANCE + " đơn vị", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "Khoảng cách hợp lệ", Toast.LENGTH_SHORT).show();
                        isValid = true;
                    }
                } else {
                    final double lat = mapFragment.getLocation().getLatitude();
                    final double log = mapFragment.getLocation().getLongitude();

                    Call<Integer> call = RestClient.getInstance().getOutletService().updateLocation(outletId, BigDecimal.valueOf(lat), BigDecimal.valueOf(log));
                    call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Integer result = response.body();
                            if(result == 1) {
                                try {
                                    int rowUpdate = repo.getOutletDAO().updateGpsOutlet(outletId, lat, log);
                                    tvGps.setText("GPS: " + String.valueOf(lat) + "," + String.valueOf(log));
                                    if(rowUpdate > 0) {
                                        showUpdateDialog();
                                    } else {
                                        //Todo Handle insert to table failed
                                        ELog.d("Insert failed", "Insert to sqlite error");
                                    }
                                } catch (SQLException e) {
                                    ELog.d(e.getMessage(), e);
                                }
                            } else {
                                Toast.makeText(getBaseContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                            }
                        }


                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            ELog.d("update fail", "Cập nhật GPS thất bại");
                        }
                    });
                }
            }
        });
    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.gps_update_title));
        builder.setMessage(getString(R.string.gps_update_content));

        String positiveText = getString(R.string.accept);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addOrUpdateGPS();
                changeStatus();
            }
        });

        String negativeText = getString(R.string.cancel);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss dialog
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void changeStatus() {
        ChangeStatusTimeline changeStatusTimeline = new ChangeStatusTimeline(getBaseContext(), routeScheduleDetailId);
        String[] next = {ScreenContants.CAPTURE_OVERVIEW_COLUMN};
        changeStatusTimeline.changeStatusToDone(ScreenContants.IN_OUTLET
                , ScreenContants.CHECK_IN_COLUMN, next, ScreenContants.END_DATE_COLUMN, false);
        Intent intent = new Intent(this, InOutletHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletDTO.getOutletId());
        intent.putExtra(ScreenContants.KEY_ROUTESCHEDULE_DETAIL, outletDTO.getRouteScheduleDetailId());
        startActivity(intent);
        finish();
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
