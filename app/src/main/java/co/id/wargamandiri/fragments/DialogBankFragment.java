package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.DataItemBank;
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.UploadBannerResponse;
import co.id.wargamandiri.services.OauthConstans;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogBankFragment extends DialogFragment {


    Unbinder unbinder;

    public CallbackResult callbackResult;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_no_rek)
    EditText etNoRek;
    @BindView(R.id.et_atas_nama)
    EditText etAtasNama;
    private ProgressDialog progressDialog;
    private String path;
    private File file;
    private int id_pengumuman;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogBankFragment() {
        // Required empty public constructor
    }

    public static DialogBankFragment newInstance(DataItemPengumuman dataItemPengumuman) {
        DialogBankFragment myFragment = new DialogBankFragment();

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
        View view = inflater.inflate(R.layout.dialog_bank, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
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
                    editPengumuman();
                } else {
                    uploadPengumuman();
                }
                break;
        }
    }

    private void uploadPengumuman() {
        openDialog();
        AndroidNetworking.post(OauthConstans.AUTHENTICATION_SERVER_URL + "api/master/bank")
                .addBodyParameter(getPengumumanToUpload())
                .build()
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

    private void editPengumuman() {
        openDialog();
        AndroidNetworking.put(OauthConstans.AUTHENTICATION_SERVER_URL + "api/master/pengumuman/{id_pengumuman}")
                .addPathParameter("id_pengumuman", String.valueOf(id_pengumuman))
                .addBodyParameter(getPengumumanToUpload())
                .build()
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

    public DataItemBank getPengumumanToUpload() {
        DataItemBank dataItemBanner = new DataItemBank();
        dataItemBanner.setIdToko(1);
        dataItemBanner.setAtasNama(etAtasNama.getText().toString());
        dataItemBanner.setNamaBank(etNama.getText().toString());
        dataItemBanner.setNoRekening(etNoRek.getText().toString());
        return dataItemBanner;
    }

    public interface CallbackResult {
        void sendResult(Object obj);
    }
}
