package co.id.wargamandiri.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.utils.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DaftarActivity extends AppCompatActivity {


    Session session;
    ProgressDialog progressDialog;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_nama_toko)
    EditText etNamaToko;
    @BindView(R.id.et_phone_toko)
    EditText etPhoneToko;
    @BindView(R.id.et_deskripsi_toko)
    EditText etDeskripsiToko;
    @BindView(R.id.et_alamat_toko)
    EditText etAlamatToko;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.linear_login)
    LinearLayout linearLogin;

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

    void openDialog() {
        progressDialog = ProgressDialog.show(this, "Harap tunggu", "Prosess .  . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
    }
}
