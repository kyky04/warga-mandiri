package co.id.wargamandiri.services;


import java.util.List;

import co.id.wargamandiri.models.AccessTokenRequest;
import co.id.wargamandiri.models.AccessTokenResponse;
import co.id.wargamandiri.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NindyaApi {
    @POST("oauth/token")
    Call<AccessTokenResponse> getAccessToken(@Body AccessTokenRequest accessTokenRequest);

    @FormUrlEncoded
    @POST("api/login")
    Call<LoginResponse> login(@Field("user_name") String user_name, @Field("password") String password);

    @GET("/api/user")
    Call<LoginResponse> getUser(@Header("Authorization") String Authorization);

}
