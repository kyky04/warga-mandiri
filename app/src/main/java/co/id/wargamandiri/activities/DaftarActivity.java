package co.id.wargamandiri.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DaftarResponse;
import co.id.wargamandiri.utils.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static co.id.wargamandiri.services.FastConstans.DAFTAR_TOKO;
import static co.id.wargamandiri.services.FastConstans.LOGIN;

public class DaftarActivity extends AppCompatActivity {


    Session session;
    ProgressDialog progressDialog;

    LinearLayout linearLogin;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_nama_toko)
    EditText etNamaToko;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.btn_signup)
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void daftar() {
        openDialog();
        ANRequest.PostRequestBuilder post = new ANRequest.PostRequestBuilder(DAFTAR_TOKO);
        post.addBodyParameter("email", etUsername.getText().toString());
        post.addBodyParameter("password", etPassword.getText().toString());
        post.addBodyParameter("name", etUsername.getText().toString());
        post.addBodyParameter("nama_toko", etNamaToko.getText().toString());
        post.build().getAsObject(DaftarResponse.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {
                closeDialog();
                if (response instanceof DaftarResponse) {
                    if (((DaftarResponse) response).isStatus()) {
                        finish();
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                closeDialog();
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


    @OnClick(R.id.btn_signup)
    public void onViewClicked() {
        daftar();
    }
}
