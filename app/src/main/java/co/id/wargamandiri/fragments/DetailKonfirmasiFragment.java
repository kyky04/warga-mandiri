package co.id.wargamandiri.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.adapter.DetailOrderAdapter;
import co.id.wargamandiri.models.DataItemKategori;
import co.id.wargamandiri.models.transaksi.konfirmasi.DataItemKonfirmasi;
import co.id.wargamandiri.utils.Session;
import co.id.wargamandiri.views.EqualSpacingItemDecoration;

import static co.id.wargamandiri.data.Constans.WEB_URL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailKonfirmasiFragment extends DialogFragment {
    public static final String ARG_DATA = "DATA";
    private static final String TAG = "DialogProdukFragment";


    Unbinder unbinder;
    public CallbackResult callbackResult;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.et_nama_user)
    EditText etNamaUser;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_tanggal)
    EditText etTanggal;
    @BindView(R.id.et_bank_tujuan)
    EditText etBankTujuan;
    @BindView(R.id.et_rek_tujuan)
    EditText etRekTujuan;
    @BindView(R.id.et_bank_pengirim)
    EditText etBankPengirim;
    @BindView(R.id.et_rek_pengirim)
    EditText etRekPengirim;
    @BindView(R.id.et_nama_pengirim)
    EditText etNamaPengirim;
    @BindView(R.id.lampiran)
    ImageView lampiran;
    @BindView(R.id.card)
    CardView card;


    private ProgressDialog progressDialog;
    private String path;
    File file;
    DataItemKonfirmasi item;

    Session session;
    DetailOrderAdapter produkAdapter;

    List<DataItemKategori> itemKategoriList = new ArrayList<>();
    List<String> stringArrayList = new ArrayList<>();
    private int pos, id_kategori = 0;
    ;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public DetailKonfirmasiFragment() {
        // Required empty public constructor
    }

    public static DetailKonfirmasiFragment newInstance(DataItemKonfirmasi item) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA, item);
        DetailKonfirmasiFragment fragment = new DetailKonfirmasiFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_konfirmasi, container, false);
        unbinder = ButterKnife.bind(this, view);

        session = new Session(getActivity());

        if (getArguments() != null) {
            item = (DataItemKonfirmasi) getArguments().getSerializable(ARG_DATA);
            etNamaUser.setText(item.getUser().getName());
            etEmail.setText(item.getUser().getEmail());
            etTanggal.setText(item.getTanggal());
            etBankTujuan.setText(item.getBankTujuan());
            etRekTujuan.setText(item.getRekeningTujuan());
            etBankPengirim.setText(item.getBankPengirim());
            etRekPengirim.setText(item.getRekeningPengirim());
            etNamaPengirim.setText(item.getNamaPengirim());

            if (item.getStatus() == 0) {
                tvStatus.setText("Pending");
            } else if (item.getStatus() == 1) {
                tvStatus.setText("Approved");
            } else if (item.getStatus() == 2) {
                tvStatus.setText("Rejected");
            }

            if (item.getGambar() != null) {
                Glide.with(getActivity()).load(WEB_URL_STORAGE + item.getGambar()).into(lampiran);
            }

        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface CallbackResult {
        void sendResult();
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
