package co.id.wargamandiri.models;

/**
 * @brief Model class for holding the details of keystore file.
 * <p/>
 * Created by abhishek on 11/06/15 at 2:19 PM.
 */
public class KeyStoreDetails {

    private final String assetsPath;
    private final String password;
    private final String alias;
    private final String aliasPassword;

    public KeyStoreDetails(String password, String alias, String aliasPassword) {
        this.assetsPath = "mandirikey.jks";
        this.password = password;
        this.alias = alias;
        this.aliasPassword = aliasPassword;
    }

    public String getAssetsPath() {
        return assetsPath;
    }

    public String getPassword() {
        return password;
    }

    public String getAlias() {
        return alias;
    }

    public String getAliasPassword() {
        return aliasPassword;
    }
}
