package co.id.wargamandiri.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.fragments.KelolaDataPengumumanFragment;
import co.id.wargamandiri.fragments.TransKonfirmasFragment;
import co.id.wargamandiri.fragments.TransOrderFragment;
import co.id.wargamandiri.fragments.TransTopupFragment;

public class TransaksiActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_pesanan)
    ImageView imgPesanan;
    @BindView(R.id.btn_pesanan)
    RelativeLayout btnPesanan;
    @BindView(R.id.img_konfirmasi)
    ImageView imgKonfirmasi;
    @BindView(R.id.btn_konfirmasi)
    RelativeLayout btnKonfirmasi;

    TransOrderFragment transOrderFragment;
    TransKonfirmasFragment transKonfirmasFragment;
    TransTopupFragment transTopupFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ButterKnife.bind(this);
        transOrderFragment = new TransOrderFragment();
        transTopupFragment = new TransTopupFragment();
        transKonfirmasFragment = new TransKonfirmasFragment();

    }

    @OnClick({R.id.btn_pesanan, R.id.btn_konfirmasi, R.id.btn_topup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pesanan:
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, transOrderFragment).addToBackStack(null).commit();
                break;
            case R.id.btn_konfirmasi:
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, transKonfirmasFragment).addToBackStack(null).commit();
                break;
            case R.id.btn_topup:
                getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                getSupportFragmentManager().beginTransaction().add(android.R.id.content, transTopupFragment).addToBackStack(null).commit();
                break;
        }
    }
}
