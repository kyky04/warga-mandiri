package co.id.wargamandiri.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_transaksi)
    RelativeLayout btnTransaksi;
    @BindView(R.id.btn_data_master)
    RelativeLayout btnDataMaster;
    @BindView(R.id.btn_kelola_toko)
    RelativeLayout btnKelolaToko;
    @BindView(R.id.btn_notifikasi)
    RelativeLayout btnNotifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_transaksi, R.id.btn_data_master, R.id.btn_kelola_toko, R.id.btn_notifikasi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_transaksi:
                goToActivity(TransaksiActivity.class);
                break;
            case R.id.btn_data_master:
                goToActivity(DataMasterActivity.class);
                break;
            case R.id.btn_kelola_toko:
                goToActivity(KelolaActivity.class);
                break;
            case R.id.btn_notifikasi:
                goToActivity(TransaksiActivity.class);
                break;
        }
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
