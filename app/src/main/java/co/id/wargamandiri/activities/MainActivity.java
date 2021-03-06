package co.id.wargamandiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.wargamandiri.R;
import co.id.wargamandiri.fragments.DialogPengumumanFragment;
import co.id.wargamandiri.fragments.KelolaBannerFragment;
import co.id.wargamandiri.fragments.KelolaDataBankFragment;
import co.id.wargamandiri.fragments.KelolaDataMemberFragment;
import co.id.wargamandiri.fragments.KelolaDataPengumumanFragment;
import co.id.wargamandiri.fragments.KelolaDataUserFragment;

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
    @BindView(R.id.img_transaksi)
    ImageView imgTransaksi;
    @BindView(R.id.img_data_master)
    ImageView imgDataMaster;
    @BindView(R.id.img_kelola_toko)
    ImageView imgKelolaToko;
    @BindView(R.id.img_notifikasi)
    ImageView imgNotifikasi;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.btn_user)
    RelativeLayout btnUser;
    @BindView(R.id.img_bank)
    ImageView imgBank;
    @BindView(R.id.btn_bank)
    RelativeLayout btnBank;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.btn_banner)
    RelativeLayout btnBanner;
    @BindView(R.id.img_pengumuman)
    ImageView imgPengumuman;
    @BindView(R.id.btn_pengumuman)
    RelativeLayout btnPengumuman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_transaksi, R.id.btn_data_master, R.id.btn_kelola_toko, R.id.btn_notifikasi, R.id.btn_user, R.id.btn_bank, R.id.btn_banner, R.id.btn_pengumuman})
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
//                FragmentManager fragmentManagerMember = getSupportFragmentManager();
//                KelolaDataMemberFragment newFragmentmember = new KelolaDataMemberFragment();
//                FragmentTransaction transactionMember = fragmentManagerMember.beginTransaction();
//                transactionMember.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transactionMember.add(android.R.id.content, newFragmentmember).addToBackStack(null).commit();

                break;
            case R.id.btn_notifikasi:
                goToActivity(TransaksiActivity.class);
                break;
            case R.id.btn_user:
                FragmentManager fragmentManager = getSupportFragmentManager();
                KelolaDataUserFragment newFragment = new KelolaDataUserFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

                break;
            case R.id.btn_bank:
                FragmentManager fragmentManagerBank = getSupportFragmentManager();
                KelolaDataBankFragment newFragmentBank = new KelolaDataBankFragment();
                FragmentTransaction transactionBank = fragmentManagerBank.beginTransaction();
                transactionBank.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionBank.add(android.R.id.content, newFragmentBank).addToBackStack(null).commit();
                break;

            case R.id.btn_banner:
                FragmentManager fragmentManagerBanner = getSupportFragmentManager();
                KelolaBannerFragment newFragmentBanner = new KelolaBannerFragment();
                FragmentTransaction transactionBanner = fragmentManagerBanner.beginTransaction();
                transactionBanner.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionBanner.add(android.R.id.content, newFragmentBanner).addToBackStack(null).commit();
                break;
            case R.id.btn_pengumuman:
                FragmentManager fragmentManagerPengumuman = getSupportFragmentManager();
                KelolaBannerFragment newFragmentPengumuman = new KelolaBannerFragment();
                FragmentTransaction transactionPengumuman = fragmentManagerPengumuman.beginTransaction();
                transactionPengumuman.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionPengumuman.add(android.R.id.content, newFragmentPengumuman).addToBackStack(null).commit();

                break;

        }
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

}
