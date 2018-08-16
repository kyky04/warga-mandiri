package co.id.wargamandiri.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static co.id.wargamandiri.services.FastConstans.WEB_URL;

public class ApiClient {
    private static Retrofit mRetrofit;

    public static Retrofit newInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();



        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            private SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            {
                // Indicate the time zone of the input date
                dtf.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
            }
            @Override
            public Date deserialize(JsonElement json, Type type,
                                    JsonDeserializationContext deserializationContext) throws JsonParseException {
                try {
                    // Get the json element as a String and parse it to get a Date
                    return dtf.parse(json.getAsString());
                } catch (ParseException e) {
                    // Throw a JsonParseException in case of a parsing error
                    throw new JsonParseException(e);
                }
            }
        });

        Gson gson1 = builder.create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(WEB_URL)
                .addConverterFactory(GsonConverterFactory.create(gson1))
                .client(client)
                .build();

        return mRetrofit;
    }



}
