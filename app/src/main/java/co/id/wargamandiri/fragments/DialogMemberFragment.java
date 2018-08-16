package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.UserResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogMemberFragment extends DialogFragment {


    Unbinder unbinder;

    public CallbackResult callbackResult;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private ProgressDialog progressDialog;
    private String path;
    private File file;
    private int id_user;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogMemberFragment() {
        // Required empty public constructor
    }

    public static DialogMemberFragment newInstance(UserResponse dataItemPengumuman) {
        DialogMemberFragment myFragment = new DialogMemberFragment();

        Bundle args = new Bundle();
        args.putInt("id_user", dataItemPengumuman.getId());
        args.putString("nama", dataItemPengumuman.getNama());
        args.putString("email", dataItemPengumuman.getEmail());
        args.putString("phone", dataItemPengumuman.getNoHp());
        args.putString("alamat", dataItemPengumuman.getAlamat());
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_member, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            etUsername.setText(getArguments().getString("nama"));
            etEmail.setText(getArguments().getString("email"));
            etPhone.setText(getArguments().getString("phone"));
            etAlamat.setText(getArguments().getString("alamat"));
            id_user = getArguments().getInt("id_user");
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
                dismiss();
//                if (getArguments() != null) {
//                    editPengumuman();
//                } else {
//                    uploadPengumuman();
//                }
                break;
        }
    }

//    private void uploadPengumuman() {
//        openDialog();
//        AndroidNetworking.post(OauthConstans.AUTHENTICATION_SERVER_URL + "api/master/pengumuman")
//                .addBodyParameter(getPengumumanToUpload())
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

//    private void editPengumuman() {
//        openDialog();
//        AndroidNetworking.put(OauthConstans.AUTHENTICATION_SERVER_URL + "api/master/pengumuman/{id_pengumuman}")
//                .addPathParameter("id_pengumuman", String.valueOf(id_user))
//                .addBodyParameter(getPengumumanToUpload())
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

//    public DataItemPengumuman getPengumumanToUpload() {
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
