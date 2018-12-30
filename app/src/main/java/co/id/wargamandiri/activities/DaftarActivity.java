package co.id.wargamandiri.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DaftarResponse;
import co.id.wargamandiri.models.alamat.KotaResponse;
import co.id.wargamandiri.models.alamat.ProvinsiResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static co.id.wargamandiri.data.Constans.DAFTAR_TOKO;

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
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.et_kode_toko)
    EditText etKodeToko;
    @BindView(R.id.et_district)
    EditText etDistrict;
    @BindView(R.id.et_city)
    EditText etCity;

    ArrayList<View> arrayView = new ArrayList<>();

    List<String> districtList = new ArrayList<>();
    List<String> cityList = new ArrayList<>();
    List<Integer> cityListID = new ArrayList<>();
    int id_provinsi = 0;
    int id_kota = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);

        arrayView.add(etEmail);
        arrayView.add(etPassword);
        arrayView.add(etUsername);
        arrayView.add(etNamaToko);
        arrayView.add(etCity);
        arrayView.add(etDistrict);
//        arrayView.add(etKodeToko);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void daftar() {
        openDialog();
        ANRequest.PostRequestBuilder post = new ANRequest.PostRequestBuilder(DAFTAR_TOKO);
        post.addBodyParameter("email", etEmail.getText().toString());
        post.addBodyParameter("password", etPassword.getText().toString());
        post.addBodyParameter("name", etUsername.getText().toString());
        post.addBodyParameter("nama_toko", etNamaToko.getText().toString());
        post.addBodyParameter("id_provinsi", String.valueOf(id_provinsi));
        post.addBodyParameter("id_kota", String.valueOf(id_kota));
//        post.addBodyParameter("kode", etKodeToko.getText().toString());
        post.build().getAsObject(DaftarResponse.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {
                closeDialog();
                if (response instanceof DaftarResponse) {
                    if (((DaftarResponse) response).isStatus()) {
                        finish();
                    } else {
                        CommonUtil.showToast(DaftarActivity.this, "Gagal Daftar");
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                CommonUtil.showToast(DaftarActivity.this, "Please check your server");
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

    public void dialogDistrict() {
        cityList.clear();
        cityListID.clear();
        ProvinsiResponse province = null;
        try {
            String myJson = inputStreamToString(this.getAssets().open("provinces.json"));
            province = new Gson().fromJson(myJson, ProvinsiResponse.class);
            for (int i = 0; i < province.getData().size(); i++) {
                districtList.add(province.getData().get(i).getProvince());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] items = new String[districtList.size()];
        items = districtList.toArray(items);

        final String[] finalItems = items;
        final ProvinsiResponse finalProvince = province;
        CommonUtil.dialogArray(this, items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etDistrict.setText(finalItems[which]);
                etDistrict.setError(null);

                if (Integer.parseInt(finalProvince.getData().get(which).getProvinceId()) != id_provinsi) {
                    etCity.setText("");
                }

                try {
                    String myJson = inputStreamToString(getAssets().open("regencies.json"));
                    KotaResponse regency = new Gson().fromJson(myJson, KotaResponse.class);
                    for (int i = 0; i < regency.getData().size(); i++) {
                        Log.d("ID", "onClick: " + regency.getData().get(i).getProvinceId() + " " + finalProvince.getData().get(which).getProvinceId());
                        if (regency.getData().get(i).getProvinceId().equals(finalProvince.getData().get(which).getProvinceId())) {
                            cityList.add(regency.getData().get(i).getCityName());
                            cityListID.add(Integer.valueOf(regency.getData().get(i).getCityId()));
                            id_provinsi = Integer.parseInt(regency.getData().get(i).getProvinceId());

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void dialogCity() {

        String[] items = new String[cityList.size()];
        items = cityList.toArray(items);
        final String[] finalItems = items;

        CommonUtil.dialogArray(this, items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etCity.setText(finalItems[which]);
                etCity.setError(null);
                id_kota = cityListID.get(which);
            }
        });
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    @OnClick({R.id.et_district, R.id.et_city, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_district:
                dialogDistrict();
                break;
            case R.id.et_city:
                if (id_provinsi == 0) {
                    Toast.makeText(this, "Isi provinsi", Toast.LENGTH_SHORT).show();
                } else {
                    dialogCity();
                }
                break;
            case R.id.btn_signup:
                if (!CommonUtil.validateEmptyEntries(arrayView)) {
                    daftar();
                } else {
                    CommonUtil.showToast(DaftarActivity.this, "Harap isi semua bidang");
                }

                break;
        }
    }
}
