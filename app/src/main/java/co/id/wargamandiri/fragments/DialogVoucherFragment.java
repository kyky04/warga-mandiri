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
import co.id.wargamandiri.models.DataItemPengumuman;
import co.id.wargamandiri.models.DataItemVoucher;
import co.id.wargamandiri.models.UploadBannerResponse;
import co.id.wargamandiri.models.UploadVoucherResponse;
import co.id.wargamandiri.utils.CommonUtil;
import co.id.wargamandiri.utils.Session;

import static co.id.wargamandiri.data.Constans.VOUCHER;
import static co.id.wargamandiri.data.Constans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogVoucherFragment extends DialogFragment {


    Unbinder unbinder;

    public CallbackResult callbackResult;
    @BindView(R.id.bt_close)
    ImageButton btClose;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.et_kode)
    EditText etKode;
    @BindView(R.id.et_tanggal_awal)
    EditText etTanggalAwal;
    @BindView(R.id.et_tanggal_akhir)
    EditText etTanggalAkhir;
    @BindView(R.id.et_jumlah_potongan)
    EditText etJumlahPotongan;
    @BindView(R.id.et_minimal_belanja)
    EditText etMinimalBelanja;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;


    private ProgressDialog progressDialog;
    private String path;
    private File file;
    private int id_pengumuman;
    private Session session;

    boolean edit = false;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DialogVoucherFragment() {
        // Required empty public constructor
    }

    public static DialogVoucherFragment newInstance(DataItemPengumuman dataItemPengumuman) {
        DialogVoucherFragment myFragment = new DialogVoucherFragment();

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
        View view = inflater.inflate(R.layout.dialog_voucher, container, false);
        unbinder = ButterKnife.bind(this, view);
        session = new Session(getActivity());
        if (getArguments() != null) {
//            etJudul.setText(getArguments().getString("judul"));
            id_pengumuman = getArguments().getInt("id_pengumuman");
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_close, R.id.bt_save,R.id.et_tanggal_awal, R.id.et_tanggal_akhir})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                dismiss();
                break;
            case R.id.bt_save:
                if (getArguments() != null) {
                    edit();
                } else {
                    upload();
                }
                break;
            case R.id.et_tanggal_awal:
                CommonUtil.dialogTanggal(getActivity(),etTanggalAwal);
                break;
            case R.id.et_tanggal_akhir:
                CommonUtil.dialogTanggal(getActivity(),etTanggalAkhir);
                break;
        }
    }

    private void upload() {
        openDialog();
        AndroidNetworking.post(VOUCHER)
                .addBodyParameter(getVoucherUpload())
                .build()
                .getAsObject(UploadVoucherResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadVoucherResponse) {
                            callbackResult.sendResult();
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

    private void edit() {
        openDialog();
        AndroidNetworking.put(WEB_URL + "api/master/pengumuman/{id_pengumuman}")
                .addPathParameter("id_pengumuman", String.valueOf(id_pengumuman))
                .build()
                .getAsObject(UploadBannerResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        closeDialog();
                        if (response instanceof UploadBannerResponse) {
                            callbackResult.sendResult();
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

    public DataItemVoucher getVoucherUpload() {
        DataItemVoucher voucher = new DataItemVoucher();
        voucher.setIdToko(session.getUser().getData().getIdToko());
        voucher.setKodeVoucher(etKode.getText().toString());
        voucher.setTanggal_awal(etTanggalAwal.getText().toString());
        voucher.setTanggal_akhir(etTanggalAkhir.getText().toString());
        voucher.setMinimalBelanja(Integer.parseInt(etMinimalBelanja.getText().toString()));
        voucher.setJumlahPotongan(Integer.parseInt(etJumlahPotongan.getText().toString()));
        voucher.setPersen(null);
        return voucher;
    }

    public interface CallbackResult {
        void sendResult();
    }
}
