package co.id.wargamandiri.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gokhan on 4/8/15.
 */
public class AccessTokenResponse extends BaseResponse {
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("refresh_token")
    @Expose
    private String refresh_token;
    @SerializedName("expires_in")
    @Expose
    private int expires_in;
    @SerializedName("token_type")
    @Expose
    private String token_type;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
            return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public String getToken_type() {
        return token_type;
    }


    @Override
    public String toString() {

        if (super.getError() != null && super.getError_description() != null) {
            return super.getError() + super.getError_description();
        }
        return "AccessToken{" +
                "accessToken='" + access_token + '\'' +
                ", tokenType='" + token_type + '\'' +
                ", expiresIn=" + expires_in +
                ", refreshToken='" + refresh_token + '\'' +
                '}';
    }
}
