package co.id.wargamandiri.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.AccessTokenRequest;
import co.id.wargamandiri.models.AccessTokenResponse;
import co.id.wargamandiri.models.LoginResponse;
import co.id.wargamandiri.services.ApiClient;
import co.id.wargamandiri.services.NindyaApi;
import co.id.wargamandiri.services.OauthClient;
import co.id.wargamandiri.services.OauthConstans;
import co.id.wargamandiri.utils.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    Session session;
    ProgressDialog progressDialog;
    @BindView(R.id.tv_nindya)
    TextView tvNindya;
    @BindView(R.id.linear_login)
    LinearLayout linearLogin;
    @BindView(R.id.tv_daftar)
    TextView tvDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new Session(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        //        //font
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    void login() {
        Retrofit retrofit = ApiClient.newInstance();
        NindyaApi service = retrofit.create(NindyaApi.class);
        service.getUser(session.getAccessToken()).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                closeDialog();
                if (response.code() == OauthConstans.HTTP_OK) {
                    session.setEmail(response.body().getEmail());
                    session.setFullName(response.body().getNama());
                    session.setId(response.body().getId());
                    session.setIsLogin(true);
//                    session.setKeyRole(response.body().getRole());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                closeDialog();
            }
        });
    }

    public void getAccessToken() {
        openDialog();
        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
        accessTokenRequest.setClient_id(OauthClient.CLIENT_ID);
        accessTokenRequest.setClient_secret(OauthClient.CLIENT_SECRET);
        accessTokenRequest.setGrant_type(OauthClient.GRANT_TYPE);
        accessTokenRequest.setUsername(etUsername.getText().toString());
        accessTokenRequest.setPassword(etPassword.getText().toString());
        accessTokenRequest.setScope("*");

        Retrofit retrofit = ApiClient.newInstance();
        NindyaApi service = retrofit.create(NindyaApi.class);


        service.getAccessToken(accessTokenRequest).enqueue(new Callback<AccessTokenResponse>() {
            @Override
            public void onResponse(Call<AccessTokenResponse> call, Response<AccessTokenResponse> response) {
                if (response.code() == OauthConstans.HTTP_OK) {
                    session.setKeyApiKey(response.body().getAccess_token());
                    login();
                } else {
                    closeDialog();
                    Toast.makeText(LoginActivity.this, "Email atau Password tidak sesuai", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AccessTokenResponse> call, Throwable t) {
                closeDialog();
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

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

    @OnClick(R.id.tv_daftar)
    public void onViewClicked() {
        startActivity(new Intent(this, DaftarActivity.class));
    }
}
