package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.models.UploadBannerResponse;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static co.id.wargamandiri.services.FastConstans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogKategoriFragment extends DialogFragment {


    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.image_bg)
    ImageView imageBg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.brief)
    Button brief;
    @BindView(R.id.lyt_parent)
    LinearLayout lytParent;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.et_deskripsi)
    EditText etDeskripsi;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    Unbinder unbinder;

    public CallbackResult callbackResult;
    @BindView(R.id.et_judul)
    EditText etJudul;
    private ProgressDialog progressDialog;
    private String path;
    private File file;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogKategoriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_kategori, container, false);
        unbinder = ButterKnife.bind(this, view);
        etJudul.addTextChangedListener(new TextWatcher() {
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
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_close, R.id.bt_save, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                dismiss();
                break;
            case R.id.bt_save:
                uploadBanner();
                break;
            case R.id.btn_upload:
                if (checkPermission()) {
                    addImage();
                } else {
                    requestPermission();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            file = new File(image.getPath());
            path = image.getPath();
            Glide.with(getActivity()).load(file).into(imageBg);
        }
    }

    public interface CallbackResult {
        void sendResult(Object obj);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
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

    private void uploadBanner() {
        openDialog();
        AndroidNetworking.upload(WEB_URL + "api/backend/master/kategori")
                .addMultipartFile("gambar", file)
                .addMultipartParameter("id_toko", String.valueOf(1))
                .addMultipartParameter("nama", etJudul.getText().toString())
                .addMultipartParameter("keterangan", etDeskripsi.getText().toString())
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        progressDialog.setProgress((int) bytesUploaded);
                    }
                })
                .getAsObject(UploadBannerResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadBannerResponse) {
                            callbackResult.sendResult(((UploadBannerResponse) response).getData());
                            dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR Response", anError.getErrorDetail());
                        closeDialog();
                    }
                });
    }

    void openDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Harap tunggu", "Prosess .  . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }

    public DataItemKategori getKategooriToUpload() {
        DataItemKategori dataItemBanner = new DataItemKategori();
        dataItemBanner.setIdToko(String.valueOf(1));
        dataItemBanner.setGambar(path);
        dataItemBanner.setKeterangan(etDeskripsi.getText().toString());
        return dataItemBanner;
    }
}
