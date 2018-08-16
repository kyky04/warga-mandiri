package co.id.wargamandiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.VpAdapter;
import co.id.wargamandiri.fragments.KelolaBuatApkFragment;
import co.id.wargamandiri.fragments.KelolaDataTokoFragment;
import co.id.wargamandiri.fragments.KelolaDataUserFragment;
import co.id.wargamandiri.fragments.MenuFragment;
import co.id.wargamandiri.utils.Session;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    VpAdapter vpAdapter;
    Session session;

    String[] menu = new String[]{"PT. DAIKIN AIRCONDITIONING\nINDONESIA TRAINING CENTER", "MY TRAINING", "E-CERTIFICATE", "QUESIONER", "MORE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        session = new Session(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(menu[0]);
        vpAdapter = new VpAdapter(getSupportFragmentManager());

        generateTabLayout();


//        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(5);
        inflateDivider();
    }

    private void generateTabLayout() {
        MenuFragment menuFragment = new MenuFragment();
        KelolaDataTokoFragment kelolaDataTokoFragment = new KelolaDataTokoFragment();
        KelolaBuatApkFragment kelolaBuatApkFragment = new KelolaBuatApkFragment();


        vpAdapter.addFragment(kelolaDataTokoFragment, menu[1]);
        vpAdapter.addFragment(menuFragment, menu[0]);
        vpAdapter.addFragment(kelolaBuatApkFragment, menu[2]);

        viewPager.setAdapter(vpAdapter);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_toko).setText("Toko"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_kelola).setText("Kelola"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_buat_apk).setText("Buat APK"));

        viewPager.setCurrentItem(0);

//        tabLayout.getTabAt(0).select();
    }


    void inflateDivider() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(this).inflate(R.layout.tab_layout, tabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.img_tab);
            tabTextView.setText(tab.getText());
            imageView.setImageDrawable(tab.getIcon());
            tab.setCustomView(relativeLayout);
//            tab.select();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_id_logout:
//                session.logoutUser();
//                finish();
//                break;
//            case R.id.menu_id_profil:
//                startActivity(new Intent(this,EditProfileActivity.class));
//                break;
//            case R.id.menu_id_change_languange:
//                dialogEditBahasa();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//

}
