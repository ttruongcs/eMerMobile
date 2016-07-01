package com.banvien.fcv.mobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.banvien.fcv.mobile.dto.UserPrincipal;
import com.banvien.fcv.mobile.rest.RestClient;
import com.banvien.fcv.mobile.core.A;
import com.banvien.fcv.mobile.utils.ELog;
import com.banvien.fcv.mobile.utils.K;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Linh Nguyen on 6/28/2016.
 */
public class LoginActivity extends BaseDrawerActivity {
    private final String TAG = "LoginActivity";
    @BindView(R.id.edUsername)
    EditText editUsername;
    @BindView(R.id.edPassword)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button loginBtn;
    @BindView(R.id.login_msg)
    TextView txtLoginMsg;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (A.getPrincipal() != null) {
            RestClient.getInstance().setAuthToken(A.getPrincipal().getJwt());
            startHomeActivity();
            finish();
        } else {
            bindEvents();
        }
    }

    private void bindEvents() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtLoginMsg.setVisibility(View.GONE);
                boolean isValid = true;
                if (TextUtils.isEmpty(editUsername.getText().toString().trim())) {
                    editUsername.setError(A.s(R.string.login_error_msg_empty_username));
                    isValid = false;
                }
                if (TextUtils.isEmpty(editPassword.getText())) {
                    editPassword.setError(A.s(R.string.login_error_msg_empty_password));
                    isValid = false;
                }

                if (isValid) {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage(A.s(R.string.loading));
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    Call<UserPrincipal> loginCall = RestClient.getInstance().getHomeService().login(editUsername.getText().toString().trim(), editPassword.getText().toString());
                    loginCall.enqueue(new Callback<UserPrincipal>() {
                        @Override
                        public void onResponse(Call<UserPrincipal> call, Response<UserPrincipal> response) {
                            UserPrincipal userPrincipal = response.body();
                            if (userPrincipal != null && !TextUtils.isEmpty(userPrincipal.getJwt())) {
                                RestClient.getInstance().setAuthToken(userPrincipal.getJwt());
                                A.setPrincipal(userPrincipal);
                                A.putc(K.PRINCIPAL_JSON, userPrincipal.toJsonString());

                                startHomeActivity();
                                finish();

                            } else {
                                setLoginFailedMsg();
                            }

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<UserPrincipal> call, Throwable t) {
                            ELog.e(t.getMessage(), t);
                            setLoginFailedMsg();
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void setLoginFailedMsg() {
        txtLoginMsg.setText(R.string.login_failed_msg);
        txtLoginMsg.setVisibility(View.VISIBLE);
    }
}
