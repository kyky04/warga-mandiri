package co.id.wargamandiri.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.activities.DataMasterActivity;
import co.id.wargamandiri.activities.KelolaActivity;
import co.id.wargamandiri.activities.TransaksiActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    @BindView(R.id.img_transaksi)
    ImageView imgTransaksi;
    @BindView(R.id.btn_transaksi)
    RelativeLayout btnTransaksi;
    @BindView(R.id.img_data_master)
    ImageView imgDataMaster;
    @BindView(R.id.btn_data_master)
    RelativeLayout btnDataMaster;
    @BindView(R.id.img_kelola_toko)
    ImageView imgKelolaToko;
    @BindView(R.id.btn_kelola_toko)
    RelativeLayout btnKelolaToko;
    @BindView(R.id.img_notifikasi)
    ImageView imgNotifikasi;
    @BindView(R.id.btn_notifikasi)
    RelativeLayout btnNotifikasi;
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
    Unbinder unbinder;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                FragmentManager fragmentManagerMember = getFragmentManager();
                KelolaDataMemberFragment newFragmentmember = new KelolaDataMemberFragment();
                FragmentTransaction transactionMember = fragmentManagerMember.beginTransaction();
                transactionMember.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionMember.add(android.R.id.content, newFragmentmember).addToBackStack(null).commit();

                break;
            case R.id.btn_notifikasi:
//                goToActivity(TransaksiActivity.class);
                Toast.makeText(getActivity(), "Under Construction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_user:
                FragmentManager fragmentManager = getFragmentManager();
                KelolaDataUserFragment newFragment = new KelolaDataUserFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

                break;
            case R.id.btn_bank:
                FragmentManager fragmentManagerBank = getFragmentManager();
                KelolaDataBankFragment newFragmentBank = new KelolaDataBankFragment();
                FragmentTransaction transactionBank = fragmentManagerBank.beginTransaction();
                transactionBank.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionBank.add(android.R.id.content, newFragmentBank).addToBackStack(null).commit();


                break;
            case R.id.btn_banner:
                FragmentManager fragmentManagerBanner = getFragmentManager();
                KelolaBannerFragment newFragmentBanner = new KelolaBannerFragment();
                FragmentTransaction transactionBanner = fragmentManagerBanner.beginTransaction();
                transactionBanner.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionBanner.add(android.R.id.content, newFragmentBanner).addToBackStack(null).commit();


                break;
            case R.id.btn_pengumuman:
                FragmentManager fragmentManagerPengumuman = getFragmentManager();
                KelolaDataPengumumanFragment newFragmentPengumuman = new KelolaDataPengumumanFragment();
                FragmentTransaction transactionPengumuman = fragmentManagerPengumuman.beginTransaction();
                transactionPengumuman.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transactionPengumuman.add(android.R.id.content, newFragmentPengumuman).addToBackStack(null).commit();

                break;
        }
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }
}
