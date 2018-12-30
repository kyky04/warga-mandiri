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
import com.androidnetworking.common.ANRequest;
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
import co.id.wargamandiri.models.UploadKategoriResponse;
import co.id.wargamandiri.utils.Session;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static co.id.wargamandiri.data.Constans.KATEGORI;
import static co.id.wargamandiri.data.Constans.WEB_URL;
import static co.id.wargamandiri.data.Constans.WEB_URL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogKategoriFragment extends DialogFragment {
    public static final String ARG_DATA = "DATA";

    Unbinder unbinder;

    public CallbackResult callbackResult;
    @BindView(R.id.et_judul)
    EditText etJudul;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.image_bg)
    ImageView imgBg;
    @BindView(R.id.more)
    ImageButton more;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lay_item_click)
    LinearLayout layItemClick;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.et_deskripsi)
    EditText etDeskripsi;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    private ProgressDialog progressDialog;
    private String path;
    File file;
    DataItemKategori item;

    Session session;
    boolean edit = false;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogKategoriFragment() {
        // Required empty public constructor
    }

    public static DialogKategoriFragment newInstance(DataItemKategori item) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, item);
        DialogKategoriFragment fragment = new DialogKategoriFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_kategori, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());

        isEdit();

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
                uploadData();
                break;
            case R.id.btn_upload:
                addImage();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            file = new File(image.getPath());
            path = image.getPath();
            Glide.with(getActivity()).load(file).into(imgBg);
        }
    }

    private boolean isEdit() {
        if (getArguments() != null) {
            edit = true;
            item = (DataItemKategori) getArguments().getSerializable(ARG_DATA);
            etJudul.setText(item.getNama());
            etDeskripsi.setText(item.getKeterangan());
            Glide.with(getActivity()).load(WEB_URL_STORAGE + item.getGambar()).into(imgBg);
            return true;
        }
        return false;
    }

    public interface CallbackResult {
        void sendResult(Object obj);
    }

    private void addImage() {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.// image selection title
                .single()// Toolbar 'up' arrow color
                .start();
    }

    private void uploadData() {
        openDialog();

        ANRequest.MultiPartBuilder upload;
        if (edit) {
            upload = new ANRequest.MultiPartBuilder(KATEGORI + "/{id}");
            upload.addPathParameter("id", String.valueOf(item.getId()));
            upload.addMultipartParameter("_method", "PUT");
        } else {
            upload = new ANRequest.MultiPartBuilder(KATEGORI);
        }
        upload.addMultipartFile("gambar", file)
                .addMultipartParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .addMultipartParameter("created_by", String.valueOf(session.getUser().getData().getId()))
                .addMultipartParameter("nama", etJudul.getText().toString())
                .addMultipartParameter("keterangan", etDeskripsi.getText().toString())
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        progressDialog.setProgress((int) bytesUploaded);
                    }
                })
                .getAsObject(UploadKategoriResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadKategoriResponse) {
                            callbackResult.sendResult(((UploadKategoriResponse) response).getData());
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
