package co.id.wargamandiri.fragments;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.helper.AXmlEditor;
import co.id.wargamandiri.helper.Config;
import co.id.wargamandiri.helper.FileUtil;
import co.id.wargamandiri.helper.StringUtils;
import co.id.wargamandiri.models.LoginResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import yuku.ambilwarna.AmbilWarnaDialog;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class BikinFragment extends Fragment {
    private static final String TAG = "BikinFragment";
    private static final int REQUEST_WRITE_STORAGE = 112;
    String gambaricon = null;
    boolean lanjut = true;
    String namaaplikasi = "aplikasimandiri";
    int num = 0;
    OutputStream out;
    String pathfile;
    String status = "sukses";
    File tempFile = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/tmpzip.tmp");
    String username = "aplikasimandiri";
    String versi = "1";
    int warna = -30584;

    @BindView(R.id.image_bg)
    ImageView imageBg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.et_nama_aplikasi)
    EditText etNamaAplikasi;
    @BindView(R.id.textView17)
    TextView textView17;
    @BindView(R.id.textView18)
    TextView textView18;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.scrollView4)
    ScrollView scrollView4;
    @BindView(R.id.layoutFailed)
    LinearLayout layoutFailed;
    @BindView(R.id.buttonStart)
    Button buttonStart;
    @BindView(R.id.layoutWelcome)
    LinearLayout layoutWelcome;
    @BindView(R.id.layoutTest)
    LinearLayout layoutTest;
    @BindView(R.id.img_click)
    ImageView imgClick;

    private ProgressDialog progressDialog;
    private String path;
    private File fileImage;
    private Unbinder unbinder;


    Session session;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class startBikin extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            bikin();
            return null;
        }

        protected void onPostExecute(String result) {
            layoutTest.setVisibility(View.GONE);
            if (status.equals("gagal")) {
                layoutFailed.setVisibility(View.VISIBLE);
                return;
            }
            CommonUtil.showToast(getActivity(), "Aplikasi berhasil di buat pada folder aplikasimandiri");
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bikin, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());

        layoutTest.setVisibility(View.GONE);
        layoutWelcome.setVisibility(View.GONE);
        layoutFailed.setVisibility(View.GONE);

        layoutWelcome.setVisibility(View.VISIBLE);
        if (!(ContextCompat.checkSelfPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, REQUEST_WRITE_STORAGE);
        }
        buttonStart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                layoutWelcome.setVisibility(View.GONE);
                layoutTest.setVisibility(View.VISIBLE);
                new startBikin().execute(new String[0]);
            }
        });

        etNamaAplikasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnUpload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    addImage();
                } else {
                    requestPermission();
                }
            }
        });

        imgClick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), Color.BLACK, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        Log.d(TAG, "onOk: "+color);
                        imgClick.setColorFilter(color);
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            fileImage = new File(image.getPath());
            path = image.getPath();
            Glide.with(this).load(fileImage).into(imageBg);
            gambaricon = path;

        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE /*112*/:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(getActivity(), "Mohon izinkan aplikasi untuk mengakses penyimpanan data.", Toast.LENGTH_LONG).show();
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        Dexter.withActivity(getActivity())
                .withPermission(WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void addImage() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.// image selection title
                .single()// Toolbar 'up' arrow color
                .start();
    }

    public void addFilesToExistingZip(File zipFile, File[] files) throws IOException {
        tempFile(zipFile);
        byte[] buf = new byte[1024];
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        for (ZipEntry entry = zin.getNextEntry(); entry != null; entry = zin.getNextEntry()) {
            int len;
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                out.putNextEntry(new ZipEntry(name));
                while (true) {
                    len = zin.read(buf);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
            }
        }
        zin.close();
        for (int i = 0; i < files.length; i++) {
            int len;
            InputStream in = new FileInputStream(files[i]);
            out.putNextEntry(new ZipEntry("assets/" + files[i].getName()));
            while (true) {
                len = in.read(buf);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
        tempFile.delete();
    }

    public void addManifest(File zipFile, File[] files) throws IOException {
        int len;
        tempFile(zipFile);
        byte[] buf = new byte[1024];
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        for (ZipEntry entry = zin.getNextEntry(); entry != null; entry = zin.getNextEntry()) {
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                out.putNextEntry(new ZipEntry(name));
                while (true) {
                    len = zin.read(buf);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
            }
        }
        zin.close();
        for (int i = 0; i < files.length; i++) {
            InputStream in = new FileInputStream(files[i]);
            out.putNextEntry(new ZipEntry(files[i].getName()));
            while (true) {
                len = in.read(buf);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
        tempFile.delete();
    }


    public void addDataLogin(File zipFile, File[] files) throws IOException {
        tempFile(zipFile);
        byte[] buf = new byte[1024];
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        for (ZipEntry entry = zin.getNextEntry(); entry != null; entry = zin.getNextEntry()) {
            int len;
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                out.putNextEntry(new ZipEntry(name));
                while (true) {
                    len = zin.read(buf);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
            }
        }
        zin.close();
        for (int i = 0; i < files.length; i++) {
            int len;
            InputStream in = new FileInputStream(files[i]);
            out.putNextEntry(new ZipEntry("assets/" + files[i].getName()));
            while (true) {
                len = in.read(buf);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
        tempFile.delete();
    }

    public void addIcon(File zipFile, File[] files) throws IOException {
        tempFile(zipFile);
        byte[] buf = new byte[1024];
        ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
        for (ZipEntry entry = zin.getNextEntry(); entry != null; entry = zin.getNextEntry()) {
            int len;
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : files) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
            }
            if (notInFiles) {
                out.putNextEntry(new ZipEntry(name));
                while (true) {
                    len = zin.read(buf);
                    if (len <= 0) {
                        break;
                    }
                    out.write(buf, 0, len);
                }
            }
        }
        zin.close();
        for (int i = 0; i < files.length; i++) {
            int len;
            InputStream in = new FileInputStream(files[i]);
            out.putNextEntry(new ZipEntry("res/drawable/" + files[i].getName()));
            while (true) {
                len = in.read(buf);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
        tempFile.delete();
    }

    public void tempFile(File src) throws IOException {
        File dst = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/tmpzip.tmp");
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    public void copyKey(File dst) throws IOException {
        InputStream in = getActivity().getAssets().open("keystore.jks");
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    public void copy(File dst) throws IOException {
        InputStream in = getActivity().getAssets().open("warga.apk");
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }


    public void copyIcon(File src, File dst) throws IOException {
        InputStream in;
        if (gambaricon == null) {
            in = getActivity().getAssets().open("ico.png");
        } else {
            in = new FileInputStream(src);
        }
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    public void editManifest() {
        AXmlEditor edit = new AXmlEditor();
        try {
            copyManifest(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/AndroidManifestt.xml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        List<String> list = new ArrayList();
        byte[] data = null;
        try {
            data = FileUtil.readFile(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp", "AndroidManifest.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            edit.read(list, data);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
//        String tulisan = StringUtils.join(list, "\n").replaceFirst(Config.TAG_TOKO_NAMA, username).replace("namatoko.situsbelanja.com", username + ".situsbelanja.com").replace("namatoko.olshp.com", username + ".olshp.com").replace("Nama Toko", namaaplikasi).replaceFirst("com.bikinaplikasi.onlineshop", "com.bikinaplikasi." + username).replaceFirst("com.bikinaplikasi.onlineshop.permission.C2D_MESSAGE", "com.bikinaplikasi." + username + ".permission.C2D_MESSAGE").replaceFirst("com.bikinaplikasi.onlineshop.google_measurement_service", "com.bikinaplikasi." + username + ".google_measurement_service").replace("android:debuggable=\"true\"", "android:debuggable=\"false\"").replace("4.24", versi + ".0");
        String tulisan = StringUtils.join(list, "\n").replaceFirst(Config.TAG_TOKO_NAMA, username).replace("Nama Toko", namaaplikasi).replace("112", versi + ".0");
//        int int_value = Integer.parseInt(versi);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File dst = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp", "AndroidManifest.xml");
        try {
            out = new FileOutputStream(dst);
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
        }
//        try {
//            edit.write(tulisan.toString(), baos);
//        } catch (IOException e22) {
//            e22.printStackTrace();
//        }
        try {
            out = new FileOutputStream(dst);
        } catch (FileNotFoundException e32) {
            e32.printStackTrace();
        }
//        try {
//            out.write(baos.toByteArray());
//        } catch (IOException e222) {
//            e222.printStackTrace();
//        }
    }

    public void copyManifest(File dst) throws IOException {
        InputStream in = getActivity().getAssets().open("AndroidManifest.xml");
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    public void bikinUN(String un) {
        try {
            FileWriter writer = new FileWriter(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp", "un"));
            writer.append(un);
            writer.flush();
            writer.close();
            lanjut = true;
        } catch (IOException e) {
            lanjut = false;
            e.printStackTrace();
        }
    }

    public void bikinCO(int co) {
        String color = "#" + Integer.toHexString(co);
        try {
            FileWriter writer = new FileWriter(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp", "co"));
            writer.append(color);
            writer.flush();
            writer.close();
            lanjut = true;
        } catch (IOException e) {
            lanjut = false;
            e.printStackTrace();
        }
    }

    public void bikinFolder() {
        File folder1 = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri");
        File folder2 = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp");
        boolean success = true;
        if (!folder2.exists()) {
            success = folder2.mkdirs();
        }
        if (success) {
            lanjut = true;
        } else {
            lanjut = false;
        }
    }

    public void bikin() {
        File file;
        File fileapk;
        File fileco;
        File filesrc = null;
        bikinFolder();
        num = R.styleable.AppCompatTheme_switchStyle;
        if (lanjut) {
            num = 222;
            bikinUN(username);
            if (lanjut) {
                bikinCO(warna);
                if (lanjut) {
                    num = 333;
                    try {
                        createDataLogin();
                        copy(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/warga.apk"));
                        copyManifest(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/AndroidManifestt.xml"));
                        editManifest();
                        if (gambaricon == null) {
                            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/un");
                        } else {
                            file = new File(gambaricon);
                        }
                        try {
                            copyIcon(file, new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/ico.png"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        lanjut = true;
                        fileapk = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/warga.apk");
                        fileco = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/co");
                        File[] filemanifest = new File[]{new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/AndroidManifestt.xml")};
                        File[] fileicon = new File[]{new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/ico.png")};
                        File[] filelogin = new File[]{new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/data_login.json")};
                        try {
                            addFilesToExistingZip(fileapk, new File[]{new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/un")});
                            addFilesToExistingZip(fileapk, new File[]{new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/co")});
                            addManifest(fileapk, filemanifest);
                            addIcon(fileapk, fileicon);
                            addDataLogin(fileapk, filelogin);
                            new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/warga.apk").renameTo(new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/NamaToko.apk"));
                            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/NamaToko.apk");
                            File dst = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/NamaToko.apk");
                            try {
                                String generatedApkPath = Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/NamaToko.apk";
                                String signedApkPath = Environment.getExternalStorageDirectory() + "/aplikasimandiri/" + username + ".apk";
                                ZipSigner zipSigner = new ZipSigner();
                                String keystore = Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/keystore.jks";
                                file = new File(keystore);
                                char[] keyAliasPassword = "password".toCharArray();
                                copyKey(file);
                                CustomKeySigner.signZip(zipSigner, keystore, "password".toCharArray(), "mandiri", keyAliasPassword, "SHA1withRSA", generatedApkPath, signedApkPath);
                                pathfile = signedApkPath;
                                lanjut = false;
                            } catch (Throwable th) {
                                status = "gagal";
                                lanjut = false;
                                Log.d("ERRORTH", String.valueOf(th.getMessage()));
                            }
                        } catch (IOException e2) {
                            status = "gagal";
                            lanjut = false;
                            Log.d("ERRORE2", String.valueOf(e2.getMessage()) + " " + e2.getStackTrace());
                        }
                    } catch (IOException e3) {
                        status = "gagal";
                        e3.printStackTrace();
                        lanjut = false;
                        Log.d("ERRORE3", e3.getMessage());
                    }
                }
            }
        }
        if (!lanjut) {
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp");
            fileapk = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/NamaToko.apk");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/un");
            fileco = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/co");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/AndroidManifestt.xml");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/keystore.jks");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/ico.png");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/1.tmp");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/2.tmp");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/3.tmp");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/4.tmp");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/5.tmp");
            file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/tmp.tmp");
            fileapk.renameTo(file);
            file.renameTo(file);
            file.renameTo(file);
            file.renameTo(file);
            file.renameTo(file);
            fileco.renameTo(file);
        }
    }


    private void createDataLogin() {
        String myJson = null;
        try {
            myJson = CommonUtil.inputStreamToString(getActivity().getAssets().open("data_login.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        LoginResponse dataUser = session.getUser();
        myJson = gson.toJson(dataUser);

        writeToFile(myJson);
    }

    public static String getPath(Context context, Uri uri) {
        boolean isKitKat;
        if (VERSION.SDK_INT >= 19) {
            isKitKat = true;
        } else {
            isKitKat = false;
        }
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            String[] split = new String[0];
            if (isExternalStorageDocument(uri)) {
                split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                return null;
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (!isMediaDocument(uri)) {
                return null;
            } else {
                String type = DocumentsContract.getDocumentId(uri).split(":")[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                return getDataColumn(context, contentUri, "_id=?", new String[]{split[1]});
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("fileImage".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {
            return null;
        }
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void writeToFile(String data) {

        final File file = new File(Environment.getExternalStorageDirectory() + "/aplikasimandiri/tmp/data_login.json");
        Log.d("TAG", "writeToFile: " + file.getName());
        // Save your stream, don't forget to flush() it before closing it.

        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
