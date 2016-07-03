package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.dto.UserPrincipal;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.K;
import com.banvien.fcv.mobile.utils.UserUtils;
import com.banvien.fcv.mobile.utils.Utils;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Linh Nguyen on 6/28/2016.
 */
public class UserprofileActivity extends BaseDrawerActivity {
    private final String TAG = "UserProfileActivity";

    @BindView(R.id.imgOut)
    ImageView imgOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        imgOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtils.logOut(UserprofileActivity.this);
                Intent intent = new Intent(UserprofileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
