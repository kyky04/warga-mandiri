package co.id.wargamandiri.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import co.id.wargamandiri.R;

public class CommonUtil {

    static Gson gson;

    static {
        if (gson == null)
            gson = new GsonBuilder()
                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                    .setExclusionStrategies()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd");
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void fireNotif(final Activity context, String title, String message) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("" + title);
        builder.setMessage("" + message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static Snackbar fireNotifSnackbar(final Activity context, ViewGroup containerLayout, String message, boolean infinite) {
        final Snackbar snackbar = Snackbar.make(containerLayout, message, infinite ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG);
        snackbar.setAction("Tutup", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(context.getResources().getColor(R.color.colorPrimary)).show();
        return snackbar;
    }

    public static String getCurrentDate(String fmt) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        SimpleDateFormat format = new SimpleDateFormat(fmt);
//        Date date = new Date();
        return format.format(cal.getTime());
    }

    // Storage Permissions
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_LOCATION_PERMISSION = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean verifyPermission(Context activity, String permissionCode) {
        if (ContextCompat.checkSelfPermission(activity, permissionCode) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static Snackbar fireNotifSnackbar(Activity activity, ViewGroup layout, String message) {
        return fireNotifSnackbar(activity, layout, message, true);
    }

    private static class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }

        class StringAdapter extends TypeAdapter<String> {
            public String read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return "";
                }
                return reader.nextString();
            }

            public void write(JsonWriter writer, String value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            }
        }
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static boolean validateEmptyEntries(ArrayList<View> fields) {
        for (View view : fields) {
            if (view instanceof EditText) {
//                Log.d("VALIDATE", "is EMPTY? " + ((EditText) view).getText());
                if (((EditText) view).getText().toString().trim().equals("")) return true;
            }
            if (view instanceof TextView) {
//                Log.d("VALIDATE", "is EMPTY? " + ((TextView) view).getText());
                if (((TextView) view).getText().toString().trim().equals("")) return true;
            }
//            if (view instanceof Spinner) {
////                Log.d("VALIDATE", "is EMPTY? " + ((Spinner) view).getSelectedItem());
//                if (((Spinner) view).getSelectedItem().toString().trim().equals("")) return true;
//            }
        }
        return false;
    }

    public static void fireAndroidNotif(Context context, int nId, int iconRes, String title, String body, String groupId) {
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),
                iconRes);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(iconRes)
                .setContentTitle(title)
                .setGroup(groupId)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentText(body);
        Log.d("NOTIF", "send new notif-" + nId);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(nId, mBuilder.build());
    }

    public static void displayFragment(Context context, @IdRes int res, Fragment fragment, String tag) {
        if (fragment != null) {
            FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            ft.replace(res, fragment, tag);
            ft.commit();
        }

    }

    public static ProgressDialog progressDialog;


    public static void showProgress(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void hideDialog() {
        progressDialog.dismiss();
    }

    public static void showSnackMessage(View v, String message, int duration) {
        Snackbar snackbar = Snackbar.make(v, message, duration);
        snackbar.show();
    }

    public static void initToolbar(Context context, android.support.v7.widget.Toolbar toolbar, String titleToolbar, boolean showHome) {
        ((AppCompatActivity) context).setSupportActionBar(toolbar);
        if (showHome) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
        }
        ((AppCompatActivity) context).getSupportActionBar().setTitle(titleToolbar);
    }

    public static void exit_input(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Batal menginput data ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.finish();
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void exit(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Keluar dari aplikasi ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.finish();
            }
        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void shownoItem(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tidak ditemukan data dalam menu ini");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                context.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showWarning(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Terjadi kesalahan");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void failure(final Activity context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void succesInput(final Activity context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setPositiveButton("Ok", onClickListener);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dialogSucces(final Activity context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

//    public static void logout(final Activity context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Anda yakin ingin logout ?");
//        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(context, LoginActivity.class);
//                // Closing all the Activities
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                // Add new Flag to start new Aktifitas
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                // Staring Login Aktifitas
//                context.startActivity(intent);
//                context.finish();
//            }
//        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    public static String formatDate(String string) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        SimpleDateFormat formatToView = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = new Date();
        try {
            convertedDate = formatter.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tglKlaim = formatToView.format(convertedDate);

        return tglKlaim;
    }

    public static String formatDateIndonesia(String date) {
        SimpleDateFormat formatIncoming = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat formatOutgoing = new SimpleDateFormat("MMMM dd, yyyy");
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        System.out.println(tz.getDisplayName(false, TimeZone.SHORT, Locale.ENGLISH)); // WIB

        formatOutgoing.setTimeZone(tz);
        String s = null;
        try {
            s = formatOutgoing.format(formatIncoming.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return s;
    }


    public static void dialogTanggal(Context context, final EditText etDate) {
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        TimeZone tz = TimeZone.getTimeZone("Asia/Jakarta");
        dateFormat.setTimeZone(tz);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static void dialogTanggalTextview(Context context, final TextView etDate) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String currentDateTimeString = dateFormat.format(new Date());
        etDate.setText(currentDateTimeString);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public static void dialogWaktu(Context context, final EditText etWaktu) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                etWaktu.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.show();
    }


    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public static void dialogArray(Context context, final String[] items, DialogInterface.OnClickListener clickListener) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context)
//                .setTitle("Organization Type")
                .setItems(items, clickListener);

        Dialog dialog = builder.create();
        dialog.show();
    }
}
