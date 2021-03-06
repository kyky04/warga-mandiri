package co.id.wargamandiri.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.LoginResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static co.id.wargamandiri.data.Constans.LOGIN;

public class LoginActivity extends AppCompatActivity {

    Session session;
    ProgressDialog progressDialog;
    @BindView(R.id.img_logo)
    ImageView img;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_signin)
    Button btnSignin;
    @BindView(R.id.blm_pnya_akun)
    TextView blmPnyaAkun;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_reset_pass)
    TextView tvResetPass;

    ArrayList<View> etView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new Session(this);

        etView.add(etUsername);
        etView.add(etPassword);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void login() {
        openDialog();
        ANRequest.PostRequestBuilder post = new ANRequest.PostRequestBuilder(LOGIN);
        post.addBodyParameter("email", etUsername.getText().toString());
        post.addBodyParameter("password", etPassword.getText().toString());
        post.build().getAsObject(LoginResponse.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {
                closeDialog();
                if (response instanceof LoginResponse) {
                    if (((LoginResponse) response).isStatus()) {
                        session.createUser(((LoginResponse) response));
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    } else {
                        CommonUtil.showToast(LoginActivity.this, "Email atau Password tidak sesuai");
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                closeDialog();
                CommonUtil.showToast(LoginActivity.this, "Please check your server");
            }
        });
    }


    void openDialog() {
        progressDialog = ProgressDialog.show(this, "Harap tunggu", "Prosess .  . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }


    @OnClick({R.id.btn_signin, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                if (!CommonUtil.validateEmptyEntries(etView)) {
                    login();
                } else {
                    CommonUtil.showToast(LoginActivity.this, "Harap isi semua bidang");
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, DaftarActivity.class));
                break;
        }
    }

    @OnClick(R.id.tv_reset_pass)
    public void onViewClicked() {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }
}
