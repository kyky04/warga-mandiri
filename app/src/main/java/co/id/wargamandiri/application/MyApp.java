package co.id.wargamandiri.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;
import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;

import java.util.concurrent.TimeUnit;

import co.id.wargamandiri.utils.TypefaceUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/nunito_sans_regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.MINUTES)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();


//        AndroidNetworking.initialize(getApplicationContext(), client);
        AndroidNetworking.enableLogging();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
