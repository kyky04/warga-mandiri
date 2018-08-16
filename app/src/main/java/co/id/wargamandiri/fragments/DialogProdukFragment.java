package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.AdapterUploadGambar;
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.UploadGambar;
import co.id.wargamandiri.models.UploadProdukResponse;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static co.id.wargamandiri.services.FastConstans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogProdukFragment extends DialogFragment {


    Unbinder unbinder;

    public CallbackResult callbackResult;


    NestedScrollView nestedScrollView;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_harga)
    EditText etHarga;
    @BindView(R.id.et_harga_diskon)
    EditText etHargaDiskon;
    @BindView(R.id.et_deskripsi)
    EditText etDeskripsi;
    @BindView(R.id.et_stok)
    EditText etStok;
    @BindView(R.id.rv_upload)
    RecyclerView rvUpload;
    @BindView(R.id.add_image)
    Button addImage;

    private ProgressDialog progressDialog;
    private String path;
    private File file;
    private int id_pengumuman;

    AdapterUploadGambar adapterUploadGambar;
    private List<String> filePathsFoto = new ArrayList<>();

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogProdukFragment() {
        // Required empty public constructor
    }

    public static DialogProdukFragment newInstance(DataItemPengumuman dataItemPengumuman) {
        DialogProdukFragment myFragment = new DialogProdukFragment();

        Bundle args = new Bundle();
        args.putInt("id_pengumuman", dataItemPengumuman.getId());
        args.putString("isi", dataItemPengumuman.getIsi());
        args.putString("judul", dataItemPengumuman.getJudul());
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_produk, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapterUploadGambar = new AdapterUploadGambar(getActivity());

        adapterUploadGambar.setOnItemClickListener(new AdapterUploadGambar.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                adapterUploadGambar.removeItem(position);
                filePathsFoto.remove(position);

            }

            @Override
            public void onItemClick(int position) {

            }
        });
        rvUpload.setAdapter(adapterUploadGambar);
        rvUpload.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        if (getArguments() != null) {
//           etIsi.setText(getArguments().getString("isi"));
            id_pengumuman = getArguments().getInt("id_pengumuman");
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_close, R.id.bt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                dismiss();
                break;
            case R.id.bt_save:
                if (getArguments() != null) {
//                    editPengumuman();
                } else {
                    uploadPengumuman();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            file = new File(image.getPath());
            filePathsFoto.add(image.getPath());
            if (file.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                adapterUploadGambar.add(new UploadGambar(myBitmap, image.getPath()));
            }
        }
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

    private void uploadPengumuman() {
        ANRequest.MultiPartBuilder builder = new ANRequest.MultiPartBuilder(WEB_URL + "api/backend/produk");
        for (int i = 0; i < filePathsFoto.size(); i++) {
            File file = new File(filePathsFoto.get(i));
            builder.addMultipartFile("gambar[]", file);
        }
        openDialog();
        builder.addMultipartParameter("id_toko", String.valueOf(1))
                .addMultipartParameter("nama", etNama.getText().toString())
                .addMultipartParameter("harga", etHarga.getText().toString())
                .addMultipartParameter("harga_diskon", etHargaDiskon.getText().toString())
                .addMultipartParameter("deskripsi", etDeskripsi.getText().toString())
                .addMultipartParameter("stok", etStok.getText().toString())
                .addMultipartParameter("kategori[]", String.valueOf(1))
                .addMultipartParameter("status", String.valueOf(1))
                .addMultipartFile("gambar_utama", file)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        progressDialog.setProgress((int) bytesUploaded);
                    }
                })
                .getAsObject(UploadProdukResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadProdukResponse) {
                            callbackResult.sendResult(((UploadProdukResponse) response).getData());
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

//    private void editPengumuman() {
//        openDialog();
//        AndroidNetworking.put(OauthConstans.AUTHENTICATION_SERVER_URL + "api/master/produk")
//                .addPathParameter("id_pengumuman", String.valueOf(id_pengumuman))
//                .addBodyParameter(getVoucherUpload())
//                .build()
//                .getAsObject(UploadBannerResponse.class, new ParsedRequestListener() {
//                    @Override
//                    public void onResponse(Object response) {
//                        closeDialog();
//                        if (response instanceof UploadBannerResponse) {
//                            callbackResult.sendResult(((UploadBannerResponse) response).getData());
//                            dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.d("ERROR Response", anError.getErrorDetail());
//                        closeDialog();
//                    }
//                });
//    }

    void openDialog() {
        progressDialog = ProgressDialog.show(getActivity(), "Harap tunggu", "Prosess .  . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }

    @OnClick(R.id.add_image)
    public void onViewClicked() {
        if(checkPermission()){
            addImage();
        }else {
            requestPermission();
        }
    }

//    public DataItemPengumuman getVoucherUpload() {
//        DataItemPengumuman dataItemBanner = new DataItemPengumuman();
//        dataItemBanner.setIdToko(String.valueOf(1));
//        dataItemBanner.setIsi(etIsi.getText().toString());
//        dataItemBanner.setJudul(etJudul.getText().toString());
//        return dataItemBanner;
//    }

    public interface CallbackResult {
        void sendResult(Object obj);
    }
}
