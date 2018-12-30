package co.id.wargamandiri.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OneSignal;

import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.VpAdapter;
import co.id.wargamandiri.fragments.BikinFragment;
import co.id.wargamandiri.fragments.KelolaBuatApkFragment;
import co.id.wargamandiri.fragments.KelolaDataTokoFragment;
import co.id.wargamandiri.fragments.KelolaDataUserFragment;
import co.id.wargamandiri.fragments.MenuFragment;
import co.id.wargamandiri.models.LoginResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.LOGIN;
import static co.id.wargamandiri.data.Constans.TOKEN;

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
        FirebaseApp.initializeApp(this);


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
//        inflateDivider();

        token();
    }

    private void generateTabLayout() {
        MenuFragment menuFragment = new MenuFragment();
        KelolaDataTokoFragment kelolaDataTokoFragment = new KelolaDataTokoFragment();
        BikinFragment kelolaBuatApkFragment = new BikinFragment();

        vpAdapter.addFragment(kelolaDataTokoFragment, menu[1]);
        vpAdapter.addFragment(menuFragment, menu[0]);
//        vpAdapter.addFragment(kelolaBuatApkFragment, menu[2]);

        viewPager.setAdapter(vpAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Toko"));
        tabLayout.addTab(tabLayout.newTab().setText("Kelola"));
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_buat_apk).setText("Buat APK"));

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

    private void tokenize(String userId) {
//        String token = FirebaseInstanceId.getInstance().getToken();
        ANRequest.PostRequestBuilder post = new ANRequest.PostRequestBuilder(TOKEN);
        post.addBodyParameter("email", session.getUser().getData().getEmail());
        post.addBodyParameter("android_token", userId);
        post.build().getAsObject(LoginResponse.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {
                if (response instanceof LoginResponse) {
                    if (((LoginResponse) response).isStatus()) {
                        Log.d("TOKEN", "onResponse: "+"Token di kirim");
                    } else {
                        Log.d("TOKEN", "onResponse: "+"Token gagal di kirim");
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                CommonUtil.showToast(MenuActivity.this, "Kesalahan Server");
            }
        });
    }

    private void token(){
        Log.d("debug", "token: ");
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                if (registrationId != null) {
                    Log.d("debug", "registrationId:" + registrationId);
                }
                tokenize(userId);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_id_logout:
                session.logoutUser();
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
