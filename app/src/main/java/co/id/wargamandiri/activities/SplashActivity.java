package co.id.wargamandiri.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import co.id.wargamandiri.R;
import co.id.wargamandiri.utils.Session;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {
    private static final int TIME = 3000;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new Session(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLoggedIn()) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    // Closing all the Activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    // Closing all the Activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    startActivity(i);
                    finish();
                }
            }
        },TIME);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
