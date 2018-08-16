package co.id.wargamandiri.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.id.wargamandiri.fragments.KelolaBannerFragment;
import co.id.wargamandiri.fragments.KelolaDataBankFragment;
import co.id.wargamandiri.fragments.KelolaDataMemberFragment;
import co.id.wargamandiri.fragments.KelolaDataPengumumanFragment;
import co.id.wargamandiri.fragments.KelolaDataTokoFragment;
import co.id.wargamandiri.fragments.KelolaDataUserFragment;

public class VpAdapterKelolaToko extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"TOKO", "USER", "BANK", "BANNER", "PENGUMUMAN", "MEMBER"};

    public VpAdapterKelolaToko(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                KelolaDataTokoFragment kelolaDataTokoFragment = new KelolaDataTokoFragment();
                return kelolaDataTokoFragment;
            case 1:
                KelolaDataUserFragment kelolaDataUserFragment = new KelolaDataUserFragment();
                return kelolaDataUserFragment;
            case 2:
                KelolaDataBankFragment kelolaDataBankFragment = new KelolaDataBankFragment();
                return kelolaDataBankFragment;
            case 3:
                KelolaBannerFragment kelolaBannerFragment = new KelolaBannerFragment();
                return kelolaBannerFragment;
            case 4:
                KelolaDataPengumumanFragment kelolaDataPengumumanFragment = new KelolaDataPengumumanFragment();
                return kelolaDataPengumumanFragment;
            case 5:
                KelolaDataMemberFragment kelolaDataMemberFragment = new KelolaDataMemberFragment();
                return kelolaDataMemberFragment;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }
}
