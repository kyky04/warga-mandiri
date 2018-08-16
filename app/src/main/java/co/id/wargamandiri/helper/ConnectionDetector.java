package co.id.wargamandiri.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        NetworkInfo activeNetwork = ((ConnectivityManager) this._context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork == null) {
            return false;
        }
        if (activeNetwork.getType() == 1 || activeNetwork.getType() == 0) {
            return true;
        }
        return false;
    }
}
