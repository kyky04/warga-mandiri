package co.id.wargamandiri.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.id.wargamandiri.R;
import co.id.wargamandiri.models.TokoResponse;

import static co.id.wargamandiri.services.FastConstans.WEB_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelolaDataTokoFragment extends Fragment {


    @BindView(R.id.et_nama_toko)
    EditText etNamaToko;
    @BindView(R.id.et_phone_toko)
    EditText etPhoneToko;
    @BindView(R.id.et_deskripsi_toko)
    EditText etDeskripsiToko;
    @BindView(R.id.et_alamat_toko)
    EditText etAlamatToko;
    @BindView(R.id.et_fb)
    EditText etFb;
    @BindView(R.id.et_wa)
    EditText etWa;
    @BindView(R.id.et_line)
    EditText etLine;
    @BindView(R.id.et_bbm)
    EditText etBbm;
    Unbinder unbinder;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    public KelolaDataTokoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toko, container, false);
        unbinder = ButterKnife.bind(this, view);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getToko(1);
            }
        });
        getToko(1);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getToko(int id) {
        refresh.setRefreshing(true);
        AndroidNetworking.get(WEB_URL + "api/master/toko/{id_toko}")
                .addPathParameter("id_toko", String.valueOf(id))
                .build()
                .getAsObject(TokoResponse.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        refresh.setRefreshing(false);
                        try {
                            if(response instanceof TokoResponse){
                                etNamaToko.setText(((TokoResponse) response).getNama());
                                etAlamatToko.setText(((TokoResponse) response).getAlamat());
                                etDeskripsiToko.setText(((TokoResponse) response).getDeskripsi());
                                etPhoneToko.setText(((TokoResponse) response).getPhone());
                                etWa.setText(((TokoResponse) response).getWa());
                                etBbm.setText(((TokoResponse) response).getBbm());
                                etLine.setText(((TokoResponse) response).getLine());
                                etFb.setText(((TokoResponse) response).getFb());
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Data Gagal Diambil", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refresh.setRefreshing(false);
                    }
                });
    }
}
