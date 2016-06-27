package com.banvien.fcv.mobile;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.banvien.fcv.mobile.utils.ELog;

import java.io.File;

import butterknife.Bind;


public class CaptureActivity extends BaseDrawerActivity {
    private static final String TAG = "CaptureActivity";
    private static Long outletId;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    File CS185Pics;

    static int counter = 0;

    @Bind(R.id.btnOverview)
    Button btnOverview;

    @Bind(R.id.btnTool)
    Button btnTool;

    @Bind(R.id.btnUniform)
    Button btnUniform;

    @Bind(R.id.btnBefore)
    Button btnBefore;

    @Bind(R.id.btnAfter)
    Button btnAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_activity);
        outletId = this.getIntent().getLongExtra(ScreenContants.KEY_OUTLET_ID, 0l);
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CaptureOnceActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                intent.putExtra(ScreenContants.CAPTURE_TYPE, ScreenContants.IMAGE_OVERVIEW);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CaptureOnceActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                intent.putExtra(ScreenContants.CAPTURE_TYPE, ScreenContants.IMAGE_TOOL);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnUniform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CaptureOnceActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                intent.putExtra(ScreenContants.CAPTURE_TYPE, ScreenContants.IMAGE_UNIFORM);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CapturePosmActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                intent.putExtra(ScreenContants.CAPTURE_TYPE, ScreenContants.IMAGE_AFTER);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });

        btnBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CapturePosmActivity.class);
                intent.putExtra(ScreenContants.KEY_OUTLET_ID, outletId);
                intent.putExtra(ScreenContants.CAPTURE_TYPE, ScreenContants.IMAGE_BEFORE);
                ELog.d("Outlet Id", String.valueOf(outletId));
                startActivity(intent);
            }
        });
    }

}
