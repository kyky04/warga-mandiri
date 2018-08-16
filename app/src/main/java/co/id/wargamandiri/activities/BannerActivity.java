package co.id.wargamandiri.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.VpAdapterKelolaToko;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_kelola_toko)
    TabLayout tabKelolaToko;
    @BindView(R.id.vp_kelola_toko)
    ViewPager vpKelolaToko;

    VpAdapterKelolaToko adapterKelolaToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapterKelolaToko = new VpAdapterKelolaToko(getSupportFragmentManager());
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
