package co.id.wargamandiri.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.VpAdapterDataMaster;
import co.id.wargamandiri.adapter.VpAdapterKelolaToko;

public class DataMasterActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_kelola_toko)
    TabLayout tabKelolaToko;
    @BindView(R.id.vp_kelola_toko)
    ViewPager vpKelolaToko;

    VpAdapterDataMaster adapterKelolaToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_master);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapterKelolaToko = new VpAdapterDataMaster(getSupportFragmentManager());
        vpKelolaToko.setAdapter(adapterKelolaToko);

        tabKelolaToko.setupWithViewPager(vpKelolaToko);
        vpKelolaToko.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabKelolaToko));
        tabKelolaToko.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpKelolaToko.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
