package co.id.wargamandiri.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.utils.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {


    Session session;
    ProgressDialog progressDialog;
    @BindView(R.id.img_logo)
    ImageView img;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.btn_signin)
    Button btnSignin;
    @BindView(R.id.blm_pnya_akun)
    TextView blmPnyaAkun;
    @BindView(R.id.tv_register)
    TextView tvRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new Session(this);



        //        //font
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



    @OnClick({R.id.btn_signin, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_signin:
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, DaftarActivity.class));
                break;
        }
    }
}
