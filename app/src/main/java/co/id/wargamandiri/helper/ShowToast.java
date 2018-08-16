package co.id.wargamandiri.helper;

import android.content.Context;
import android.widget.Toast;

public class ShowToast {
    Context context;

    public ShowToast(Context context) {
        this.context = context;
    }

    public void ToastError() {
        Toast.makeText(this.context, "Tidak dapat terhubung ke server. Coba lagi nanti.", 1).show();
    }

    public void Toast(String message) {
        Toast.makeText(this.context, message, 1).show();
    }
}
