package co.id.wargamandiri.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class FormValidation {
    Context context;

    public FormValidation(Context c) {
        this.context = c;
    }

    public static final boolean isValidPassword(CharSequence target) {
        if (target.length() < 5) {
            return false;
        }
        return true;
    }

    public static final boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static final boolean isSamePassword(CharSequence password1, CharSequence password2) {
        if (password1.equals(password2)) {
            return true;
        }
        return false;
    }

    public static final boolean isValidName(CharSequence name) {
        if (name.length() > 0) {
            return true;
        }
        return false;
    }

    public static final boolean isValidId(CharSequence id) {
        if (id.length() <= 0) {
            return false;
        }
        boolean isDigit;
        char c = id.charAt(0);
        if (c < '0' || c > '9') {
            isDigit = false;
        } else {
            isDigit = true;
        }
        if (isDigit) {
            return false;
        }
        if (id.length() <= 15) {
            return true;
        }
        return false;
    }

    public static final boolean isValidNumber(CharSequence number) {
        if (number.equals("0")) {
            return false;
        }
        return true;
    }

    public static final boolean isValidPhoneNumber(CharSequence number) {
        if (number.length() > 9) {
            return true;
        }
        return false;
    }

    public void showKeyboard() {
        ((InputMethodManager) this.context.getSystemService("input_method")).toggleSoftInput(2, 1);
    }

    public void hideKeyboard(View v) {
        ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
