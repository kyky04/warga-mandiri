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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.models.DataItemProduk;
import co.id.wargamandiri.models.KategoriResponse;
import co.id.wargamandiri.models.UploadKategoriResponse;
import co.id.wargamandiri.models.UploadProdukResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;
import co.id.wargamandiri.utils.Tools;

import static co.id.wargamandiri.data.Constans.KATEGORI;
import static co.id.wargamandiri.data.Constans.PRODUK;
import static co.id.wargamandiri.data.Constans.WEB_URL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogProdukFragment extends DialogFragment {
    public static final String ARG_DATA = "DATA";
    private static final String TAG = "DialogProdukFragment";


    Unbinder unbinder;
    public CallbackResult callbackResult;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.image_bg)
    ImageView imageBg;
    @BindView(R.id.more)
    ImageButton more;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lay_item_click)
    LinearLayout layItemClick;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.et_judul)
    EditText etJudul;
    @BindView(R.id.et_deskripsi)
    EditText etDeskripsi;
    @BindView(R.id.et_harga)
    EditText etHarga;
    @BindView(R.id.et_stok)
    EditText etStok;
    @BindView(R.id.et_kategori)
    EditText etKategori;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    private ProgressDialog progressDialog;
    private String path;
    File file;
    DataItemProduk item;

    Session session;
    boolean edit = false;


    List<DataItemKategori> itemKategoriList = new ArrayList<>();
    List<String> stringArrayList = new ArrayList<>();
    private int pos, id_kategori = 0;
    ;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogProdukFragment() {
        // Required empty public constructor
    }

    public static DialogProdukFragment newInstance(DataItemProduk item) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, item);
        DialogProdukFragment fragment = new DialogProdukFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_produk, container, false);
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

    @OnClick({R.id.bt_close, R.id.bt_save, R.id.btn_upload, R.id.et_kategori})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                dismiss();
                break;
            case R.id.et_kategori:
                if (stringArrayList.size() == 0) {
                    getKategori();
                } else {
                    kategoriDialog();
                }
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
            Glide.with(getActivity()).load(file).into(imageBg);
        }
    }

    private boolean isEdit() {
        if (getArguments() != null) {
            edit = true;
            item = (DataItemProduk) getArguments().getSerializable(ARG_DATA);
            etJudul.setText(item.getNama());
            etDeskripsi.setText(item.getDeskripsi());
            etStok.setText(item.getStok() + "");
            etHarga.setText(item.getHarga() + "");
            etKategori.setText(item.getKategori().getNama());

            Glide.with(getActivity()).load(WEB_URL_STORAGE + item.getGambarUtama()).into(imageBg);
            return true;
        }
        return false;
    }

    public interface CallbackResult {
        void sendResult();
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
            upload = new ANRequest.MultiPartBuilder(PRODUK + "/{id}");
            upload.addPathParameter("id", String.valueOf(item.getId()));
            upload.addMultipartParameter("_method", "PUT");
        } else {
            upload = new ANRequest.MultiPartBuilder(PRODUK);
        }
        upload.addMultipartFile("gambar_utama", file)
                .addMultipartParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .addMultipartParameter("created_by", String.valueOf(session.getUser().getData().getId()))
                .addMultipartParameter("nama", etJudul.getText().toString())
                .addMultipartParameter("deskripsi", etDeskripsi.getText().toString())
                .addMultipartParameter("harga", etHarga.getText().toString())
                .addMultipartParameter("stok", etStok.getText().toString())
                .addMultipartParameter("status", "1")
                .addMultipartParameter("id_kategori", String.valueOf(id_kategori))
                .addMultipartFile("gambar[]", file)
//                .addMultipartParameter("gambar[]", String.valueOf(file))
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

    private void getKategori() {
        openDialog();
        AndroidNetworking.get(KATEGORI)
                .addQueryParameter("id_toko", String.valueOf(session.getUser().getData().getIdToko()))
                .build()
                .getAsObject(KategoriResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof KategoriResponse) {
                            itemKategoriList.addAll(((KategoriResponse) response).getData());
                            for (int i = 0; i < itemKategoriList.size(); i++) {
                                stringArrayList.add(itemKategoriList.get(i).getNama());
                            }
                            kategoriDialog();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ERROR RESPONSE", anError.getErrorDetail());
                        closeDialog();

                    }
                });
    }

    public void kategoriDialog() {

        String[] items = new String[stringArrayList.size()];

        items = stringArrayList.toArray(items);

        final String[] finalItems = items;
        CommonUtil.dialogArray(getActivity(), items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                etKategori.setText(finalItems[which]);
                pos = which;
                id_kategori = itemKategoriList.get(which).getId();
            }
        });

    }

}
