package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.KategoriResponse;
import co.id.wargamandiri.models.UploadPengumumaResponse;
import co.id.wargamandiri.models.UploadProdukResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.KATEGORI;
import static co.id.wargamandiri.data.Constans.NEWS;
import static co.id.wargamandiri.data.Constans.PRODUK;
import static co.id.wargamandiri.data.Constans.WEB_URL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogPengumumanFragment extends DialogFragment {
    public static final String ARG_DATA = "DATA";
    private static final String TAG = "DialogProdukFragment";


    Unbinder unbinder;
    public CallbackResult callbackResult;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.et_judul)
    EditText etJudul;
    @BindView(R.id.et_isi)
    EditText etIsi;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    private ProgressDialog progressDialog;
    private String path;
    File file;
    DataItemPengumuman item;

    Session session;

    boolean edit = false;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogPengumumanFragment() {
        // Required empty public constructor
    }

    public static DialogPengumumanFragment newInstance(DataItemPengumuman item) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, item);
        DialogPengumumanFragment fragment = new DialogPengumumanFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pengumuman, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());

        isEdit();


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
                uploadData();
                break;
        }
    }
    

    private boolean isEdit() {
        if (getArguments() != null) {
            edit = true;
            item = (DataItemPengumuman) getArguments().getSerializable(ARG_DATA);
            etJudul.setText(item.getJudul());
            etIsi.setText(item.getIsi());

            return true;
        }
        return false;
    }

    public interface CallbackResult {
        void sendResult();
    }


    private void uploadData() {
        openDialog();
        ANRequest.PostRequestBuilder upload;
        if (edit) {
            upload = new ANRequest.PostRequestBuilder(NEWS + "/{id}");
            upload.addPathParameter("id", String.valueOf(item.getId()));
            upload.addBodyParameter("_method", "PUT");
        } else {
            upload = new ANRequest.PostRequestBuilder(NEWS);
        }
        upload.addBodyParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .addBodyParameter("created_by", String.valueOf(session.getUser().getData().getId()))
                .addBodyParameter("judul", etJudul.getText().toString())
                .addBodyParameter("isi", etIsi.getText().toString())
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        progressDialog.setProgress((int) bytesUploaded);
                    }
                })
                .getAsObject(UploadPengumumaResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadPengumumaResponse) {
//                            callbackResult.sendResult();
                            dismiss();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR Response", anError.getErrorBody());
                        closeDialog();
                    }
                });


    }

    void openDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Harap Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }



}
