package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import android.widget.LinearLayout;
import android.widget.TextView;

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
import co.id.wargamandiri.models.DataItemBank;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.models.UploadBankResponse;
import co.id.wargamandiri.models.UploadProdukResponse;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.BANK;
import static co.id.wargamandiri.data.Constans.PRODUK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogBankFragment extends DialogFragment {
    public static final String ARG_DATA = "DATA";
    private static final String TAG = "DialogProdukFragment";


    Unbinder unbinder;
    public CallbackResult callbackResult;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.no_rek)
    TextView noRek;
    @BindView(R.id.atas_nama)
    TextView atasNama;
    @BindView(R.id.nama_bank)
    TextView namaBank;
    @BindView(R.id.lyt_parent)
    LinearLayout lytParent;
    @BindView(R.id.more)
    ImageButton more;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_no_rek)
    EditText etNoRek;
    @BindView(R.id.et_atas_nama)
    EditText etAtasNama;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    private ProgressDialog progressDialog;
    private String path;
    File file;
    DataItemBank item;

    Session session;
    boolean edit = false;

    List<DataItemKategori> itemKategoriList = new ArrayList<>();
    List<String> stringArrayList = new ArrayList<>();
    private int pos, id_kategori = 0;
    ;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogBankFragment() {
        // Required empty public constructor
    }

    public static DialogBankFragment newInstance(DataItemBank item) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, item);
        DialogBankFragment fragment = new DialogBankFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bank, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());

        isEdit();


        etNoRek.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                noRek.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etAtasNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                atasNama.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                namaBank.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
            item = (DataItemBank) getArguments().getSerializable(ARG_DATA);
            etNoRek.setText(item.getNoRekening() + "");
            etAtasNama.setText(item.getAtasNama() + "");
            etNama.setText(item.getNamaBank() + "");

            noRek.setText(item.getNoRekening() + "");
            atasNama.setText(item.getAtasNama() + "");
            namaBank.setText(item.getNamaBank() + "");

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
            upload = new ANRequest.PostRequestBuilder(BANK + "/{id}");
            upload.addPathParameter("id", String.valueOf(item.getId()));
            upload.addBodyParameter("_method", "PUT");
        } else {
            upload = new ANRequest.PostRequestBuilder(BANK);
        }
        upload
                .addBodyParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .addBodyParameter("created_by", String.valueOf(session.getUser().getData().getId()))
                .addBodyParameter("nama_bank", etNama.getText().toString())
                .addBodyParameter("nomor_rekening", etNoRek.getText().toString().trim())
                .addBodyParameter("atas_nama", etAtasNama.getText().toString())
//                .addMultipartParameter("gambar[]", String.valueOf(file))
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        progressDialog.setProgress((int) bytesUploaded);
                    }
                })
                .getAsObject(UploadBankResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadBankResponse) {
                            callbackResult.sendResult();
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
        progressDialog = ProgressDialog.show(getActivity(), "Harap tunggu", "Prosess .  . .");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void closeDialog() {
        progressDialog.dismiss();
    }


}
