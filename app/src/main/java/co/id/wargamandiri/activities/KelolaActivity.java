package co.id.wargamandiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.VpAdapterKelolaToko;

public class KelolaActivity extends AppCompatActivity {

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
        getSupportActionBar().setTitle("");
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
            case R.id.buat_apk:
                startActivity(new Intent(this,BikinActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kelola, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
