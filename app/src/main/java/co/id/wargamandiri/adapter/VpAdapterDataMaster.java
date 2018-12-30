package co.id.wargamandiri.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.id.wargamandiri.fragments.KelolaBannerFragment;
import co.id.wargamandiri.fragments.KelolaDataBankFragment;
import co.id.wargamandiri.fragments.KelolaDataPengumumanFragment;
import co.id.wargamandiri.fragments.KelolaDataTokoFragment;
import co.id.wargamandiri.fragments.KelolaDataUserFragment;
import co.id.wargamandiri.fragments.MasterKetegoriFragment;
import co.id.wargamandiri.fragments.MasterProdukFragment;
import co.id.wargamandiri.fragments.MasterVoucherFragment;

public class VpAdapterDataMaster extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"Kategori", "Voucher"};

    public VpAdapterDataMaster(FragmentManager fm) {
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
                MasterKetegoriFragment masterKetegoriFragment = new MasterKetegoriFragment();
                return masterKetegoriFragment;
//            case 1:
//                MasterProdukFragment masterProdukFragment = new MasterProdukFragment();
//                return masterProdukFragment;
            case 1:
                MasterVoucherFragment masterVoucherFragment = new MasterVoucherFragment();
                return masterVoucherFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
